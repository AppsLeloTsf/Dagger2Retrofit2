package com.molitics.molitician.ui.dashboard.articles.feedArticle;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.termCondition.TermConditionActivity;
import com.molitics.molitician.util.CompressImage;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.Picasso;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by rahul on 07/04/18.
 */

public class CreateArticleActivity extends BasicAcivity implements AddArticlePresenter.AddArticleView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.news_heading)
    EditText news_heading;
    @BindView(R.id.news_image)
    ImageView news_image;
    @BindView(R.id.ll_add_image)
    LinearLayout ll_add_image;
    @BindView(R.id.remove_image)
    ImageView remove_image;
    @BindView(R.id.rl_image)
    RelativeLayout rl_image;
    @BindView(R.id.editor)
    RichEditor mEditor;

    @BindView(R.id.insert_bullets_dot)
    ImageView insert_bullets_dot;
    @BindView(R.id.align_right_dot)
    ImageView align_right_dot;
    @BindView(R.id.align_center_dot)
    ImageView align_center_dot;
    @BindView(R.id.align_left_dot)
    ImageView align_left_dot;
    @BindView(R.id.bg_color_dot)
    ImageView bg_color_dot;
    @BindView(R.id.txt_color_dot)
    ImageView txt_color_dot;
    @BindView(R.id.heading6_dot)
    ImageView heading6_dot;
    @BindView(R.id.heading5_dot)
    ImageView heading5_dot;
    @BindView(R.id.heading4_dot)
    ImageView heading4_dot;
    @BindView(R.id.heading3_dot)
    ImageView heading3_dot;
    @BindView(R.id.heading2_dot)
    ImageView heading2_dot;
    @BindView(R.id.heading1_dot)
    ImageView heading1_dot;
    @BindView(R.id.underline_dot)
    ImageView underline_dot;
    @BindView(R.id.strikethrough_dot)
    ImageView strikethrough_dot;
    @BindView(R.id.superscript_dot)
    ImageView superscript_dot;
    @BindView(R.id.subscript_dot)
    ImageView subscript_dot;
    @BindView(R.id.italic_dot)
    ImageView italic_dot;
    @BindView(R.id.bold_dot)
    ImageView bold_dot;

    private boolean is_insert_bullets_dot = false;
    private boolean is_align_right_dot = false;
    private boolean is_align_center_dot = false;
    private boolean is_align_left_dot = false;

    private boolean is_bg_color_dot = false;
    private boolean is_txt_color_dot = false;
    private boolean is_heading6_dot = false;
    private boolean is_heading5_dot = false;
    private boolean is_heading4_dot = false;
    private boolean is_heading3_dot = false;
    private boolean is_heading2_dot = false;
    private boolean is_heading1_dot = false;
    private boolean is_underline_dot = false;
    private boolean is_strikethrough_dot = false;
    private boolean is_superscript_dot = false;
    private boolean is_subscript_dot = false;
    private boolean is_italic_dot = false;
    private boolean is_bold_dot = false;


    private Uri news_path;

    private String content_txt = "";

    private AddArticleHandler addNewsHandler;
    //type 1-create, 2-edit
    private int news_id = 0;
    private boolean is_news_edit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        ButterKnife.bind(this);
        setActionBar();

        setFindEditorView();
        settextViewEditor();

        setPreviousData();

        addNewsHandler = new AddArticleHandler(this);
    }

    public void setActionBar() {
        toolbar.setTitle("Article");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }

        toolbar.setNavigationOnClickListener(v -> goBack());
    }

    // private void setupCropper(Uri imageUri) {
    private void setupCropper() {
     /*   CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(10, 7)
                .start(this);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.info) {
            Intent mIntent = new Intent(this, TermConditionActivity.class);
            mIntent.putExtra("tool_bar", "Instructions");
            mIntent.putExtra("url", "http://api.molitics.net/article_terms");
            startActivity(mIntent);
            return true;
        }
        return true;
    }

    @OnClick(R.id.remove_image)
    public void onImageRemoveClick() {
        is_news_edit = true;
        news_path = null;
        rl_image.setVisibility(View.GONE);
        ll_add_image.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.ll_add_image)
    public void onImageClick() {
        setupCropper();
    }

    @OnClick(R.id.post_txt)
    public void postNews() {
        if (news_path == null) {
            Util.toastShort(this, getString(R.string.article_image_m));
        } else if (news_heading.getText().toString().isEmpty()) {
            //toast enter heading
            Util.toastShort(this, getString(R.string.article_tilte_m));

        } else if (TextUtils.isEmpty(content_txt)) {
            //toast enter heading
            Util.toastShort(this, getString(R.string.article_description_m));

        } else {
            DialogClass.showDialog(this);
            submitProfileImage(news_path.toString());
        }
    }

    private void submitProfileImage(String image_path) {

        if (news_id != 0) {
            if (!is_news_edit) {
                addNewsHandler.editArticleNonImage(news_id, news_heading.getText().toString(), content_txt);
            } else {
                MultipartBody.Part filePart = null;
                if (!TextUtils.isEmpty(image_path)) {
                    File mFile = new File(CompressImage.compressImage(this, image_path));
                    filePart = MultipartBody.Part.createFormData("image",
                            mFile.getName(), RequestBody.create(MediaType.parse("image/*"), mFile));
                    addNewsHandler.editArticle(news_id, filePart, news_heading.getText().toString(), content_txt);
                }
            }
        } else {

            MultipartBody.Part filePart = null;
            if (!TextUtils.isEmpty(image_path)) {
                File mFile = new File(CompressImage.compressImage(this, image_path));
                filePart = MultipartBody.Part.createFormData("image",
                        mFile.getName(), RequestBody.create(MediaType.parse("image/*"), mFile));
                //   registerHandler.submitUserImage(filePart);
                addNewsHandler.addArticle(filePart, news_heading.getText().toString(), content_txt);
            }
        }
    }

    @Override
    public void onAddArticleResponse(APIResponse apiResponse) {
        DialogClass.dismissDialog();

        Intent mIntent = new Intent();
        ///// mIntent.putParcelableArrayListExtra(apiResponse.getData().getNew)

        setResult(RESULT_OK);
        finish();
        Toast.makeText(this, getString(R.string.sucess), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddArticleError(ApiException apiException) {
        DialogClass.dismissDialog();
        DialogClass.showAlert(this, apiException.getMessage());
    }

    @Override
    public void onEditArticleResponse(APIResponse apiResponse) {
        DialogClass.dismissDialog();

        Intent mIntent = new Intent();

        mIntent.putExtra(Constant.IntentKey.NEWS_LIST, apiResponse.getData().getArticle_detail());
        setResult(RESULT_OK, mIntent);
        finish();
    }

    @Override
    public void onEditArticleError(ApiException apiException) {
        DialogClass.dismissDialog();
        DialogClass.showAlert(this, apiException.getMessage());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                news_path = result.getUri();
                setStatusImage(news_path.getPath());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }*/
    }

    private void setStatusImage(String image_txt) {
        //is_news_edit = false;
        rl_image.setVisibility(View.VISIBLE);
        ll_add_image.setVisibility(View.GONE);
        news_image.setImageBitmap(BitmapFactory.decodeFile(image_txt));
    }

    private void settextViewEditor() {
        mEditor.setPlaceholder(getString(R.string.aeticle_description));
        mEditor.setEditorHeight(160);
        mEditor.setEditorFontSize(18);
        mEditor.setEditorFontColor(getResources().getColor(R.color.theme));
        mEditor.setPadding(10, 10, 10, 0);
        mEditor.setBackgroundColor(getResources().getColor(R.color.article_bg));

        mEditor.setOnTextChangeListener(text -> {
            content_txt = text;
            // Do Something
        });
    }

    private void setFindEditorView() {
        findViewById(R.id.action_bold).setOnClickListener(v -> {
            if (is_bold_dot) {
                is_bold_dot = false;
                bold_dot.setVisibility(View.INVISIBLE);
            } else {
                is_bold_dot = true;
                bold_dot.setVisibility(View.VISIBLE);
            }
            mEditor.setBold();
        });

        findViewById(R.id.action_italic).setOnClickListener(v -> {
            if (is_italic_dot) {
                is_italic_dot = false;
                italic_dot.setVisibility(View.INVISIBLE);
            } else {
                is_italic_dot = true;
                italic_dot.setVisibility(View.VISIBLE);
            }
            mEditor.setItalic();
        });

        findViewById(R.id.action_subscript).setOnClickListener(v -> {
            if (is_subscript_dot) {
                is_subscript_dot = false;
                subscript_dot.setVisibility(View.INVISIBLE);
            } else {
                is_subscript_dot = true;
                subscript_dot.setVisibility(View.VISIBLE);
            }
            mEditor.setSubscript();
        });

        findViewById(R.id.action_superscript).setOnClickListener(v -> {
            if (is_superscript_dot) {
                is_superscript_dot = false;
                superscript_dot.setVisibility(View.INVISIBLE);
            } else {
                is_superscript_dot = true;
                superscript_dot.setVisibility(View.VISIBLE);
            }
            mEditor.setSuperscript();
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(v -> {
            if (is_strikethrough_dot) {
                is_strikethrough_dot = false;
                strikethrough_dot.setVisibility(View.INVISIBLE);
            } else {
                is_strikethrough_dot = true;
                strikethrough_dot.setVisibility(View.VISIBLE);
            }
            mEditor.setStrikeThrough();
        });

        findViewById(R.id.action_underline).setOnClickListener(v -> {
            if (is_underline_dot) {
                is_underline_dot = false;
                underline_dot.setVisibility(View.INVISIBLE);
            } else {
                is_underline_dot = true;
                underline_dot.setVisibility(View.VISIBLE);
            }
            mEditor.setUnderline();
        });

        findViewById(R.id.action_heading1).setOnClickListener(v -> onTextSize(1));

        findViewById(R.id.action_heading2).setOnClickListener(v -> onTextSize(2));

        findViewById(R.id.action_heading3).setOnClickListener(v -> onTextSize(3));

        findViewById(R.id.action_heading4).setOnClickListener(v -> onTextSize(4));

        findViewById(R.id.action_heading5).setOnClickListener(v -> onTextSize(5));

        findViewById(R.id.action_heading6).setOnClickListener(v -> onTextSize(6));

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {

                if (is_txt_color_dot) {
                    is_txt_color_dot = false;
                    txt_color_dot.setVisibility(View.INVISIBLE);
                } else {
                    txt_color_dot.setVisibility(View.VISIBLE);
                    is_txt_color_dot = true;
                }
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {

                if (is_bg_color_dot) {
                    is_bg_color_dot = false;
                    bg_color_dot.setVisibility(View.INVISIBLE);
                } else {
                    is_bg_color_dot = true;
                    bg_color_dot.setVisibility(View.VISIBLE);
                }

                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(v -> {

            alignText(1);
            mEditor.setAlignLeft();
        });

        findViewById(R.id.action_align_center).setOnClickListener(v -> {
            alignText(2);
            mEditor.setAlignCenter();
        });

        findViewById(R.id.action_align_right).setOnClickListener(v -> {

            alignText(3);
            mEditor.setAlignRight();
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(v -> {
            if (is_insert_bullets_dot) {
                is_insert_bullets_dot = false;
                insert_bullets_dot.setVisibility(View.INVISIBLE);

            } else {
                insert_bullets_dot.setVisibility(View.VISIBLE);
                is_insert_bullets_dot = true;
            }
            mEditor.setBullets();
        });
    }

    private void onTextSize(int selection) {
        switch (selection) {
            case 1:
                if (is_heading1_dot) {
                    is_heading1_dot = false;
                    heading1_dot.setVisibility(View.INVISIBLE);
                } else {
                    is_heading1_dot = true;
                    heading1_dot.setVisibility(View.VISIBLE);
                }
                is_heading2_dot = false;
                is_heading3_dot = false;
                is_heading4_dot = false;
                is_heading5_dot = false;
                is_heading6_dot = false;
                heading2_dot.setVisibility(View.INVISIBLE);
                heading3_dot.setVisibility(View.INVISIBLE);
                heading4_dot.setVisibility(View.INVISIBLE);
                heading5_dot.setVisibility(View.INVISIBLE);
                heading6_dot.setVisibility(View.INVISIBLE);
                break;

            case 2:

                if (is_heading2_dot) {
                    is_heading2_dot = false;
                    heading2_dot.setVisibility(View.INVISIBLE);
                } else {
                    is_heading2_dot = true;
                    heading2_dot.setVisibility(View.VISIBLE);
                }
                is_heading1_dot = false;
                is_heading3_dot = false;
                is_heading4_dot = false;
                is_heading5_dot = false;
                is_heading6_dot = false;
                heading1_dot.setVisibility(View.INVISIBLE);
                heading3_dot.setVisibility(View.INVISIBLE);
                heading4_dot.setVisibility(View.INVISIBLE);
                heading5_dot.setVisibility(View.INVISIBLE);
                heading6_dot.setVisibility(View.INVISIBLE);
                break;

            case 3:

                if (is_heading3_dot) {
                    is_heading3_dot = false;
                    heading3_dot.setVisibility(View.INVISIBLE);
                } else {
                    is_heading3_dot = true;
                    heading3_dot.setVisibility(View.VISIBLE);
                }
                is_heading1_dot = false;
                is_heading2_dot = false;
                is_heading4_dot = false;
                is_heading5_dot = false;
                is_heading6_dot = false;
                heading1_dot.setVisibility(View.INVISIBLE);
                heading2_dot.setVisibility(View.INVISIBLE);
                heading4_dot.setVisibility(View.INVISIBLE);
                heading5_dot.setVisibility(View.INVISIBLE);
                heading6_dot.setVisibility(View.INVISIBLE);
                break;

            case 4:
                if (is_heading4_dot) {
                    is_heading4_dot = false;
                    heading4_dot.setVisibility(View.INVISIBLE);
                } else {
                    is_heading4_dot = true;
                    heading4_dot.setVisibility(View.VISIBLE);
                }
                is_heading1_dot = false;
                is_heading2_dot = false;
                is_heading3_dot = false;
                is_heading5_dot = false;
                is_heading6_dot = false;
                heading1_dot.setVisibility(View.INVISIBLE);
                heading2_dot.setVisibility(View.INVISIBLE);
                heading3_dot.setVisibility(View.INVISIBLE);
                heading5_dot.setVisibility(View.INVISIBLE);
                heading6_dot.setVisibility(View.INVISIBLE);
                break;

            case 5:
                if (is_heading5_dot) {
                    is_heading5_dot = false;
                    heading5_dot.setVisibility(View.INVISIBLE);
                } else {
                    is_heading5_dot = true;
                    heading5_dot.setVisibility(View.VISIBLE);
                }
                is_heading1_dot = false;
                is_heading2_dot = false;
                is_heading3_dot = false;
                is_heading4_dot = false;
                is_heading6_dot = false;
                heading1_dot.setVisibility(View.INVISIBLE);
                heading2_dot.setVisibility(View.INVISIBLE);
                heading3_dot.setVisibility(View.INVISIBLE);
                heading4_dot.setVisibility(View.INVISIBLE);
                heading6_dot.setVisibility(View.INVISIBLE);
                break;

            case 6:

                if (is_heading6_dot) {
                    is_heading6_dot = false;
                    heading6_dot.setVisibility(View.INVISIBLE);
                } else {
                    is_heading6_dot = true;
                    heading6_dot.setVisibility(View.VISIBLE);
                }
                is_heading1_dot = false;
                is_heading2_dot = false;
                is_heading3_dot = false;
                is_heading4_dot = false;
                is_heading5_dot = false;
                heading1_dot.setVisibility(View.INVISIBLE);
                heading2_dot.setVisibility(View.INVISIBLE);
                heading3_dot.setVisibility(View.INVISIBLE);
                heading4_dot.setVisibility(View.INVISIBLE);
                heading5_dot.setVisibility(View.INVISIBLE);
                break;

        }
        mEditor.setHeading(selection);

    }

    private void alignText(int type) {

        if (type == 1) {
            if (is_align_left_dot) {
                is_align_left_dot = false;
                align_left_dot.setVisibility(View.INVISIBLE);
            } else {
                align_left_dot.setVisibility(View.VISIBLE);

                is_align_left_dot = true;
            }
            is_align_center_dot = false;
            align_center_dot.setVisibility(View.INVISIBLE);
            is_align_right_dot = false;
            align_right_dot.setVisibility(View.INVISIBLE);

        } else if (type == 2) {
            if (is_align_center_dot) {
                is_align_center_dot = false;
                align_center_dot.setVisibility(View.INVISIBLE);
            } else {
                is_align_center_dot = true;
                align_center_dot.setVisibility(View.VISIBLE);

            }
            is_align_left_dot = false;
            align_left_dot.setVisibility(View.INVISIBLE);
            is_align_right_dot = false;
            align_right_dot.setVisibility(View.INVISIBLE);

        } else if (type == 3) {
            if (is_align_right_dot) {
                is_align_right_dot = false;
                align_right_dot.setVisibility(View.INVISIBLE);
            } else {
                is_align_right_dot = true;
                align_right_dot.setVisibility(View.VISIBLE);
            }
            is_align_left_dot = false;
            align_left_dot.setVisibility(View.INVISIBLE);
            is_align_center_dot = false;
            align_center_dot.setVisibility(View.INVISIBLE);
        }

    }

    private void setPreviousData() {
        Intent mIntent = getIntent();
        if (mIntent.hasExtra(Constant.IntentKey.ARTICLE_ID)) {
            news_id = mIntent.getIntExtra(Constant.IntentKey.ARTICLE_ID, 0);
            news_heading.setText(mIntent.getStringExtra(Constant.IntentKey.ARTICLE_TITLE));
            news_heading.setSelection(news_heading.getText().length());
            content_txt = mIntent.getStringExtra(Constant.IntentKey.ARTICLE_CONTENT);
            mEditor.setHtml(content_txt);
            String image_url = mIntent.getStringExtra(Constant.IntentKey.ARTICLE_IMAGE);
            rl_image.setVisibility(View.VISIBLE);
            ll_add_image.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(image_url)) {
                Picasso.with(CreateArticleActivity.this).load(image_url).into(news_image);
                news_path = Uri.parse(image_url);
            } else {
                news_path = null;
            }
        }
    }
}

