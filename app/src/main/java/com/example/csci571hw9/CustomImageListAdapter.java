package com.example.csci571hw9;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.csci571hw9.helper.eventObject;

import java.util.ArrayList;

public class CustomImageListAdapter extends ArrayAdapter<eventObject> implements View.OnClickListener{

    private ArrayList<eventObject> dataSet;
    Context mContext;

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //Log.i("onclick","1");
    }

    // View lookup cache
    private static class ViewHolder {
        TextView eventName;
        TextView venue;
        TextView date;
        ImageView categoryIcon;
        ImageView favoriteIcon;
    }

    public CustomImageListAdapter(ArrayList<eventObject> data, Context context) {
        super(context, R.layout.list_item_view, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final eventObject event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_view, parent, false);
            viewHolder.eventName = (TextView) convertView.findViewById(R.id.eventName);
            viewHolder.venue = (TextView) convertView.findViewById(R.id.venueTab);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.favoriteIcon = (ImageView) convertView.findViewById(R.id.favoriteIcon);
            viewHolder.categoryIcon = (ImageView) convertView.findViewById(R.id.categoryIcon);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.eventName.setText(event.getEvent());
        viewHolder.date.setText(event.getDate());
        viewHolder.venue.setText(event.getVenue());

        //Log.i("category",event.getCategory());

        switch (event.getCategory()){
            case "Music":
                viewHolder.categoryIcon.setImageResource(R.drawable.music_icon);
                break;
            case "Sports":
                viewHolder.categoryIcon.setImageResource(R.drawable.sport_icon);
                break;
            case "Arts & Theatre":
                viewHolder.categoryIcon.setImageResource(R.drawable.art_icon);
                break;
            case "Miscellaneous":
                viewHolder.categoryIcon.setImageResource(R.drawable.miscellaneous_icon);
                break;
            case "Film":
                viewHolder.categoryIcon.setImageResource(R.drawable.film_icon);
                break;
        }

        //TODO: change favorite icon by states
        if(!favoriteFragement.isFavorite(dataSet.get(position))){
            viewHolder.favoriteIcon.setImageResource(R.drawable.heart_outline_black);
        }else{
            viewHolder.favoriteIcon.setImageResource(R.drawable.heart_fill_red);
        }

        viewHolder.favoriteIcon.setClickable(true);
        viewHolder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("iconClick",String.valueOf(favoriteFragement.isFavorite(dataSet.get(position))));
//                //Log.i("iconClick",favoriteFragement.getFavorite().get(0).getEvent());
//                Log.i("iconClick"," get clicked");
                if(!favoriteFragement.isFavorite(dataSet.get(position))){
                    favoriteFragement.addFavorite(dataSet.get(position));
                    viewHolder.favoriteIcon.setImageResource(R.drawable.heart_fill_red);
                }else{
                    favoriteFragement.removeFavorite(dataSet.get(position));
                    viewHolder.favoriteIcon.setImageResource(R.drawable.heart_outline_black);
                }
            }
        });



        // Return the completed view to render on screen
        return convertView;
    }


}
