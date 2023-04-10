package com.example.dkaushik_mycarfootprint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GasStationAdapter extends ArrayAdapter<GasStation> {
    public GasStationAdapter(@NonNull Context context,  @NonNull ArrayList<GasStation> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        // convertView is used for converting the information from the front end (view)
        // to backend

        View view;
        if(convertView == null){
            // if there is no previously used view for the fragment, then the addgasstation layout
            // must be inflated
            // this is for optimization so that the previously used view can be used.
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,parent,false);
        } else {
            view = convertView;
        }
        // Get the item from the array adapter that we want to project onto our listview
        GasStation gasStation = super.getItem(position);

        // get all the components from the GasStation object that we want on the listview

        TextView carbonFP = view.findViewById(R.id.carbon_footprint);
        TextView litresPrev = view.findViewById(R.id.litres_preview);
        TextView sName = view.findViewById(R.id.station_text);
        TextView pricePerLitre = view.findViewById(R.id.ppl_column);
        TextView dateView = view.findViewById(R.id.date_column);
        TextView dieselView = view.findViewById(R.id.diesel_column);
        TextView totalCostView = view.findViewById(R.id.total_cost_view);

        // Name
        String name = gasStation.getName();
        // Carbon
        float carb = gasStation.getCarbFP();
        // Litres
        float litres = gasStation.getLitres();
        // Price per Litre
        float PPL = gasStation.getPricePerLitre();
        // Date
        Calendar rawDate = gasStation.getDate();
        // Diesel
        boolean isDiesel = gasStation.getDiesel();
        // Total cost
        float totalCost = gasStation.getTotalCost();
        // Credits: Android Studio Website
        // Reference: https://developer.android.com/reference/java/text/SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Simple date format requires date object
        Date tempDate = new Date(rawDate.getTimeInMillis());
        DecimalFormat df2 = new DecimalFormat("0.00");
        DecimalFormat df3 = new DecimalFormat("0");

        sName.setText(name);
        carbonFP.setText("Carbon FP: " + df3.format(carb));
        litresPrev.setText("Litres: " + String.valueOf(litres));
        pricePerLitre.setText("Price/Litre: " + df2.format(PPL));
        totalCostView.setText("Total Cost:" + df2.format(totalCost));
        dateView.setText("Date: " + dateFormat.format(tempDate));
        dieselView.setText("Diesel: " + String.valueOf(isDiesel));

        return view;
    }

}