package com.thedevelopercat.whatsappstatussave.BackgroundTasks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;

import com.thedevelopercat.whatsappstatussave.Activities.HomeActivity;
import com.thedevelopercat.whatsappstatussave.Models.SelectedMediaFilesModel;
import com.thedevelopercat.whatsappstatussave.Callbacks.WhatsAppFolderTaskCallback;

import java.io.File;

/**
 * Created by Ariq on 15-09-2017.
 */

public class FetchWhatsAppFolderTask extends AsyncTask<Void,Void,Void>
{
    private Activity activity;
    private File dcim;
    private File statusFolder;
    private String folder;
    private boolean folderExists;

    public FetchWhatsAppFolderTask(Activity activity)
    {
        this.activity = activity;
        SelectedMediaFilesModel.initializeList();
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        String sourceFolder;
        SharedPreferences sharedPreferences = activity.getSharedPreferences("preferences",0);
        sourceFolder = sharedPreferences.getString("sourceFolder", "");

        if(sourceFolder.equalsIgnoreCase(""))
        {
            dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            dcim = dcim.getParentFile();
            sourceFolder = dcim.getAbsolutePath();
            sourceFolder = sourceFolder + "/WhatsApp/Media/.Statuses";

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("sourceFolder", sourceFolder);
            editor.apply();
        }

        statusFolder = new File(sourceFolder);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        folderExists = statusFolder != null && statusFolder.exists();
        WhatsAppFolderTaskCallback whatsAppFolderTaskCallback = (HomeActivity) activity;
        whatsAppFolderTaskCallback.whatAppFolderExist(folderExists, statusFolder);
    }
}
