package com.project.comparecarts;


import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CustomTabLayout {
    public static void setTabLayout(Activity activity, TabLayout tabLayout, ViewPager2 pager) {
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy(){
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0) {
                    tab.setText("Home");
                } else if(position == 1) {
                    tab.setText("Upload");
                } else if(position == 2) {
                    tab.setText("Your receipt");
                } else if(position == 3) {
                    tab.setText("Contact us");
                }
            }
        }).attach();
    }
}
