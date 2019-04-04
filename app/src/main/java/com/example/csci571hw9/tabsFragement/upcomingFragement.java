package com.example.csci571hw9.tabsFragement;

import android.annotation.TargetApi;
import android.app.LauncherActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.csci571hw9.ApiCall;
import com.example.csci571hw9.CustomImageListAdapter;
import com.example.csci571hw9.R;
import com.example.csci571hw9.helper.eventObject;
import com.example.csci571hw9.helper.upcomingEventObject;
import com.example.csci571hw9.tabs;
import com.example.csci571hw9.upcomingListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class upcomingFragement extends Fragment {

    private static ArrayList<upcomingEventObject> listItems=new ArrayList<>();

    private static upcomingListAdapter adapter;
    private static RecyclerView.LayoutManager manager;

    private static TextView progressText;
    private static ProgressBar progressBar;
    private static RecyclerView upcomingView;
    private static LinearLayout srotingBtns;

    private static TextView noRecords;

    private static Spinner categorySpinner;
    private static Spinner ascendingSpinner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragement_upcoming_tab, container, false);

        RecyclerView upcomingEventList = retView.findViewById(R.id.upcomingEventList);


        manager = new LinearLayoutManager(getContext());
        upcomingEventList.setLayoutManager(manager);


        adapter = new upcomingListAdapter(listItems,R.layout.fagement_upcoming_item);


        adapter.setOnItemClickListener(new upcomingListAdapter.ClickListener(){

            @Override
            public void onItemClick(int position, View v) {
                //Log.i("upcomingURL",listItems.get(position).getUri());
                String url = listItems.get(position).getUri();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        upcomingEventList.setAdapter(adapter);
        upcomingEventList.setItemAnimator(new DefaultItemAnimator());

        progressText = retView.findViewById(R.id.upcomingProgressText);
        progressBar = retView.findViewById(R.id.upcomingProgress);
        upcomingView = retView.findViewById(R.id.upcomingEventList);
        srotingBtns = retView.findViewById(R.id.sortBtns);

        noRecords = retView.findViewById(R.id.upcomingNoRecord);

        categorySpinner = retView.findViewById(R.id.categorySpinner);
        ascendingSpinner = retView.findViewById(R.id.ascendingSpinner);

        AdapterView.OnItemSelectedListener sortClickListener = new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(categorySpinner.getSelectedItemPosition() == 0){
                    ascendingSpinner.setEnabled(false);
                    updateData();
                }else{
                    ascendingSpinner.setEnabled(true);
                    updateData(sort(tabs.getUpcomingEvents(),categorySpinner.getSelectedItemPosition(),ascendingSpinner.getSelectedItemPosition()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        categorySpinner.setOnItemSelectedListener(sortClickListener);
        ascendingSpinner.setOnItemSelectedListener(sortClickListener);


        if (tabs.isUpcomingReady()){
            updateData();
        }


        if(tabs.isUpcomingReady()){
            resetProgress();
        }else{
            setProgress();
        }

        if(tabs.isUpcomingReady() && tabs.getUpcomingEvents().size() == 0){
            setNoRecords();
            resetProgress();
        }else{
            resetNoRecords();
            resetProgress();
        }



        return retView;
    }

    public void updateData(){

//        if(tabs.isUpcomingReady() && tabs.getUpcomingEvents().size() == 0){
//            //set no results
//            resetProgress();
//            setNoRecords();
//            return;
//        }else{
//            //reset
//            resetNoRecords();
//        }
//
//        if(tabs.isUpcomingReady()){
//           resetProgress();
//        }

        if(adapter == null){
            return;
        }


        listItems.clear();
        if(tabs.getUpcomingEvents().size() > 5){
            listItems.addAll(tabs.getUpcomingEvents().subList(0,5));
        }else{
            listItems.addAll(tabs.getUpcomingEvents());
        }
        adapter.notifyDataSetChanged();
    }

    public void updateData(List<upcomingEventObject> data){

        if(adapter == null){
            return;
        }else{
            resetProgress();
        }

        listItems.clear();
        if(data.size() > 5){
            listItems.addAll(data.subList(0,5));
        }else{
            listItems.addAll(data);
        }
        adapter.notifyDataSetChanged();
    }


    @TargetApi(Build.VERSION_CODES.N)
    public ArrayList<upcomingEventObject> sort(ArrayList<upcomingEventObject> input, Integer category, Integer ascending){


        ArrayList<upcomingEventObject> data = new ArrayList<upcomingEventObject>();
        data.addAll(input);

        Comparator<upcomingEventObject> comparator = (upcomingEventObject o1, upcomingEventObject o2) -> o1.getDispalyName().compareTo(o2.getDispalyName());
        //ArrayList<upcomingEventObject> results = new ArrayList<>();

        switch (category){
            case 1:
                comparator = (upcomingEventObject o1, upcomingEventObject o2) -> o1.getDispalyName().compareTo(o2.getDispalyName());
                break;
            case 2:
                comparator = (o1, o2) -> {

                    if(o1.getDateTime() == null || o2.getDateTime() == null){
                        return 0;
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("MMM d, y HH:mm:ss");
                    try {
                        Date d1 = sdf.parse(o1.getDateTime());
                        Date d2 = sdf.parse(o2.getDateTime());
                        //Log.i("dateCompare","d1:"+o1.getDateTime()+" d2:"+o2.getDateTime()+" results:"+d1.compareTo(d2));
                        return d1.compareTo(d2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                };
                //SimpleDateFormat sdf = new SimpleDateFormat("MMM d, y HH:mm:ss");
                //comparator = (upcomingEventObject o1, upcomingEventObject o2) -> o1.getDateTime().compareTo(o2.getDispalyName());
                break;
            case 3:
                comparator = (upcomingEventObject o1, upcomingEventObject o2) -> o1.getArtist().compareTo(o2.getArtist());
                break;
            case 4:
                comparator = (upcomingEventObject o1, upcomingEventObject o2) -> o1.getType().compareTo(o2.getType());
                break;
        }

        if(ascending == 0){
            Collections.sort(data, comparator);
        }else if(ascending == 1){
            Collections.sort(data, comparator.reversed());
        }

        //resetProgress();

        return data;

    }


    public void setProgress(){
        progressBar.setVisibility(View.VISIBLE);
        progressText.setVisibility(View.VISIBLE);
        upcomingView.setVisibility(View.GONE);
        srotingBtns.setVisibility(View.GONE);
    }

    public void resetProgress(){

//        if(progressBar == null || progressText == null || upcomingView == null || srotingBtns == null){
//            Log.e("reset","reset fail");
//        }

        progressBar.setVisibility(View.GONE);
        progressText.setVisibility(View.GONE);
        upcomingView.setVisibility(View.VISIBLE);
        srotingBtns.setVisibility(View.VISIBLE);
    }


    public static void setNoRecords(){
        noRecords.setVisibility(View.VISIBLE);
        categorySpinner.setEnabled(false);
        ascendingSpinner.setEnabled(false);
    }

    public static void resetNoRecords(){
        noRecords.setVisibility(View.GONE);
        categorySpinner.setEnabled(true);
        ascendingSpinner.setEnabled(false);
    }
}
