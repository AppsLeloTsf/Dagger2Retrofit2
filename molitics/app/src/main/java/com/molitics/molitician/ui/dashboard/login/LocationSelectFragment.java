package com.molitics.molitician.ui.dashboard.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.molitics.molitician.GoogleAnalytics.GAEventTracking;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.constants.ApiFieldConstant;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.InitializeServerRequest;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.DeviceRegistration;
import com.molitics.molitician.network.commonApi.EventTrackCommonApi;
import com.molitics.molitician.ui.dashboard.DashBoardActivity;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.molitics.molitician.util.Constant.GoogleAnalyticsKey.LOCATION_SELECTION;
import static com.molitics.molitician.util.Constant.GoogleAnalyticsKey.USER_LOCATION_SCREEN;
import static com.molitics.molitician.util.Constant.GoogleAnalyticsKey.VISIT;

/**
 * Created by rahul on 12/15/2016.
 */

public class LocationSelectFragment extends Fragment implements InitializeServerRequest, LocationSelectionPresenter.LocationView, ViewRefreshListener {

    private LocationSelectionHandler selectionHandler;
    private List<ConstantModel> state_list;
    private List<ConstantModel> district_list;
    private List<ConstantModel> mp_list;
    private List<ConstantModel> mla_list;

    @BindView(R.id.state_txt_view)
    TextView state_txt_view;
    @BindView(R.id.district_txt_view)
    TextView district_txt_view;
    @BindView(R.id.mp_txt_view)
    TextView mp_txt_view;
    @BindView(R.id.mla_txt_view)
    TextView mla_txt_view;

