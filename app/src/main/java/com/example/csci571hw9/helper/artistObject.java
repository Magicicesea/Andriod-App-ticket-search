package com.example.csci571hw9.helper;

import org.json.JSONObject;

public class artistObject {

    private String name;
    private String Followers;
    private String popularity;
    private String checkAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFollowers() {
        return Followers;
    }

    public void setFollowers(String followers) {
        Followers = followers;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getCheckAt() {
        return checkAt;
    }

    public void setCheckAt(String checkAt) {
        this.checkAt = checkAt;
    }

    public artistObject(JSONObject data){
        try{

            //name
            if(data.has("name")){
                setName(data.getString("name"));
            }

            //followers
            if(data.has("followers") && data.getJSONObject("followers").has("total")){
                setFollowers(data.getJSONObject("followers").getString("total"));
            }

            //popularity
            if(data.has("popularity")){
                setPopularity(data.getString("popularity"));
            }

            //check at
            if(data.has("external_urls") && data.getJSONObject("external_urls").has("spotify")){
                setCheckAt(data.getJSONObject("external_urls").getString("spotify"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
