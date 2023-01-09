package com.project.comparecarts.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.comparecarts.CustomTabLayout;
import com.project.comparecarts.DataPreference;
import com.project.comparecarts.adapters.ViewPagerAdapter;
import com.project.comparecarts.databinding.ActivityMainBinding;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(DataPreference.getInstance(this).getUserId() == null) {
            DataPreference.getInstance(this).setUserId(UUID.randomUUID().toString());
        }
        init();
    }

    public void moveToUsersReceipt() {
        binding.pager.setCurrentItem(2);
    }

    private void init(){
        binding.pager.setAdapter(new ViewPagerAdapter(MainActivity.this));
        binding.pager.setCurrentItem(0);
        CustomTabLayout.setTabLayout(MainActivity.this, binding.tabLayout, binding.pager);
    }
}