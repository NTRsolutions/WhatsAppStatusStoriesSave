package com.thedevelopercat.whatsappstatussave.MediaViewer;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.thedevelopercat.whatsappstatussave.R;

import static com.thedevelopercat.whatsappstatussave.Utils.FileUtils.saveFile;
import static com.thedevelopercat.whatsappstatussave.Utils.FileUtils.shareFile;

/**
 * Created by Ariq on 18-09-2017.
 */

public class MediaViewerClickListener implements View.OnClickListener
{

    private Activity activity;
    private ViewPager viewPager;

    public MediaViewerClickListener(Activity activity, ViewPager viewPager)
    {

        this.activity = activity;
        this.viewPager = viewPager;
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        if(id == R.id.fabShare)
        {
            int position = viewPager.getCurrentItem();
            shareFile(activity,position);
        }

        else if(id == R.id.fabSave)
            saveFile(activity,viewPager.getCurrentItem(), false);
    }
}