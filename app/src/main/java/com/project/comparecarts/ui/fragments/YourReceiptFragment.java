package com.project.comparecarts.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.project.comparecarts.CustomProgressDialog;
import com.project.comparecarts.DataPreference;
import com.project.comparecarts.OnItemClickListener;
import com.project.comparecarts.R;
import com.project.comparecarts.adapters.ReceiptProductAdapter;
import com.project.comparecarts.adapters.ReceiptUsersAdapter;
import com.project.comparecarts.api.ApiServices;
import com.project.comparecarts.api.RetrofitHelper;
import com.project.comparecarts.databinding.FragmentYourReceiptBinding;
import com.project.comparecarts.models.Products;
import com.project.comparecarts.models.YourReceiptsParent;
import com.project.comparecarts.ui.activities.ReceiptDetailActivity;

import java.util.ArrayList;
import java.util.UUID;


public class YourReceiptFragment extends Fragment implements View.OnClickListener {
    private FragmentYourReceiptBinding binding;
    private CustomProgressDialog customProgressDialog;

    private YourReceiptFragment() {
    }

    private static YourReceiptFragment instance = null;
    private ApiServices apiServices;

    public static YourReceiptFragment getInstance() {
        if (instance == null) {
            instance = new YourReceiptFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentYourReceiptBinding.inflate(inflater);
        init();
        return binding.getRoot();
    }

    private void init() {
        customProgressDialog = new CustomProgressDialog(requireContext());
        apiServices = RetrofitHelper.getInstance().create(ApiServices.class);
        initializeClickListeners();
        searchByUserName("abc");
    }

    private void initializeClickListeners() {
        binding.btnSearchById.setOnClickListener(this);
        binding.btnSearchByUserName.setOnClickListener(this);
    }

    private void searchById(String id) {
        customProgressDialog.show();
        apiServices.getReceiptById(id).enqueue(new retrofit2.Callback<YourReceiptsParent>() {
            @Override
            public void onResponse(retrofit2.Call<YourReceiptsParent> call, retrofit2.Response<YourReceiptsParent> response) {
                customProgressDialog.dismiss();
                binding.etUserName.setText("");
                if (response.body() != null)
                    initializeReceiptProductsAdapter(response.body().products);
            }

            @Override
            public void onFailure(retrofit2.Call<YourReceiptsParent> call, Throwable t) {
                customProgressDialog.dismiss();
            }
        });
    }

    private void searchByUserName(String userName) {
        customProgressDialog.show();
        String userRandomId = DataPreference.getInstance(requireContext()).getUserId();  //use this for testing
        apiServices.getReceiptByUserName(userName).enqueue(new retrofit2.Callback<ArrayList<YourReceiptsParent>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<YourReceiptsParent>> call, retrofit2.Response<ArrayList<YourReceiptsParent>> response) {
                customProgressDialog.dismiss();
                binding.etId.setText("");
                if(response.body() != null)  initializeReceiptUsersAdapter(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<YourReceiptsParent>> call, Throwable t) {
                customProgressDialog.dismiss();
            }
        });
}

    private void initializeReceiptUsersAdapter(ArrayList<YourReceiptsParent> usersReceipts) {
        ReceiptUsersAdapter adapter = new ReceiptUsersAdapter(usersReceipts);
        binding.rvReceipts.setAdapter(adapter);
        adapter.observerOnItemClick(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(requireActivity(), ReceiptDetailActivity.class);
                intent.putExtra("receiptDetails", new Gson().toJson(usersReceipts.get(position)));
                startActivity(intent);
            }
        });
    }

    private void initializeReceiptProductsAdapter(Products[] products) {
        ReceiptProductAdapter adapter = new ReceiptProductAdapter(products);
        binding.rvReceipts.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search_by_id) {
            if (!binding.etId.getText().toString().trim().equals(""))
                searchById(binding.etId.getText().toString());
        } else if (v.getId() == R.id.btn_search_by_user_name) {
            if (!binding.etUserName.getText().toString().trim().equals(""))
                searchByUserName(binding.etUserName.getText().toString());
        }
    }
}