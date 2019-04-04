package com.example.csci571hw9.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;



//this code is cited from http://androidopentutorials.com/android-how-to-store-list-of-values-in-sharedpreferences/


public class SharedPreference {

    public static final String PREFS_NAME = "CSCI571";
    public static final String FAVORITES = "CSCI571_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<eventObject> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, eventObject product) {
        List<eventObject> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<eventObject>();
        if(isFavorites(context,product)){
            return;
        }
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, eventObject product) {
        ArrayList<eventObject> favorites = getFavorites(context);
        if (favorites != null) {
            for(int i =0; i <favorites.size(); i++){
                if(favorites.get(i).getId().equals(product.getId())){
                    favorites.remove(i);
                }
            }
            //favorites.remove(product);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<eventObject> getFavorites(Context context) {
        SharedPreferences settings;
        List<eventObject> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            eventObject[] favoriteItems = gson.fromJson(jsonFavorites,
                    eventObject[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<eventObject>(favorites);
        } else
            return null;

        return (ArrayList<eventObject>) favorites;
    }

    public boolean isFavorites(Context context, eventObject data){

        ArrayList<eventObject> tmp = getFavorites(context);

        if(tmp == null){
            return false;
        }

        for(int i=0; i < tmp.size(); i++){
            if(tmp.get(i).getId().equals(data.getId())){
                return true;
            }
        }
        return false;

    }
}