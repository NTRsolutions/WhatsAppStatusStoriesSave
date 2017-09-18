package com.thedevelopercat.whatsappstatussave.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.thedevelopercat.whatsappstatussave.Models.ActivityResultCodes;
import com.thedevelopercat.whatsappstatussave.Models.MediaFilesModel;
import com.thedevelopercat.whatsappstatussave.Models.SelectedMediaFilesModel;
import com.thedevelopercat.whatsappstatussave.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import static com.thedevelopercat.whatsappstatussave.Models.ActivityResultCodes.*;

/**
 * Created by Ariq on 15-09-2017.
 */

public class FileUtils
{
    public static boolean isVideo(@NonNull String fileNameOrPath)
    {
        String[] videoFormats = {
                ".mp4",
                ".3gp",
                ".mkv",
                ".mov",
                ".avi",
                ".flv",
        };
        boolean isVideoFile = false;
        fileNameOrPath = fileNameOrPath.toLowerCase();

        for (int i = 0; i < videoFormats.length; i++)
        {
            if(fileNameOrPath.endsWith(videoFormats[i]))
            {
                isVideoFile = true;
                break;
            }

            else
            {
                isVideoFile = false;
            }
        }
        return isVideoFile;
    }

    public static boolean isVideo(@NonNull File file)
    {
        return isVideo(file.getAbsolutePath());
    }

    public static File[] sortFilesByLastModified(@Nullable File[] files)
    {
        if(files!=null)
        {
            File temp;
            for (int i = 0; i < files.length; i++)
            {
                for (int j = i; j < files.length; j++)
                {
                    if(files[i].lastModified()<files[j].lastModified())
                    {
                        temp = files[i];
                        files[i] = files[j];
                        files[j] = temp;
                    }
                }
            }
            return files;
        }

        else
            return null;
    }

    public static void shareFile(@NonNull Activity activity, @IntRange(from=0) int position)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);

        File file = MediaFilesModel.getFileAt(position);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

        if(SelectedMediaFilesModel.imagesAndVideosSelected())
            intent.setType("video/*");

        else
            intent.setType("image/*");

        activity.startActivity(intent);
    }

    public static void shareFileForResult(@NonNull Activity activity,
                                          @IntRange(from=0) int position,
                                          int REQUEST_Code)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);

        File file = MediaFilesModel.getFileAt(position);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

        if(SelectedMediaFilesModel.imagesAndVideosSelected())
            intent.setType("video/*");

        else
            intent.setType("image/*");

        activity.startActivityForResult(intent,REQUEST_Code);
    }

    public static void shareFiles(@NonNull Activity activity)
    {
        Intent intent = new Intent();
        ArrayList<Uri> files = new ArrayList<Uri>();

        for(String position : SelectedMediaFilesModel.getSelectionList())
        {
            File file = MediaFilesModel.getFileAt(Integer.parseInt(position));
            Uri uri = Uri.fromFile(file);
            files.add(uri);
        }

        if(SelectedMediaFilesModel.imagesAndVideosSelected())
            intent.setType("video/*");

        else
            intent.setType("image/*");

        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        activity.startActivityForResult(intent, FILES_SHARED_FROM_GALLERY);
    }

    public static void saveFile(@NonNull Context context,
                                @IntRange(from=0) int position, boolean isNotification)
    {
        File source = MediaFilesModel.getFileAt(position);
        File destination = new File(getDestination(source));

        try
        {
            FileInputStream inStream = new FileInputStream(source);
            FileOutputStream outStream = new FileOutputStream(destination);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();

            if(!isNotification)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        }

        catch (NullPointerException | IOException e)
        {
            Toast.makeText(context, "Fail to save", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private static String getDestination(File sourceFile)
    {
        File destination;

        String destinationFilePath;
        String destinationFolder;

        destinationFolder = sourceFile.
                getParentFile().
                getParentFile().
                getParentFile().
                getAbsolutePath();

        destinationFolder = destinationFolder + " Save Status";

        destination = new File(destinationFolder);
        if(!destination.exists())
            destination.mkdir();


        destinationFilePath = destinationFolder + "/" + sourceFile.getName();
        return destinationFilePath;
    }

}
