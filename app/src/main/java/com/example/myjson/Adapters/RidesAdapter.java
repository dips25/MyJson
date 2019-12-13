package com.example.myjson.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myjson.Models.Rides;
import com.example.myjson.R;

import java.util.ArrayList;

public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.ViewHolder> {

    Context context;
    ArrayList<Rides> rides;


    public RidesAdapter(Context context,ArrayList<Rides> rides) {

        this.context = context;
        this.rides = rides;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ride_item,parent,false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final  Rides srides = rides.get(position);

        holder.from.setText(srides.getFrom());
        holder.to.setText(srides.getTo());
        holder.cost.setText(srides.getCurrency()+String.valueOf(srides.getCost()));
        holder.travel_time.setText("Travel Time:"+ srides.getTime());

    }

    @Override
    public int getItemCount() {
        return rides.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView from;
        TextView to;
        TextView cost;
        TextView travel_time;

        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);

            from = (TextView) cardView.findViewById(R.id.from);
            to = (TextView) cardView.findViewById(R.id.to);
            cost = (TextView) cardView.findViewById(R.id.cost);
            travel_time = (TextView) cardView.findViewById(R.id.travel_time);


        }
    }
}
