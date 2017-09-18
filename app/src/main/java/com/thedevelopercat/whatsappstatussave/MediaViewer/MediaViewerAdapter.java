package com.thedevelopercat.whatsappstatussave.MediaViewer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.thedevelopercat.whatsappstatussave.Models.MediaFilesModel;

/**
 * Created by Ariq on 18-09-2017.
 */

public class MediaViewerAdapter extends FragmentStatePagerAdapter
{

    public MediaViewerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a MediaViewerPlaceholder (defined as a static inner class below).
        return MediaViewerPlaceholder.newInstance(position);
    }

    @Override
    public int getCount()
    {
        // Show 3 total pages.
        return MediaFilesModel.getTotalFilesCount();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}
