package com.molitics.molitician.ui.dashboard.voice.createVoice;

import android.content.Context;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.PrefUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.ponnamkarthik.richlinkpreview.MetaData;
import io.github.ponnamkarthik.richlinkpreview.ResponseListener;
import io.github.ponnamkarthik.richlinkpreview.RichPreview;

import static com.molitics.molitician.util.Constant.URL_REGEX;


/**
 * Created by rahul on 05/06/18.
 */

public class CreateVoiceViewHolder extends VoiceVideoViewHolder {

    @BindView(R.id.user_image_view)
    CircleImageView user_image_view;
    @BindView(R.id.edt_issue_detail)
    EditText edt_issue_detail;
    @BindView(R.id.ll_tag_leader_list)
    LinearLayout ll_tag_leader_list;
    @BindView(R.id.ll_url_view)
    LinearLayout ll_url_view;
    @BindView(R.id.tag_title)
    TextView tag_title;
    @BindView(R.id.url_card_progress)
    ProgressBar url_card_progress;

    private ArrayList<AllLeaderModel> tagged_leader_list = new ArrayList<>();
    private List<Integer> tag_leader_id_list = new ArrayList<>();
    private Context mContext;
    private CreateVoiceInterface mVoiceInterface;
    private VoiceAllModel voiceAllModel;

    public CreateVoiceViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Context mContext, int position, Object data, CreateVoiceInterface voiceInterface) {
        this.mContext = mContext;
        mVoiceInterface = voiceInterface;
        if (data instanceof VoiceAllModel)
            manageVoice(mContext, (VoiceAllModel) data);
    }

    private void manageVoice(Context mContext, VoiceAllModel voiceAllModel) {
        if (voiceAllModel != null) {
            this.voiceAllModel = voiceAllModel;
            getUrlFromText(voiceAllModel.getContent());
            edt_issue_detail.setText(voiceAllModel.getContent());
            edt_issue_detail.setSelection(edt_issue_detail.getText().length());
            edt_issue_detail.setRawInputType(InputType.TYPE_CLASS_TEXT);
            tagLeaderList(voiceAllModel.getCandidateLeader());
        }

        if (!TextUtils.isEmpty(voiceAllModel.getTagName())) {
            tag_title.setVisibility(View.VISIBLE);
            tag_title.setText(voiceAllModel.getTagName());
        } else {
            tag_title.setVisibility(View.GONE);
        }

        String user_image = PrefUtil.getString(Constant.PreferenceKey.USER_IMAGE);
        if (!TextUtils.isEmpty(user_image))
            Picasso.with(mContext).load(user_image).placeholder(R.drawable.sample_image).into(user_image_view);
        else
            user_image_view.setImageResource(R.drawable.sample_image);

        edt_issue_detail.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(voiceAllModel.getUrlSource()))
                    getUrlFromText(s);
                voiceAllModel.setContent(s.toString());
            }
        });
    }

    private void getUrlFromText(CharSequence input) {

        String local_url_string = "";
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            local_url_string = matcher.group();
        }
        if (!TextUtils.isEmpty(local_url_string)) {
            url_card_progress.setVisibility(View.VISIBLE);
            richPreview.getPreview(local_url_string);
        }
    }

    private void tagLeaderList(List<AllLeaderModel> candidateLeaderModels) {
        tagged_leader_list = (ArrayList<AllLeaderModel>) candidateLeaderModels;
        ll_tag_leader_list.removeAllViews();
        tag_leader_id_list.clear();
        for (int i = 0; i < candidateLeaderModels.size(); i++) {
            tag_leader_id_list.add(candidateLeaderModels.get(i).getId());

            View child = LayoutInflater.from(mContext).inflate(R.layout.tag_leader_view, null);
            ll_tag_leader_list.addView(child);
            TextView leader_name = child.findViewById(R.id.leader_name);
            CircleImageView leader_image = child.findViewById(R.id.leader_image);
            ImageView remove_leader = child.findViewById(R.id.remove_leader);

            leader_name.setText(candidateLeaderModels.get(i).getName());
            Picasso.with(mContext).load(candidateLeaderModels.get(i).getProfileImage()).into(leader_image);

            remove_leader.setOnClickListener(view -> {
                View mView = (RelativeLayout) view.getParent();
                View parentView = (RelativeLayout) mView.getParent();
                int index = ll_tag_leader_list.indexOfChild(parentView);
                View mRemoveView = ll_tag_leader_list.getChildAt(index);
                ((LinearLayout) mRemoveView.getParent()).removeView(mRemoveView);
                tag_leader_id_list.remove(index);
                tagged_leader_list.remove(index);
            });
        }
    }


    RichPreview richPreview = new RichPreview(new ResponseListener() {
        @Override
        public void onData(MetaData metaData) {
            url_card_progress.setVisibility(View.GONE);
            if (metaData != null) {
                if (voiceAllModel != null) {
                    voiceAllModel.setUrlSource(metaData.getSitename());
                    voiceAllModel.setSharedUrl(metaData.getUrl());
                }
                if (!TextUtils.isEmpty(metaData.getImageurl())) {
                    mVoiceInterface.addLinkImage(metaData.getImageurl());
                }
                edt_issue_detail.setText(metaData.getTitle());
            }
        }

        @Override
        public void onError(Exception e) {
            //handle error
        }
    });
}
