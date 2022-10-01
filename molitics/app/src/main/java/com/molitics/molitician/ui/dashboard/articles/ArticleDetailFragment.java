package com.molitics.molitician.ui.dashboard.articles;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.molitics.molitician.R;
import com.molitics.molitician.model.News;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 7/14/2017.
 */

public class ArticleDetailFragment extends Fragment {
    @BindView(R.id.expand_image)
    ImageView expand_image;
    @BindView(R.id.headline_text)
    TextView headline_text;
    @BindView(R.id.time_text)
    TextView time_text;
    @BindView(R.id.news_text)
    TextView news_text;
    @BindView(R.id.article_content_field)
    TextView article_content_field;
    @BindView(R.id.author_name_txt)
    TextView author_name_txt;

    public static Fragment getInstance(int article_id, String image, String heading, String detail, String time, String author_name) {
        ArticleDetailFragment mFragment = new ArticleDetailFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt("id", article_id);
        mBundle.putString("heading", heading);
        mBundle.putString("image", image);
        mBundle.putString("detail", detail);
        mBundle.putString("time", time);
        mBundle.putString("author_name", author_name);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View primary_layout = inflater.inflate(R.layout.adapter_article_detail, container, false);
        ButterKnife.bind(this, primary_layout);

        Bundle mBundle = getArguments();
        ////// assert mBundle != null;
        article_content_field.setVisibility(View.VISIBLE);
        int id = mBundle != null ? mBundle.getInt("id") : 0;
        checkNull(headline_text, mBundle != null ? mBundle.getString("heading") : null);
        checkNull(time_text, mBundle != null ? mBundle.getString("time") : null);
        String context_string = mBundle != null ? mBundle.getString("detail") : null;
        if (mBundle != null) {
            author_name_txt.setText(mBundle.getString("author_name"));
        }

        checkNull(article_content_field, context_string);
        Log.d("detail", mBundle.getString("detail"));
        if (!TextUtils.isEmpty(mBundle != null ? mBundle.getString("image") : null)) {
            Picasso.with(getContext()).load(mBundle.getString("image")).into(expand_image);
        }
        return primary_layout;
    }

    public void updateData(News mNews) {
        checkNull(headline_text, mNews.getHeading());
        checkNull(time_text, mNews.getTime());
        checkNull(article_content_field, mNews.getContent());
    }

    private void checkNull(TextView mView, String mTxt) {

        if (!TextUtils.isEmpty(mTxt)) {
            mView.setVisibility(View.VISIBLE);

            mView.setText(Html.fromHtml(mTxt));
        } else {
            mView.setVisibility(View.GONE);
        }
    }
}
