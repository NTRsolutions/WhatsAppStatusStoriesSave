package com.thedevelopercat.whatsappstatussave.MediaViewer;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.thedevelopercat.whatsappstatussave.Activities.MediaViewerActivity;

import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayerManager;

/**
 * Created by Ariq on 18-09-2017.
 */

public class MediaViewerOnPageChangeListener implements ViewPager.OnPageChangeListener
{
    private Context context;

    public MediaViewerOnPageChangeListener(Context context)
    {

        this.context = context;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        JZUtils.clearSavedProgress(context,"");
        JZVideoPlayerManager.completeAll();
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }
}
