package com.molitics.molitician.ui.dashboard.voice.voiceDetail;

import android.os.AsyncTask;
import android.util.Log;

import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.util.DownloadFiles;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by om on 21/06/18.
 */

public class DownloadVideoHandler {
    DownloadVideoResponse myView;

    public interface DownloadVideoResponse {

        void onVideoResponse();
    }


    public DownloadVideoHandler(DownloadVideoResponse mView) {
        myView = mView;
    }

    public void downloadVideo(String url) {
        Call<ResponseBody> call = RetrofitRestClient.getInstance().downloadFileWithDynamicUrlAsync(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            boolean writtenToDisk = DownloadFiles.writeResponseBodyToDisk(response.body());
                            Log.d("download", "file download was a success? " + writtenToDisk);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            myView.onVideoResponse();

                        }
                    }.execute();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}
