package com.molitics.molitician.ui.dashboard.cartoon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.DashBoardActivity;
import com.molitics.molitician.ui.dashboard.cartoon.cartoonAdapter.CartoonAdapter;
import com.molitics.molitician.ui.dashboard.cartoon.cartoonModel.AllCartoonModel;
import com.molitics.molitician.ui.dashboard.cartoon.detail.CartoonDetailActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DownloadFiles;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.util.MoliticsAppPermission.checkWritePermission;

/**
 * Created by rahul on 19/02/18.
 */

public class CartoonFragment extends Fragment implements CartoonPresenter.CartoonUI,
        CartoonAdapter.CartoonAdapterListener, ViewRefreshListener {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.inner_container)
    FrameLayout rl_error;
    @BindView(R.id.loading_grid)
    LinearLayout loading_grid;
    @BindView(R.id.cartoon_progress_bar)
    ProgressBar cartoon_progress_bar;

    private CartoonHandler cartoonHandler;
    private int page_no = 0;
    private CartoonAdapter cartoonAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_cartoon, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //setSupportActionBar(bookmark_toolbar);
        //showHeader(true, getResources().getString(R.string.cartoon));
        PrefUtil.putBoolean(Constant.PreferenceKey.NOTIFICATION_CARTOON, false);

        //bookmark_toolbar.setNavigationOnClickListener(v -> onBackPressed());
        setUI();
        cartoonHandler = new CartoonHandler(this);
        cartoonHandler.getCartoon(page_no);
        cartoon_progress_bar.setVisibility(View.VISIBLE);
        //setResult(SUCCESS_RESULT);
    }


    private void setUI() {
        cartoonAdapter = new CartoonAdapter(getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(cartoonAdapter);
    }

    @Override
    public void onCartoonResponse(APIResponse apiResponse) {
        cartoon_progress_bar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);
        cartoonAdapter.addCartoonList(apiResponse.getData().getAllCartoonModel());
    }

    @Override
    public void onCartoonError(ApiException apiException) {
        cartoon_progress_bar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);
        if (apiException.getCode() == 2004) {
            cartoonAdapter.isLoadMore(false);
        } else {
            Util.validateError(getContext(), apiException, rl_error, this, cartoonAdapter.getItemCount());
        }
    }


    @Override
    public void onCartoonClick(int position, String image_url) {

        rl_error.setVisibility(View.VISIBLE);
        rl_error.removeAllViews();

        Intent mIntent = new Intent(getContext(), CartoonDetailActivity.class);
        mIntent.putParcelableArrayListExtra(Constant.IntentKey.CARTOON_LIST, (ArrayList<AllCartoonModel>) cartoonAdapter.getCartoonList());
        mIntent.putExtra(Constant.IntentKey.POSITION, position);
        startActivity(mIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_blank, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onResume() {
        super.onResume();
        rl_error.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        rl_error.setVisibility(View.VISIBLE);
    }



   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        invalidateOptionsMenu();
        goInnerBack();
        rl_error.setVisibility(View.GONE);
    }*/

    @Override
    public void loadMore(int total_count) {
        loading_grid.setVisibility(View.VISIBLE);
        rl_error.removeAllViews();
        cartoonHandler.getCartoon(total_count);
    }

    @Override
    public void shareCartoon(String url) {
        if (checkStoragePermission())
            DownloadFiles.saveImage(getContext(), DownloadFiles.SHARE_TYPE, url);
    }

    @Override
    public void onRefereshClick() {
        cartoonHandler.getCartoon(page_no);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }


    private boolean checkStoragePermission() {
        if (checkWritePermission()) {
            return true;
        } else {
            MoliticsAppPermission.requestStoragePermission(getActivity());
            return false;
        }
    }
}
