package com.ca_dreamers.cadreamers.models.cart.checkout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("total_product")
    @Expose
    private Integer totalProduct;
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;
    @SerializedName("discountamt")
    @Expose
    private Integer discountamt;
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;

    public Integer getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(Integer totalProduct) {
        this.totalProduct = totalProduct;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getDiscountamt() {
        return discountamt;
    }

    public void setDiscountamt(Integer discountamt) {
        this.discountamt = discountamt;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }
}
