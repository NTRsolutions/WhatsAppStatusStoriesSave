package com.thedevelopercat.whatsappstatussave.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.thedevelopercat.whatsappstatussave.Models.MediaFilesModel;
import com.thedevelopercat.whatsappstatussave.Models.SelectedMediaFilesModel;

import java.io.File;
import java.util.ArrayList;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by Ariq on 12-09-2017.
 */

public class OtherUtils
{
    public static int calculateWidthOfItem(@NonNull Activity activity)
    {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        return (int)(displayMetrics.widthPixels/3);
    }

    public static void setToFullScreen(@NonNull Activity activity)
    {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
