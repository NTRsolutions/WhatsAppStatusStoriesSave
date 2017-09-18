package com.thedevelopercat.whatsappstatussave.Models;

import com.thedevelopercat.whatsappstatussave.Utils.FileUtils;
import com.thedevelopercat.whatsappstatussave.Utils.OtherUtils;

import java.util.ArrayList;

import static com.thedevelopercat.whatsappstatussave.Utils.FileUtils.*;

/**
 * Created by Ariq on 15-09-2017.
 */

public class SelectedMediaFilesModel
{
    private static ArrayList<String> selectionList;

    public static void initializeList()
    {
        selectionList = new ArrayList<>();
    }

    public static ArrayList<String> getSelectionList()
    {
        return selectionList;
    }

    public static void setSelectionList(ArrayList<String> selectionList)
    {
        SelectedMediaFilesModel.selectionList = selectionList;
    }

    public static void clearList()
    {
        selectionList.removeAll(selectionList);
    }

    public static boolean contains(String string)
    {
        return selectionList.contains(string);
    }

    public static void add(String string)
    {
        selectionList.add(string);
    }

    public static void remove(String string)
    {
        if(selectionList!=null)
            if(selectionList.size()>0)
                if(selectionList.contains(string))
                    selectionList.remove(string);
    }

    public static void removeSelectedFileAt(int position)
    {
        if(selectionList!=null)
            if(selectionList.size()>0)
                    selectionList.remove(position);
    }

    public static boolean imagesAndVideosSelected()
    {
        boolean imagesAndVideosSelected = false;

        for (int i = 0; i < selectionList.size(); i++)
        {
            int position = Integer.parseInt(selectionList.get(i));
            String path = MediaFilesModel.getFilePathAt(position);
            imagesAndVideosSelected = isVideo(path);
            if(imagesAndVideosSelected)
                break;
        }
        return imagesAndVideosSelected;
    }

    public static int getSelectedFilesCount()
    {
        return selectionList.size();
    }

    public static int indexOfSelectedFileAt(int index)
    {
        return Integer.parseInt(selectionList.get(index));
    }

    public static void clearSelection()
    {
        selectionList.removeAll(selectionList);
        initializeList();
    }
}
