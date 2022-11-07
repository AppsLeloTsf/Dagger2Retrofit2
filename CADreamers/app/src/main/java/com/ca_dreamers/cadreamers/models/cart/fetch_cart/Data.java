package com.ca_dreamers.cadreamers.models.cart.fetch_cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("tot_item")
    @Expose
    private Integer totItem;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("mrp")
    @Expose
    private Integer mrp;
    @SerializedName("p_mode")
    @Expose
    private String pMode;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public Integer getTotItem() {
        return totItem;
    }

    public void setTotItem(Integer totItem) {
        this.totItem = totItem;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMrp() {
        return mrp;
    }

    public void setMrp(Integer mrp) {
        this.mrp = mrp;
    }

    public String getpMode() {
        return pMode;
    }

    public void setpMode(String pMode) {
        this.pMode = pMode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
