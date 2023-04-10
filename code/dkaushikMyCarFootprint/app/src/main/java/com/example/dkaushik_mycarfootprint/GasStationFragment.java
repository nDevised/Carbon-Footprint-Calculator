package com.example.dkaushik_mycarfootprint;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.View;

import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import java.util.Objects;

public class GasStationFragment extends Fragment {
    // Buttons and EditText
    private Button cancelBtn;
    private Button addBtn;
    private EditText gasStationNameTxt;

    private EditText litresTxt;
    private EditText pricePerLitreTxt;
    private String gasStationName = "";

    private int litres;
    private float pricePerLitre;
    private boolean isAdding;
    private GasStation gs;

    private CheckBox boxDiesel;
    private boolean isDiesel;

    private CalendarView calView;
    private Calendar date;

    private AddStationListener listener;


    public GasStationFragment() {
        // if the intent got a position to edit

        this.isAdding = true;
    }

    public GasStationFragment(GasStation editable) {

        // editing mode
        this.gs = editable;
        this.isAdding = false;
    }

    public interface AddStationListener{
        // Interface will be implemented in MainActivity
        void addStation(GasStation station);
        void editStation(GasStation item, Calendar date, String name, int litres, float pricePerLitre, boolean isDiesel);
    }
    // from lab 3


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddStationListener) {
            // listener refers to MainActivity
            listener = (AddStationListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddStationListener");
        }
    }

    public void setAttributes(Dialog dialog){
        // Find all the GUI elements and assign them to their variables
        cancelBtn = dialog.findViewById(R.id.cancelStationAddButton);
        addBtn = dialog.findViewById(R.id.confirmButton);
        gasStationNameTxt = dialog.findViewById(R.id.gasStationName);
        litresTxt = dialog.findViewById(R.id.litres);
        pricePerLitreTxt = dialog.findViewById(R.id.pricePerLitre);
        boxDiesel = dialog.findViewById(R.id.checkBox);
        calView  = dialog.findViewById(R.id.calendarView);

        if (!isAdding){
            // If editing, assign the GasStation's values object to the GUI elements
            assignValuesEdit();
        }
    }

    public void assignValues(){
        // assign values to all attributes that are not being checked
        gasStationName = gasStationNameTxt.getText().toString();
        isDiesel = boxDiesel.isChecked();

        if (date == null){
            date = Calendar.getInstance();

            // Get today's date (default value for date when no part of calendar was clicked)
            date.setTimeInMillis(calView.getDate());
        }


    }

    public void assignValuesEdit(){
        // If editing, assign the GasStation's values object to the GUI elements
        gasStationNameTxt.setText(gs.getName());
        litresTxt.setText(String.valueOf(gs.getLitres()));
        pricePerLitreTxt.setText(String.valueOf(gs.getPricePerLitre()));
        boxDiesel.setChecked(gs.getDiesel());

        // set the date
        calView.setDate(gs.getDate().getTimeInMillis());

    }

    private boolean checkLitres(){
        try {
            // Check if the input for the litres, if it can be parsed
            litres = Integer.parseInt(litresTxt.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println(e);
            // Reset the text
            litresTxt.setText("");
            return false;
        }
        return true;
    }

    private boolean checkPPL(){
        try {
            // Check if the input for the PPL, if it can be parsed
            pricePerLitre = Float.parseFloat(pricePerLitreTxt.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println(e);
            // Reset the text
            pricePerLitreTxt.setText("");
            return false;
        }
        return true;
    }

    private boolean tester(){
        boolean TestComplete;
        assignValues();
        // Check if all the input fields are filled in / correct
        if (gasStationName.length() <= 30 && !Objects.equals(gasStationName, "") && checkLitres() && checkPPL()) {
            // Assign this to our gas station object
            TestComplete = true;
            System.out.println("NICE!");
        } else {
            TestComplete = false;
        }

        return TestComplete;
    }

    void showDialogueBox(){
        // Credits: Chirag-sam
        // https://github.com/Pro-Grammerr/Custom-Dialog/blob/master/app/src/main/java/com/awesomeness/customdialog/MainActivity.java
        // He's the real MVP
        Dialog dialog = new Dialog(getContext());

        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.fragment_gas_station);

        // show the dialog box
        dialog.show();
        setAttributes(dialog);


        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                // Credits: Android Studio Website
                // https://developer.android.com/reference/android/widget/CalendarView.OnDateChangeListener
                // https://developer.android.com/reference/java/util/Calendar (For Calender)

                // Note: There was an issue with date object storing time so I had to use this method
                date = Calendar.getInstance();
                date.set(year,month,day);


            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tester()){
                    // if the checks pass
                    if (isAdding){
                        // if adding

                        listener.addStation(new GasStation(date, gasStationName,litres,pricePerLitre,isDiesel));
                        System.out.println("name:");
                        System.out.println(litres);
                        System.out.println(pricePerLitre);

                        dialog.dismiss();

                    } else{
                        // editing mode (get the gas station object's position and set the attributes)
                        listener.editStation(gs, date,gasStationName,litres,pricePerLitre,isDiesel);
                        dialog.dismiss();
                    }
                } else{
                    // Show error about incorrect entries
                    Toast toast = Toast.makeText(getContext(), "Please fill in all input fields with valid input and make sure name is max of 30 chars.", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // exit the menu
                dialog.dismiss();

            }

        });

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.showDialogueBox();

    }
}