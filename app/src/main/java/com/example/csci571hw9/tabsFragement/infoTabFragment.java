package com.example.csci571hw9.tabsFragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.csci571hw9.R;
import com.example.csci571hw9.helper.detailObject;
import com.example.csci571hw9.helper.eventObject;
import com.example.csci571hw9.showEvnetList;
import com.example.csci571hw9.tabs;


public class infoTabFragment extends Fragment {

    private static detailObject data;

    //private Integer exLock;

    private static TextView artist;
    private static TextView artistDetail;
    private static TextView venue;
    private static TextView venueDetail;
    private static TextView time;
    private static TextView timeDetail;
    private static TextView category;
    private static TextView categoryDetail;
    private static TextView priceRange;
    private static TextView priceRangeDetail;
    private static TextView ticketStatus;
    private static TextView ticketStatusDetail;
    private static TextView butTicketAt;
    private static TextView butTicketAtDetail;
    private static TextView seatMap;
    private static TextView seatMapDetail;

    private static TextView progressText;
    private static ProgressBar progressBar;
    private static ScrollView infoTabs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View retView = inflater.inflate(R.layout.fragment_info_tab, container, false);

        artist = retView.findViewById(R.id.artist);
        artistDetail = retView.findViewById(R.id.artistDetail);
        venue = retView.findViewById(R.id.venue);
        venueDetail = retView.findViewById(R.id.venueDetail);
        time = retView.findViewById(R.id.time);
        timeDetail = retView.findViewById(R.id.timeDetail);
        category = retView.findViewById(R.id.category);
        categoryDetail = retView.findViewById(R.id.categoryDetail);
        priceRange = retView.findViewById(R.id.priceRange);
        priceRangeDetail = retView.findViewById(R.id.priceRangeDetail);
        ticketStatus = retView.findViewById(R.id.ticketStatus);
        ticketStatusDetail = retView.findViewById(R.id.ticketStatusDetail);
        butTicketAt = retView.findViewById(R.id.buyTicketAt);
        butTicketAtDetail = retView.findViewById(R.id.buyTicketAtDetail);
        seatMap = retView.findViewById(R.id.seatMap);
        seatMapDetail = retView.findViewById(R.id.seatMapDetail);

        progressText = retView.findViewById(R.id.infoTabProgressText);
        progressBar = retView.findViewById(R.id.infoTabProgress);
        infoTabs = retView.findViewById(R.id.infoTabView);


        if(tabs.isDetailisReady()){
            updateData();
        }

        if(tabs.isDetailisReady()){
            resetProgress();
        }else{
            setProgress();
        }

        return retView;
    }

    public static void updateData(){


        data = tabs.getDetailObject();

        //artist
        if(data.getArtist() != null){
            artist.setVisibility(View.VISIBLE);
            artistDetail.setVisibility(View.VISIBLE);
            artistDetail.setText(data.getArtist());
        }else{
            artist.setVisibility(View.GONE);
            artistDetail.setVisibility(View.GONE);
        }

        //venue
        if(data.getVenue() != null){
            venue.setVisibility(View.VISIBLE);
            venue.setVisibility(View.VISIBLE);
            venueDetail.setText(data.getVenue());
        }else{
            venue.setVisibility(View.GONE);
            venueDetail.setVisibility(View.GONE);
        }

        //time
        if(data.getTime() != null){
            timeDetail.setVisibility(View.VISIBLE);
            time.setVisibility(View.VISIBLE);
            timeDetail.setText(data.getTime());
        }else{
            time.setVisibility(View.GONE);
            timeDetail.setVisibility(View.GONE);
        }

        //category
        if(data.getCategory() != null){
            category.setVisibility(View.VISIBLE);
            categoryDetail.setVisibility(View.VISIBLE);
            categoryDetail.setText(data.getCategory());
        }else{
            category.setVisibility(View.GONE);
            categoryDetail.setVisibility(View.GONE);
        }

        //price range
        if(data.getPriceRange() != null){
            priceRange.setVisibility(View.VISIBLE);
            priceRangeDetail.setVisibility(View.VISIBLE);
            priceRangeDetail.setText(data.getPriceRange());
        }else{
            priceRange.setVisibility(View.GONE);
            priceRangeDetail.setVisibility(View.GONE);
        }

        //ticket status
        if(data.getTicketStatus() != null){
            ticketStatus.setVisibility(View.VISIBLE);
            ticketStatusDetail.setVisibility(View.VISIBLE);
            ticketStatusDetail.setText(data.getTicketStatus());
        }else{
            ticketStatusDetail.setVisibility(View.GONE);
            ticketStatus.setVisibility(View.GONE);
        }

        //buy ticket at
        if(data.getBuyTicketAt() != null){
            butTicketAtDetail.setVisibility(View.VISIBLE);
            butTicketAt.setVisibility(View.VISIBLE);

            butTicketAtDetail.setText(Html.fromHtml("<a href='" + data.getBuyTicketAt()+ "'>Ticketmaster</a>"));
            Linkify.addLinks(butTicketAtDetail, Linkify.WEB_URLS);
            butTicketAtDetail.setMovementMethod(LinkMovementMethod.getInstance());

        }else{
            butTicketAtDetail.setVisibility(View.GONE);
            butTicketAt.setVisibility(View.GONE);
        }

        //seat map
        if(data.getSeatMap() != null){
            seatMap.setVisibility(View.VISIBLE);
            seatMapDetail.setVisibility(View.VISIBLE);
            seatMapDetail.setText(Html.fromHtml("<a href='" + data.getSeatMap()+ "'>View Here</a>"));
            Linkify.addLinks(seatMapDetail, Linkify.WEB_URLS);
            seatMapDetail.setMovementMethod(LinkMovementMethod.getInstance());
        }else {
            seatMap.setVisibility(View.GONE);
            seatMapDetail.setVisibility(View.GONE);
        }


    }



    public void setProgress(){

        if(progressBar == null || progressText == null || infoTabs == null){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        progressText.setVisibility(View.VISIBLE);
        infoTabs.setVisibility(View.GONE);
    }

    public void resetProgress(){

        if(progressBar == null || progressText == null || infoTabs == null){
            return;
        }

        progressBar.setVisibility(View.GONE);
        progressText.setVisibility(View.GONE);
        infoTabs.setVisibility(View.VISIBLE);
    }


}
