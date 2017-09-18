package com.thedevelopercat.whatsappstatussave.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.thedevelopercat.whatsappstatussave.MediaViewer.MediaViewerAdapter;
import com.thedevelopercat.whatsappstatussave.MediaViewer.MediaViewerClickListener;
import com.thedevelopercat.whatsappstatussave.MediaViewer.MediaViewerOnPageChangeListener;
import com.thedevelopercat.whatsappstatussave.R;

import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayerManager;

import static com.thedevelopercat.whatsappstatussave.Utils.OtherUtils.setToFullScreen;

public class MediaViewerActivity extends AppCompatActivity
{

    private int position;
    private ViewPager viewPager;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabShare;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initialize();
        linkingViews();
        initializeMediaViewer();
        settingUpListener();
    }

    private void settingUpListener()
    {
        MediaViewerClickListener mediaViewerClickListener =
                new MediaViewerClickListener(this, viewPager);
        viewPager.addOnPageChangeListener(new MediaViewerOnPageChangeListener(this));
        fabSave.setOnClickListener(mediaViewerClickListener);
        fabShare.setOnClickListener(mediaViewerClickListener);
    }

    private void initializeMediaViewer()
    {
        MediaViewerAdapter mediaViewerAdapter = new MediaViewerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mediaViewerAdapter);
        viewPager.setCurrentItem(position);
    }

    private void linkingViews()
    {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fabShare = (FloatingActionButton) findViewById(R.id.fabShare);
    }

    private void initialize()
    {
        setToFullScreen(this);
        setContentView(R.layout.activity_media_viewer);
        position = getIntent().getIntExtra("position",0);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        JZUtils.clearSavedProgress(getApplicationContext(),"");
        JZVideoPlayerManager.completeAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.media_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
