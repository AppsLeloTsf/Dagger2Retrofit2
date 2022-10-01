package com.molitics.molitician.ui.dashboard.userProfile;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.userProfile.model.UserProfileModel;
import com.molitics.molitician.ui.dashboard.voice.adapter.ImageViewPager;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.VoiceAllViewHolder;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.KotlinUtilsKt;
import com.molitics.molitician.util.MoliticsActivity;
import com.molitics.molitician.util.PrefUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.molitics.molitician.util.ShareScreenShot.takeScreenshot;

/**
 * Created by rahul on 30/11/17.
 */

public class UserProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        ImageViewPager.VoiceImageListener, ExtraUtils.NotifyCustomDataSetChanged {

    String TAG = "UserProfileAdapter";
    private List<VoiceAllModel> voiceAllModels = new ArrayList<>();
    private UserProfileModel userProfileModel;

    private Context mContext;
    private int user_id;
    private int image_position = 0;

    public static int CREATE_ISSUE = 0;
    public static int ALL_ISSUE = 1;
    private VoiceAllAdapter.VoiceInterFace voiceInterFace;
    private UserProfileListener userProfileListener;

    @Override
    public void onImageClick(int position, int issue_id, List<String> imageList) {
        voiceInterFace.onImageClick(null, issue_id, position, imageList);
    }

    @Override
    public void onVideoPLay() {
    }

    @Override
    public void onVideoPause() {
    }

    @Override
    public void onShareClick(String url) {
    }

    @Override
    public void onDownloadClick(String url) {
    }

    @Override
    public void notifyCustom(int image_position) {
        this.image_position = image_position;
        notifyDataSetChanged();
    }

    public interface UserProfileListener {

        void onUserFollow(int user_id);

        void onUserUnFollow(int user_id);

        void onProfileImageClick(String image);

        void getUserFollowingList(int user_id, int action);

        void onReportDialogClick(int userId);
    }

    public UserProfileAdapter(Context mContext, VoiceAllAdapter.VoiceInterFace voiceInterFace,
                              UserProfileListener userProfileListener, int user_id) {
        this.mContext = mContext;
        this.voiceInterFace = voiceInterFace;
        this.userProfileListener = userProfileListener;
        this.user_id = user_id;
    }

    public void addVoiceList(List<VoiceAllModel> voiceAllModels) {
        this.voiceAllModels.addAll(voiceAllModels);
        notifyDataSetChanged();
    }

    public void updateVoiceModel(int position, VoiceAllModel singleVoiceModel) {
        voiceAllModels.set(position, singleVoiceModel);
        notifyDataSetChanged();
    }

    public void updateProgressView(int percentDone) {
        voiceAllModels.get(0).setUploadedPercent(percentDone);
        /// notifyItemChanged(0);
        notifyDataSetChanged();
    }

    public void addUserProfileData(UserProfileModel userProfileModel) {
        this.userProfileModel = userProfileModel;
        notifyDataSetChanged();
    }

