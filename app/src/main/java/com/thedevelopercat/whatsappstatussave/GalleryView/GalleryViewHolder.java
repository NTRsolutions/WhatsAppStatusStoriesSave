package com.thedevelopercat.whatsappstatussave.GalleryView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.thedevelopercat.whatsappstatussave.R;

/**
 * Created by Ariq on 13-09-2017.
 */

public class GalleryViewHolder extends RecyclerView.ViewHolder
{
    ImageButton itemImageButton;
    ImageView itemSelected;
    ImageView itemVideoIcon;


    public GalleryViewHolder(@NonNull View itemView)
    {
        super(itemView);
        itemImageButton = itemView.findViewById(R.id.itemImageButton);
        itemSelected = itemView.findViewById(R.id.itemSelected);
        itemVideoIcon = itemView.findViewById(R.id.itemVideoIcon);
    }
}