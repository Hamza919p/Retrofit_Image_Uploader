package com.project.comparecarts.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.project.comparecarts.OnItemClickListener;
import com.project.comparecarts.adapters.ReceiptDetailAdapter;
import com.project.comparecarts.databinding.ActivityReceiptDetailBinding;
import com.project.comparecarts.models.YourReceiptsParent;

public class ReceiptDetailActivity extends AppCompatActivity {
    private ActivityReceiptDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReceiptDetailBinding.inflate(getLayoutInflater());
        init();
        setContentView(binding.getRoot());
    }

    private void init() {
        getData() ;
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        YourReceiptsParent userReceipt = new Gson().fromJson(getIntent().getStringExtra("receiptDetails"), YourReceiptsParent.class);
        setValues(userReceipt);
        initializeAdapter(userReceipt);
    }

    @SuppressLint("SetTextI18n")
    private void setValues(YourReceiptsParent userReceipt) {
        binding.tvReceiptId.setText(userReceipt.storeName + "(" + userReceipt.zipcode+")");
        if(userReceipt.createdDate != null)
            binding.tvCreatedDate.setText("Created Date: " + userReceipt.createdDate);
        if(userReceipt.transactionDate != null)
            binding.tvTransDate.setText("Created Date: " + userReceipt.createdDate);
        binding.tvTotal.setText("Total: " + userReceipt.total);

        if(userReceipt.imagePath != null)
            binding.tvReceiptImage.setText(userReceipt.imagePath);
        else
            binding.tvReceiptImage.setVisibility(View.GONE);
        binding.tvReceiptImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReceiptDetailActivity.this, LoadImageActivity.class);
                intent.putExtra("image_url", userReceipt.imagePath);
                startActivity(intent);
            }
        });
    }

    private void initializeAdapter(YourReceiptsParent userReceipt) {
        ReceiptDetailAdapter adapter = new ReceiptDetailAdapter(userReceipt.products);
        binding.rvProducts.setAdapter(adapter);
    }

}