package com.ca_dreamers.cadreamers.adapter.my_course;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.Topic;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.topics.ModelTopics;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterCourseDetailsChapterDetails extends RecyclerView.Adapter<AdapterCourseDetailsChapterDetails.TopicViewHolder> {

    private GridLayoutManager layoutManager;
    private String strTopicId;
    private String strUserId;
    private final Context tContext;
    private final List<Topic> tModels;
    private final String strChapterId;

    public AdapterCourseDetailsChapterDetails(List<Topic> tModels, String strChapterId, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.strChapterId = strChapterId;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chapter_detail, viewGroup, false);


        SharedPrefManager sharedPrefManager = new SharedPrefManager(tContext);
        strUserId = sharedPrefManager.getUserId();
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder topicViewHolder, final int i) {
        final Topic tModel = tModels.get(i);
        strTopicId = tModel.getId();
        layoutManager = new GridLayoutManager(topicViewHolder.rvTopicVideo.getContext(), 2);
        topicViewHolder.rvTopicVideo.setLayoutManager(layoutManager);
        callCourseApi(topicViewHolder.rvTopicVideo);
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder{

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.rvTopicVideo)
        protected RecyclerView rvTopicVideo;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.rlChapterDetailsItem)
        protected LinearLayout rlChapterDetailsItem;
        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private void callCourseApi(RecyclerView recyclerView){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("chapter_id", strChapterId);
            paramObject.put("topic_id", strTopicId);
            paramObject.put("user_id", strUserId);
            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelTopics> call = api.getChapterTopic(gsonObject);

            call.enqueue(new Callback<ModelTopics>() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onResponse(@NotNull Call<ModelTopics> call, @NotNull Response<ModelTopics> response) {
                    ModelTopics modelTopics = response.body();

                    assert modelTopics != null;
                    layoutManager.setInitialPrefetchItemCount(modelTopics.getData().getContents().size());

                    AdapterTopicVideo adapterCourseDetails = new AdapterTopicVideo(modelTopics.getData().getContents());

                    recyclerView.setAdapter(adapterCourseDetails);

                }

                @Override
                public void onFailure(@NotNull Call<ModelTopics> call, @NotNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
