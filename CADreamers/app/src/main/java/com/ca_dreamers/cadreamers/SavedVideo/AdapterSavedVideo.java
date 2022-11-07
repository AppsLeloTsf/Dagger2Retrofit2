package com.ca_dreamers.cadreamers.SavedVideo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.activity.PdfViewerActivity;
import com.ca_dreamers.cadreamers.activity.VideoActivity;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSavedVideo extends RecyclerView.Adapter<AdapterSavedVideo.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<ModelSavedVideo> courseModalArrayList;
    private Context context;
    private String strFolderName;
    private File fileDel;
    private  AlertDialog.Builder builder;

    // creating a constructor for our variables.
    public AdapterSavedVideo(ArrayList<ModelSavedVideo> courseModalArrayList, Context context, String strFolderName) {
        this.courseModalArrayList = courseModalArrayList;
        this.strFolderName = strFolderName;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSavedVideo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_video, parent, false);
        builder = new AlertDialog.Builder(view.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSavedVideo.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        ModelSavedVideo tModel = courseModalArrayList.get(position);
        holder.courseNameTV.setText(tModel.getStrNameOfFile());
        if (tModel.getStrNameOfFile().contains(".mp4")) {
            holder.courseNameTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_video, 0, 0, 0);
        }else if (tModel.getStrNameOfFile().contains(".pdf")){
            holder.courseNameTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pdf, 0, 0, 0);
        }
        holder.rlSavedVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tModel.getStrNameOfFile().contains(".mp4")) {
                    Intent intentUrl = new Intent(context, VideoActivity.class);
                    intentUrl.putExtra(Constant.MY_FILE_PATH, tModel.getStrAbsolutePath());
                    context.startActivity(intentUrl);
                }else if (tModel.getStrNameOfFile().contains(".pdf")){
                    Intent intentUrl = new Intent(context, PdfViewerActivity.class);
                    intentUrl.putExtra(Constant.BOOKS_PDF_URL, tModel.getStrAbsolutePath());
                    context.startActivity(intentUrl);
                }
            }
        });
        holder.ivDeleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Do you want to delete the file?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    fileDel = new File(context.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + strFolderName+ File.separator);
                                } else {
                                    fileDel = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + strFolderName+ File.separator);
                                }
                                File file = new File(fileDel, tModel.getStrNameOfFile());

                                file.delete();
                                removeItem(holder);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(context, "Cancelled.", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete Alert!!");
                alert.show();

            }
        });
    }
    private void removeItem(AdapterSavedVideo.ViewHolder viewHolder) {
        int newPosition = viewHolder.getLayoutPosition();
        courseModalArrayList.remove(newPosition);
        notifyItemRemoved(newPosition);
        notifyItemRangeChanged(newPosition, courseModalArrayList.size());
        Toast.makeText(context, "File deleted Successfully!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public int getItemCount() {
        return courseModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.rlSavedVideo)
        protected RelativeLayout rlSavedVideo;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.idTVCourseName)
        protected TextView courseNameTV;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivDeleteFile)
        protected AppCompatImageView ivDeleteFile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}

