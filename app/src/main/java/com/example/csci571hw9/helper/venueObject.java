package com.example.csci571hw9.helper;

import org.json.JSONObject;

public class venueObject {

    private String name;
    private String address;
    private String city;
    private String phoneNumber;
    private String openHours;
    private String generalRule;
    private String childRule;
    private Double lat;
    private Double lng;

    public venueObject(JSONObject data){

        try {


            //venue
            if(data.has("_embedded") && data.getJSONObject("_embedded").has("venues")){
                JSONObject venueObject = data.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0);

                //name
                if(venueObject.has("name")){
                    setName(venueObject.getString("name"));
                }

                //address
                if(venueObject.has("address") && venueObject.getJSONObject("address").has("line1")){
                    setAddress(venueObject.getJSONObject("address").getString("line1"));
                }

                //city
                if(venueObject.has("city") && venueObject.getJSONObject("city").has("name")
                        && venueObject.has("state") && venueObject.getJSONObject("city").has("name")){

                    setCity(venueObject.getJSONObject("city").getString("name") + ", " + venueObject.getJSONObject("state").getString("name"));
                }

                else if(venueObject.has("city") && venueObject.getJSONObject("city").has("name")
                        && !venueObject.has("state") && venueObject.getJSONObject("city").has("name")){

                    setCity(venueObject.getJSONObject("city").getString("name"));
                }

                else if(!venueObject.has("city") && venueObject.getJSONObject("city").has("name")
                        && venueObject.has("state") && venueObject.getJSONObject("city").has("name")){

                    setCity(venueObject.getJSONObject("state").getString("name"));
                }


                //phoneNumber
                if(venueObject.has("boxOfficeInfo") && venueObject.getJSONObject("boxOfficeInfo").has("phoneNumberDetail")){
                    setPhoneNumber(venueObject.getJSONObject("boxOfficeInfo").getString("phoneNumberDetail"));
                }

                //open hours
                if(venueObject.has("boxOfficeInfo") && venueObject.getJSONObject("boxOfficeInfo").has("openHoursDetail")){
                    setOpenHours(venueObject.getJSONObject("boxOfficeInfo").getString("openHoursDetail"));
                }

                //general rule
                if(venueObject.has("generalInfo") && venueObject.getJSONObject("generalInfo").has("generalRule")){
                    setGeneralRule(venueObject.getJSONObject("generalInfo").getString("generalRule"));
                }

                //child rule
                if(venueObject.has("generalInfo") && venueObject.getJSONObject("generalInfo").has("childRule")){
                    setChildRule(venueObject.getJSONObject("generalInfo").getString("childRule"));
                }

                //lat
                if(venueObject.has("location") && venueObject.getJSONObject("location").has("latitude")){
                    setLat(venueObject.getJSONObject("location").getDouble("latitude"));
                }

                //lng
                if(venueObject.has("location") && venueObject.getJSONObject("location").has("longitude")){
                    setLng(venueObject.getJSONObject("location").getDouble("longitude"));
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOpenHours(String openHours) {
        this.openHours = openHours;
    }

    public void setGeneralRule(String generalRule) {
        this.generalRule = generalRule;
    }

    public void setChildRule(String childRule) {
        this.childRule = childRule;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOpenHours() {
        return openHours;
    }

    public String getGeneralRule() {
        return generalRule;
    }

    public String getChildRule() {
        return childRule;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
