package com.molitics.molitician.ui.dashboard.voice.createVoice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.leader.LeaderPresenter;
import com.molitics.molitician.ui.dashboard.voice.ApiMeditor.LeaderSearchHandler;
import com.molitics.molitician.ui.dashboard.voice.adapter.LeaderListAdapter;
import com.molitics.molitician.util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 23/05/18.
 */

public class TagLeaderFragment extends BasicAcivity implements LeaderPresenter.LeaderListResponse {

    String TAG = TagLeaderFragment.class.getSimpleName();
    private static int FRAGMENT_VIEW = R.layout.fragment_leader_list;

    @BindView(R.id.leader_list_recycler)
    RecyclerView leader_list_recycler;
    @BindView(R.id.leader_search_field)
    EditText leader_search_field;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

    LeaderSearchHandler searchHandler;
    LeaderListAdapter leaderListAdapter;

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        searchHandler = new LeaderSearchHandler(this);
        setContentView(FRAGMENT_VIEW);
        ButterKnife.bind(this);
        setToolbar();
        bindUi();
        getMyArguments();
        editTextChangeList();
        getApiData("");
        progress_bar.setVisibility(View.VISIBLE);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        showHeader(true, getString(R.string.tag_leaders));
        toolbar.setNavigationOnClickListener(v -> goBack());
    }

    private void bindUi() {
        leaderListAdapter = new LeaderListAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        leader_list_recycler.setLayoutManager(linearLayoutManager);
        leader_list_recycler.setAdapter(leaderListAdapter);
    }

    private void getMyArguments() {
        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getExtras();
        List<AllLeaderModel> tagged_leader_list = (ArrayList<AllLeaderModel>) mBundle.getSerializable(Constant.IntentKey.LEADER_LIST);
        if (tagged_leader_list != null)
            leaderListAdapter.addSelectedLeaders(tagged_leader_list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.done_menu) {
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            Intent mIntent = new Intent();
            Bundle mBundle = new Bundle();
            mBundle.putSerializable(Constant.IntentKey.LEADER_LIST, (ArrayList<AllLeaderModel>) leaderListAdapter.getSelectedLeaders());
            mIntent.putExtras(mBundle);
            setResult(RESULT_OK, mIntent);
            finish();
        }
        return true;
    }

    private void editTextChangeList() {
        leader_search_field.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0 || s.length() > 3)
                    getApiData(s.toString());
            }
        });
    }

    private void getApiData(String leader_name) {
        searchHandler.searchCandidate(0, leader_name);
    }

    @Override
    public void onLeaderSearchSuccess(APIResponse apiResponse) {
        if (leaderListAdapter.getItemCount() == 0) {
            progress_bar.setVisibility(View.GONE);
        }
        leaderListAdapter.addLeaderList(apiResponse.getData().getLeaderModels());
    }

    @Override
    public void onLeaderSearchError(ApiException apiError) {
        progress_bar.setVisibility(View.GONE);
    }
}
