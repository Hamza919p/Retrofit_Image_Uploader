package com.project.comparecarts.adapters;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.comparecarts.OnItemClickListener;
import com.project.comparecarts.R;
import com.project.comparecarts.models.YourReceiptsParent;

import java.util.List;

public class ReceiptUsersAdapter extends RecyclerView.Adapter<ReceiptUsersAdapter.ReceiptUsersViewHolder>{

    private List<YourReceiptsParent> usersReceipt;
    private OnItemClickListener onItemClickListener;
    public void observerOnItemClick(OnItemClickListener mCallback) {
        onItemClickListener = mCallback;
    }

    public ReceiptUsersAdapter(List<YourReceiptsParent> usersReceipt) {
        this.usersReceipt = usersReceipt;
    }

    @NonNull
    @Override
    public ReceiptUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_users, parent, false);
        return new ReceiptUsersViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReceiptUsersViewHolder holder, int position) {
        holder.storeName.setText("StoreName: " + usersReceipt.get(position).storeName
                + "(" + usersReceipt.get(position).zipcode + ")");
        holder.total.setText("Total: " + usersReceipt.get(position).total);

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersReceipt.size();
    }

    protected class ReceiptUsersViewHolder extends RecyclerView.ViewHolder {
        TextView storeName, total;
        LinearLayout root;
        public ReceiptUsersViewHolder(@NonNull View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.tv_store_name);
            total = itemView.findViewById(R.id.tv_total);
            root = itemView.findViewById(R.id.root);
        }
    }
}
