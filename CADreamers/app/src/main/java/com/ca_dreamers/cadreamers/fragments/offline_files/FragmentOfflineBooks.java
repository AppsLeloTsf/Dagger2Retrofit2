package com.ca_dreamers.cadreamers.fragments.offline_files;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.SavedVideo.AdapterSavedVideo;
import com.ca_dreamers.cadreamers.SavedVideo.ModelSavedVideo;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentOffline extends Fragment {

    private Context tContext;
    private AdapterSavedVideo adapter;
    private ArrayList<ModelSavedVideo> courseModalArrayList;
    List<String> results = new ArrayList<String>();
    @BindView(R.id.rvSavedVideo)
    protected RecyclerView rvSavedVideo;

    @BindView(R.id.tvEmptyMyVideo)
    protected TextView tvEmptyMyVideo;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        ButterKnife.bind(this, view);
        tContext = view.getContext();
        loadData();
        File[] files = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/"+ Constant.FOLDER_NAME).listFiles();

        if (files != null) {
            tvEmptyMyVideo.setVisibility(View.GONE);
            for (File file : files) {
                if (file.isFile()) {
                    results.add(file.getName());
                    if (courseModalArrayList == null) {
                        courseModalArrayList = new ArrayList<>();
                    }
                    courseModalArrayList.add(new ModelSavedVideo(file.getAbsolutePath(), file.getName()));
                    adapter.notifyItemInserted(courseModalArrayList.size());
                    Log.e("FILE_LIST", file.getName());
                }
            }
        }else {
            rvSavedVideo.setVisibility(View.GONE);
            tvEmptyMyVideo.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void loadData() {

        if (courseModalArrayList == null) {
            courseModalArrayList = new ArrayList<>();
        }
        adapter = new AdapterSavedVideo(courseModalArrayList, tContext);
        LinearLayoutManager manager = new LinearLayoutManager(tContext);
        rvSavedVideo.setHasFixedSize(true);

        rvSavedVideo.setLayoutManager(manager);
        rvSavedVideo.setAdapter(adapter);
    }



}