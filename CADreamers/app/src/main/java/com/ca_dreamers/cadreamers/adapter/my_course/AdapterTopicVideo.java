package com.ca_dreamers.cadreamers.adapter.my_course;

import android.Manifest;
import android.annotation.SuppressLint;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import android.os.PowerManager;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.activity.PdfActivity;
import com.ca_dreamers.cadreamers.activity.PdfViewerActivity;
import com.ca_dreamers.cadreamers.activity.VideoActivity;
import com.ca_dreamers.cadreamers.activity.VimeoPlayerActivity;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.topics.Content;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterTopicVideo extends RecyclerView.Adapter<AdapterTopicVideo.TopicViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {
    private final ArrayList<File>  filesAll = new ArrayList<>();
    private final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123;


    private Activity activity;
    private Context tContext;
    private final List<Content> tModels;




    public AdapterTopicVideo(List<Content> tModels) {
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_course_video, viewGroup, false);

        tContext = view.getContext();
        activity = (Activity)tContext;
        return new TopicViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder topicViewHolder, final int i) {
        requestPermissions();
        final Content tModel = tModels.get(i);
        String strCourseFullUrl = null;
            topicViewHolder.tvTopicContentTitle.setText(tModel.getTitle());
        if (tModel.getCtype().equals("video")) {
            strCourseFullUrl = tModel.getVideoContent().getFullUrl();

            topicViewHolder.tvTopicContentTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_video, 0, 0, 0);
        }else if (tModel.getCtype().equals("docs")){
            strCourseFullUrl = tModel.getDocContent().getFilePath();
            topicViewHolder.tvTopicContentTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pdf, 0, 0, 0);
        }


        topicViewHolder.rlChapterTopic.setOnClickListener(v -> {
            Intent intent;
            if (tModel.getCtype().equals("video")) {
                intent = new Intent(tContext, VimeoPlayerActivity.class);
                intent.putExtra(Constant.TOPIC_MODEL, tModel);
                intent.putExtra(Constant.CONTENT_ID, tModel.getId());
                intent.putExtra(Constant.CONTENT_TITLE, tModel.getTitle());
                intent.putExtra(Constant.CONTENT_EMBED, tModel.getVideoContent().getEmbed());
                intent.putExtra(Constant.CONTENT_FULL_URL, tModel.getVideoContent().getFullUrl());
                tContext.startActivity(intent);
            }else if(tModel.getCtype().equals("docs")){
                byte[] decrypt= Base64.decode(tModel.getDocContent().getFilePath(), Base64.DEFAULT);
                String pdf = new String(decrypt, StandardCharsets.UTF_8);
                intent = new Intent(tContext, PdfActivity.class);
                intent.putExtra(Constant.BOOKS_PDF_URL, pdf);
                tContext.startActivity(intent);
            }
        });

           String strCourseTitle = tModel.getTitle();
           String strCourseType = tModel.getCtype();

        File[] filesVideo;
        File[] filesBooks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                filesVideo = new File(tContext.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES).listFiles();
                filesBooks = new File(tContext.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS).listFiles();
            } else {
                filesVideo = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES).listFiles();
                filesBooks = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS).listFiles();
            }
        if( filesVideo != null) {
            filesAll.addAll(Arrays.asList(filesVideo));
        }
       if(filesBooks != null) {
           filesAll.addAll(Arrays.asList(filesBooks));
       }
        for (File file : filesAll) {

            if (file.isFile()) {
                if ((strCourseTitle + ".mp4").equals(file.getName()) || (strCourseTitle + ".pdf").equals(file.getName())) {
                    topicViewHolder.btnDownload.setVisibility(View.GONE);
                    topicViewHolder.tvTopicContentTitle.setTextColor(ContextCompat.getColor(tContext, R.color.grey));
                    topicViewHolder.rlChapterTopic.setOnClickListener(v -> {
                        if (tModel.getCtype().equals("video")) {
                            Intent intentUrl = new Intent(tContext, VideoActivity.class);
                            intentUrl.putExtra(Constant.MY_FILE_PATH, file.getAbsolutePath());
                            tContext.startActivity(intentUrl);
                        } else if (tModel.getCtype().equals("docs")) {
                            Intent intentUrl = new Intent(tContext, PdfViewerActivity.class);
                            intentUrl.putExtra(Constant.BOOKS_PDF_URL, file.getAbsolutePath());
                            tContext.startActivity(intentUrl);
                        }
                    });

                    break;
                }
            else {
                    topicViewHolder.btnDownload.setVisibility(View.VISIBLE);
                    topicViewHolder.tvTopicContentTitle.setTextColor(ContextCompat.getColor(tContext, R.color.logo));
                    topicViewHolder.rlChapterTopic.setOnClickListener(v -> {
                        Intent intent;
                        if (tModel.getCtype().equals("video")) {
                            intent = new Intent(tContext, VimeoPlayerActivity.class);
                            intent.putExtra(Constant.TOPIC_MODEL, tModel);
                            intent.putExtra(Constant.CONTENT_ID, tModel.getId());
                            intent.putExtra(Constant.CONTENT_TITLE, tModel.getTitle());
                            intent.putExtra(Constant.CONTENT_EMBED, tModel.getVideoContent().getEmbed());
                            intent.putExtra(Constant.CONTENT_FULL_URL, tModel.getVideoContent().getFullUrl());
                            tContext.startActivity(intent);
                        }else if(tModel.getCtype().equals("docs")){
                            byte[] decrypt= Base64.decode(tModel.getDocContent().getFilePath(), Base64.DEFAULT);
                            String pdf = new String(decrypt, StandardCharsets.UTF_8);
                            intent = new Intent(tContext, PdfActivity.class);
                            intent.putExtra(Constant.BOOKS_PDF_URL, pdf);
                            tContext.startActivity(intent);
                        }
                    });

                }
            }
        }

        String finalStrCourseFullUrl = strCourseFullUrl;
        topicViewHolder.btnDownload.setOnClickListener(v -> {
            topicViewHolder.btnDownload.setVisibility(View.GONE);
            newDownload(finalStrCourseFullUrl, strCourseTitle, strCourseType);
        });
    }
    public void newDownload(String url, String strCourseTitle, String strCourseType) {
        byte[] decrypt= Base64.decode(url, Base64.DEFAULT);
        String text = new String(decrypt, StandardCharsets.UTF_8);
        final DownloadCourse downloadCourse = new DownloadCourse(tContext, strCourseTitle, strCourseType);
        downloadCourse.execute(text);
        Log.d("TSF_APPS", "Course_Url: "+text);

    }
    @Override
    public int getItemCount() {
        return tModels.size();
    }
    public static class TopicViewHolder extends RecyclerView.ViewHolder{

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvTopicContentTitle)
        protected TextView tvTopicContentTitle;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.btnDownload)
        protected Button btnDownload;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.rlChapterTopic)
        protected RelativeLayout rlChapterTopic;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void checkFolderCourses() {

        File file;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            file = new File(tContext.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES);
        } else {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES);
        }

        if (!file.exists()) file.mkdirs();
    }
    public void checkFolderBooks() {
        File file;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            file = new File(tContext.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS);
        } else {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS);
        }

        if (!file.exists()) {
            file.mkdirs();
        }
    }


    public void requestPermissions() {

        Dexter.withContext(tContext)

                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            checkFolderCourses();
                            checkFolderBooks();
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(error -> Toast.makeText(tContext, "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread().check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(tContext);

        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs storage permission to download videos for offline mode. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", tContext.getPackageName(), null);
            intent.setData(uri);
            activity.startActivityForResult(intent, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            Toast.makeText(tContext, "You must have to enable the storage permission to save the videos for offline.", Toast.LENGTH_SHORT).show();
            showSettingsDialog();
            dialog.cancel();
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkFolderCourses();
                checkFolderBooks();
            } else {
                showSettingsDialog();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadCourse extends AsyncTask<String, Integer, String> {
        private final Context context;
        private final String strTitle;
        private final String strCourseType;
        private String fileN = null;

        private File fileDel;
        private Dialog downloadDialog;
        private PowerManager.WakeLock mWakeLock;



        private DownloadCourse(Context context, String strTitle, String strCourseType) {
            this.context = context;
            this.strTitle = strTitle;
            this.strCourseType = strCourseType;
        }
        private NumberProgressBar bnp;

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();

                input = connection.getInputStream();


                File file;
                if (strCourseType.equals("video")){
                    fileN = strTitle + ".mp4";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        file = new File(context.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES, fileN);
                    } else {
                        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+ Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES, fileN);

                    }
                }
                else {
                    fileN = strTitle + ".pdf";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        file = new File(context.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS, fileN);

                    } else {
                        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+ Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS, fileN);
                    }
                }
                output = new FileOutputStream(file);
                Log.d("TSF_APPS", "Type: "+strCourseType+ "\tPath: "+ file.getAbsolutePath() + "\tTitle: "+fileN);

                byte[] data = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire(10*60*1000L /*10 minutes*/);
            LayoutInflater dialogLayout = LayoutInflater.from(context);
            @SuppressLint("InflateParams")
            View DialogView = dialogLayout.inflate(R.layout.progress_dialog, null);
            downloadDialog = new Dialog(context, R.style.CustomAlertDialog);
            downloadDialog.setContentView(DialogView);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(downloadDialog.getWindow().getAttributes());
            lp.width = (context.getResources().getDisplayMetrics().widthPixels);
            lp.height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.65);
            downloadDialog.getWindow().setAttributes(lp);

            final Button cancel = DialogView.findViewById(R.id.cancel_btn);
            cancel.setOnClickListener(v -> {

                cancel(true);

                if (fileN.contains(".mp4")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        fileDel = new File(context.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES + File.separator);
                    } else {
                        fileDel = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES + File.separator);
                    }
                }else if (fileN.contains(".pdf")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        fileDel = new File(context.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS + File.separator);
                    } else {
                        fileDel = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS + File.separator);
                    }
                }
                File file = new File(fileDel, fileN);

                file.delete();
                notifyDataSetChanged();
                Toast.makeText(context, "Downloading Canceled...", Toast.LENGTH_SHORT).show();
                downloadDialog.dismiss();
            });


            downloadDialog.setCancelable(false);
            downloadDialog.setCanceledOnTouchOutside(false);
            bnp = DialogView.findViewById(R.id.number_progress_bar);
            bnp.setProgress(0);
            bnp.setMax(100);
            downloadDialog.show();

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            bnp.setProgress(progress[0]);
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            downloadDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
                Log.e("Download error", ": " + result);
            } else {
                notifyDataSetChanged();
                Toast.makeText(context, fileN+" downloaded.", Toast.LENGTH_SHORT).show();

            }


        }

    }

}
