package com.project.comparecarts.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.comparecarts.ui.fragments.ContactUsFragment;
import com.project.comparecarts.ui.fragments.HomeFragment;
import com.project.comparecarts.ui.fragments.UploadReceiptFragment;
import com.project.comparecarts.ui.fragments.YourReceiptFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) {
            return HomeFragment.getInstance();
        } else if(position == 1) {
            return UploadReceiptFragment.getInstance();
        } else if(position == 2) {
            return  YourReceiptFragment.getInstance();
        } else {
            return ContactUsFragment.getInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
