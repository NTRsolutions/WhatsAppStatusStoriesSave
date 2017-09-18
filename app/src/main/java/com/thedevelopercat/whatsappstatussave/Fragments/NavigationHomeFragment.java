package com.thedevelopercat.whatsappstatussave.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thedevelopercat.whatsappstatussave.R;

public class NavigationHomeFragment extends Fragment
{

    public NavigationHomeFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_home, container, false);
    }

}
