package com.example.csci571hw9.tabsFragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.csci571hw9.R;
import com.example.csci571hw9.artistListAdapter;
import com.example.csci571hw9.helper.artistObject;
import com.example.csci571hw9.helper.upcomingEventObject;
import com.example.csci571hw9.tabs;
import com.example.csci571hw9.upcomingListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class artistFragement extends Fragment {


    private static ArrayList<artistObject> dataSet=new ArrayList<>();
    private static ArrayList<ArrayList<String>> artistImages = new ArrayList<>();
    private static ArrayList<String> titles = new ArrayList<>();

    private static RecyclerView.Adapter adapter;
    private static RecyclerView.LayoutManager manager;


    private static ProgressBar artistProgressBar;
    private static TextView artistProgressText;
    private static RecyclerView artistList;

    public static void setProgress(){
        artistProgressBar.setVisibility(View.VISIBLE);
        artistProgressText.setVisibility(View.VISIBLE);
        artistList.setVisibility(View.GONE);
    }

    public static void resetProgress(){
        artistProgressBar.setVisibility(View.GONE);
        artistProgressText.setVisibility(View.GONE);
        artistList.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragement_artist, container, false);

        artistList = retView.findViewById(R.id.artistRecyleView);

        artistProgressBar = retView.findViewById(R.id.artistProgressBar);
        artistProgressText = retView.findViewById(R.id.artistProgressText);

        //setProgress();


        manager = new LinearLayoutManager(getContext());
        artistList.setLayoutManager(manager);

        adapter = new artistListAdapter(dataSet,artistImages,titles,R.layout.fragement_artist_ite);

        artistList.setAdapter(adapter);
        artistList.setItemAnimator(new DefaultItemAnimator());

        if (tabs.isArtistInfoReady()){
            updateData();
        }

        if(tabs.isArtistInfoReady()){
            resetProgress();
        }else{
            setProgress();
        }

        return retView;
    }

    public void updateData(){




        dataSet = tabs.getArtisInfo();
        artistImages = tabs.getArtistImages();
        titles = tabs.getTitles();



        if(tabs.isArtistInfoReady() && titles.size() != 0){
            resetProgress();

        }


        manager = new LinearLayoutManager(getContext());
        artistList.setLayoutManager(manager);

        adapter = new artistListAdapter(dataSet,artistImages,titles,R.layout.fragement_artist_ite);

        artistList.setAdapter(adapter);
        artistList.setItemAnimator(new DefaultItemAnimator());

//
//        adapter = new artistListAdapter(dataSet,artistImages,titles,R.layout.fragement_artist_ite);
//        adapter.notifyDataSetChanged();

        //resetProgress();
    }


    public void refresh(){

        if(adapter == null){
            return;
        }

        dataSet = new ArrayList<>();
        artistImages = new ArrayList<>();
        titles = new ArrayList<>();

        adapter = new artistListAdapter(dataSet,artistImages,titles,R.layout.fragement_artist_ite);
        adapter.notifyDataSetChanged();

    }
}