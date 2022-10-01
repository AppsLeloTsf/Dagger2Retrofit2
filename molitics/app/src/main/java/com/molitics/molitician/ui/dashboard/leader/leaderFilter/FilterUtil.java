package com.molitics.molitician.ui.dashboard.leader.leaderFilter;

import com.molitics.molitician.database.District;
import com.molitics.molitician.database.EventAction;
import com.molitics.molitician.database.MLA;
import com.molitics.molitician.database.MP;
import com.molitics.molitician.database.Party;
import com.molitics.molitician.database.Post;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.leader.leaderFilter.data.FilterKeyData;
import com.molitics.molitician.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 12/30/2016.
 */

public class FilterUtil {

    public static List<FilterKeyData> getEventFilters() {
        List<FilterKeyData> stringList = new ArrayList<>();
        stringList.add(new FilterKeyData(false, Constant.FilterKey.STATE));
        stringList.add(new FilterKeyData(false, Constant.FilterKey.DISTRICT));
        stringList.add(new FilterKeyData(false, Constant.FilterKey.MP_CONSTITUENCY));
        stringList.add(new FilterKeyData(false, Constant.FilterKey.MLA_CONSTITUENCY));
        stringList.add(new FilterKeyData(false, Constant.FilterKey.PARTY));
        stringList.add(new FilterKeyData(false, Constant.FilterKey.POST));

        return stringList;
    }

    public static List<FilterKeyData> getNationEventFilters() {
        List<FilterKeyData> stringList = new ArrayList<>();
        stringList.add(new FilterKeyData(false, Constant.FilterKey.STATE));
        stringList.add(new FilterKeyData(false, Constant.FilterKey.MP_CONSTITUENCY));
        stringList.add(new FilterKeyData(false, Constant.FilterKey.PARTY));
        stringList.add(new FilterKeyData(false, Constant.FilterKey.POST));

        return stringList;
    }


    public static int getPosition(String key, List<FilterKeyData> filterKeyDatas) {
        List<FilterKeyData> list = filterKeyDatas;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKeyName().equalsIgnoreCase(key)) {
                return i;
            }

        }

        return -1;

    }

    public static int getSelectedStatePosition(int value, List<ConstantModel> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() == value) {
                return i;
            }

        }

        return -1;

    }

    public static int getDistrictSelectedPosition(int value, List<District> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() == value) {
                return i;
            }

        }

        return -1;

    }

    public static int getStatusSelectedPosition(int value, List<EventAction> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() == value) {
                return i;
            }

        }
        return -1;
    }

    public static int getMlaSelectedPosition(int value, List<MLA> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() == value) {
                return i;
            }

        }
        return -1;
    }

    public static int getMpSelectedPosition(int value, List<MP> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() == value) {
                return i;
            }

        }
        return -1;
    }

    public static int getPartySelectedPosition(int value, List<Party> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() == value) {
                return i;
            }

        }
        return -1;
    }

    public static int getPostSelectedPosition(int value, List<Post> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() == value) {
                return i;
            }

        }
        return -1;
    }
}
