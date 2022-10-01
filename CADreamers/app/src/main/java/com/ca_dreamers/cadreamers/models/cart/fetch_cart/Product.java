package com.ca_dreamers.cadreamers.fragments.cart.fetch_cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("cartid")
    @Expose
    private String cartid;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("languageid")
    @Expose
    private String languageid;
    @SerializedName("productmode")
    @Expose
    private String productmode;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("promo")
    @Expose
    private String promo;
    @SerializedName("producttype")
    @Expose
    private String producttype;
    @SerializedName("languagename")
    @Expose
    private Object languagename;
    @SerializedName("discountamt")
    @Expose
    private String discountamt;
    @SerializedName("days")
    @Expose
    private Object days;
    @SerializedName("mrp")
    @Expose
    private String mrp;

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLanguageid() {
        return languageid;
    }

    public void setLanguageid(String languageid) {
        this.languageid = languageid;
    }

    public String getProductmode() {
        return productmode;
    }

    public void setProductmode(String productmode) {
        this.productmode = productmode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public Object getLanguagename() {
        return languagename;
    }

    public void setLanguagename(Object languagename) {
        this.languagename = languagename;
    }

    public String getDiscountamt() {
        return discountamt;
    }

    public void setDiscountamt(String discountamt) {
        this.discountamt = discountamt;
    }

    public Object getDays() {
        return days;
    }

    public void setDays(Object days) {
        this.days = days;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

}
