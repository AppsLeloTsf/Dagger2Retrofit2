package com.ca_dreamers.cadreamers.adapter.my_orders.course;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.Topic;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.topics.ModelTopics;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterCourseDetailsChapterDetails extends RecyclerView.Adapter<AdapterCourseDetailsChapterDetails.TopicViewHolder> {

    private SharedPrefManager sharedPrefManager;
    private LinearLayoutManager layoutManager;
    private String strTopicId;
    private String strUserId;
    private final Context tContext;
    private final List<Topic> tModels;
    private final String strChapterId;
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public AdapterCourseDetailsChapterDetails(List<Topic> tModels, String strChapterId, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.strChapterId = strChapterId;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chapter_detail, viewGroup, false);

        sharedPrefManager = new SharedPrefManager(tContext);
        strUserId = sharedPrefManager.getUserId();
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder topicViewHolder, final int i) {
        final Topic tModel = tModels.get(i);
        strTopicId = tModel.getId();
        final String strCourseTitle = tModel.getTopicName();
            topicViewHolder.tvChapterDetailsTitle.setText("");
         layoutManager = new LinearLayoutManager(
                topicViewHolder.rvTopicVideo.getContext(), LinearLayoutManager.HORIZONTAL, false);
        topicViewHolder.rvTopicVideo.setLayoutManager(layoutManager);
        callCourseApi(topicViewHolder.rvTopicVideo);
        topicViewHolder.tvChapterDetailsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicViewHolder.rlChapterDetailsItem.removeAllViews();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvChapterDetailsTitle)
        protected TextView tvChapterDetailsTitle;
        @BindView(R.id.rvTopicVideo)
        protected RecyclerView rvTopicVideo;

        @BindView(R.id.rlChapterDetailsItem)
        protected LinearLayout rlChapterDetailsItem;
        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private void callCourseApi(RecyclerView recyclerView){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("chapter_id", strChapterId);
            paramObject.put("topic_id", strTopicId);
            paramObject.put("user_id", strUserId);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelTopics> call = api.getChapterTopic(gsonObject);

            call.enqueue(new Callback<ModelTopics>() {
                @Override
                public void onResponse(Call<ModelTopics> call, Response<ModelTopics> response) {
                    ModelTopics modelTopics = response.body();

                    layoutManager.setInitialPrefetchItemCount(modelTopics.getData().getContents().size());

                    AdapterTopicVideo adapterCourseDetails
                            = new AdapterTopicVideo(modelTopics.getData().getContents(), tContext);

                    recyclerView.setAdapter(adapterCourseDetails);
                    recyclerView.setRecycledViewPool(viewPool);

                }

                @Override
                public void onFailure(Call<ModelTopics> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
