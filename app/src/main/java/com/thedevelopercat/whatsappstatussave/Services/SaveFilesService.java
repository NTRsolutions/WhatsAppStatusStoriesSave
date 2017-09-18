package com.thedevelopercat.whatsappstatussave.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.thedevelopercat.whatsappstatussave.BackgroundTasks.SaveFilesServiceThread;
import com.thedevelopercat.whatsappstatussave.R;

import static com.thedevelopercat.whatsappstatussave.Models.SelectedMediaFilesModel.*;
import static com.thedevelopercat.whatsappstatussave.Utils.FileUtils.saveFile;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SaveFilesService extends IntentService
{
    NotificationCompat.Builder builder;
    NotificationManager manager;

    public SaveFilesService()
    {
        super("SaveFilesService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if (intent != null)
        {
            SaveFilesServiceThread saveFilesServiceThread =
                    new SaveFilesServiceThread(this, builder, manager);

            saveFilesServiceThread.run();
        }
    }
}