    public void editCommentCount(int position, int count) {
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);
        voiceAllModel.setComments(voiceAllModel.getComments() + count);
        notifyDataSetChanged();
    }

    public List<VoiceAllModel> getVoiceList() {
        return voiceAllModels;
    }

    public int getVoiceListSize() {
        return voiceAllModels.size();
    }

    public void clearVoiceList() {
        voiceAllModels.clear();
        notifyDataSetChanged();
    }

    public void deleteVoice(int position) {
        voiceAllModels.remove(position);
        userProfileModel.setIssues(userProfileModel.getIssues() - 1);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ALL_ISSUE) {
            View mView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.adapter_voice_all, parent, false);
            return new VoiceAllViewHolder(mView);
        } else {
            View mView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.fragment_user_profile, parent, false);
            return new CreateIssueViewHolder(mView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == ALL_ISSUE) {
            VoiceAllViewHolder viewHolder = (VoiceAllViewHolder) holder;
            int temp_position = position - 1;

            VoiceAllModel voiceModel = voiceAllModels.get(temp_position);
            if (voiceModel != null) {
                viewHolder.bindView(mContext, TAG, viewHolder, voiceModel, holder.getAdapterPosition(),
                        temp_position, voiceInterFace, this, image_position);
            }
        } else {
            CreateIssueViewHolder viewHolder = (CreateIssueViewHolder) holder;
            if (userProfileModel != null) {
                Resources resources_ref = mContext.getResources();

                // viewHolder.following_txt.setText(R.string.following);
                viewHolder.follow_user_txt.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(userProfileModel.getImage()))
                    Picasso.with(mContext).load(userProfileModel.getImage()).placeholder(R.drawable.sample_image).into(viewHolder.user_image);
                else
                    viewHolder.user_image.setImageResource(R.drawable.sample_image);

                if (userProfileModel.getIsVerify() == 1) {
                    viewHolder.m_verified_view.setVisibility(View.VISIBLE);
                } else
                    viewHolder.m_verified_view.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(userProfileModel.getName()))
                    viewHolder.user_name_txt.setText(userProfileModel.getName());
                else
                    viewHolder.user_name_txt.setText(R.string.anonymous_tag);


                if (!TextUtils.isEmpty(userProfileModel.getMobile()))
                    viewHolder.contact_number_txt.setText(userProfileModel.getMobile());
                else {
                    viewHolder.contact_number_txt.setText(notMentioned("Not Mentioned"));
                }
                if (!TextUtils.isEmpty(userProfileModel.getEmail())) {
                    viewHolder.emailTxtView.setVisibility(View.VISIBLE);
                    viewHolder.emailTxtView.setText(userProfileModel.getEmail());
                } else {
                    viewHolder.emailTxtView.setVisibility(View.GONE);
                }

                viewHolder.userUniqueNameView.setText("@"+userProfileModel.getUserName());
                viewHolder.bioTextView.setText(new StringBuilder()
                        .append(mContext.getString(R.string.user_bio_title))
                        .append(userProfileModel.getUserBio()).toString());

                viewHolder.locationTextView.setText(userProfileModel.getState());
                viewHolder.following_count_txt.setText(String.valueOf(userProfileModel.getFollowing()));
                viewHolder.issues_count_txt.setText(String.valueOf(userProfileModel.getIssues()));
                viewHolder.followers_count_txt.setText(String.valueOf(userProfileModel.getFollowers()));

                if (userProfileModel.getIssues() == 0) {
                    viewHolder.issues_txt.setText(R.string.issue_tag);
                } else {
                    viewHolder.issues_txt.setText(resources_ref.getQuantityString(R.plurals.plural_issue_tag, userProfileModel.getIssues()));
                }
                if (userProfileModel.getFollowers() == 0) {
                    viewHolder.followers_txt.setText(R.string.follower_tag);
                } else {
                    viewHolder.followers_txt.setText(resources_ref.getQuantityString(R.plurals.plural_follower_tag, userProfileModel.getFollowers()));
                }
                if (voiceAllModels.size() > 0) {
                    viewHolder.no_issue_layout.setVisibility(View.GONE);

                    viewHolder.issue_raised_txt.setText(resources_ref.getQuantityString(R.plurals.plural_issue_raise_tag, voiceAllModels.size()));
                } else {
                    viewHolder.no_issue_layout.setVisibility(View.VISIBLE);
                    viewHolder.issue_raised_txt.setText(R.string.issue_raised_tag);
                }

                if (user_id == PrefUtil.getInt(Constant.PreferenceKey.USER_ID)) {
                    viewHolder.follow_user_txt.setVisibility(View.GONE);
                } else {
                    viewHolder.follow_user_txt.setVisibility(View.VISIBLE);
                }
                if (userProfileModel.getIsUserFollowing() == 0) {
                    viewHolder.follow_user_txt.setText(R.string.follow_tag);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        viewHolder.follow_user_txt.setBackground(mContext.getResources().getDrawable(R.drawable.know_more_bg));
                    } else
                        viewHolder.follow_user_txt.setBackgroundResource(R.drawable.know_more_bg);
                    viewHolder.follow_user_txt.setTextColor(mContext.getResources().getColor(R.color.follow_txte));
                } else {
                    viewHolder.follow_user_txt.setText(R.string.following);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        viewHolder.follow_user_txt.setBackground(mContext.getResources().getDrawable(R.drawable.bg_primary));
                    } else viewHolder.follow_user_txt.setBackgroundResource(R.drawable.bg_primary);
                    viewHolder.follow_user_txt.setTextColor(mContext.getResources().getColor(R.color.white));
                }

                MediaPlayer follow_player = MediaPlayer.create(mContext, R.raw.notification);
                viewHolder.follow_user_txt.setOnClickListener(v -> {

                    if (userProfileModel.getIsUserFollowing() == 0) {
                        follow_player.start();
                        userProfileListener.onUserFollow(user_id);
                    } else {
                        userProfileListener.onUserUnFollow(user_id);
                    }
                });

                viewHolder.user_image.setOnClickListener(v -> {
                    if (!TextUtils.isEmpty(userProfileModel.getImage()))
                        userProfileListener.onProfileImageClick(userProfileModel.getImage());
                });

                viewHolder.shareView.setOnClickListener(v -> {
                            takeScreenshot(mContext, userProfileModel.getName(),
                                    KotlinUtilsKt.generateProfileLink(userProfileModel.getId(),
                                            userProfileModel.getName()));
                        }
                );

                viewHolder.followers_txt.setOnClickListener(v -> userProfileListener.getUserFollowingList(userProfileModel.getId(), 2));

                //  viewHolder.following_txt.setOnClickListener(v -> userProfileListener.getUserFollowingList(userProfileModel.getId(), 1));
                viewHolder.followers_count_txt.setOnClickListener(v -> userProfileListener.getUserFollowingList(userProfileModel.getId(), 2));

                viewHolder.following_count_txt.setOnClickListener(v -> userProfileListener.getUserFollowingList(userProfileModel.getId(), 1));
                viewHolder.profileThreeDotView.setOnClickListener(view -> {
                    PopupMenu popup = new PopupMenu(mContext, view);
                    if (user_id == PrefUtil.getInt(Constant.PreferenceKey.USER_ID)) {
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.menu_user_profile, popup.getMenu());

                        popup.setOnMenuItemClickListener(item -> {
                            if (item.getTitle().equals(mContext.getString(R.string.verification))) {
                                MoliticsActivity.startVerificationActivity(mContext);
                            } else if (item.getTitle().equals(mContext.getString(R.string.change_password))) {
                                MoliticsActivity.startChangePasswordActivity(mContext);
                            } else if (item.getTitle().equals(mContext.getString(R.string.edit_profile))) {
                                MoliticsActivity.startEditProfileActivity(mContext);
                            }
                            return true;
                        });
                    } else {
                        popup.getMenuInflater().inflate(R.menu.menu_other_user_profile, popup.getMenu());
                        popup.setOnMenuItemClickListener(item -> {
                            if (item.getTitle().equals(mContext.getString(R.string.report))) {
                                KotlinUtilsKt.reportUser(mContext, userProfileModel.getId());
                            } else if (item.getTitle().equals(mContext.getString(R.string.copy_profile_link))) {
                                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
                                clipboard.setText(KotlinUtilsKt.generateProfileLink(userProfileModel.getId(),
                                        userProfileModel.getName()));
                            }
                            return true;
                        });
                    }
                    popup.show();
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return CREATE_ISSUE;
        } else {
            return ALL_ISSUE;
        }
    }

    @Override
    public int getItemCount() {
        return voiceAllModels.size() != 0 ? voiceAllModels.size() + 1 : 1;
    }

    class CreateIssueViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_image)
        CircleImageView user_image;
        @BindView(R.id.user_name_txt)
        TextView user_name_txt;
        @BindView(R.id.issues_count_txt)
        TextView issues_count_txt;
        @BindView(R.id.followers_count_txt)
        TextView followers_count_txt;
        @BindView(R.id.following_count_txt)
        TextView following_count_txt;
        @BindView(R.id.follow_user_txt)
        TextView follow_user_txt;
        @BindView(R.id.contact_number_txt)
        TextView contact_number_txt;
        @BindView(R.id.followers_txt)
        TextView followers_txt;
        @BindView(R.id.issues_txt)
        TextView issues_txt;
        /*  @BindView(R.id.following_txt)
          TextView following_txt;*/
        @BindView(R.id.issue_raised_txt)
        TextView issue_raised_txt;
        @BindView(R.id.no_issue_layout)
        RelativeLayout no_issue_layout;
        @BindView(R.id.male_female_view)
        ImageView male_female_view;
        @BindView(R.id.m_verified_view)
        ImageView m_verified_view;
        @BindView(R.id.emailTxtView)
        TextView emailTxtView;
        @BindView(R.id.profileThreeDotView)
        ImageView profileThreeDotView;
        @BindView(R.id.shareView)
        ImageView shareView;
        @BindView(R.id.locationTextView)
        TextView locationTextView;
        @BindView(R.id.bioTextView)
        TextView bioTextView;
        @BindView(R.id.userUniqueNameView)
        TextView userUniqueNameView;

        public CreateIssueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateLikeDislike(int position, int like_count, int dislike_count, int my_action, int image_position) {
        this.image_position = image_position;
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);
        voiceAllModel.setLikes(like_count);
        voiceAllModel.setDislikes(dislike_count);
        voiceAllModel.setMyAction(my_action);

        voiceAllModels.set(position, voiceAllModel);
        notifyDataSetChanged();
    }

    public void onFollowResponse(int position, int count, int image_position) {
        this.image_position = image_position;
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);
        voiceAllModel.setIsFollowing(1);
        voiceAllModel.setFollowing(count);
        voiceAllModels.set(position, voiceAllModel);
        notifyDataSetChanged();
    }

    public void onUnFollowResponse(int position, int count, int image_position) {
        this.image_position = image_position;
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);
        voiceAllModel.setFollowing(count);
        voiceAllModel.setIsFollowing(0);
        voiceAllModels.set(position, voiceAllModel);
        notifyDataSetChanged();
    }

    public void replaceUserImage(String user_image) {
        userProfileModel.setImage(user_image);
        notifyDataSetChanged();
    }

    public void followUser(Boolean following, int count) {
        if (following) {
            userProfileModel.setIsUserFollowing(1);
        } else {
            userProfileModel.setIsUserFollowing(0);
        }
        userProfileModel.setFollowers(count);

        notifyDataSetChanged();
    }

    private SpannableString notMentioned(String txt) {
        SpannableString styledString = new SpannableString(txt);
        StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        styledString.setSpan(bold, 0, txt.indexOf(":"), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        styledString.setSpan(new ForegroundColorSpan(Color.RED), txt.indexOf(":") + 1, txt.length(), 0);

        return styledString;
    }
}
