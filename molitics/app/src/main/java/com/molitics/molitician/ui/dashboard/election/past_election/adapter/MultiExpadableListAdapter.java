package com.molitics.molitician.ui.dashboard.election.past_election.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.collection.ArrayMap;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;

import java.util.List;

/**
 * Created by rahul on 4/19/2016.
 */
public class MultiExpadableListAdapter extends BaseExpandableListAdapter {

    // 1 Group types
    private static final int GROUP_TYPE = 0;

    private Activity context;
    private ArrayMap<String, List<ConstantModel>> filters_feed_collection;
    private List<ConstantModel> group_list;
    private ExpandableInterface expandableInterface;
    private int lastExpandedGroupPosition = 0;

    public interface ExpandableInterface {
        void CollapseExpListView(int position);
    }

    public MultiExpadableListAdapter(Activity context, ExpandableInterface expandableInterface, List<ConstantModel> group_list,
                                     ArrayMap<String, List<ConstantModel>> filters_feed_collection) {
        this.context = context;
        this.expandableInterface = expandableInterface;
        this.filters_feed_collection = filters_feed_collection;
        this.group_list = group_list;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

        if (groupPosition != lastExpandedGroupPosition) {
            expandableInterface.CollapseExpListView(lastExpandedGroupPosition);
        }
        super.onGroupExpanded(groupPosition);
        lastExpandedGroupPosition = groupPosition;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return filters_feed_collection.get(group_list.get(groupPosition).getKey()).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        final ConstantModel constantModel = (ConstantModel) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        ViewHolderChild holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expandable_filterdata_child, null);
            holder = new ViewHolderChild();
            holder.chkBox = convertView.findViewById(R.id.chkBox);
            holder.txtName = convertView.findViewById(R.id.txtName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderChild) convertView.getTag();
        }
        holder.chkBox.setFocusable(false);
        holder.txtName.setText(constantModel.getKey());
        holder.chkBox.setChecked(constantModel.isCheck());
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        ConstantModel groupName = group_list.get(groupPosition);
        List<ConstantModel> groupContent = filters_feed_collection.get(groupName.getKey());
        return groupContent == null ? 0 : groupContent.size();
    }

    public Object getGroup(int groupPosition) {
        return group_list.get(groupPosition);
    }

    public int getGroupCount() {
        return group_list.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final ConstantModel incoming_text = (ConstantModel) getGroup(groupPosition);


        ViewHolderParent holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expandable_filterdata_parent, null);
            holder = new ViewHolderParent();
            holder.item = convertView.findViewById(R.id.expandable_parent1);
            holder.arrow_indicator = convertView.findViewById(R.id.arrow_indicator);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderParent) convertView.getTag();
        }
        if (isExpanded) {
            holder.arrow_indicator.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_down_arrow));
        } else {
            holder.item.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.item.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.arrow_indicator.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_right_24));
        }
        if (incoming_text.isCheck())
            holder.item.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.survey_click), null);
        else holder.item.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        holder.item.setText(incoming_text.getKey());

        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public int getChildTypeCount() {
        return group_list.size();
    }

    @Override
    public int getGroupType(int groupPosition) {
        return GROUP_TYPE;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return 0;
    }

    public static class ViewHolderChild {
        public TextView txtName;
        public CheckBox chkBox;
    }

    public static class ViewHolderParent {
        public TextView item;
        private ImageView arrow_indicator;
    }
}