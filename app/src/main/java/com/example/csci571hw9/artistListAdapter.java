package com.example.csci571hw9;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.csci571hw9.helper.artistObject;
import com.example.csci571hw9.helper.upcomingEventObject;
import com.example.csci571hw9.tabsFragement.artistFragement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class artistListAdapter extends RecyclerView.Adapter<artistListAdapter.ViewHolder> {

    private ArrayList<artistObject> dataSet;
    private ArrayList<ArrayList<String>> artistImages;
    private ArrayList<String> titles;
    private int itemLayout;


    public artistListAdapter(ArrayList<artistObject> items, ArrayList<ArrayList<String>> artistImages, ArrayList<String> titles, int itemLayout) {
        this.artistImages = artistImages;
        this.dataSet = items;
        this.itemLayout = itemLayout;
        this.titles = titles;
    }


    // View lookup cache
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView artistTitle;

        public TextView nameContent;
        public TableRow nameRow;

        public TextView followersContent;
        public TableRow followersRow;

        public TextView popularityContent;
        public TableRow popularityRow;

        public TextView checkAtContent;
        public TableRow checkAtRow;

        public ImageView artistImage1;
        public ImageView artistImage2;

        public ViewHolder(View itemView) {
            super(itemView);
            this.artistTitle = (TextView) itemView.findViewById(R.id.artistTitle);

            this.nameContent = (TextView) itemView.findViewById(R.id.nameContent);
            this.nameRow = (TableRow) itemView.findViewById(R.id.nameRow);

            this.followersContent = (TextView) itemView.findViewById(R.id.followersContent);
            this.followersRow = (TableRow) itemView.findViewById(R.id.followersRow);

            this.popularityContent = (TextView) itemView.findViewById(R.id.popularityContent);
            this.popularityRow = (TableRow) itemView.findViewById(R.id.popularityRow);

            this.checkAtContent = (TextView) itemView.findViewById(R.id.checkAtContent);
            this.checkAtRow = (TableRow) itemView.findViewById(R.id.checkAtRow);

            this.artistImage1 = (ImageView) itemView.findViewById(R.id.artistImage1);
            this.artistImage2 = (ImageView) itemView.findViewById(R.id.artistImage2);


        }
    }

    @NonNull
    @Override
    public artistListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new artistListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull artistListAdapter.ViewHolder holder, int i) {

        if (i < titles.size()) {
            String title = titles.get(i);
            holder.artistTitle.setText(title);
        }else{
            holder.artistTitle.setVisibility(View.GONE);
        }


        if (dataSet.size() != 0) {
            artistObject item = dataSet.get(i);


            //name
            if (item.getName() != null) {
                holder.nameContent.setText(item.getName());
                holder.nameRow.setVisibility(View.VISIBLE);
            } else {
                holder.nameRow.setVisibility(View.GONE);
            }

            //followers
            if (item.getFollowers() != null) {
                holder.followersContent.setText(item.getFollowers());
                holder.followersRow.setVisibility(View.VISIBLE);
            } else {
                holder.followersRow.setVisibility(View.GONE);
            }

            //popularity
            if (item.getPopularity() != null) {
                holder.popularityContent.setText(item.getPopularity());
                holder.popularityRow.setVisibility(View.VISIBLE);
            } else {
                holder.popularityRow.setVisibility(View.GONE);
            }

            //get check at
            if (item.getCheckAt() != null) {
                holder.checkAtContent.setText(item.getCheckAt());

                holder.checkAtContent.setText(Html.fromHtml("<a href='" + item.getCheckAt() + "'>Spotify</a>"));
                Linkify.addLinks(holder.checkAtContent, Linkify.WEB_URLS);
                holder.checkAtContent.setMovementMethod(LinkMovementMethod.getInstance());


                holder.checkAtRow.setVisibility(View.VISIBLE);
            } else {
                holder.checkAtRow.setVisibility(View.GONE);
            }
        }else{
            holder.popularityRow.setVisibility(View.GONE);
            holder.checkAtRow.setVisibility(View.GONE);
            holder.followersRow.setVisibility(View.GONE);
            holder.nameRow.setVisibility(View.GONE);
        }


        if (artistImages.size() > i) {
            ArrayList<String> images = artistImages.get(i);

            if (!images.get(0).isEmpty()) {
                String url1 = images.get(0);
                Picasso.with(holder.artistImage1.getContext()).load(url1).into(holder.artistImage1, new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {
                        picassoCallBack();
                    }

                    @Override
                    public void onError() {

                    }
                });
                holder.artistImage1.setAdjustViewBounds(true);
            }

            if (!images.get(1).isEmpty()) {
                String url2 = images.get(1);
                Picasso.with(holder.artistImage2.getContext()).load(url2).into(holder.artistImage2);
                holder.artistImage2.setAdjustViewBounds(true);
                //Log.i("image", "image2 finished loading");
            }
        }else{
            holder.artistImage1.setVisibility(View.GONE);
            holder.artistImage2.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public void picassoCallBack(){
        Log.i("picasso","picasso callback");
        artistFragement.resetProgress();
    }
}
