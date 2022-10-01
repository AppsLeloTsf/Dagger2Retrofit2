package com.molitics.molitician.ui.dashboard.filter;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.collection.ArrayMap;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.MCustomAlertInterface;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.DashBoardActivity;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.election.past_election.adapter.MultiExpadableListAdapter;
import com.molitics.molitician.ui.dashboard.leader.leaderFilter.LeaderFilterHandler;
import com.molitics.molitician.ui.dashboard.leader.leaderFilter.LeaderFilterPresenter;
import com.molitics.molitician.ui.dashboard.leader.leaderFilter.data.LeaderFilter;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.CustomAlert;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rahul on 6/21/2017.
 */

public class LeaderFilterActivity extends BasicAcivity implements MultiExpadableListAdapter.ExpandableInterface, LeaderFilterPresenter.LeaderFilterView, MCustomAlertInterface {

    @BindView(R.id.expandable_list_view)
    ExpandableListView expandable_list_view;

    private MultiExpadableListAdapter main_list_adapter;
    private List<ConstantModel> listDataHeader = new ArrayList<>();

    private ArrayMap<String, List<ConstantModel>> listDataChild = new ArrayMap<>();
    private LeaderFilterHandler filterHandler;
    private List<ConstantModel> stateList = new ArrayList<>();
    private List<ConstantModel> postList = new ArrayList<>();
    private LeaderFilter leaderFilter;
    private Boolean setAgainClick = false;

