package com.ca_dreamers.cadreamers.fragments.offline_files;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.SavedVideo.AdapterSavedVideo;
import com.ca_dreamers.cadreamers.SavedVideo.ModelSavedVideo;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentOfflineBooks extends Fragment {

    private Context tContext;
    private AdapterSavedVideo adapter;
    private ArrayList<ModelSavedVideo> courseModalArrayList;
    List<String> results = new ArrayList<>();
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvSavedBooks)
    protected RecyclerView rvSavedBooks;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvEmptySavedBooks)
    protected TextView tvEmptySavedBooks;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline_books, container, false);
        ButterKnife.bind(this, view);
        tContext = view.getContext();
        loadData();

        File[] files;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            files = new File(tContext.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS).listFiles();
        } else {
            files = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS).listFiles();
        }
        if (files != null) {
            tvEmptySavedBooks.setVisibility(View.GONE);
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
            rvSavedBooks.setVisibility(View.GONE);
            tvEmptySavedBooks.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void loadData() {

        if (courseModalArrayList == null) {
            courseModalArrayList = new ArrayList<>();
        }
        adapter = new AdapterSavedVideo(courseModalArrayList, tContext, Constant.FOLDER_BOOKS);
        LinearLayoutManager manager = new LinearLayoutManager(tContext);
        rvSavedBooks.setHasFixedSize(true);

        rvSavedBooks.setLayoutManager(manager);
        rvSavedBooks.setAdapter(adapter);
    }



}