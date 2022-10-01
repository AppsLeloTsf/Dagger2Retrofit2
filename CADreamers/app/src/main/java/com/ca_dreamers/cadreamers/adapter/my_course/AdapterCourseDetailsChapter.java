package com.ca_dreamers.cadreamers.adapter.my_orders.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.Datum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterCourseDetailsChapter extends RecyclerView.Adapter<AdapterCourseDetailsChapter.ChapterDetailsViewHolder> {

    private LinearLayoutManager layoutManager;
    private  AdapterCourseDetailsChapterDetails adapter;
    private Context tContext;
    private List<Datum> dataList;
    private String strCatId;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public AdapterCourseDetailsChapter(List<Datum> dataList, Context tContext) {
        this.dataList = dataList;
        this.tContext = tContext;
        this.strCatId = strCatId;
    }
    @NonNull
    @Override
    public ChapterDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chapter, viewGroup, false);
        return new ChapterDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterDetailsViewHolder chapterDetailsViewHolder, final int i) {
        final Datum tModel = dataList.get(i);
        final String strChapterId = tModel.getId();
        final String strChapterName = tModel.getChapterName();
        chapterDetailsViewHolder.tvChapterTitle.setText(strChapterName);

        chapterDetailsViewHolder.llChapterItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 layoutManager
                        = new LinearLayoutManager(
                        chapterDetailsViewHolder.rvChapterDetail.getContext(), LinearLayoutManager.HORIZONTAL, false);
                layoutManager.setInitialPrefetchItemCount(tModel.getTopics().size());

                adapter = new AdapterCourseDetailsChapterDetails(tModel.getTopics(),strChapterId ,tContext);
                chapterDetailsViewHolder.rvChapterDetail.setLayoutManager(layoutManager);
                chapterDetailsViewHolder.rvChapterDetail.setAdapter(adapter);
                chapterDetailsViewHolder.rvChapterDetail.setRecycledViewPool(viewPool);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ChapterDetailsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvChapterTitle)
        protected TextView tvChapterTitle;
        @BindView(R.id.rvChapterDetail)
        protected RecyclerView rvChapterDetail;
        @BindView(R.id.llChapterItem)
        protected LinearLayout llChapterItem;

        public ChapterDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
