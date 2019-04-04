package com.example.csci571hw9;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.csci571hw9.helper.SharedPreference;
import com.example.csci571hw9.helper.artistObject;
import com.example.csci571hw9.helper.detailObject;
import com.example.csci571hw9.helper.eventObject;
import com.example.csci571hw9.helper.upcomingEventObject;
import com.example.csci571hw9.helper.venueObject;
import com.example.csci571hw9.tabsFragement.artistFragement;
import com.example.csci571hw9.tabsFragement.infoTabFragment;
import com.example.csci571hw9.tabsFragement.upcomingFragement;
import com.example.csci571hw9.tabsFragement.venueFragement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.csci571hw9.showEvnetList.FOCUS_EVENT_OBJECT;

public class tabs extends AppCompatActivity {

    private boolean infoReturn = true;
    private boolean imageReturn = true;

    private static eventObject focusEvent;

    private static detailObject detailObject;
    private static boolean detailisReady = false;

    private static venueObject venueObject;
    private static boolean venueReady = false;

    private static ArrayList<upcomingEventObject> upcomingEvents = new ArrayList<>();
    private static boolean upcomingReady = false;

    private static ArrayList<artistObject> artisInfo = new ArrayList<>();
    private static ArrayList<ArrayList<String>> artistImages = new ArrayList<>();
    private static ArrayList<String> titles = new ArrayList<>();
    private static boolean artistInfoReady = false;

    private static infoTabFragment f1 = new infoTabFragment();
    private static artistFragement f2 = new artistFragement();
    private static venueFragement f3 = new venueFragement();
    private static upcomingFragement f4 = new upcomingFragement();

