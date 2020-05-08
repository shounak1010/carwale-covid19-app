package com.amaxindustries.carwaleappfinal;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    private Context context;
    private List<Country> list;

    public CountryAdapter(Context context, List<Country> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Country country = list.get(position);
        holder.txtCountry.setText(country.getCountry());
        holder.txtConfirmed.setText(String.valueOf(country.getConfirmed()));
        holder.txtRecovered.setText(String.valueOf(country.getRecovered()));
        holder.txtCritical.setText(String.valueOf(country.getCritical()));
        holder.txtDeaths.setText(String.valueOf(country.getDeaths()));
        holder.txtLatitude.setText(country.getLatitude());
        holder.txtLongitude.setText(country.getLongitude());
        holder.txtLastChange.setText(country.getLastChange().toString());
        holder.txtLastUpdate.setText(country.getLastUpdate().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCountry, txtConfirmed, txtRecovered, txtCritical, txtDeaths, txtLatitude, txtLongitude, txtLastChange, txtLastUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCountry = itemView.findViewById(R.id.country);
            txtConfirmed = itemView.findViewById(R.id.confirmed);
            txtRecovered = itemView.findViewById(R.id.recovered);
            txtCritical = itemView.findViewById(R.id.critical);
            txtDeaths = itemView.findViewById(R.id.deaths);
            txtLatitude = itemView.findViewById(R.id.latitude);
            txtLongitude = itemView.findViewById(R.id.longitude);
            txtLastChange = itemView.findViewById(R.id.lastChange);
            txtLastUpdate = itemView.findViewById(R.id.lastUpdate);
        }
    }
}
