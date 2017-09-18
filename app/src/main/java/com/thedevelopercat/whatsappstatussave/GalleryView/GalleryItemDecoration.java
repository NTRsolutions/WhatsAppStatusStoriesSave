package com.thedevelopercat.whatsappstatussave.GalleryView;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ariq on 12-09-2017.
 */

public class GalleryItemDecoration extends RecyclerView.ItemDecoration
{
    int spacing;

    public GalleryItemDecoration(int spacing)
    {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect rect, View view, @NonNull RecyclerView parent, RecyclerView.State state)
    {
        int position = parent.getChildLayoutPosition(view);

        if(position%3==2)
            rect.right = 0;
        else
            rect.right = spacing;

        if(position%3==0)
            rect.left = 0;
        else
            rect.left = spacing;

        rect.top = 0;
        rect.bottom = spacing;
    }
}
