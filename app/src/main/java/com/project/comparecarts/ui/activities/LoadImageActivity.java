package com.project.comparecarts.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.project.comparecarts.R;
import com.project.comparecarts.databinding.ActivityLoadImageBinding;

public class LoadImageActivity extends AppCompatActivity {
    private ActivityLoadImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoadImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String imageUrl = getIntent().getStringExtra("image_url");
        binding.imageView.setImageURI(Uri.parse(imageUrl));
    }
}