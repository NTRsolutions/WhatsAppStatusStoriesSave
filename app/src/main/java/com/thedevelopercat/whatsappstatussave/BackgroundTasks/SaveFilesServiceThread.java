package com.thedevelopercat.whatsappstatussave.BackgroundTasks;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.thedevelopercat.whatsappstatussave.R;

import static com.thedevelopercat.whatsappstatussave.Models.SelectedMediaFilesModel.getSelectedFilesCount;
import static com.thedevelopercat.whatsappstatussave.Models.SelectedMediaFilesModel.indexOfSelectedFileAt;
import static com.thedevelopercat.whatsappstatussave.Utils.FileUtils.saveFile;

/**
 * Created by Ariq on 17-09-2017.
 */

public class SaveFilesServiceThread implements Runnable
{
    Context service;
    NotificationCompat.Builder builder;
    NotificationManager manager;

    public SaveFilesServiceThread(Context service, NotificationCompat.Builder builder, NotificationManager manager)
    {
        this.service = service;
        this.builder = builder;
        this.manager = manager;
    }

    @Override
    public void run()
    {
        StringBuilder postSavingMessage = new StringBuilder("");
        manager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(service,"");

        int selectedFilesCount = getSelectedFilesCount();

        postSavingMessage.append(selectedFilesCount);

        if(selectedFilesCount == 1)
            postSavingMessage.append(" Status Saved");
        else
            postSavingMessage.append(" Statuses Saved");


        builder.setContentTitle("WhatsApp Status Save")
                .setContentText("Saving")
                .setSmallIcon(R.drawable.ic_save);

        for (int progress = 0; progress < selectedFilesCount; progress++)
        {
            int position = indexOfSelectedFileAt(progress);
            saveFile(service,position,true);

            builder.setProgress(selectedFilesCount,progress,false);
            manager.notify(0, builder.build());
        }

        builder.setContentText(postSavingMessage);
        builder.setProgress(0,0,false);
        manager.notify(0, builder.build());
    }
}
