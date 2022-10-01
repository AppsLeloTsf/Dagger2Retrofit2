package com.ca_dreamers.cadreamers.adapter.address;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.MainActivity;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.activity.PaymentActivity;
import com.ca_dreamers.cadreamers.adapter.cart.AdapterFetchCart;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.address.Datum;
import com.ca_dreamers.cadreamers.models.address.delete_address.ModelDeleteAddress;
import com.ca_dreamers.cadreamers.models.cart.delete_cart.ModelDeleteCart;
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


public class AdapterAddress extends RecyclerView.Adapter<AdapterAddress.AddressViewHolder> {

    private SharedPrefManager sharedPrefManager;
    private String strUserId;
    private String strAddId;
    private  AlertDialog.Builder builder;

    private Context tContext;
    private List<Datum> tModels;
    private String strCatId;

    public AdapterAddress(List<Datum> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.strCatId = strCatId;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_address, viewGroup, false);

        sharedPrefManager = new SharedPrefManager(tContext);
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

            addressViewHolder.ivAddressEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.ADDRESS_ID, tModel.getId());
                    bundle.putString(Constant.ADDRESS_ACTION, "update");
                    bundle.putString(Constant.ADDRESS_NAME, tModel.getName());
                    bundle.putString(Constant.ADDRESS_MOBILE, tModel.getMobile());
                    bundle.putString(Constant.ADDRESS_ADDRESS, tModel.getCompleteAddress());
                    bundle.putString(Constant.ADDRESS_PIN_CODE, tModel.getPincode());
                    bundle.putString(Constant.ADDRESS_ADHAR, tModel.getAadharCard());
                    Navigation.findNavController(addressViewHolder.itemView).navigate(R.id.nav_update_address, bundle);

                }
            });
            addressViewHolder.btnAddressSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(tContext, PaymentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constant.ADDRESS_ID, tModel.getId());
                    intent.putExtra(Constant.ADDRESS_NAME, tModel.getName());
                    intent.putExtra(Constant.ADDRESS_MOBILE, tModel.getMobile());
                    tContext.startActivity(intent);

                }
            });
        addressViewHolder.ivAddressDelete.setOnClickListener(v -> {

            builder.setMessage("Do you want to delete the address?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            callAddressDeleteApi(addressViewHolder);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(tContext, "Cancelled", Toast.LENGTH_SHORT).show();
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

    public class AddressViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvAddressName)
        protected TextView tvAddressName;
        @BindView(R.id.tvAddressCount)
        protected TextView tvAddressCount;
        @BindView(R.id.ivAddressEdit)
        protected ImageView ivAddressEdit;
        @BindView(R.id.btnAddressSelect)
        protected Button btnAddressSelect;
        @BindView(R.id.ivAddressAdharShow)
        protected ImageView ivAddressAdharShow;
        @BindView(R.id.ivAddressDelete)
        protected ImageView ivAddressDelete;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void callAddressDeleteApi(AddressViewHolder cartViewHolder){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("addrid", strAddId);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelDeleteAddress> call = api.deleteAddress(gsonObject);
            call.enqueue(new Callback<ModelDeleteAddress>() {
                @Override
                public void onResponse(Call<ModelDeleteAddress> call, Response<ModelDeleteAddress> response) {
                    ModelDeleteAddress modelDeleteCart = response.body();
                    Toast.makeText(tContext,modelDeleteCart.getMessage().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    removeItem(cartViewHolder);
                }

                @Override
                public void onFailure(Call<ModelDeleteAddress> call, Throwable t) {

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

}
