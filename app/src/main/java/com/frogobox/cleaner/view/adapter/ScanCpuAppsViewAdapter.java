package com.frogobox.cleaner.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.frogobox.cleaner.model.Apps;
import com.frogobox.cleaner.myapplication.R;

import java.util.List;

/**
 * Created by Frogobox Software Industries 2/25/2017.
 */

public class ScanCpuAppsViewAdapter extends RecyclerView.Adapter<ScanCpuAppsViewAdapter.MyViewHolder> {

    /// Get List of Apps Causing Junk Files

    private List<Apps> apps;

    public ScanCpuAppsViewAdapter(List<Apps> getapps) {
        apps = getapps;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_cpu_apps, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        Apps app = apps.get(position);
        holder.size.setText("");
        holder.image.setImageDrawable(app.getImage());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView size;
        private ImageView image;

        private MyViewHolder(View view) {
            super(view);
            size = view.findViewById(R.id.apptext);
            image = view.findViewById(R.id.appimage);

        }
    }
}
