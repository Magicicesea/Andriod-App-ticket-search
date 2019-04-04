package com.example.csci571hw9.helper;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class detailObject {

    private String artist;
    private String venue;
    private String time;
    private String category;
    private HashMap<String,Float> priceRange;
    private String ticketStatus;
    private String buyTicketAt;
    private String seatMap;
    private String name;
    private String id;

    @TargetApi(Build.VERSION_CODES.O)
    public detailObject(JSONObject data){

        try{

            //artist
            if(data.has("_embedded") && data.getJSONObject("_embedded").has("attractions")){

                String tmp = "";

                if(data.getJSONObject("_embedded").getJSONArray("attractions").getJSONObject(0).has("name")){
                    tmp = data.getJSONObject("_embedded").getJSONArray("attractions").getJSONObject(0).getString("name");
                }

                for(int i = 1; i < data.getJSONObject("_embedded").getJSONArray("attractions").length();i++){

                    if(data.getJSONObject("_embedded").getJSONArray("attractions").getJSONObject(i).has("name")){
                        tmp += " | " + data.getJSONObject("_embedded").getJSONArray("attractions").getJSONObject(i).getString("name");
                    }

                }
                setArtist(tmp);

            }

            //venue
            if(data.has("_embedded") && data.getJSONObject("_embedded").has("venues")
                    && data.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).has("name")){
                setVenue(data.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name"));

            }

            //time
            if(data.has("dates") && data.getJSONObject("dates").has("start")
                    && data.getJSONObject("dates").getJSONObject("start").has("dateTime")){



                DateTimeFormatter inFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                LocalDateTime dateTime = LocalDateTime.parse(data.getJSONObject("dates").getJSONObject("start").getString("dateTime"),inFormatter);
                String date = dateTime.format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                setTime(date);
            }

            //category
            if(data.has("classifications")){

                if(data.getJSONArray("classifications").getJSONObject(0).has("genre") && !data.getJSONArray("classifications").getJSONObject(0).has("segment")){
                    setCategory(data.getJSONArray("classifications").getJSONObject(0).getJSONObject("genre").getString("name"));
                }

                if(!data.getJSONArray("classifications").getJSONObject(0).has("genre") && data.getJSONArray("classifications").getJSONObject(0).has("segment")){
                    setCategory(data.getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name"));
                }


                if(data.getJSONArray("classifications").getJSONObject(0).has("genre") && data.getJSONArray("classifications").getJSONObject(0).has("segment")){
                    setCategory(data.getJSONArray("classifications").getJSONObject(0).getJSONObject("genre").getString("name") + " | " + data.getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name"));
                }

                //Log.i("category1",getCategory());

            }

            //priceRange
            if(data.has("priceRanges") ){

                if(data.getJSONArray("priceRanges").getJSONObject(0).has("max")&& data.getJSONArray("priceRanges").getJSONObject(0).has("min")){
                    setPriceRangeMin((float) data.getJSONArray("priceRanges").getJSONObject(0).getDouble("min") );
                    setPriceRangeMin((float) data.getJSONArray("priceRanges").getJSONObject(0).getDouble("max") );
                }

                if(data.getJSONArray("priceRanges").getJSONObject(0).has("max")&& !data.getJSONArray("priceRanges").getJSONObject(0).has("min")){
                    setPriceRangeMin((float) data.getJSONArray("priceRanges").getJSONObject(0).getDouble("max") );
                }

                if(!data.getJSONArray("priceRanges").getJSONObject(0).has("max")&& data.getJSONArray("priceRanges").getJSONObject(0).has("min")){
                    setPriceRangeMin((float) data.getJSONArray("priceRanges").getJSONObject(0).getDouble("min") );
                }

            }

            //ticket status
            if(data.has("dates") && data.getJSONObject("dates").has("status")
                    && data.getJSONObject("dates").getJSONObject("status").has("code")){
                setTicketStatus(data.getJSONObject("dates").getJSONObject("status").getString("code"));
            }

            //buy ticket at
            if(data.has("url")){
                setBuyTicketAt(data.getString("url"));
                //Log.i("buyticketat",getBuyTicketAt());
            }

            //seatmap
            if(data.has("seatmap") && data.getJSONObject("seatmap").has("staticUrl")){
                setSeatMap(data.getJSONObject("seatmap").getString("staticUrl"));
                //Log.i("seatmap",getSeatMap());
            }

            //name
            if(data.has("name")){
                setName(data.getString("name"));
            }

            //id
            if(data.has("id")){
                setId(data.getString("id"));
                Log.i("id",getId());
            }

        }catch (Exception e){
            Log.e("error",e.getStackTrace().toString());
        }



    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriceRange(float min, float max) {

        this.priceRange = priceRange;
    }

    public void setPriceRangeMin(float min){
        this.priceRange.put("min",min);
    }

    public void setPriceRangeMax(float max){
        this.priceRange.put("max",max);
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public void setBuyTicketAt(String buyTicketAt) {
        this.buyTicketAt = buyTicketAt;
    }

    public void setSeatMap(String seatMap) {
        this.seatMap = seatMap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getArtist() {
        return artist;
    }

    public String getVenue() {
        return venue;
    }

    public String getTime() {
        return time;
    }

    public String getCategory() {
        return category;
    }

    public String getPriceRange() {

        if(priceRange == null){
            return null;
        }

        if(priceRange.get("min") != null && priceRange.get("max") != null){
            return priceRange.get("min") + " ~ " + priceRange.get("max");
        }

        else if(priceRange.get("min") != null && priceRange.get("max") == null){
            return priceRange.get("min").toString();
        }

        else if(priceRange.get("min") == null && priceRange.get("max") != null){
            return priceRange.get("max").toString();
        }

        return null;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public String getBuyTicketAt() {
        return buyTicketAt;
    }

    public String getSeatMap() {
        return seatMap;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }





}
