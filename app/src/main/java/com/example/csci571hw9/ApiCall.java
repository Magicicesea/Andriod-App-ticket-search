package com.example.csci571hw9;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;


public class ApiCall {

    public static final String categeoryMusic = "KZFzniwnSyZfZ7v7nJ";
    public static final String categeorySports = "KZFzniwnSyZfZ7v7nE";
    public static final String categeoryArts = "KZFzniwnSyZfZ7v7na";
    public static final String categeoryFilm = "KZFzniwnSyZfZ7v7nn";
    public static final String categeoryMiscellaneous = "KZFzniwnSyZfZ7v7n1";



    private static ApiCall mySingleton;
    private RequestQueue queue;
    private static Context cache;

    public ApiCall(Context ctx) {
        cache = ctx;
        queue = getRequestQueue();
    }

    public static synchronized ApiCall getInstance(Context context) {
        if (mySingleton == null) {
            mySingleton = new ApiCall(context);
        }
        return mySingleton;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(cache.getApplicationContext());
        }
        return queue;
    }



    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    //request method changed from string to JSON
    //searchGeoCode
    public static void searchGeoCode(Context ctx,String address, Response.Listener
            listener, Response.ErrorListener errorListener) {
        String url = "https://csci571-backend.appspot.com/searchGeoCode?address="+address;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,null , listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public static void autoCompleteKeyword(Context ctx,String keyword, Response.Listener
            listener, Response.ErrorListener errorListener) {
        String url = "https://csci571-backend.appspot.com/searchKeyword?keyword="+keyword;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,null , listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }



    public static void searchEvent(Context ctx, HashMap<String, String> input, Response.Listener
            listener, Response.ErrorListener errorListener) {

        String url = "https://csci571-backend.appspot.com/searchEvent?keyword="+input.get("keyword");


        switch (input.get("category")){
            case "Music":
                url += "&segmentId=";
                url += categeoryMusic;
                break;
            case "Sports":
                url += "&segmentId=";
                url += categeorySports;
                break;
            case "Arts & Theatre":
                url += "&segmentId=";
                url += categeoryArts;
                break;
            case "Film":
                url += "&segmentId=";
                url += categeoryFilm;
                break;
            case "Miscellaneous":
                url += "&segmentId=";
                url += categeoryMiscellaneous;
                break;
        }

        url += "&radius=" + input.get("distanceInput");
        url += "&unit=" + input.get("distanceUnit");

        //TODO: add geoPoint get method to url
        url += "&geoPoint=" + input.get("geoHash");

        //Log.i("eventDetail",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,null , listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonObjectRequest);


    }

    public static void geoHash(Context ctx, Double lat, Double lng, Response.Listener
            listener, Response.ErrorListener errorListener){
        //Log.i(lat.toString(),lng.toString());
        String url = "https://csci571-backend.appspot.com/geoHash?lat="+lat.toString();
        url += "&lon=" + lng.toString();
        //Log.i("geoHash",url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,null , listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public static void retriveEventDetail(Context ctx, String id, Response.Listener
            listener, Response.ErrorListener errorListener){
        //Log.i(lat.toString(),lng.toString());
        String url = "https://csci571-backend.appspot.com/retriveEventDetail?id="+id;
        //Log.i("retriveEventDetail",url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,null , listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public static void searchVenue(Context ctx, String keyword, Response.Listener
            listener, Response.ErrorListener errorListener){
        //Log.i(lat.toString(),lng.toString());
        String url = "https://csci571-backend.appspot.com/searchVenue?keyword="+keyword;
        //Log.i("retriveEventDetail",url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,null , listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public static void getUpcomingEvent(Context ctx, String query, Response.Listener
            listener, Response.ErrorListener errorListener){
        //Log.i(lat.toString(),lng.toString());
        String url = "https://csci571-backend.appspot.com/getUpcomingEvent?query="+query;
        //Log.i("retriveEventDetail",url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,null , listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public static void showSpotifyDetail(Context ctx, String keyword, Response.Listener
            listener, Response.ErrorListener errorListener){


        String url = "http://csci571-backend.appspot.com/showSpotifyDetail?keyword="+keyword;
        Log.i("showSpotifyDetail",url);
//        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest()
//                (Request.Method.GET, url, listener, errorListener);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET,
                url,null,
                listener,errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public static void customSearch(Context ctx, String q, Response.Listener
            listener, Response.ErrorListener errorListener){
        //Log.i(lat.toString(),lng.toString());
        String url = "http://csci571-backend.appspot.com/customSearch?q="+q;
        //Log.i("retriveEventDetail",url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,null , listener, errorListener);
        ApiCall.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }







}