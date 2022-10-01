package com.molitics.molitician.util;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.database.NewsLeaderModel;
import com.molitics.molitician.model.NewsVideoModel;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.home.adapter.HomeBrowseAdapter;
import com.molitics.molitician.ui.dashboard.home.adapter.TopHomeAdapter;
import com.molitics.molitician.ui.dashboard.home.model.HomeBrowseModel;
import com.molitics.molitician.ui.dashboard.more.adapter.ContactListAdapter;
import com.molitics.molitician.ui.dashboard.more.model.MyContactListModel;
import com.molitics.molitician.ui.dashboard.register.location.adapter.DistrictLocationAdapter;
import com.molitics.molitician.ui.dashboard.register.location.adapter.LocationAdapter;
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.adapter.YoutubeMoreAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BindingUtils {

    @BindingAdapter("contactAdapter")
    public static void addContactItems(RecyclerView recyclerView, List<MyContactListModel> openSourceItems) {

        ContactListAdapter adapter = (ContactListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearContactList();
            adapter.addContactList(openSourceItems);
        }
    }

    @BindingAdapter("youtubeViewAdapter")
    public static void addYoutubeItems(RecyclerView recyclerView, List<NewsVideoModel> openSourceItems) {
        YoutubeMoreAdapter adapter = (YoutubeMoreAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            //  adapter.clearContactList();
            adapter.addData(openSourceItems);
        }
    }

    @BindingAdapter("setVisibility")
    public static void setVisibility(Button button, int value) {
        if (button != null) {
            if (value == 0)
                button.setVisibility(View.GONE);
            else
                button.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("contactVisibility")
    public static void contactVisibility(TextView button, String value) {
        if (button != null) {
            if (value == null || value.isEmpty())
                button.setVisibility(View.GONE);
            else
                button.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("imageBinding")
    public static void loadImage(CircleImageView imageView, String value) {
        if (!TextUtils.isEmpty(value)) {
            Picasso.with(imageView.getContext()).load(value).placeholder(R.drawable.sample_image).into(imageView);
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.sample_image));
        }
    }

    @BindingAdapter("followBackground")
    public static void setFollowBackGround(Button button, int value) {
        if (button != null) {
            if (value == Constant.InviteFriend.INVITE) {
                button.setText(button.getResources().getString(R.string.invite));
                button.setBackground(button.getResources().getDrawable(R.drawable.leader_unfollow_btn));
            } else if (value == Constant.InviteFriend.INVITED) {
                button.setText(button.getResources().getString(R.string.invited));
                button.setBackground(button.getResources().getDrawable(R.drawable.leader_unfollow_btn));

            } else if (value == Constant.InviteFriend.FOLLOW) {
                button.setText(button.getResources().getString(R.string.follow_tag));
                button.setBackground(button.getResources().getDrawable(R.drawable.leader_downvote_bg));
            } else if (value == Constant.InviteFriend.FOLLOWING) {
                button.setText(button.getResources().getString(R.string.following));
                button.setBackground(button.getResources().getDrawable(R.drawable.leader_unfollow_btn));
            }
        }
    }

    @BindingAdapter("homeTopAdapter")
    public static void addHomeTopItems(RecyclerView recyclerView, List<NewsLeaderModel> openSourceItems) {

        TopHomeAdapter adapter = (TopHomeAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.addTrendingLeaderData(openSourceItems);
        }
    }

    @BindingAdapter("homeBrowseAdapter")
    public static void addBrowseItems(RecyclerView recyclerView, List<HomeBrowseModel> openSourceItems) {

        HomeBrowseAdapter adapter = (HomeBrowseAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.addData(openSourceItems);
        }
    }

    @BindingAdapter("locationAdapter")
    public static void addLocationItems(RecyclerView recyclerView, List<ConstantModel> openSourceItems) {

        LocationAdapter adapter = (LocationAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.addData(openSourceItems);
        }
    }

    @BindingAdapter("districtLocationAdapter")
    public static void addDistrictLocationItems(RecyclerView recyclerView, List<ConstantModel> openSourceItems) {

        DistrictLocationAdapter adapter = (DistrictLocationAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.addData(openSourceItems);
        }
    }
}
