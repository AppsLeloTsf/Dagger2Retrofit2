package com.molitics.molitician.ui.dashboard.articles;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.articles.feedArticle.CreateArticleActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.molitics.molitician.util.Constant.Action.FROM_USER_ARTICLE;

/**
 * Created by Rahul on 7/14/2017.
 */

public class ArticleDetailActivity extends BasicAcivity implements ArticlePresenter.UserDetailResponse, BranchShareClass.DeepLinkCallBack {


    public static final int ARTICLE_EDIT = 123;
    @BindView(R.id.common_view_pager)
    ViewPager common_view_pager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_article)
    FloatingActionButton edit_article;


    List<News> all_news = new ArrayList<>();
    int current_position = 0;
    ArticleDetailPagerAdapter adapter;
    Integer FROM = -1;
    int is_active = 0;
    ArticleDeleteHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        ButterKnife.bind(this);

        Intent mIntent = getIntent();
        Bundle bundle = mIntent.getExtras();
        assert bundle != null;
        setToolbar();
        all_news = (List<News>) (bundle != null ? bundle.getSerializable(Constant.IntentKey.NEWS_LIST) : null);
        FROM = bundle.getInt(Constant.IntentKey.FROM, 0);
        handler = new ArticleDeleteHandler(this);

        if (FROM.equals(FROM_USER_ARTICLE)) {
            edit_article.show();
        } else {
            edit_article.hide();
        }
        int position = mIntent.getIntExtra(Constant.IntentKey.POSITION, 0);
        adapter = new ArticleDetailPagerAdapter(getSupportFragmentManager());

        common_view_pager.setAdapter(adapter);
        adapter.addArticle(all_news);
        current_position = position;
        common_view_pager.setCurrentItem(current_position);

        common_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                current_position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        showHeader(true, getString(R.string.article_detail));
        toolbar.setNavigationOnClickListener(v -> goBack());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem delete_option = menu.findItem(R.id.delete_menu);
        MenuItem share_menu = menu.findItem(R.id.share_menu);
        if (all_news.get(current_position).getIsActive() == 0) {
            share_menu.setVisible(false);
        } else {
            share_menu.setVisible(true);
        }

        if (FROM == FROM_USER_ARTICLE) {
            delete_option.setVisible(true);
        } else {
            delete_option.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share_menu) {
            shareNews(all_news.get(current_position).getId(), all_news.get(current_position).getHeading());
        } else if (item.getItemId() == R.id.delete_menu) {
            deleteArticle();
        }
        return true;
    }


    private void deleteArticle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.want_to_delete_article))
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    handler.deleteUserArticle(all_news.get(current_position).getId());

                    DialogClass.showDialog(ArticleDetailActivity.this);
                })
                .setNegativeButton("No", (dialog, id) -> {
                    //  Action for 'NO' Button
                    dialog.cancel();
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Article");
        alert.show();
    }

    private void shareNews(int newsId, String headline) {
        News newsDetailModel = all_news.get(current_position);
        ///article
        BranchShareClass.generateNewsShareLink(this, newsDetailModel.getHeading(),
                newsDetailModel.getShareLink());
    }


    @OnClick(R.id.edit_article)
    public void onEditArticleClick() {
        News mNews = all_news.get(current_position);

        Intent mIntent = new Intent(this, CreateArticleActivity.class);
        mIntent.putExtra(Constant.IntentKey.ARTICLE_ID, mNews.getId());
        mIntent.putExtra(Constant.IntentKey.ARTICLE_TITLE, mNews.getHeading());
        mIntent.putExtra(Constant.IntentKey.ARTICLE_CONTENT, mNews.getContent());
        mIntent.putExtra(Constant.IntentKey.ARTICLE_IMAGE, mNews.getImage());
        startActivityForResult(mIntent, ARTICLE_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ARTICLE_EDIT && data != null) {
                Bundle bundle = data.getExtras();
                News edited_news = null;
                if (bundle != null) {
                    edited_news = (News) bundle.getSerializable(Constant.IntentKey.NEWS_LIST);
                    adapter.updateArticle(current_position, edited_news);
                    common_view_pager.setCurrentItem(current_position);


                    Fragment current_fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.common_view_pager + ":" + current_position);
                    // based on the current position you can then cast the page to the correct
                    // class and call the method:
                    if (current_fragment != null) {
                        ((ArticleDetailFragment) current_fragment).updateData(edited_news);
                    }
                }
            }
        }
    }


    @Override
    public void onDeleteResponse(APIResponse apiResponse) {
        DialogClass.dismissDialog();
        finish();
    }

    @Override
    public void onDeleteError(ApiException apiException) {
        DialogClass.dismissDialog();
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String headLine = "";
        headLine = full_txt + "-" + "Molitics";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, headLine);
        shareIntent.putExtra(Intent.EXTRA_TITLE, "Molitics");
        String shareText = headLine + "\n" + url + "\n" + "via @MoliticsIndia";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, "Share link using"));
    }
}