    private int STATE_POSITION = 0;
    private int DISTRICT_POSITION = 1;
    private int MLA_POSITION = 2;
    private int MP_POSITION = 3;
    private int PARTY_POSITION = 4;
    private int POST_POSITION = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_filter_activity);
        ButterKnife.bind(this);

        leaderFilter = getIntent().getParcelableExtra(Constant.IntentKey.FILTERS);
        filterHandler = new LeaderFilterHandler(this);
        List<ConstantModel> states = DashBoardActivity.state_list;
        final List<ConstantModel> post_data = new ArrayList<>();
        post_data.addAll(DashBoardActivity.post_list);
        if (states != null) {
            stateList.addAll(states);
            postList.addAll(post_data);
        } else
            filterHandler.getStateList();

        setListOfFilter();

        main_list_adapter = new MultiExpadableListAdapter(this, this, listDataHeader, listDataChild);
        expandable_list_view.setAdapter(main_list_adapter);

        expandable_list_view.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            if (listDataChild != null) {
                List<ConstantModel> tempModel = listDataChild.get(listDataHeader.get(groupPosition).getKey());
                for (int i = 0; i < tempModel.size(); i++) {
                    if (childPosition != i) {
                        tempModel.get(i).setCheck(false);
                    } else {
                        if (tempModel.get(i).isCheck()) {
                            tempModel.get(i).setCheck(false);
                            setAgainClick = false;
                        } else {
                            setAgainClick = true;
                            tempModel.get(i).setCheck(true);
                        }
                    }
                }
                tempModel.get(childPosition).setCheck(tempModel.get(childPosition).isCheck());
                main_list_adapter.notifyDataSetChanged();

                switch (groupPosition) {
                    case 0:
                        if (setAgainClick) {
                            filterHandler.getOtherLocationList(tempModel.get(childPosition).getValue());
                            leaderFilter.setState(String.valueOf(tempModel.get(childPosition).getValue()));
                            listDataHeader.get(STATE_POSITION).setCheck(true);
                            main_list_adapter.notifyDataSetChanged();

                        } else {
                            leaderFilter.setState("");
                            listDataChild.put(getString(R.string.select_district), blankList);
                            listDataChild.put(getString(R.string.select_mla), blankList);
                            listDataChild.put(getString(R.string.select_mp), blankList);
                            listDataChild.put(getString(R.string.select_party), blankList);
                            listDataHeader.get(STATE_POSITION).setCheck(false);
                            main_list_adapter.notifyDataSetChanged();
                        }
                        leaderFilter.setDistrict("");
                        leaderFilter.setMla_constituency("");
                        leaderFilter.setMp_constituency("");
                        leaderFilter.setParty("");
                        break;
                    case 1:
                        if (setAgainClick) {
                            leaderFilter.setDistrict(String.valueOf(tempModel.get(childPosition).getValue()));
                            listDataHeader.get(DISTRICT_POSITION).setCheck(true);
                            main_list_adapter.notifyDataSetChanged();
                        } else {
                            leaderFilter.setDistrict("");
                            listDataHeader.get(DISTRICT_POSITION).setCheck(false);
                            main_list_adapter.notifyDataSetChanged();
                        }

                        break;
                    case 2:
                        if (setAgainClick) {
                            leaderFilter.setMla_constituency(String.valueOf(tempModel.get(childPosition).getValue()));
                            listDataHeader.get(MLA_POSITION).setCheck(true);
                            main_list_adapter.notifyDataSetChanged();
                        } else {
                            leaderFilter.setMla_constituency("");
                            listDataHeader.get(MLA_POSITION).setCheck(false);
                            main_list_adapter.notifyDataSetChanged();
                        }
                        break;
                    case 3:
                        if (setAgainClick) {
                            leaderFilter.setMp_constituency(String.valueOf(tempModel.get(childPosition).getValue()));
                            listDataHeader.get(MP_POSITION).setCheck(true);
                            main_list_adapter.notifyDataSetChanged();
                        } else {
                            leaderFilter.setMp_constituency("");
                            listDataHeader.get(MP_POSITION).setCheck(false);
                            main_list_adapter.notifyDataSetChanged();
                        }

                        break;
                    case 4:
                        if (setAgainClick) {
                            leaderFilter.setParty(String.valueOf(tempModel.get(childPosition).getValue()));
                            listDataHeader.get(PARTY_POSITION).setCheck(true);
                            main_list_adapter.notifyDataSetChanged();
                        } else {
                            leaderFilter.setParty("");
                            listDataHeader.get(PARTY_POSITION).setCheck(false);
                            main_list_adapter.notifyDataSetChanged();
                        }

                        break;
                    case 5:
                        if (setAgainClick) {
                            leaderFilter.setPost(String.valueOf(tempModel.get(childPosition).getValue()));

                            listDataHeader.get(POST_POSITION).setCheck(true);
                            main_list_adapter.notifyDataSetChanged();
                        } else {
                            leaderFilter.setPost("");
                            listDataHeader.get(POST_POSITION).setCheck(false);
                            main_list_adapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
            return false;
        });

        expandable_list_view.setOnGroupClickListener((parent, v, groupPosition, id) -> {

            switch (groupPosition) {
                case 0:
                    if (stateList.size() == 0) {
                        Util.toastShort(LeaderFilterActivity.this, getString(R.string.no_internet_connection));
                    }
                    break;
                case 1:
                    if (leaderFilter.getState().equals("")) {
                        Toast.makeText(LeaderFilterActivity.this, getString(R.string.select_state_first), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LeaderFilterActivity.this, getString(R.string.data_loading), Toast.LENGTH_SHORT).show();
                    }

                    if (!leaderFilter.getMla_constituency().equals("") || !leaderFilter.getMp_constituency().equals("")) {
                        displayAlertDialog(String.valueOf(groupPosition));
                    }
                    break;
                case 2:
                    if (leaderFilter.getState().equals("")) {
                        Toast.makeText(LeaderFilterActivity.this, getString(R.string.select_state_first), Toast.LENGTH_SHORT).show();
                    } else {
                        //  Toast.makeText(LeaderFilterActivity.this, "Data loading", Toast.LENGTH_SHORT).show();
                    }

                    if (!leaderFilter.getDistrict().equals("") || !leaderFilter.getMp_constituency().equals("")) {
                        displayAlertDialog(String.valueOf(groupPosition));
                    }
                    break;

                case 3:
                    if (leaderFilter.getState().equals("")) {
                        Toast.makeText(LeaderFilterActivity.this, getString(R.string.select_state_first), Toast.LENGTH_SHORT).show();
                    } else {
                        //  Toast.makeText(LeaderFilterActivity.this, "Data loading", Toast.LENGTH_SHORT).show();
                    }

                    if (!leaderFilter.getDistrict().equals("") || !leaderFilter.getMla_constituency().equals("")) {
                        displayAlertDialog(String.valueOf(groupPosition));
                    }
                    break;

                case 4:

                    break;
                case 5:

                    break;

            }
            return false;
        });

        setPreviousData(leaderFilter);

    }

    @Override
    public void CollapseExpListView(int position) {
        expandable_list_view.collapseGroup(position);
    }

    @Override
    public void onLeaderFilterStateList(List<ConstantModel> stateList) {
        this.stateList.clear();
        this.stateList.addAll(stateList);
    }

    @Override
    public void onLeaderFilterStateException(ApiException apiException) {
        Util.toastLong(this, getString(R.string.no_internet_connection));
    }

    @Override
    public void onPartyUpdate(List<ConstantModel> leaderPartyList) {
        listDataChild.put(getString(R.string.select_party), leaderPartyList);
        if (!leaderFilter.getParty().equals("")) {
            listDataChild.get(getString(R.string.select_party)).get(getCheckPosition(leaderPartyList, Integer.valueOf(leaderFilter.getParty()))).setCheck(true);
            listDataHeader.get(PARTY_POSITION).setCheck(true);
            main_list_adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMpUpdate(List<ConstantModel> leaderMpList) {
        listDataChild.put(getString(R.string.select_mp), leaderMpList);
        if (!leaderFilter.getMp_constituency().equals("")) {
            listDataChild.get(getString(R.string.select_mp)).get(getCheckPosition(leaderMpList, Integer.valueOf(leaderFilter.getMp_constituency()))).setCheck(true);
            listDataHeader.get(MP_POSITION).setCheck(true);
            main_list_adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMlaUpdate(List<ConstantModel> leaderMlaList) {
        listDataChild.put(getString(R.string.select_mla), leaderMlaList);
        if (!leaderFilter.getMla_constituency().equals("")) {
            listDataChild.get(getString(R.string.select_mla)).get(getCheckPosition(leaderMlaList, Integer.valueOf(leaderFilter.getMla_constituency()))).setCheck(true);
            listDataHeader.get(MLA_POSITION).setCheck(true);
            main_list_adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDistrictUpdate(List<ConstantModel> leaderDistrictList) {
        listDataChild.put(getString(R.string.select_district), leaderDistrictList);
        if (!leaderFilter.getDistrict().equals("")) {
            listDataChild.get(getString(R.string.select_district)).get(getCheckPosition(leaderDistrictList, Integer.valueOf(leaderFilter.getDistrict()))).setCheck(true);
            listDataHeader.get(DISTRICT_POSITION).setCheck(true);
            main_list_adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLeaderApiException(ApiException apiException) {

    }

    private void onPostUpdate() {
        listDataChild.put(getString(R.string.select_post), postList);
        if (!leaderFilter.getPost().equals("")) {
            listDataChild.get(getString(R.string.select_post)).get(getCheckPosition(postList, Integer.valueOf(leaderFilter.getPost()))).setCheck(true);
            listDataHeader.get(POST_POSITION).setCheck(true);
            main_list_adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLeaderFilterStateEntity(APIResponse apiResponse) {

    }

    @Override
    public void onFilterPost(List<ConstantModel> leaderPostList) {
        postList = leaderPostList;
        listDataChild.put(getString(R.string.select_post), postList);
    }

    @Override
    public void onFilterPostException(ApiException apiException) {

    }

    private void setListOfFilter() {
        listDataHeader.add(new ConstantModel(getString(R.string.select_state), false));
        listDataHeader.add(new ConstantModel(getString(R.string.select_district), false));
        listDataHeader.add(new ConstantModel(getString(R.string.select_mla), false));
        listDataHeader.add(new ConstantModel(getString(R.string.select_mp), false));
        listDataHeader.add(new ConstantModel(getString(R.string.select_party), false));
        listDataHeader.add(new ConstantModel(getString(R.string.select_post), false));
        listDataChild.put(getString(R.string.select_state), stateList);
        listDataChild.put(getString(R.string.select_post), postList);
    }

    List<ConstantModel> blankList = new ArrayList<>();

    private void resetList() {
        ///////listDataHeader.get(0)
        List<ConstantModel> stateModel = listDataChild.get(getString(R.string.select_state));
        for (int i = 0; i < stateModel.size(); i++) {
            stateModel.get(i).setCheck(false);
        }
        List<ConstantModel> postModel = listDataChild.get(getString(R.string.select_post));
        for (int i = 0; i < postModel.size(); i++) {
            postModel.get(i).setCheck(false);
        }
    }

    @OnClick(R.id.reset_btn)
    public void onResetClick() {
        resetList();
        listDataChild.put(getString(R.string.select_district), blankList);
        listDataChild.put(getString(R.string.select_mla), blankList);
        listDataChild.put(getString(R.string.select_mp), blankList);
        listDataChild.put(getString(R.string.select_party), blankList);
        main_list_adapter.notifyDataSetChanged();
        leaderFilter.setState("");
        leaderFilter.setDistrict("");
        leaderFilter.setMla_constituency("");
        leaderFilter.setMp_constituency("");
        leaderFilter.setParty("");
        leaderFilter.setPost("");
        listDataHeader.get(STATE_POSITION).setCheck(false);
        listDataHeader.get(DISTRICT_POSITION).setCheck(false);
        listDataHeader.get(MLA_POSITION).setCheck(false);
        listDataHeader.get(MP_POSITION).setCheck(false);
        listDataHeader.get(PARTY_POSITION).setCheck(false);
        listDataHeader.get(POST_POSITION).setCheck(false);
        Toast.makeText(this, getString(R.string.rest_done), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.cross_btn)
    public void onCloseClick() {
        finish();
    }

    @OnClick(R.id.apply_btn)
    public void onApplyClick() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constant.IntentKey.FILTERS, leaderFilter);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onPositiveAlertClick(String tab_selected) {

        if (tab_selected.equals("1")) {
            List<ConstantModel> mlaModel = listDataChild.get(listDataHeader.get(MLA_POSITION).getKey());
            for (int i = 0; i < mlaModel.size(); i++) {
                mlaModel.get(i).setCheck(false);
            }
            List<ConstantModel> mpModel = listDataChild.get(listDataHeader.get(MP_POSITION).getKey());
            for (int i = 0; i < mpModel.size(); i++) {
                mpModel.get(i).setCheck(false);
            }
            leaderFilter.setMp_constituency("");
            leaderFilter.setMla_constituency("");
            listDataHeader.get(MP_POSITION).setCheck(false);
            listDataHeader.get(MLA_POSITION).setCheck(false);
            main_list_adapter.notifyDataSetChanged();

        } else if (tab_selected.equals("2")) {

            List<ConstantModel> disModel = listDataChild.get(listDataHeader.get(DISTRICT_POSITION).getKey());
            for (int i = 0; i < disModel.size(); i++) {
                disModel.get(i).setCheck(false);
            }
            List<ConstantModel> mpModel = listDataChild.get(listDataHeader.get(MP_POSITION).getKey());
            for (int i = 0; i < mpModel.size(); i++) {
                mpModel.get(i).setCheck(false);
            }
            leaderFilter.setDistrict("");
            leaderFilter.setMp_constituency("");
            listDataHeader.get(DISTRICT_POSITION).setCheck(false);
            listDataHeader.get(MP_POSITION).setCheck(false);
            main_list_adapter.notifyDataSetChanged();
        } else {
            List<ConstantModel> mlaModel = listDataChild.get(listDataHeader.get(2).getKey());
            for (int i = 0; i < mlaModel.size(); i++) {
                mlaModel.get(i).setCheck(false);
            }
            List<ConstantModel> disModel = listDataChild.get(listDataHeader.get(1).getKey());
            for (int i = 0; i < disModel.size(); i++) {
                disModel.get(i).setCheck(false);
            }
            leaderFilter.setDistrict("");
            leaderFilter.setMla_constituency("");
            listDataHeader.get(DISTRICT_POSITION).setCheck(false);
            listDataHeader.get(MLA_POSITION).setCheck(false);
            main_list_adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNegativeAlertClick(String tab_selected) {
        if (!leaderFilter.getDistrict().equals("")) {
            expandable_list_view.expandGroup(DISTRICT_POSITION);
        } else if (!leaderFilter.getMla_constituency().equals("")) {
            expandable_list_view.expandGroup(MLA_POSITION);
        } else if (!leaderFilter.getMp_constituency().equals("")) {
            expandable_list_view.expandGroup(MP_POSITION);
        }
    }

    private void displayAlertDialog(String tab_selected) {
        CustomAlert customAlert = new CustomAlert();
        customAlert.setAlertId(1);
        customAlert.setTabType(tab_selected);
        customAlert.setMessage(getString(R.string.sure_exit));
        customAlert.setPositiveButton(getString(R.string.exit));
        customAlert.setNegativeButton(getString(R.string.cancel));
        customAlert.setCancelable(false);
        customAlert.show(this, getString(R.string.exit_dialog));
    }

    private void setPreviousData(LeaderFilter leaderFilter) {

        if (!leaderFilter.getState().isEmpty()) {

            filterHandler.getOtherLocationList(Integer.valueOf(leaderFilter.getState()));
            listDataHeader.get(STATE_POSITION).setCheck(true);
            main_list_adapter.notifyDataSetChanged();

        } else {
            resetList();
            listDataHeader.get(STATE_POSITION).setCheck(false);
            main_list_adapter.notifyDataSetChanged();

        }
        onPostUpdate();
    }

    private int getCheckPosition(List<ConstantModel> constantModels, int value) {
        for (int i = 0; i < constantModels.size(); i++) {
            if (constantModels.get(i).getValue() == value) {
                return i;
            }
        }
        return 0;
    }
}
