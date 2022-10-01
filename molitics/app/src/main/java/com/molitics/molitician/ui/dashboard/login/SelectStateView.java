package com.molitics.molitician.ui.dashboard.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.InitializeServerRequest;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.DeviceRegistration;
import com.molitics.molitician.ui.dashboard.DashBoardActivity;
import com.molitics.molitician.ui.dashboard.adapter.SelectStateAdapter;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 5/11/2017.
 */

public class SelectStateView extends Fragment implements InitializeServerRequest, LocationSelectionPresenter.LocationView, ViewRefreshListener, SelectStateAdapter.OnItemClick {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;


    private LocationSelectionHandler selectionHandler;
    private SelectStateAdapter selectStateAdapter;
    private int statePosition = 0;
    private DeviceRegistration submitLocationModel;

    public SelectStateView() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View primary_layout = inflater.inflate(R.layout.recyclerview_layout, container, false);
        ButterKnife.bind(this, primary_layout);
        assert getActivity() != null;
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Select State");

        swipeContainer.setEnabled(false);
        selectStateAdapter = new SelectStateAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(selectStateAdapter);
        selectionHandler = new LocationSelectionHandler(this);
        createServerRequest(Constant.RequestTag.STATE_LIST);
        return primary_layout;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_check, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefereshClick() {

    }

    @Override
    public void onNetworkAvailable() {

    }

    @Override
    public void onState(List<ConstantModel> stateList) {
        Loader.dismissMyDialog(getActivity());
        if (stateList != null) {
            selectStateAdapter.addStateList(stateList);
        }
    }

    @Override
    public void onStateSelection(APIResponse onStateSelect) {

    }

    @Override
    public void onLocationSelectionResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(getActivity());

    }

    @Override
    public void onLocationSelectionApiException(ApiException apiException) {
        Loader.dismissMyDialog(getActivity());
        if (apiException.getCode() == 2005 && submitLocationModel != null) {
            Util.saveUserPreference(submitLocationModel);
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
    public void onItemClick(int position, boolean isChecked) {
        if (isChecked) {
            statePosition = 0;
            selectStateAdapter.setSelectedItem(0);
        } else {
            setDistrictSelection(position);

        }
    }

    public void setDistrictSelection(int position) {
        statePosition = position;
        selectStateAdapter.setSelectedItem(statePosition);
    }
}
