package com.project.comparecarts.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comparecarts.OnItemClickListener;
import com.project.comparecarts.R;
import com.project.comparecarts.models.Products;

public class ReceiptDetailAdapter extends RecyclerView.Adapter<ReceiptDetailAdapter.ReceiptDetailViewHolder>{

    Products[] products;
    public ReceiptDetailAdapter(Products[] products){
        this.products = products;
    }

    @NonNull
    @Override
    public ReceiptDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_table_view, parent, false);
        return new ReceiptDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptDetailViewHolder holder, int position) {
        holder.name.setText(products[position].name);
        holder.price.setText(String.valueOf(products[position].price));
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    protected static class ReceiptDetailViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        public ReceiptDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_price);
        }
    }
}
