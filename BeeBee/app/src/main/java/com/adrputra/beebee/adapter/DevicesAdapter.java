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
import com.adrputra.beebee.model.callback.DevicesCallback;

import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder> {

    private List<DevicesCallback> devices;
    private int rowLayout;
    private Context context;

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView device_name;
        TextView device_version;
        TextView device_brand;
        TextView device_type;

        public DeviceViewHolder(View v) {
            super(v);
            moviesLayout = v.findViewById(R.id.devices_layout);
            device_name = v.findViewById(R.id.device_name);
            device_version = v.findViewById(R.id.device_version);
            device_brand = v.findViewById(R.id.device_brand);
            device_type = v.findViewById(R.id.device_type);
        }
    }

    public DevicesAdapter(List<DevicesCallback> devices, int rowLayout, Context context) {
        this.devices = devices;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public DevicesAdapter.DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        view.setLongClickable(true);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DevicesAdapter.DeviceViewHolder holder, int position) {
        holder.device_name.setText(devices.get(position).getName());
        holder.device_version.setText(devices.get(position).getModel());
        holder.device_brand.setText(devices.get(position).getBrand());
        holder.device_type.setText(devices.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

}
