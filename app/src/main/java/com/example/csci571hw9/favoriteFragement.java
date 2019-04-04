package com.example.csci571hw9;

//import android.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csci571hw9.helper.SharedPreference;
import com.example.csci571hw9.helper.eventObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.csci571hw9.showEvnetList.FOCUS_EVENT_OBJECT;

public class favoriteFragement extends Fragment {

    private static CustomImageListAdapter adapter;
    private static ArrayList<eventObject> listItems=new ArrayList<>();
    private static SharedPreference sharedPreference;
    private static Activity activity;

    private static ListView list;
    private static TextView noResultText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sharedPreference = new SharedPreference();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //context possibly not same one as wish
        activity = getActivity();
        listItems = sharedPreference.getFavorites(activity);


        if(listItems == null){
            //TODO: no favorites
            listItems = new ArrayList<>();
            //sharedPreference.saveFavorites(getContext(), listItems);
            sharedPreference.saveFavorites(activity, listItems);

        }

        View retView = inflater.inflate(R.layout.switch_favorite, container, false);


        //adapter= new CustomImageListAdapter(listItems,getContext());
        adapter= new CustomImageListAdapter(getFavorite(),activity);
        list = retView.findViewById(R.id.favoriteList);
        //listItems = read from preference
        list.setAdapter(adapter);

        noResultText = retView.findViewById(R.id.noResult);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                eventObject singleEvent= listItems.get(position);
                Intent intent = new Intent(getContext(), tabs.class);
                intent.putExtra(FOCUS_EVENT_OBJECT,singleEvent);
                startActivity(intent);

            }
        });

        if(getFavorite().size() == 0){
            showNoResult();
        }else{
            hideNoResult();
        }


        return retView;

    }


    public static boolean isFavorite(eventObject data){
        return sharedPreference.isFavorites(activity,data);
    }

    public static void addFavorite(eventObject data){

        Toast.makeText(activity, data.getEvent()+" was added to favorites", Toast.LENGTH_SHORT).show();

        sharedPreference.addFavorite(activity, data);
        adapter= new CustomImageListAdapter(getFavorite(),activity);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if(getFavorite().size() == 0){
            showNoResult();
        }else{
            hideNoResult();
        }
    }

    public static void removeFavorite(eventObject data){

        Toast.makeText(activity, data.getEvent()+" was removed from favorites", Toast.LENGTH_SHORT).show();

        sharedPreference.removeFavorite(activity, data);
        adapter= new CustomImageListAdapter(getFavorite(),activity);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if(getFavorite().size() == 0){
            showNoResult();
        }else{
            hideNoResult();
        }
    }

    public static ArrayList<eventObject> getFavorite(){
       return sharedPreference.getFavorites(activity);
    }


    public static void showNoResult(){
        noResultText.setVisibility(View.VISIBLE);
    }

    public static void hideNoResult(){
        noResultText.setVisibility(View.GONE);
    }

}
