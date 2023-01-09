package com.project.comparecarts.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comparecarts.R;
import com.project.comparecarts.models.Products;

public class ReceiptProductAdapter extends RecyclerView.Adapter<ReceiptProductAdapter.ReceiptProductViewHolder> {

    private Products[] products;
    public ReceiptProductAdapter(Products[] products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ReceiptProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipts, parent, false);
        return new ReceiptProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptProductViewHolder holder, int position) {
        holder.receipt.setText(products[position].name);
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    protected static class ReceiptProductViewHolder extends RecyclerView.ViewHolder {
        TextView receipt;
        public ReceiptProductViewHolder(@NonNull View itemView) {
            super(itemView);
            receipt = itemView.findViewById(R.id.tv_receipt);
        }
    }
}
