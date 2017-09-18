package com.thedevelopercat.whatsappstatussave.GalleryView;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thedevelopercat.whatsappstatussave.Activities.HomeActivity;
import com.thedevelopercat.whatsappstatussave.Activities.MediaViewerActivity;
import com.thedevelopercat.whatsappstatussave.BackgroundTasks.FetchWhatsAppFolderTask;
import com.thedevelopercat.whatsappstatussave.Callbacks.InvokeContextualMenu;
import com.thedevelopercat.whatsappstatussave.Callbacks.LongTouchModeCallBack;
import com.thedevelopercat.whatsappstatussave.Models.SelectedMediaFilesModel;
import com.thedevelopercat.whatsappstatussave.R;

import java.io.File;
import java.util.ArrayList;

import static com.thedevelopercat.whatsappstatussave.Models.MediaFilesModel.*;
import static com.thedevelopercat.whatsappstatussave.Utils.FileUtils.*;
import static com.thedevelopercat.whatsappstatussave.Utils.OtherUtils.*;

/**
 * Created by Ariq on 08-09-2017.
 */

public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewHolder>
{
    public boolean longClickMode = false;
    private File statusFolder;

    private Activity activity;
    private String folder;
    private int thumbnailSize = 100;

    private RequestOptions options = new RequestOptions();

    public GalleryViewAdapter(Activity activity)
    {
        this.activity = activity;
        options.centerCrop();

        FetchWhatsAppFolderTask fetchWhatsAppFolderTask =
                new FetchWhatsAppFolderTask(activity);
        fetchWhatsAppFolderTask.execute();
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_recycler_view, parent, false);
        return new GalleryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position)
    {
        setListeners(holder);
        setThumbnailSize(holder);
        setThumbnails(holder,position);
        videoIconVisibility(holder,position);
        itemImageSelectIconFix(holder,position);
    }

    @Override
    public int getItemCount()
    {
        if (isNotNull())
            return getTotalFilesCount();
        else return 0;
    }

    public void cancelLongClickMode()
    {
        SelectedMediaFilesModel.clearList();
        notifyDataSetChanged();
        longClickMode = false;
    }

    private void itemImageSelectIconFix(@NonNull GalleryViewHolder holder, int position)
    {
        if(SelectedMediaFilesModel.contains(String.valueOf(position)))
        {
            holder.itemSelected.setAlpha(1f);
        }

        else
        {
            holder.itemSelected.setAlpha(0f);
        }
    }

    private void setListeners(@NonNull GalleryViewHolder holder)
    {
        holder.itemImageButton.setOnLongClickListener(new OnRecyclerViewItemLongClickListener(holder));
        holder.itemImageButton.setOnClickListener(new OnRecyclerViewItemClickListener(holder));
    }

    private void setThumbnailSize(@NonNull GalleryViewHolder holder)
    {
        thumbnailSize = calculateWidthOfItem(activity);
        holder.itemImageButton.getLayoutParams().width=thumbnailSize;
        holder.itemImageButton.getLayoutParams().height=thumbnailSize;
    }

    private void setThumbnails(@NonNull GalleryViewHolder holder, int position)
    {
        if (isNotNull())
        {
            File file = getFileAt(position);
            if(file.exists())
            {
                Glide.with(activity)
                        .asBitmap()
                        .apply(options)
                        .load(file.getAbsolutePath())
                        .into(holder.itemImageButton);
            }
            else
            {
                removeFile(file);
            }
        }
    }


    private void videoIconVisibility(@NonNull GalleryViewHolder holder, int position)
    {
        String name = getFilePathAt(position);

        if(isVideo(name))
        {
            holder.itemVideoIcon.setAlpha(1f);
        }

        else
        {
            holder.itemVideoIcon.setAlpha(0f);
        }
    }

    private void longTouchModeOperation(@NonNull GalleryViewHolder holder)
    {
        String position = String.valueOf(holder.getAdapterPosition());
        if(SelectedMediaFilesModel.contains(position))
        {
            SelectedMediaFilesModel.remove(position);
            holder.itemSelected.animate().alpha(0f).setDuration(250).start();
        }

        else
        {
            SelectedMediaFilesModel.add(position);
            holder.itemSelected.animate().alpha(1f).setDuration(250).start();
        }

        updateActionModeAndFab(activity,SelectedMediaFilesModel.getSelectionList());
    }

    private void updateActionModeAndFab(Activity activity,
                                        @NonNull ArrayList<String> selectionList)
    {
        LongTouchModeCallBack callBack = (HomeActivity)activity;
        callBack.updateActionModeAndShowFab(selectionList.size(), getTotalFilesCount());
    }

    private class OnRecyclerViewItemLongClickListener implements View.OnLongClickListener
    {
        GalleryViewHolder holder;

        OnRecyclerViewItemLongClickListener(GalleryViewHolder holder)
        {
            this.holder = holder;
        }

        @Override
        public boolean onLongClick(View view)
        {
            if(longClickMode)
            {
                longTouchModeOperation(holder);
            }

            else
            {
                longClickMode = true;
                InvokeContextualMenu contextualMenu = (HomeActivity) activity;
                contextualMenu.showContextualMenu(getTotalFilesCount());

                String position = String.valueOf(holder.getAdapterPosition());
                SelectedMediaFilesModel.add(position);
                holder.itemSelected.animate().alpha(1f).setDuration(250).start();

                updateActionModeAndFab(activity,SelectedMediaFilesModel.getSelectionList());
            }

            return true;
        }
    }

    private class OnRecyclerViewItemClickListener implements View.OnClickListener
    {
        GalleryViewHolder holder;

        OnRecyclerViewItemClickListener(GalleryViewHolder holder)
        {
            this.holder = holder;
        }

        @Override
        public void onClick(View view)
        {
            if(longClickMode)
            {
                longTouchModeOperation(holder);
            }

            else
            {
                Intent i = new Intent(activity, MediaViewerActivity.class);
                i.putExtra("position",holder.getAdapterPosition());
                activity.startActivity(i);
            }
        }
    }
}

