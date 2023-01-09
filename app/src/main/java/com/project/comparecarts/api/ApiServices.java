package com.project.comparecarts.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.comparecarts.models.YourReceiptsParent;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    @GET(ApiEndPoints.RECEIPT + "{id}")
    Call<YourReceiptsParent> getReceiptById(@Path("id") String id);

    @GET(ApiEndPoints.RECEIPTS)
    Call<ArrayList<YourReceiptsParent>> getReceiptByUserName(@Query("username") String name);

    @Multipart
    @POST(ApiEndPoints.RECEIPT + ApiEndPoints.OCR_CONVERSION)
    Call<YourReceiptsParent> uploadImage(@Part MultipartBody.Part image, @Query("test") Boolean test);

    @POST(ApiEndPoints.RECEIPT + "{id}")
    Call<YourReceiptsParent> updateReceipt(@Path("id") String id, @Body JsonObject body);

}
