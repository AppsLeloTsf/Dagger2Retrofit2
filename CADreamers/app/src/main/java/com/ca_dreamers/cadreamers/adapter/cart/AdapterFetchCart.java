package com.ca_dreamers.cadreamers.adapter.cart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.activity.MainActivity;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.cart.delete_cart.ModelDeleteCart;
import com.ca_dreamers.cadreamers.models.cart.fetch_cart.Product;
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


public class AdapterFetchCart extends RecyclerView.Adapter<AdapterFetchCart.CartViewHolder> {

    private SharedPrefManager sharedPrefManager;
    private String strUserId;
    private String strProdId;
    private String strProMode;
    private  AlertDialog.Builder builder;
    private Activity activity;

    private Context tContext;
    private List<Product> tModels;

    public AdapterFetchCart(List<Product> tModels, String strProMode, Context tContext, Activity activity) {
        this.tModels = tModels;
        this.strProMode = strProMode;
        this.tContext = tContext;
        this.activity = activity;


    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart, viewGroup, false);
        sharedPrefManager = new SharedPrefManager(view.getContext());
        strUserId = sharedPrefManager.getUserId();
        builder = new AlertDialog.Builder(view.getContext());

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, final int i) {
        final Product tModel = tModels.get(i);
        strProdId = tModel.getId();


        cartViewHolder.tvCartProdTitle.setText(tModel.getName());
        cartViewHolder.tvCartProdMode.setText(strProMode);
        cartViewHolder.tvCartProdPrice.setText(tModel.getPrice());
        Glide.with(tContext)
                .load(tModel.getImage())
                .into(cartViewHolder.ivCartProdIcon);


            cartViewHolder.ivCartProdDelete.setOnClickListener(v -> {

                builder.setMessage("Do you want to delete "+tModel.getName()+" from cart?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                callCartDeleteApi(cartViewHolder, tModel.getName());
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(tContext,tModel.getName()+ " is not deleted",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete Alert!!");
                alert.show();

            });
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }


    public static class CartViewHolder extends RecyclerView.ViewHolder{

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvCartProdTitle)
        protected TextView tvCartProdTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvCartProdMode)
        protected TextView tvCartProdMode;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvCartProdPrice)
        protected TextView tvCartProdPrice;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivCartProdIcon)
        protected ImageView ivCartProdIcon;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivCartProdDelete)
        protected ImageView ivCartProdDelete;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
    public void callCartDeleteApi(CartViewHolder cartViewHolder, String strProdName){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", strUserId);
            paramObject.put("productid", strProdId);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelDeleteCart> call = api.deleteCart(gsonObject);
            call.enqueue(new Callback<ModelDeleteCart>() {
                @Override
                public void onResponse(@NonNull Call<ModelDeleteCart> call, @NonNull Response<ModelDeleteCart> response) {
                    ModelDeleteCart modelDeleteCart = response.body();
                    if(activity instanceof MainActivity){
                        ((MainActivity)activity).callFetchCartApi();
                    }
                    removeItem(cartViewHolder);
                    Toast.makeText(tContext,strProdName+" is deleted successfully from your cart",
                            Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(@NonNull Call<ModelDeleteCart> call, @NonNull Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void removeItem(CartViewHolder cartViewHolder) {
        int newPosition = cartViewHolder.getLayoutPosition();
        tModels.remove(newPosition);
        notifyItemRemoved(newPosition);
        notifyItemRangeChanged(newPosition, tModels.size());
    }
}
