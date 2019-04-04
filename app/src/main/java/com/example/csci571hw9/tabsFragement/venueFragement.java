package com.example.csci571hw9.tabsFragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.csci571hw9.R;
import com.example.csci571hw9.helper.venueObject;
import com.example.csci571hw9.tabs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class venueFragement extends Fragment implements OnMapReadyCallback {

    private static venueObject data;

    private static TextView name;
    private static TableRow nameRow;

    private static TextView address;
    private static TableRow addressRow;

    private static TextView city;
    private static TableRow cityRow;

    private static TextView phoneNumber;
    private static TableRow phoneNumberRow;

    private static TextView openHour;
    private static TableRow openHourRow;

    private static TextView generalRule;
    private static TableRow generalRuleRow;

    private static TextView childRule;
    private static TableRow childRuleRow;

    private static SupportMapFragment mapView;
    private static GoogleMap mMap;

    private static TextView progressText;
    private static ProgressBar progressBar;
    private static ScrollView venueTabs;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View retView = inflater.inflate(R.layout.fragement_venue_tab, container, false);

        name = retView.findViewById(R.id.nameDetail);
        nameRow = retView.findViewById(R.id.nameRow);

        address = retView.findViewById(R.id.addressDetail);
        addressRow = retView.findViewById(R.id.addressRow);

        city = retView.findViewById(R.id.cityDetail);
        cityRow = retView.findViewById(R.id.cityRow);

        phoneNumber = retView.findViewById(R.id.phoneNumberDetail);
        phoneNumberRow = retView.findViewById(R.id.phoneNumberRow);

        openHour = retView.findViewById(R.id.openHourDetail);
        openHourRow = retView.findViewById(R.id.openHourRow);

        generalRule = retView.findViewById(R.id.generalRuleDetail);
        generalRuleRow = retView.findViewById(R.id.generalRuleRow);

        childRule = retView.findViewById(R.id.childRuleDetail);
        childRuleRow = retView.findViewById(R.id.childRuleRow);


        mapView = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapView.getMapAsync(this);

        mapView.getView().setVisibility(View.GONE);
        //mapView.setMenuVisibility(false);

        progressText = retView.findViewById(R.id.venueProgressText);
        progressBar = retView.findViewById(R.id.venueProgressBar);
        venueTabs = retView.findViewById(R.id.venueTabs);


        if(tabs.isVenueReady()){
            updateData();
        }

        if(tabs.isVenueReady()){
            //resetProgress();
        }else{
            setProgress();
        }

        return retView;
    }


    public static void updateData(){
        data = tabs.getVenueObject();

        //name
        if(data.getName() != null){
            name.setText(data.getName());
            nameRow.setVisibility(View.VISIBLE);
        }else{
            nameRow.setVisibility(View.GONE);
        }

        //address
        if(data.getAddress() != null){
            address.setText(data.getAddress());
            addressRow.setVisibility(View.VISIBLE);
        }else{
            addressRow.setVisibility(View.GONE);
        }

        //city
        if(data.getCity() != null){
            city.setText(data.getCity());
            cityRow.setVisibility(View.VISIBLE);
        }else{
            cityRow.setVisibility(View.GONE);
        }

        //Log.i("phonenumber",data.getPhoneNumber());
        //phone number
        if(data.getPhoneNumber() != null){
            phoneNumber.setText(data.getPhoneNumber());
            phoneNumberRow.setVisibility(View.VISIBLE);
        }else{
            phoneNumberRow.setVisibility(View.GONE);
        }

        //open hours
        if(data.getOpenHours() != null){
            openHour.setText(data.getOpenHours());
            openHourRow.setVisibility(View.VISIBLE);
        }else{
            openHourRow.setVisibility(View.GONE);
        }

        //general rule
        if(data.getGeneralRule() != null){
            generalRule.setText(data.getGeneralRule());
            generalRuleRow.setVisibility(View.VISIBLE);
        }else{
            generalRuleRow.setVisibility(View.GONE);
        }

        //child rule
        if(data.getChildRule() != null){
            childRule.setText(data.getChildRule());
            childRuleRow.setVisibility(View.VISIBLE);
        }else{
            childRuleRow.setVisibility(View.GONE);
        }


        //map
//        if(data.getLat() != null && data.getLng() != null){
//            mapView.setMenuVisibility(true);
//
//            if(mMap != null ){
//                LatLng venueLocation = new LatLng(data.getLat(),data.getLng());
//                mMap.addMarker(new MarkerOptions().position(venueLocation).title(data.getName()));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(venueLocation));
//            }
//
//        }




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if(data.getLat() != null && data.getLng() != null){
            //mapView.setMenuVisibility(true);
            mapView.getView().setVisibility(View.VISIBLE);

            if(mMap != null ){
                LatLng venueLocation = new LatLng(data.getLat(),data.getLng());
                mMap.addMarker(new MarkerOptions().position(venueLocation).title(data.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(venueLocation,12));
                resetProgress();
            }

        }


    }

    public void setProgress(){

        progressBar.setVisibility(View.VISIBLE);
        progressText.setVisibility(View.VISIBLE);
        venueTabs.setVisibility(View.GONE);
    }

    public void resetProgress(){
        progressBar.setVisibility(View.GONE);
        progressText.setVisibility(View.GONE);
        venueTabs.setVisibility(View.VISIBLE);
    }



}