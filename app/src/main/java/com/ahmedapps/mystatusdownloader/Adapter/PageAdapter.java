package com.ahmedapps.mystatusdownloader.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ahmedapps.mystatusdownloader.Fragments.ImageFragment;
import com.ahmedapps.mystatusdownloader.Fragments.SavedFilesFragment;
import com.ahmedapps.mystatusdownloader.Fragments.VideoFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private final int totalTabs;

    public PageAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0)
            return new ImageFragment();

        if (position == 1)
            return new VideoFragment();

        return new SavedFilesFragment();

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}