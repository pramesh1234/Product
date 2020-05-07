package com.codestrela.product.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.codestrela.product.fragments.GroupTabFragment;
import com.codestrela.product.fragments.RequestTabFragment;

public class HomeAdapter extends FragmentStatePagerAdapter {
    String title;
    public HomeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new GroupTabFragment();
        } else if (position == 1) {
            fragment = new RequestTabFragment();
        } else if (position == 2) {
            fragment = new RequestTabFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            title = "Group";
        } else if (position == 1) {
            title = "Request";
        } else if (position == 2) {
            title = "Pending";

        }
        return title;
    }
}
