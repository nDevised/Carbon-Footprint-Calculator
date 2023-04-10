package com.example.dkaushik_mycarfootprint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

// Class declaration
public class MainActivity extends AppCompatActivity implements GasStationFragment.AddStationListener {

    // Class level variables
    ArrayList<GasStation> gsList; // array list to store gas stations
    GasStationAdapter gsAdapter; // adapter to display gas stations in the list view
    private Button addNewVisitBtn; // button to add a new visit to a gas station
    private Button delVistBtn; // button to delete visits to gas stations
    private Button editBtn; // button to edit a visit to a gas station
    private int itemIndex = -1; // index of the selected item in the list view
    private ListView mListView; // list view to display gas stations
    private TextView totalCarbonFP; // text view to show the total carbon footprint
    private TextView totalCost; // text view to show the total cost of visits to gas stations

    private DecimalFormat df2; // decimal format to format floating point numbers with 2 decimal places
    private DecimalFormat df3; // decimal format to format floating point numbers with 3 decimal places

    // Override onCreate method
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the array list and the adapter
        gsList = new ArrayList<GasStation>();
        gsAdapter = new GasStationAdapter(this, gsList);

        // Get references to the buttons and the list view
        addNewVisitBtn = (Button)findViewById(R.id.addNewVisit);
        delVistBtn = (Button)findViewById(R.id.deleteVisits);
        editBtn = (Button)findViewById(R.id.editButton);
        mListView = findViewById(R.id.list_view_city);
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Set the adapter to the list view
        mListView.setAdapter(gsAdapter);

        // Get references to the text views
        totalCarbonFP = findViewById(R.id.CarbonFPTotalTxt);
        totalCost = findViewById(R.id.TotalCostTxt);

        // Initialize the decimal formats
        df2 = new DecimalFormat("0.00");
        df3 = new DecimalFormat("0");

        // Add a click listener to the add
        addNewVisitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Credits: Android Studio Website
                // https://developer.android.com/reference/android/view/View.OnClickListener
                // FragmentManager manages all the fragments within an activity
                // beginTransaction will access the fragment manager and listen to what the
                // transaction will be
                // Create new fragment and transaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);

                // Replace whatever is in the fragment_container view with this fragment
                transaction.replace(R.id.main_activity, GasStationFragment.class, null);

                // Commit the transaction

                transaction.commit();


            }
        });

        // List view event listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                // Adapted from Lab 2
                itemIndex = position;
            }
        });


        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // make sure there is a position selected
                try {

                    // Credits: Android Studio Website
                    // https://developer.android.com/reference/android/view/View.OnClickListener
                    // FragmentManager manages all the fragments within an activity
                    // beginTransaction will access the fragment manager and listen to what the
                    // transaction will be
                    // Create new fragment and transaction

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setReorderingAllowed(true);

                    // Replace whatever is in the fragment_container view with this fragment
                    transaction.replace(R.id.main_activity, new GasStationFragment(gsList.get(itemIndex)), null);

                    // Commit the transaction

                    transaction.commit();

                } catch(Exception e){
                    // show toast
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select an item to edit", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        // delete button
        delVistBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // make sure there is a position selected
                try {
                    // Credits: Android Studio Website
                    // https://developer.android.com/reference/android/view/View.OnClickListener
                    // FragmentManager manages all the fragments within an activity
                    // beginTransaction will access the fragment manager and listen to what the
                    // transaction will be
                    // Create new fragment and transaction

                    deleteStation(itemIndex);
                    setCarbonFPandCost();
                    gsAdapter.notifyDataSetChanged();
                    if (gsList.isEmpty()){
                        itemIndex = -1;
                    }


                } catch(Exception e){
                    // show toast
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select an item to delete", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    @Override
    public void addStation(GasStation station) {
        // Adds a new gas station to the list of gas stations (gsList).

        gsList.add(station);
        gsAdapter.notifyDataSetChanged();
        setCarbonFPandCost();
    }
    @Override
    public void editStation(GasStation item, Calendar date, String name, int litres, float pricePerLitre, boolean isDiesel) {
        // Edits an existing gas station by updating its properties like date, name, liters, pricePerLiter, and diesel/petrol flag.
        item.setDate(date);
        item.setName(name);
        item.setLitres(litres);
        item.setPricePerLitre(pricePerLitre);
        item.setDiesel(isDiesel);
        // update the view.
        gsAdapter.notifyDataSetChanged();

        // update the total carbon footprint and cost.
        setCarbonFPandCost();
    }
    public void deleteStation(int position){
        // Deletes a gas station from the list of gas stations (gsList) by specifying its position.
        gsList.remove(position);
        // Deselect all items on listview

    }

    private float getTotalCFP(){
        //Calculates the total carbon footprint of all the gas stations by adding the carbon footprint of each station.
        //Returns the total carbon footprint.
        float total = 0;
        for (int i = 0; i < gsList.size(); i++){
            total += gsList.get(i).getCarbFP();
        }
        return total;
    }

    private float getTotalCost(){
        // Calculates the total cost of all the gas stations by adding the cost of each station.
        // Returns the total cost.
        float total = 0;
        for (int i = 0; i < gsList.size(); i++){
            total += gsList.get(i).getTotalCost();
        }
        return total;
    }

    private void setCarbonFPandCost(){
        // Credits: Android Studio Website
        // https://developer.android.com/reference/android/icu/text/DecimalFormat
        // Updates the total carbon footprint and cost
        float totalCostVal, totalCarbonVal;
        totalCarbonVal = getTotalCFP();
        totalCostVal = getTotalCost();
        totalCarbonFP.setText("Total Carbon FP:" + df3.format(totalCarbonVal));
        totalCost.setText("Total Cost:" + df2.format(totalCostVal));
    }
}