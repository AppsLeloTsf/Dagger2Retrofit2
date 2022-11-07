package com.ca_dreamers.cadreamers.adapter.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.books.Datum;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterBooksList extends RecyclerView.Adapter<AdapterBooksList.CategoryViewHolder> {

    private List<Datum> tModels;
    private Context tContext;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private String strCatId;

    public AdapterBooksList(List<Datum> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.strCatId = strCatId;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_books_list, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int i) {
        final Datum tModel = tModels.get(i);
        final String strCourseId = tModel.getId();
        final String strCourseTitle = tModel.getSubCategoryName();


            categoryViewHolder.tvBooksListTitle.setText(strCourseTitle);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                        categoryViewHolder.rvBooksDetails.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(tModel.getCourses().size());

        AdapterBooksDetails adapterCourseDetails
                = new AdapterBooksDetails(tModel.getCourses(), tContext);
        categoryViewHolder.rvBooksDetails.setLayoutManager(layoutManager);
        categoryViewHolder.rvBooksDetails.setAdapter(adapterCourseDetails);
        categoryViewHolder.rvBooksDetails.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvBooksListTitle)
        protected TextView tvBooksListTitle;

        @BindView(R.id.rvBooksDetails)
        protected RecyclerView rvBooksDetails;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
