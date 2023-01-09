package com.project.comparecarts.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YourReceiptsParent {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("storename")
    @Expose
    public String storeName;

    @SerializedName("zipcode")
    @Expose
    public int zipcode;

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("products")
    @Expose
    public Products[] products;

    @SerializedName("imagePath")
    @Expose
    public String imagePath;

    @SerializedName("transactiondate")
    @Expose
    public String transactionDate;

    @SerializedName("createddate")
    @Expose
    public String createdDate;

    @SerializedName("total")
    @Expose
    public double total;

}
