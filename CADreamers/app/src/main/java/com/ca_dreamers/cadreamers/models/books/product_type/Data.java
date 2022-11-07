package com.ca_dreamers.cadreamers.models.books.product_type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("list")
    @Expose
    private java.util.List<com.ca_dreamers.cadreamers.models.books.product_type.List> list = null;

    public java.util.List<com.ca_dreamers.cadreamers.models.books.product_type.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.ca_dreamers.cadreamers.models.books.product_type.List> list) {
        this.list = list;
    }
}
