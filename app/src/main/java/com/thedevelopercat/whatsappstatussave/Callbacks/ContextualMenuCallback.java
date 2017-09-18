package com.thedevelopercat.whatsappstatussave.Callbacks;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.thedevelopercat.whatsappstatussave.Activities.HomeActivity;
import com.thedevelopercat.whatsappstatussave.R;

import static com.thedevelopercat.whatsappstatussave.Utils.FileUtils.*;

/**
 * Created by Ariq on 13-09-2017.
 */

public class ContextualMenuCallback implements ActionMode.Callback
{
    private Activity activity;
    private int total;

    public ContextualMenuCallback(Activity activity, int total)
    {
        this.activity = activity;
        this.total = total;
    }

    @Override
    public boolean onCreateActionMode(@NonNull ActionMode mode, Menu menu)
    {
        mode.getMenuInflater().inflate(R.menu.contextual,menu);

        StringBuilder title = new StringBuilder("");
        title.append(String.valueOf(1));
        title.append("/");
        title.append(String.valueOf(total));
        title.append(" selected");
        mode.setTitle(title);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu)
    {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.action_share)
        {
            shareFiles(activity);
        }

        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode)
    {
        ActionModeCloseCallback actionModeCloseCallback = (HomeActivity) activity;
        actionModeCloseCallback.hideFabAndClearSelection();
    }
}
