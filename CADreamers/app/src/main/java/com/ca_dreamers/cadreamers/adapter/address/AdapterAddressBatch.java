package com.ca_dreamers.cadreamers.adapter.address;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.activity.MakePaymentActivity;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.address.Datum;
import com.ca_dreamers.cadreamers.models.address.delete_address.ModelDeleteAddress;
import com.ca_dreamers.cadreamers.models.my_payment.package_payment.ModelComboPayment;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
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

import static com.ca_dreamers.cadreamers.utils.Constant.TAG;


public class AdapterAddressBatch extends RecyclerView.Adapter<AdapterAddressBatch.AddressViewHolder> {

    private String strUserId;
    private String strAddId;
    private  AlertDialog.Builder builder;
    private final String strId;
    private final String strPrice;

    private final Context tContext;
    private final List<Datum> tModels;

    public AdapterAddressBatch(List<Datum> tModels, Context tContext, String strId, String strPrice) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.strId = strId;
        this.strPrice = strPrice;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_address, viewGroup, false);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(tContext);
        strUserId = sharedPrefManager.getUserId();
        builder = new AlertDialog.Builder(view.getContext());

        return new AddressViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder addressViewHolder, final int i) {
        final Datum tModel = tModels.get(i);
        strAddId = tModel.getId();
        final String strAddressName = tModel.getName();
        final String strAddressMobile = tModel.getMobile();
        final String strAddressPinCode = tModel.getPincode();
        final String strAddressAdhar = "https://cadreamers.com/uploads/profile/"+tModel.getAadharCard();
        final String strAddressFullAddress = tModel.getCompleteAddress();


            addressViewHolder.tvAddressCount.setText("Address "+(i+1));
            addressViewHolder.tvAddressName.setText(strAddressName+", "+strAddressFullAddress+" - "+strAddressPinCode+"\n"+strAddressMobile);
        Glide.with(tContext).load(strAddressAdhar).into(addressViewHolder.ivAddressAdharShow);

            addressViewHolder.ivAddressEdit.setOnClickListener(v -> {

                Bundle bundle = new Bundle();
                bundle.putString(Constant.ADDRESS_ID, tModel.getId());
                bundle.putString(Constant.ADDRESS_ACTION, "update");
                bundle.putString(Constant.ADDRESS_NAME, tModel.getName());
                bundle.putString(Constant.ADDRESS_MOBILE, tModel.getMobile());
                bundle.putString(Constant.ADDRESS_ADDRESS, tModel.getCompleteAddress());
                bundle.putString(Constant.ADDRESS_PIN_CODE, tModel.getPincode());
                bundle.putString(Constant.ADDRESS_ADHAR, tModel.getAadharCard());
                Navigation.findNavController(addressViewHolder.itemView).navigate(R.id.nav_update_address, bundle);

            });
            addressViewHolder.btnAddressSelect.setOnClickListener(v -> callPackagePaymentApi());
        addressViewHolder.ivAddressDelete.setOnClickListener(v -> {

            builder.setMessage("Do you want to delete the address?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        callAddressDeleteApi(addressViewHolder);
                        dialog.dismiss();
                    })
                    .setNegativeButton("No", (dialog, id) -> {
                        dialog.cancel();
                        Toast.makeText(tContext, "Cancelled", Toast.LENGTH_SHORT).show();
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Delete Alert!!");
            alert.show();

        });


    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvAddressName)
        protected TextView tvAddressName;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvAddressCount)
        protected TextView tvAddressCount;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivAddressEdit)
        protected ImageView ivAddressEdit;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.btnAddressSelect)
        protected AppCompatButton btnAddressSelect;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivAddressAdharShow)
        protected ImageView ivAddressAdharShow;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivAddressDelete)
        protected ImageView ivAddressDelete;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void callAddressDeleteApi(AddressViewHolder cartViewHolder){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("addrid", strAddId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelDeleteAddress> call = api.deleteAddress(gsonObject);
            call.enqueue(new Callback<ModelDeleteAddress>() {
                @Override
                public void onResponse(@NonNull Call<ModelDeleteAddress> call, @NonNull Response<ModelDeleteAddress> response) {
                    ModelDeleteAddress modelDeleteCart = response.body();
                    assert modelDeleteCart != null;
                    Toast.makeText(tContext,modelDeleteCart.getMessage().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    removeItem(cartViewHolder);
                }

                @Override
                public void onFailure(@NonNull Call<ModelDeleteAddress> call, @NonNull Throwable t) {

                    Log.d(TAG, "Del Add Failure: "+t.getMessage());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void removeItem(AddressViewHolder cartViewHolder) {
        int newPosition = cartViewHolder.getLayoutPosition();
        tModels.remove(newPosition);
        notifyItemRemoved(newPosition);
        notifyItemRangeChanged(newPosition, tModels.size());
    }



    private void callPackagePaymentApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("product_id", strId);
            paramObject.put("payment_method", "instamojo");
            paramObject.put("userid", strUserId);
            paramObject.put("price", strPrice);
            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelComboPayment> call = api.getComboPayment(gsonObject);

            call.enqueue(new Callback<ModelComboPayment>() {
                @Override
                public void onResponse(@NonNull Call<ModelComboPayment> call, @NonNull Response<ModelComboPayment> response) {
                    ModelComboPayment modelComboPayment = response.body();

                    Intent intent = new Intent(tContext, MakePaymentActivity.class);
                    assert modelComboPayment != null;
                    intent.putExtra(Constant.PAYMENT_URL, modelComboPayment.getData().getRedirectLongUrl());
                    tContext.startActivity(intent);
                }

                @Override
                public void onFailure(@NonNull Call<ModelComboPayment> call, @NonNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
