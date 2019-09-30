package com.frogobox.cleaner.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.frogobox.cleaner.model.PowerItem;
import com.frogobox.cleaner.myapplication.R;

import java.util.List;

/**
 * Created by Frogobox Software Industries 2/16/2017.
 */

public class PowerViewAdapter extends RecyclerView.Adapter<PowerViewAdapter.MyViewHolder> {

    private List<PowerItem> apps;

    public PowerViewAdapter(List<PowerItem> getapps) {
        apps = getapps;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.powe_itemlist, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PowerItem app = apps.get(position);
        holder.size.setText(app.getText());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView size;


        private MyViewHolder(View view) {
            super(view);
            size = view.findViewById(R.id.items);
        }
    }
}
