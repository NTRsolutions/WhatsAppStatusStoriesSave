package com.thedevelopercat.whatsappstatussave.Models;

import java.io.File;
import java.util.ArrayList;

import static com.thedevelopercat.whatsappstatussave.Utils.FileUtils.sortFilesByLastModified;

/**
 * Created by Ariq on 13-09-2017.
 */

public class MediaFilesModel
{
    static private ArrayList<File> mediaFilesList;

    public static void setMediaFilesList(File[] mediaFilesArray)
    {
        mediaFilesList = new ArrayList<File>();
        mediaFilesArray = sortFilesByLastModified(mediaFilesArray);
        for (int i = 0; i < mediaFilesArray.length; i++)
        {
            mediaFilesList.add(mediaFilesArray[i]);
        }
    }


    public static File getFileAt(int position)
    {
        return mediaFilesList.get(position);
    }

    public static String getFilePathAt(int position)
    {
        return getFileAt(position).getAbsolutePath();
    }

    public static boolean fileExistsAt(int position)
    {
        return getFileAt(position).exists();
    }

    public static void removeFileAt(int position)
    {
        mediaFilesList.remove(position);
    }

    public static void removeFile(File file)
    {
        mediaFilesList.remove(file);
    }

    public static int getTotalFilesCount()
    {
        return mediaFilesList.size();
    }

    public static boolean isNotNull()
    {
        return mediaFilesList != null;
    }
}
