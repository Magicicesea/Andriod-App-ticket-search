package com.example.csci571hw9;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.csci571hw9.helper.upcomingEventObject;

import java.util.ArrayList;
import java.util.List;



public class upcomingListAdapter extends RecyclerView.Adapter<upcomingListAdapter.ViewHolder> {
    private static ClickListener clickListener;

    private ArrayList<upcomingEventObject> dataSet;
    private int itemLayout;
    Context mContext;

    public void setOnItemClickListener(ClickListener clickListener) {
        upcomingListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public upcomingListAdapter(ArrayList<upcomingEventObject> items, int itemLayout){
        this.dataSet = items;
        this.itemLayout = itemLayout;
    }


    // View lookup cache
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public TextView artist;
        public TextView time;
        public TextView type;

        public ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            this.name =(TextView) itemView.findViewById(R.id.name);
            this.artist = (TextView) itemView.findViewById(R.id.artist);
            this.time = (TextView) itemView.findViewById(R.id.time);
            this.type = (TextView) itemView.findViewById(R.id.type);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        upcomingEventObject item = dataSet.get(i);
        holder.name.setText(item.getDispalyName());
        holder.artist.setText(item.getArtist());
        holder.type.setText(item.getType());
        holder.time.setText(item.getDateTime());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

//    // View lookup cache
//    private static class ViewHolder {
//        TextView name;
//        TextView artist;
//        TextView time;
//        TextView type;
//    }
//
//    public upcomingListAdapter(ArrayList<upcomingEventObject> data, Context context){
//        super(context, R.layout.list_item_view, data);
//        this.dataSet = data;
//        this.mContext=context;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        final upcomingEventObject event = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        upcomingListAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
//
//        final View result;
//
//        if (convertView == null) {
//
//            viewHolder = new upcomingListAdapter.ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.fagement_upcoming_item, parent, false);
//
//            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
//            viewHolder.artist = (TextView) convertView.findViewById(R.id.artist);
//            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
//            viewHolder.type = (TextView) convertView.findViewById(R.id.type);
//
//            result=convertView;
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (upcomingListAdapter.ViewHolder) convertView.getTag();
//            result=convertView;
//        }
//
//        viewHolder.name.setText(event.getDispalyName());
//        viewHolder.artist.setText(event.getArtist());
//        viewHolder.time.setText(event.getDateTime());
//        viewHolder.type.setText(event.getType());
//
//        return convertView;
//
//    }

}
