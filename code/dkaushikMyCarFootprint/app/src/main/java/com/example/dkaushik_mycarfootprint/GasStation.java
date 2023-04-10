package com.example.dkaushik_mycarfootprint;

import java.util.Calendar;


public class GasStation {
    // GasStation class will store all the required information for a visit to a station.
    private Calendar date; //The date when the gas was purchased
    private String name; //The name of the gas station
    private int litres; //The number of litres of gas purchased
    private float pricePerLitre; //The price per litre of the gas purchased
    private boolean isDiesel; //Whether the gas purchased is diesel or not

    //Constructor for the GasStation object
    public GasStation(Calendar date, String name, int litres, float pricePerLitre, boolean isDiesel) {
        this.date = date;
        this.name = name;
        this.litres = litres;
        this.pricePerLitre = pricePerLitre;
        this.isDiesel = isDiesel;
    }

    //Getter for the date
    public Calendar getDate() {
        return date;
    }

    //Setter for the date
    public void setDate(Calendar date) {
        this.date = date;
    }

    //Getter for the name
    public String getName() {
        return name;
    }

    //Setter for the name
    public void setName(String name) {
        this.name = name;
    }

    //Getter for the number of litres
    public int getLitres() {
        return litres;
    }

    //Setter for the number of litres
    public void setLitres(int litres) {
        this.litres = litres;
    }

    //Getter for the price per litre
    public float getPricePerLitre() {
        return pricePerLitre;
    }

    //Setter for the price per litre
    public void setPricePerLitre(float pricePerLitre) {
        this.pricePerLitre = pricePerLitre;
    }

    //Getter for whether the gas is diesel or not
    public boolean getDiesel() {
        return isDiesel;
    }

    //Setter for whether the gas is diesel or not
    public void setDiesel(boolean diesel) {
        isDiesel = diesel;
    }

    //Calculates the cost of the gas based on whether it is diesel or not
    public float getCarbFP(){
        if (this.isDiesel){
            return (float) (2.69 * this.litres);
        }
        return (float) (2.32 * this.litres);
    }

    //Calculates the total cost of the gas purchased
    public float getTotalCost(){
        return this.litres * this.pricePerLitre;
    }
}
