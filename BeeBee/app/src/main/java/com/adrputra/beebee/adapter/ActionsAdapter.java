package com.adrputra.beebee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adrputra.beebee.R;
import com.adrputra.beebee.model.callback.ActionsCallback;

import java.util.List;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.ActionViewHolder> {
    private List<ActionsCallback> actions;
    private int rowLayout;
    private Context context;

    public static class ActionViewHolder extends RecyclerView.ViewHolder {
        LinearLayout actionLayout;
        TextView action_name;
        TextView action_displayname;
        TextView action_visible;

        public ActionViewHolder(View v) {
            super(v);
            actionLayout = v.findViewById(R.id.action_layout);
            action_name = v.findViewById(R.id.action_name);
            action_displayname = v.findViewById(R.id.action_displayname);
            action_visible = v.findViewById(R.id.action_visible);
        }
    }

    public ActionsAdapter(List<ActionsCallback> actions, int rowLayout, Context context) {
        this.actions = actions;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public ActionsAdapter.ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ActionsAdapter.ActionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionsAdapter.ActionViewHolder holder, int position) {
        holder.action_name.setText(actions.get(position).getName());
        holder.action_displayname.setText(actions.get(position).getDisplayName());
        holder.action_visible.setText(actions.get(position).getVisible().toString());
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }
}
