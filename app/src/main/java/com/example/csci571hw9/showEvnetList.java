package com.example.csci571hw9;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.csci571hw9.helper.eventObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class showEvnetList extends AppCompatActivity {

    ArrayList<eventObject> listItems=new ArrayList<>();
    private static HashMap<String, String> searchRequest;


    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    CustomImageListAdapter adapter;

    private static eventObject focusEvent;

    public static final String FOCUS_EVENT_OBJECT = "com.example.csci571hw9.searchRequest";


    private static boolean isNoResult;


    private showEvnetList activity = this;


    private static double lat;
    private static double lon;

//    private static TextView progressText;
//    private static ProgressBar progressBar;

    private static ListView list;
    private static TextView noResultsText;


    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT);

        }
    };

    public void setProgress(){
        getApplicationContext();
        TextView progressText = findViewById(R.id.showEventProgressText);
        ProgressBar progressBar = this.findViewById(R.id.showEventProgress);

        list.setVisibility(View.GONE);
        progressText.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

    }

    public void resetProgress(){
        TextView progressText = this.findViewById(R.id.showEventProgressText);
        ProgressBar progressBar = this.findViewById(R.id.showEventProgress);

        list.setVisibility(View.GONE);
        progressText.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }


    public final Response.Listener<JSONObject> resultsListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {


                try {
                    if(response.has("error")){

                        Toast.makeText(activity, response.getString("error"), Toast.LENGTH_SHORT);
                    }

                    if(response.has("warning")){
                        Toast.makeText(activity, response.getString("warning"), Toast.LENGTH_SHORT);
                    }
                }catch (Exception e){

                }


                if(!response.has("_embedded") || !response.getJSONObject("_embedded").has("events")){
                    //set no results
                    setNoResults();
                    resetProgress();
                    return;
                }
                else{
                    resetNoResults();
                }

                JSONArray events = response.getJSONObject("_embedded").getJSONArray("events");
                for (int i = 0; i < events.length(); i++) {
                    JSONObject event = events.getJSONObject(i);
                    eventObject singleEvent = new eventObject(event);

                    listItems.add(singleEvent);
                }
                adapter.notifyDataSetChanged();

                TextView progressText = findViewById(R.id.showEventProgressText);
                ProgressBar progressBar = findViewById(R.id.showEventProgress);

                list.setVisibility(View.VISIBLE);
                progressText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    public static double getLat() {
        return lat;
    }

    public static void setLat() {
        if(MainActivity.getGeoLocaiton().get("lat") == null){
            return;
        }
        showEvnetList.lat = MainActivity.getGeoLocaiton().get("lat");

    }

    public static double getLon() {
        return lon;
    }

    public static void setLon() {
        if(MainActivity.getGeoLocaiton().get("lon") == null){
            return;
        }
        showEvnetList.lon = MainActivity.getGeoLocaiton().get("lon");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Results");

        Intent intent = getIntent();

        searchRequest = (HashMap<String, String>) intent.getExtras().getSerializable(searchFragement.SEARCH_REQUEST_MESSAGE);
        setContentView(R.layout.activity_show_evnet_list);

       // setProgress();

        list = findViewById(R.id.eventList);
        noResultsText = findViewById(R.id.noEventsText);

        if(isNoResult){
            noResultsText.setVisibility(View.VISIBLE);
        }else{
            noResultsText.setVisibility(View.GONE);
        }


        String fromSelectionId = searchRequest.get("fromSelectionId");

        TextView progressText = findViewById(R.id.showEventProgressText);
        ProgressBar progressBar = findViewById(R.id.showEventProgress);

        list.setVisibility(View.GONE);
        progressText.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);



        switch (fromSelectionId) {
                    case "0":

                        setLat();
                        setLon();

                        ApiCall.geoHash(getApplicationContext(), getLat(), getLon(), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try{
                                    String geoHash = response.getString("geohash");
                                    searchRequest.put("geoHash",geoHash);
                                    ApiCall.searchEvent(getApplicationContext(),searchRequest,resultsListener,null);



                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        },errorListener);
                        break;

                    case "1":

                        ApiCall.searchGeoCode(getApplicationContext(),searchRequest.get("otherInput"), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject responseObject = response;
                                    Double lat = responseObject.getJSONArray("results").getJSONObject(0).
                                            getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                                    Double lon = responseObject.getJSONArray("results").getJSONObject(0).
                                            getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                                    Log.i("results",lat+" , "+ lon);

                                    ApiCall.geoHash(getApplicationContext(), lat, lon, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try{
                                                String geoHash = response.getString("geohash");
                                                //Log.i("geoHash",geoHash);
                                                //searchRequest.put("geoHash",geoHash);
                                                ApiCall.searchEvent(getApplicationContext(),searchRequest,resultsListener,null);

                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }

                                        }
                                    },errorListener);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, errorListener);

                }

        adapter= new CustomImageListAdapter(listItems,getApplicationContext());

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                eventObject singleEvent= listItems.get(position);
                Intent intent = new Intent(getApplicationContext(), tabs.class);
                intent.putExtra(FOCUS_EVENT_OBJECT,singleEvent);
                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onResume() {
        super.onResume();

//        list.setVisibility(View.GONE);
//        progressText.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(View.VISIBLE);

        adapter.notifyDataSetChanged();

//        list.setVisibility(View.VISIBLE);
//        progressText.setVisibility(View.GONE);
//        progressBar.setVisibility(View.GONE);

    }


    public static void setNoResults(){

        isNoResult = true;
        if(noResultsText == null){
            return;
        }
        noResultsText.setVisibility(View.VISIBLE);
    }

    public static void resetNoResults(){
        isNoResult = false;
        if(noResultsText == null){
            return;
        }

        noResultsText.setVisibility(View.GONE);
    }

}
