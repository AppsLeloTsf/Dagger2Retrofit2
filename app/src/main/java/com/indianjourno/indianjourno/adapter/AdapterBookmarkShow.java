package com.indianjourno.indianjourno.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indianjourno.indianjourno.activity.BookmarkDetails;
import com.indianjourno.indianjourno.model.bookmarks.Bookmark;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterBookmarkShow extends RecyclerView.Adapter<AdapterBookmarkShow.BookmarkViewHolder> {

    private Context tContext;
    private List<Bookmark> tBookmark;

    public AdapterBookmarkShow(List<Bookmark> tBookmark) {
        this.tBookmark = tBookmark;
     }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_bookmark_item, viewGroup, false);
        tContext = view.getContext();
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder bookmarkViewHolder, final int i) {
        final Bookmark tBookmarks = tBookmark.get(i);
        final String strNewsId = tBookmarks.getNewsId();
        final String strNewsTitle = tBookmarks.getNewsTitle();
        final String strNewsDate = tBookmarks.getNewsSchedule();
        final String strNewsContent = tBookmarks.getNewsContent();
        final String strImgUrl = tBookmarks.getNewsPhoto();

            bookmarkViewHolder.tvBookmarkTitle.setText(strNewsTitle);
        Glide.with(tContext)
                .load(Constant.IMG_URL+strImgUrl)
                .skipMemoryCache(true)
                .into(bookmarkViewHolder.ivBookmarkImage);
            bookmarkViewHolder.llBookmarkNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tIntent = new Intent(tContext, BookmarkDetails.class);
//                    tIntent.putExtra(Constant.CAT_ID, strCatId);
                    tIntent.putExtra(Constant.NEWS_ID, strNewsId);
                    tIntent.putExtra(Constant.NEWS_TITLE, strNewsTitle);
                    tIntent.putExtra(Constant.NEWS_DATE, strNewsDate);
                    tIntent.putExtra(Constant.NEWS_CONTENT, strNewsContent);
                    tIntent.putExtra(Constant.NEWS_IMAGE, strImgUrl);
                    tContext.startActivity(tIntent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return tBookmark.size();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivBookmarkImage)
        protected ImageView ivBookmarkImage;
        @BindView(R.id.tvBookmarkTitle)
        protected TextView tvBookmarkTitle;
        @BindView(R.id.llBookmarkNews)
        protected LinearLayout llBookmarkNews;
        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
