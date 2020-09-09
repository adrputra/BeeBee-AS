package com.adrputra.beebee.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adrputra.beebee.R;
import com.adrputra.beebee.model.callback.LastStatusCallback;

import java.text.BreakIterator;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {
    private List<LastStatusCallback> events;
    private int rowLayout;
    private Context context;
    private ImageView mImageView,img;

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        LinearLayout eventLayout;
        TextView event_parameter;
        TextView event_value;
        ImageView mImageView,img;

        public EventViewHolder(View v) {
            super(v);
            mImageView = v.findViewById(R.id.imageView);
            eventLayout = v.findViewById(R.id.event_layout);
            event_parameter = v.findViewById(R.id.event_parameter);
            event_value = v.findViewById(R.id.event_value);
        }
    }

    public EventsAdapter(List<LastStatusCallback> events, int rowLayout, Context context) {
        this.events = events;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    @NonNull
    @Override
    public EventsAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new EventsAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
//        img.setImageResource();
        holder.event_parameter.setText(events.get(position).getParameter());
        holder.event_value.setText(events.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
