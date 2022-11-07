package com.ca_dreamers.cadreamers.fragments.offline_files;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Build;
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
    List<String> results = new ArrayList<>();
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvSavedVideo)
    protected RecyclerView rvSavedVideo;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvEmptyMyVideo)
    protected TextView tvEmptyMyVideo;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        ButterKnife.bind(this, view);
        tContext = view.getContext();
        loadData();

        File[] files;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            files = new File(tContext.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES).listFiles();
        } else {
            files = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES).listFiles();
        }

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
                    Log.e("FILE_LIST", file.getAbsolutePath());
                    MediaScannerConnection.scanFile(tContext,
                            new String[]{Environment.getExternalStorageDirectory() +
                                    File.separator + Constant.FOLDER_COURSES + File.separator + file.getName()}, null,
                            (newpath, newuri) -> {
                                Log.i("ExternalStorage", "Scanned " + newpath + ":");
                                Log.i("ExternalStorage", "-> uri=" + newuri);

                            });

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
        adapter = new AdapterSavedVideo(courseModalArrayList, tContext, Constant.FOLDER_COURSES);
        LinearLayoutManager manager = new LinearLayoutManager(tContext);
        rvSavedVideo.setHasFixedSize(true);

        rvSavedVideo.setLayoutManager(manager);
        rvSavedVideo.setAdapter(adapter);
    }



}