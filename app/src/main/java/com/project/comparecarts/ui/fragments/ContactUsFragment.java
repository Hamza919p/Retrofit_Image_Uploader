package com.project.comparecarts.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.comparecarts.R;

public class ContactUsFragment extends Fragment {
    private ContactUsFragment(){}
    private static ContactUsFragment instance = null;
    public static ContactUsFragment getInstance() {
        if(instance == null) {
            instance = new ContactUsFragment();
        }
        return instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }
}