package com.project.comparecarts.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.project.comparecarts.R;
import com.project.comparecarts.UpdateReceiptResultOnItemClickListener;
import com.project.comparecarts.models.Products;

public class UploadReceiptResultAdapter extends RecyclerView.Adapter<UploadReceiptResultAdapter.UploadReceiptResultViewHolder>{

    private Products[] products;
    public UploadReceiptResultAdapter(Products[] products) {
        this.products = products;
    }
    private UpdateReceiptResultOnItemClickListener onItemClickListener;

    public void observeClickListener(UpdateReceiptResultOnItemClickListener mCallback) {
        onItemClickListener = mCallback;
    }
    public int selectedPos = -1;
    public String itemName = "";
    public String price = "";
    public String storeName = "";

    @NonNull
    @Override
    public UploadReceiptResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_receipt_result_view, parent, false);
        return new UploadReceiptResultViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UploadReceiptResultViewHolder holder, int position) {
        holder.itemName.setText(products[position].name);
        holder.price.setText(String.valueOf(products[position].price));
        holder.storeName.setText(products[position].storeName);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = holder.itemName.getText().toString();
                String price = holder.price.getText().toString();
                String storeName = holder.storeName.getText().toString();
                onItemClickListener.onClick(position, storeName, price, itemName);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    protected static class UploadReceiptResultViewHolder extends RecyclerView.ViewHolder {
        EditText storeName, price, itemName;
        LinearLayout root;
        MaterialButton btn;
        public UploadReceiptResultViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            storeName = itemView.findViewById(R.id.et_store_name);
            price = itemView.findViewById(R.id.et_price);
            itemName = itemView.findViewById(R.id.et_item_name);
            btn = itemView.findViewById(R.id.btn_update_value);
        }
    }
}
