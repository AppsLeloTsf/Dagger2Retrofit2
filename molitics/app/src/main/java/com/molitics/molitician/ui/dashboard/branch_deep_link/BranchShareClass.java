package com.molitics.molitician.ui.dashboard.branch_deep_link;

import android.app.Activity;
import android.content.Context;


import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.PrefUtil;

import java.util.Calendar;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.util.BranchEvent;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;

/**
 * Created by rahul on 06/03/18.
 */

public class BranchShareClass {

    static String MOLITICS_BASE_URL = "https://www.molitics.in/";

    public interface DeepLinkCallBack {
        void onGenerateShareLink(String full_txt, String url);
    }

    public static void generateShareLink(Context mActivity, DeepLinkCallBack deepLinkCallBack,
                                         String title, String description, String campaign, String key,
                                         String value, String web_key) {
        String trimTitle = title.replaceAll(" ", "");

        String url = MOLITICS_BASE_URL;
        if (campaign.equalsIgnoreCase(Constant.ShareCampaign.VOICE) || campaign.equalsIgnoreCase(Constant.ShareCampaign.SURVEY)) {
            url = url + key + "/" + value;
        } else {
            url = url + key + "/" + value + "/" + trimTitle;
        }
        deepLinkCallBack.onGenerateShareLink(title, url);
    }

    public static void generateNewsShareLink(DeepLinkCallBack deepLinkCallBack, String title,
                                             String share_url) {
        deepLinkCallBack.onGenerateShareLink(title, share_url);
    }
}