    private DeviceRegistration submitLocationModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View primary_layout = inflater.inflate(R.layout.fragment_location_selection, container, false);
        ButterKnife.bind(this, primary_layout);
        selectionHandler = new LocationSelectionHandler(this);
        createServerRequest(Constant.RequestTag.STATE_LIST);
        setGAEvent(USER_LOCATION_SCREEN, VISIT);
        EventTrackCommonApi.updateEventOnServer(getContext(), ApiFieldConstant.USER_EVENT_SELECT_STATE,
                ApiFieldConstant.USER_EVENT_ACTION_TYPE_1, "", "", callback -> {
                });
        return primary_layout;
    }

    @OnClick(R.id.state_txt_view)
    public void onStateClick() {
        SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
        if (state_list != null) {
            locationDialog.showDialog(getContext(), "State", state_list, new RecyclerTouchWithType() {
                @Override
                public void onVerticalRecycler(int position, int type) {
                    SelectLocationDialog.dismissDialog();
                    state_txt_view.setText(state_list.get(position).getKey());
                    submitLocationModel = new DeviceRegistration();
                    submitLocationModel.setState(state_list.get(position).getValue());
                    submitLocationModel.setState_name(state_list.get(position).getKey());
                    submitLocationModel.setDistrict(null);
                    submitLocationModel.setMla(null);
                    submitLocationModel.setMp(null);
                    submitLocationModel.setDistrict_name("");
                    submitLocationModel.setMla_name("");
                    submitLocationModel.setMp_name("");
                    district_txt_view.setText(getContext().getString(R.string.district_display));
                    mp_txt_view.setText(getContext().getString(R.string.mp_constituency));
                    mla_txt_view.setText(getContext().getString(R.string.mla_constituency));
                    selectionHandler.getAllLocationFromState(state_list.get(position).getValue());
                    setGAEvent(Constant.GoogleAnalyticsKey.STATE);
                }

                @Override
                public void onCloseClick() {

                }
            });
        }
    }

    @OnClick(R.id.district_txt_view)
    public void onDistrictClick() {
        SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
        if (district_list != null) {
            locationDialog.showDialog(getContext(), "District", district_list, new RecyclerTouchWithType() {
                @Override
                public void onVerticalRecycler(int position, int type) {
                    SelectLocationDialog.dismissDialog();
                    district_txt_view.setText(district_list.get(position).getKey());
                    submitLocationModel.setDistrict(district_list.get(position).getValue());
                    submitLocationModel.setDistrict_name(district_list.get(position).getKey());

                    setGAEvent(Constant.GoogleAnalyticsKey.DISTRICT);
                }

                @Override
                public void onCloseClick() {
                }
            });
        } else if (submitLocationModel == null) {
            Toast.makeText(getContext(), R.string.select_state_first, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), getString(R.string.data_fetching), Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.mp_txt_view)
    public void onMpClick() {
        SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
        if (mp_list != null) {
            locationDialog.showDialog(getContext(), "MP", mp_list, new RecyclerTouchWithType() {
                @Override
                public void onVerticalRecycler(int position, int type) {
                    SelectLocationDialog.dismissDialog();
                    mp_txt_view.setText(mp_list.get(position).getKey());
                    submitLocationModel.setMp(mp_list.get(position).getValue());
                    submitLocationModel.setMp_name(mp_list.get(position).getKey());
                    setGAEvent(Constant.GoogleAnalyticsKey.MP);
                }

                @Override
                public void onCloseClick() {
                }
            });
        } else if (submitLocationModel == null) {
            Toast.makeText(getContext(), R.string.select_state_first, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), getString(R.string.data_fetching), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.mla_txt_view)
    public void onMlaClick() {
        SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
        if (mla_list != null) {
            locationDialog.showDialog(getContext(), "MLA", mla_list, new RecyclerTouchWithType() {
                @Override
                public void onVerticalRecycler(int position, int type) {
                    SelectLocationDialog.dismissDialog();
                    mla_txt_view.setText(mla_list.get(position).getKey());
                    submitLocationModel.setMla(mla_list.get(position).getValue());
                    submitLocationModel.setMla_name(mla_list.get(position).getKey());
                    setGAEvent(Constant.GoogleAnalyticsKey.MLA);
                }

                @Override
                public void onCloseClick() {
                }
            });
        } else if (submitLocationModel == null) {
            Toast.makeText(getContext(), R.string.select_state_first, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), getString(R.string.data_fetching), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.submit_button)
    public void onSubmitClick() {
        if (submitLocationModel != null) {
            EventTrackCommonApi.updateEventOnServer(getContext(), ApiFieldConstant.USER_EVENT_SELECT_STATE,
                    ApiFieldConstant.USER_EVENT_ACTION_TYPE_2, "", "", callback -> {
                    });
            createServerRequest(Constant.RequestTag.SUBMIT_LOCATION);
        } else {
            Toast.makeText(getContext(), getString(R.string.enter_detail_first), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onState(List<ConstantModel> stateList) {
        Loader.dismissMyDialog(getActivity());
        if (stateList != null) {
            state_list = stateList;
            onStateClick();
        }
    }

    @Override
    public void onStateSelection(APIResponse apiResponse) {
        Loader.dismissMyDialog(getActivity());
        if (apiResponse.getData().getDistrict_list() != null) {
            district_list = apiResponse.getData().getDistrict_list();
        }
        if (apiResponse.getData().getMp_list() != null) {
            mp_list = apiResponse.getData().getMp_list();
        }
        if (apiResponse.getData().getMla_list() != null) {
            mla_list = apiResponse.getData().getMla_list();
        }
    }

    @Override
    public void onLocationSelectionResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(getActivity());

    }

    @Override
    public void onLocationSelectionApiException(ApiException apiException) {
        Loader.dismissMyDialog(getActivity());
        if (apiException.getCode() == 2005 && submitLocationModel != null) {
            Util.saveUserInfo(submitLocationModel);
            Util.saveUserPreference(submitLocationModel);
            Intent start_intent = new Intent(getActivity(), DashBoardActivity.class);
            startActivity(start_intent);
            getActivity().finish();
        } else {
            Util.validateError(getContext(), apiException, null, this, null);
        }
    }

    @Override
    public void onLocationSelectionServerException(ServerException serverException) {
        Loader.dismissMyDialog(getActivity());
        DialogClass.showAlert(getContext(), getString(R.string.something_went_wrong));
    }

    @Override
    public void createServerRequest(int tag) {
        Loader.showMyDialog(getActivity());
        switch (tag) {
            case Constant.RequestTag.STATE_LIST:
                selectionHandler.hitStateList();
                break;
            case Constant.RequestTag.SUBMIT_LOCATION:
                selectionHandler.submitLocation(submitLocationModel);
                break;
        }
    }

    @Override
    public void onRefereshClick() {

    }

    @Override
    public void onNetworkAvailable() {

    }

    private void setGAEvent(String action) {
        GAEventTracking.googleEventTracker(getActivity(), LOCATION_SELECTION, action);
    }

    private void setGAEvent(String Description, String action) {
        GAEventTracking.googleEventTracker(getActivity(), Description, action);
    }
}
