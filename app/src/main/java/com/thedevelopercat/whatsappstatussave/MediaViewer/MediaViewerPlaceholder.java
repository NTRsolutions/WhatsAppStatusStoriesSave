package com.thedevelopercat.whatsappstatussave.MediaViewer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thedevelopercat.whatsappstatussave.Models.MediaFilesModel;
import com.thedevelopercat.whatsappstatussave.R;

import java.io.File;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

import static com.thedevelopercat.whatsappstatussave.Utils.FileUtils.isVideo;

/**
 * Created by Ariq on 18-09-2017.
 */

public class MediaViewerPlaceholder extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public MediaViewerPlaceholder()
    {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    @NonNull
    public static MediaViewerPlaceholder newInstance(int sectionNumber)
    {
        MediaViewerPlaceholder fragment = new MediaViewerPlaceholder();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        int position = getArguments().getInt(ARG_SECTION_NUMBER);
        File file = MediaFilesModel.getFileAt(position);

        View rootView = inflater.inflate(R.layout.fragment_media_viewer, container, false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        JZVideoPlayerStandard videoView = (JZVideoPlayerStandard) rootView.findViewById(R.id.videoView);

        RequestOptions options = new RequestOptions();
        options.fitCenter();

        if(isVideo(file))
        {
            imageView.setVisibility(View.GONE);
            Glide.with(this).
                    asBitmap().
                    apply(options).
                    load(file).
                    into(videoView.thumbImageView);

            videoView.setUp(file.getAbsolutePath(), JZVideoPlayer.SCREEN_LAYOUT_NORMAL);
            videoView.batteryTimeLayout.setVisibility(View.GONE);
            videoView.backButton.setVisibility(View.GONE);
            videoView.clarity.setVisibility(View.INVISIBLE);
            videoView.fullscreenButton.setVisibility(View.GONE);
            videoView.retryTextView.setText("");
        }

        else
        {
            Glide.with(this).asBitmap().apply(options).load(file).into(imageView);
            videoView.setVisibility(View.GONE);
        }

        return rootView;
    }
}