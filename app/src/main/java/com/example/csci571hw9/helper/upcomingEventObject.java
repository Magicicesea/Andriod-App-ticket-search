package com.example.csci571hw9.helper;

import android.annotation.TargetApi;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class upcomingEventObject{

    private String dispalyName;
    private String artist;
    private String dateTime;
    private String type;
    private String uri;


    public upcomingEventObject(JSONObject data) {

        try {

            JSONObject event = data;

            //display name
            if (event.has("displayName")) {
                setDispalyName(event.getString("displayName"));
            }

            //artist
            if (event.has("performance")
                    && event.getJSONArray("performance").getJSONObject(0).has("artist")
                    && event.getJSONArray("performance").getJSONObject(0).getJSONObject("artist").has("displayName")) {
                setArtist(event.getJSONArray("performance").getJSONObject(0).getJSONObject("artist").getString("displayName"));
            }

            //datetime
            if (event.has("start") && event.getJSONObject("start").has("datetime")) {

                setDateTime(dateTimeTansfer(event.getJSONObject("start").getString("datetime")));
            }

            //type
            if (event.has("type")) {
                setType(event.getString("type"));
            }

            //uri
            if (event.has("uri")) {
                setUri(event.getString("uri"));
            }

            //Log.i("id",getId());

            //Log.i("date",date);

            //setDate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getDispalyName() {
        return dispalyName;
    }

    public void setDispalyName(String dispalyName) {
        this.dispalyName = dispalyName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private String dateTimeTansfer(String data) {

        if(data.equals("null")){
            return null;
        }

        DateTimeFormatter inFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZ");
        LocalDateTime dateTime = LocalDateTime.parse(data,inFormatter);
        String date = dateTime.format(DateTimeFormatter.ofPattern("MMM d, y HH:mm:ss"));
        return date;
    }

}
