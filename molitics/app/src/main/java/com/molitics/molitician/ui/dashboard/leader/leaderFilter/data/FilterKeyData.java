package com.molitics.molitician.ui.dashboard.leader.leaderFilter.data;

/**
 * Created by rahul on 18/12/16.
 */

public class FilterKeyData
{

    boolean isSelected;
    String keyName;

    public FilterKeyData(boolean isSelected, String keyName)
    {
        this.isSelected=isSelected;
        this.keyName=keyName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
