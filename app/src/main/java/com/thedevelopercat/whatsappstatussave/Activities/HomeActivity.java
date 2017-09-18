package com.thedevelopercat.whatsappstatussave.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.thedevelopercat.whatsappstatussave.Callbacks.ActionModeCloseCallback;
import com.thedevelopercat.whatsappstatussave.Callbacks.ContextualMenuCallback;
import com.thedevelopercat.whatsappstatussave.Callbacks.InvokeContextualMenu;
import com.thedevelopercat.whatsappstatussave.Callbacks.LongTouchModeCallBack;
import com.thedevelopercat.whatsappstatussave.Callbacks.RefreshRecyclerViewCallBack;
import com.thedevelopercat.whatsappstatussave.Models.ActivityResultCodes;
import com.thedevelopercat.whatsappstatussave.R;
import com.thedevelopercat.whatsappstatussave.GalleryView.GalleryViewAdapter;
import com.thedevelopercat.whatsappstatussave.GalleryView.GalleryItemDecoration;
import com.thedevelopercat.whatsappstatussave.Callbacks.WhatsAppFolderTaskCallback;
import com.thedevelopercat.whatsappstatussave.Services.SaveFilesService;

import java.io.File;

import static com.thedevelopercat.whatsappstatussave.Models.MediaFilesModel.*;
import static com.thedevelopercat.whatsappstatussave.Models.MediaFilesModel.setMediaFilesList;
import static com.thedevelopercat.whatsappstatussave.Models.SelectedMediaFilesModel.*;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LongTouchModeCallBack,
        InvokeContextualMenu, ActionModeCloseCallback, WhatsAppFolderTaskCallback,
        RefreshRecyclerViewCallBack
{
    final private int ANIMATION_DURATION = 250;
    final private int RECYCLER_VIEW_ITEM_SPACING = 1;
    final private int RECYCLER_VIEW_COLUMN_COUNT = 3;
    final private int TRANSLATE_Y = 10;
    final private int OPAQUE = 1;
    final private int TRANSPARENT = 0;

    private FloatingActionButton fabSave;
    private DrawerLayout drawer;
    private RecyclerView recyclerView;
    private GalleryViewAdapter adapter;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Nullable
    private ActionMode actionMode;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        linkingView();
        setListeners();
        setUpActionBarAndNavigationDrawer();
        setUpRecyclerView();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(ActivityResultCodes.FILES_SHARED_FROM_GALLERY == requestCode)
            refreshRecyclerView();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            if(adapter.longClickMode)
            {
                hideFabAndClearSelection();
            }

            else
            {
                super.onBackPressed();
                cleanUp();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
        if (id == R.id.action_exit)
            cleanUp();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {

        }
        else if (id == R.id.nav_gallery)
        {

        }
        else if (id == R.id.nav_slideshow)
        {

        }
        else if (id == R.id.nav_manage)
        {

        }
        else if (id == R.id.nav_share)
        {

        }
        else if (id == R.id.nav_send)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void updateActionModeAndShowFab(int count, int total)
    {
        if(count>=0)
        {
            setActionModeTitle(count,total);
            showFab();
        }
    }

    @Override
    public void showContextualMenu(int total)
    {
        actionMode = HomeActivity.this.
                startSupportActionMode(new ContextualMenuCallback(this, total));
    }

    @Override
    public void hideFabAndClearSelection()
    {
        fabSave.animate().
                alpha(TRANSPARENT).
                translationYBy(-1*TRANSLATE_Y).
                setDuration(ANIMATION_DURATION).
                start();
        adapter.cancelLongClickMode();
    }

    @Override
    public void whatAppFolderExist(boolean exists, File statusFolder)
    {
        if(exists)
        {
            setMediaFilesList(statusFolder.listFiles());
            adapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(this, "Cannot find the folder", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refreshRecyclerView()
    {
        int fileCount = getTotalFilesCount() - getSelectedFilesCount();
        clearSelection();
        adapter = new GalleryViewAdapter(this);
        recyclerView.swapAdapter(adapter,false);
        updateActionModeAndShowFab(getSelectedFilesCount(),fileCount);
    }

    private void cleanUp()
    {
        finish();
        Runtime runtime = Runtime.getRuntime();
        runtime.freeMemory();
        runtime.gc();
    }

    private void linkingView()
    {
        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void setListeners()
    {
        fabSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(@NonNull View view)
            {
                Intent service = new Intent(getApplicationContext(),SaveFilesService.class);
                startService(service);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setUpActionBarAndNavigationDrawer()
    {
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    private void setUpRecyclerView()
    {
        GalleryItemDecoration itemDecoration =
                new GalleryItemDecoration(RECYCLER_VIEW_ITEM_SPACING);
        recyclerView.addItemDecoration(itemDecoration);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager
                (this, RECYCLER_VIEW_COLUMN_COUNT);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GalleryViewAdapter(this);
        recyclerView.setAdapter(adapter);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void showFab()
    {
        if(fabSave.getAlpha()<OPAQUE)
            fabSave.animate().
                    alpha(OPAQUE).
                    translationYBy(TRANSLATE_Y).
                    setDuration(ANIMATION_DURATION).
                    start();
    }

    private void setActionModeTitle(int count, int total)
    {
        if (actionMode != null)
        {
            StringBuilder title = new StringBuilder("");
            title.append(String.valueOf(count));
            title.append("/");
            title.append(String.valueOf(total));
            title.append(" selected");
            actionMode.setTitle(title);
        }
    }
}
