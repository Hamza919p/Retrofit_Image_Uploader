package com.project.comparecarts.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.comparecarts.R;


public class HomeFragment extends Fragment {

    private HomeFragment(){}
    private static HomeFragment instance = null;
    public static HomeFragment getInstance() {
        if(instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}