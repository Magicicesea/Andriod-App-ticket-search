package com.example.csci571hw9.helper;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONObject;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class eventObject implements Parcelable {

    private String event;
    private String category;
    private String venue;
    private String id;
    private String date;
    private String url;

    @TargetApi(Build.VERSION_CODES.O)
    public eventObject(JSONObject data){
        try {
            setEvent(data.getString("name"));

            setId(data.getString("id")) ;

            setCategory(data.getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name"));

            setVenue(data.getJSONObject("_embedded").getJSONArray("venues")
                    .getJSONObject(0).getString("name"));

            DateTimeFormatter inFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDateTime dateTime = LocalDateTime.parse(data.getJSONObject("dates").getJSONObject("start").getString("dateTime"),inFormatter);
            String date = dateTime.format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            setDate(date);

            if(data.has("url")){
                setUrl(data.getString("url"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    protected eventObject(Parcel in) {
        event = in.readString();
        category = in.readString();
        venue = in.readString();
        id = in.readString();
        date = in.readString();
        url = in.readString();
    }

    public static final Creator<eventObject> CREATOR = new Creator<eventObject>() {
        @Override
        public eventObject createFromParcel(Parcel in) {
            return new eventObject(in);
        }

        @Override
        public eventObject[] newArray(int size) {
            return new eventObject[size];
        }
    };

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(event);
        dest.writeString(category);
        dest.writeString(venue);
        dest.writeString(id);
        dest.writeString(date);
        dest.writeString(url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
