package com.project.comparecarts.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products {

    @SerializedName("id")
    @Expose
    public long id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("price")
    @Expose
    public double price;

    @SerializedName("unit")
    @Expose
    public String unit;

    @SerializedName("imageurl")
    @Expose
    public String imageUrl;

    @SerializedName("brand")
    @Expose
    public String brand;

    @SerializedName("storename")
    @Expose
    public String storeName;

}
