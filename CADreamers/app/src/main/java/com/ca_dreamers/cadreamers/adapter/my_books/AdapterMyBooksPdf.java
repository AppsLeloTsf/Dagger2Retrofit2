package com.ca_dreamers.cadreamers.adapter.my_books;

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
import android.widget.ImageView;
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
import com.ca_dreamers.cadreamers.models.my_orders.books.books_details_pdf.Datum;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterMyBooksPdf extends RecyclerView.Adapter<AdapterMyBooksPdf.BooksViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {
    private final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123;
    private String strCourseFullUrl;
    Activity activity;

    private final List<Datum> tModels;
    private Context context;

    public AdapterMyBooksPdf(List<Datum> tModels) {
        this.tModels = tModels;

    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_books_pdf, viewGroup, false);
        context = view.getContext();
        activity = (Activity)context;
    return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder booksViewHolder, final int i) {
        requestPermissions();
        final Datum tModel = tModels.get(i);
            booksViewHolder.tvPurchasedBooksPdfTitle.setText(tModel.getTitle());

        booksViewHolder.llPurchasedBooksPdf.setOnClickListener(v -> {
            byte[] decrypt= Base64.decode( tModel.getPdfUrl(), Base64.DEFAULT);
            String pdf = new String(decrypt, StandardCharsets.UTF_8);
            Intent intent = new Intent(context, PdfActivity.class);
            intent.putExtra(Constant.BOOKS_PDF_URL, pdf);
            context.startActivity(intent);
        });

        String strCourseTitle = tModel.getTitle();
               String strCourseFullUrl = tModel.getPdfUrl();
                Log.d("STR_TITLE", strCourseTitle);
        File[] filesBooks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            filesBooks = new File(context.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS).listFiles();
        }else {
            filesBooks = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS).listFiles();
        }
                if (filesBooks != null) {
                    for (File file : filesBooks) {
                        if (file.isFile()) {
                            if ((strCourseTitle + ".pdf").equals(file.getName())) {
                                booksViewHolder.tvPurchasedBooksPdfTitle.setTextColor(ContextCompat.getColor(context, R.color.grey));
                                booksViewHolder.ivDownloadBooks.setVisibility(View.GONE);
                                booksViewHolder.llPurchasedBooksPdf.setOnClickListener(v -> {
                                        Intent intentUrl = new Intent(context, PdfViewerActivity.class);
                                        intentUrl.putExtra(Constant.BOOKS_PDF_URL, file.getAbsolutePath());
                                        context.startActivity(intentUrl);
                                });
                                break;
                            } else {
                                booksViewHolder.ivDownloadBooks.setVisibility(View.VISIBLE);
                                booksViewHolder.tvPurchasedBooksPdfTitle.setTextColor(ContextCompat.getColor(context, R.color.logo));
                                booksViewHolder.llPurchasedBooksPdf.setOnClickListener(v -> {
                                    byte[] decrypt= Base64.decode( tModel.getPdfUrl(), Base64.DEFAULT);
                                    String pdf = new String(decrypt, StandardCharsets.UTF_8);
                                    Intent intent = new Intent(context, PdfActivity.class);
                                    intent.putExtra(Constant.BOOKS_PDF_URL, pdf);
                                    context.startActivity(intent);
                                });

                            }
                        }
                    }
                }
        booksViewHolder.ivDownloadBooks.setOnClickListener(v -> {
            booksViewHolder.ivDownloadBooks.setVisibility(View.GONE);
            newDownload(strCourseFullUrl, strCourseTitle);

        });
    }
    public void newDownload(String url, String strCourseTitle) {
        byte[] decrypt= Base64.decode(url, Base64.DEFAULT);
        String pdf = new String(decrypt, StandardCharsets.UTF_8);
        final DownloadMyBooks downloadMyBooks = new DownloadMyBooks(context, strCourseTitle);
        downloadMyBooks.execute(pdf);

    }


    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public static class BooksViewHolder extends RecyclerView.ViewHolder{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.rlPurchasedBooksPdf)
        protected RelativeLayout llPurchasedBooksPdf;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvPurchasedBooksPdfTitle)
        protected TextView tvPurchasedBooksPdfTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivDownloadBooks)
        protected ImageView ivDownloadBooks;
        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void checkFolderCourses() {

        File file;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            file = new File(context.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES);
        } else {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES);
        }

        if (!file.exists()) {
            file.mkdirs();
        }
    }
    public void checkFolderBooks() {
        File file;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            file = new File(context.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS);
        } else {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS);
        }

        if (!file.exists()) {
            file.mkdirs();
        }
    }
    public void requestPermissions() {

        Dexter.withContext(context)

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
                }).withErrorListener(error -> Toast.makeText(context, "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread().check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs storage permission to download videos for offline mode. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            activity.startActivityForResult(intent, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            Toast.makeText(context, "You must have to enable the storage permission to save the videos for offline.", Toast.LENGTH_SHORT).show();
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

    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    private class DownloadMyBooks extends AsyncTask<String, Integer, String> {
        private final Context context;
        private final String strTitle;
        private File file;
        private File fileDel;
        private String fileN = null;
        private Dialog downloadDialog;
        private PowerManager.WakeLock mWakeLock;

        private DownloadMyBooks(Context context, String strTitle) {
            this.context = context;
            this.strTitle = strTitle;
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

                fileN = strTitle + ".pdf";
                Log.d("FILE_NAME", "FILE_N: "+fileN);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    file = new File(context.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS, fileN);
                } else {
                    file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+ Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS, fileN);

                    Log.d("FILE_PATH", file.toString());
                }
                output = new FileOutputStream(file);

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

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        fileDel = new File(context.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS + File.separator);
                    } else {
                        fileDel = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS + File.separator);
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
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();

            }

        }

    }
}