    private tabs activity = this;

    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT);

        }
    };


    public static boolean isDetailisReady() {
        return detailisReady;
    }

    public static com.example.csci571hw9.helper.detailObject getDetailObject() {
        return detailObject;
    }

    public static boolean isVenueReady() {
        return venueReady;
    }

    public static com.example.csci571hw9.helper.venueObject getVenueObject() {
        return venueObject;
    }

    public static ArrayList<String> getTitles() {
        return titles;
    }

    public static void setTitles(ArrayList<String> titles) {
        tabs.titles = titles;
    }

    public boolean isInfoReturn() {
        return infoReturn;
    }

    public void setInfoReturn(boolean infoReturn) {
        this.infoReturn = infoReturn;
    }

    public boolean isImageReturn() {
        return imageReturn;
    }

    public void setImageReturn(boolean imageReturn) {
        this.imageReturn = imageReturn;
    }


    public class tabsPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 4;
        private String tabTitles[] = new String[]{"event", "artist", "venue", "upcoming"};
        private Context context;


        public tabsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return f1;
            } else if (position == 1) {
                return f2;
            } else if (position == 2) {
                return f3;
            } else {
                return f4;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
//            TabLayout tabLayout = findViewById(R.id.tabLayout2);
//            tabLayout.getTabAt(position).setIcon(R.drawable.info_outline);
            return tabTitles[position];
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        detailisReady = false;
        venueReady = false;
        upcomingReady = false;
        artistInfoReady = false;


        //focusEvent = showEvnetList.getFocusEvent();
        focusEvent = getIntent().getExtras().getParcelable(FOCUS_EVENT_OBJECT);

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_tabs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(focusEvent.getEvent());
//

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new tabs.tabsPagerAdapter(getSupportFragmentManager(),
                this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tabLayout2);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.info_outline);
        tabLayout.getTabAt(1).setIcon(R.drawable.artist);
        tabLayout.getTabAt(2).setIcon(R.drawable.venue);
        tabLayout.getTabAt(3).setIcon(R.drawable.upcoming);


        ApiCall.retriveEventDetail(this, focusEvent.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.has("error")){

                        Toast.makeText(activity, response.getString("error"), Toast.LENGTH_SHORT);
                    }

                    if(response.has("warning")){
                        Toast.makeText(activity, response.getString("warning"), Toast.LENGTH_SHORT);
                    }
                }catch (Exception e){

                }


                f1.setProgress();
                detailObject = new detailObject(response);
                detailisReady = true;
                f1.updateData();
                f1.resetProgress();

                if(detailObject.getArtist() == null){
                    Log.e("artistTab","No artist found in detailobject");
                    return;
                }
                //Log.i("artist",detailObject.getArtist());
                final String[] artists = detailObject.getArtist().split(" \\| ");

                setArtistInfoReady(false);
                setArtisInfo(new ArrayList<artistObject>());
                setArtistImages(new ArrayList<ArrayList<String>>());
                setTitles(new ArrayList<String>());

                f2.refresh();
                f2.setProgress();


                for (Integer i = 0; i < 2 && i < artists.length; i++) {


                    synchronized (titles){
                        titles.add(artists[i]);
                    }



                    synchronized (artisInfo) {


                        ApiCall.showSpotifyDetail(getApplicationContext(), artists[i], new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {


                                try {

                                    if(response.length() == 0){
                                        return;
                                    }

                                    artistObject singleArtisInfo = new artistObject(response.getJSONObject(0));
                                    artisInfo.add(singleArtisInfo);
                                    Log.i("artistInfo",singleArtisInfo.getName());

                                    f2.updateData();


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        },errorListener);

                        ApiCall.customSearch(getApplicationContext(), artists[i], new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {

                                    ArrayList<String> singleArtisImage = new ArrayList<>();

                                    try {
                                        if(response.has("error")){

                                            Toast.makeText(activity, response.getString("error"), Toast.LENGTH_SHORT);
                                        }

                                        if(response.has("warning")){
                                            Toast.makeText(activity, response.getString("warning"), Toast.LENGTH_SHORT);
                                        }
                                    }catch (Exception e){

                                    }


                                    if (response.has("items")) {
                                        for (int i = 0; i < 2 && i < response.getJSONArray("items").length(); i++) {
                                            singleArtisImage.add(
                                                    response.getJSONArray("items").getJSONObject(i).getString("link")
                                            );
                                        }
                                    }

                                    artistImages.add(singleArtisImage);
                                    Log.i("artistImage", singleArtisImage.get(0));
                                    setArtistInfoReady(true);

                                    f2.updateData();
                                    f2.resetProgress();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        },errorListener);
                    }

                }


            }
        }, null);


        ApiCall.searchVenue(this, focusEvent.getVenue(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.has("error")){

                        Toast.makeText(activity, response.getString("error"), Toast.LENGTH_SHORT);
                        venueReady = true;
                    }

                    if(response.has("warning")){
                        Toast.makeText(activity, response.getString("warning"), Toast.LENGTH_SHORT);
                        venueReady = true;
                    }
                }catch (Exception e){

                }


                //f3.setProgress();
                venueObject = new venueObject(response);
                venueReady = true;
                //f3.resetProgress();


            }
        }, errorListener);


        ApiCall.getUpcomingEvent(this,focusEvent.getVenue(),new Response.Listener<JSONObject>(){

        @Override
        public void onResponse (JSONObject response){
            try {
                setUpcomingEvents(new ArrayList<>());

                try {
                    if(response.has("error")){

                        Toast.makeText(activity, response.getString("error"), Toast.LENGTH_SHORT);
                    }

                    if(response.has("warning")){
                        Toast.makeText(activity, response.getString("warning"), Toast.LENGTH_SHORT);
                    }
                }catch (Exception e){

                }



                if(response.has("resultsPage")
                        && response.getJSONObject("resultsPage").has("results")
                        && response.getJSONObject("resultsPage").getJSONObject("results").has("event")){
                    JSONArray events = response.getJSONObject("resultsPage").getJSONObject("results").getJSONArray("event");
                    for (int i = 0; i < events.length(); i++) {
                        JSONObject event = events.getJSONObject(i);
                        upcomingEventObject singleEvent = new upcomingEventObject(event);
                        getUpcomingEvents().add(singleEvent);
                    }
                }

                upcomingReady = true;
                f4.updateData();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    },errorListener);


}

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.favorite:
                //TODO: add favorite and change icon
                if(!favoriteFragement.isFavorite(focusEvent)){
                    favoriteFragement.addFavorite(focusEvent);
                    menuItem.setIcon(R.drawable.heart_fill_white);
                }else{
                    favoriteFragement.removeFavorite(focusEvent);
                    menuItem.setIcon(R.drawable.heart_outline_white);
                }
                break;
            case R.id.twitter:
                twitter();
                break;

        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        if(favoriteFragement.isFavorite(focusEvent)){
            menu.findItem(R.id.favorite).setIcon(R.drawable.heart_fill_white);
        }else{
            menu.findItem(R.id.favorite).setIcon(R.drawable.heart_outline_white);
        }



        //menu.getItem(R.menu.menu_tabs).setTitle(focusEvent.getEvent());
        return true;
    }

    public static boolean isUpcomingReady() {
        return upcomingReady;
    }


    public static ArrayList<upcomingEventObject> getUpcomingEvents() {
        return upcomingEvents;
    }

    public static void setUpcomingEvents(ArrayList<upcomingEventObject> data) {
        upcomingEvents = data;
    }

    public static ArrayList<artistObject> getArtisInfo() {
        return artisInfo;
    }

    public static void setArtisInfo(ArrayList<artistObject> artisInfo) {
        tabs.artisInfo = artisInfo;
    }

    public static boolean isArtistInfoReady() {
        return artistInfoReady;
    }

    public static void setArtistInfoReady(boolean artistInfoReady) {
        tabs.artistInfoReady = artistInfoReady;
    }

    public static ArrayList<ArrayList<String>> getArtistImages() {
        return artistImages;
    }

    public static void setArtistImages(ArrayList<ArrayList<String>> artistImages) {
        tabs.artistImages = artistImages;
    }

    public void twitter(){
        String url = "http://twitter.com/share?text=";
        url += "Check out ";
        url += focusEvent.getEvent();
        url += " located at ";
        url += focusEvent.getVenue();
        url += ". Website:";

        url += "&url=";
        url += focusEvent.getUrl();
        Log.i("eventUrl","" + focusEvent.getUrl());

        url += "&hashtags=";
        url += "CSCI571EventSearch";

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
