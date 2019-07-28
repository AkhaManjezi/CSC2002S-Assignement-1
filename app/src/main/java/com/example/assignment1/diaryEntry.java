package com.example.assignment1;

import java.util.Date;

public class diaryEntry {
    private double breakfast;
    private double lunch;
    private double dinner;
    private double snacks;
    private double weightlifting;
    private double cardio;
    private double mixed;
    private Date date;



    public diaryEntry(double breakfast, double lunch, double dinner, double snacks, double weightlifting, double cardio, double mixed, Date date){
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.snacks = snacks;
        this.weightlifting = weightlifting;
        this.cardio = cardio;
        this.mixed = mixed;
        this.date = date;
    }

    public void setBreakfast(double breakfast) {
        this.breakfast = breakfast;
    }

    public void setLunch(double lunch) {
        this.lunch = lunch;
    }

    public void setDinner(double dinner) {
        this.dinner = dinner;
    }

    public void setSnacks(double snacks) {
        this.snacks = snacks;
    }

    public void setWeightlifting(double weightlifting) {
        this.weightlifting = weightlifting;
    }

    public void setCardio(double cardio) {
        this.cardio = cardio;
    }

    public void setMixed(double mixed) {
        this.mixed = mixed;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getBreakfast() {
        return breakfast;
    }

    public double getLunch() {
        return lunch;
    }

    public double getDinner() {
        return dinner;
    }

    public double getSnacks() {
        return snacks;
    }

    public double getCardio() {
        return cardio;
    }

    public double getMixed() {
        return mixed;
    }

    public double getWeightlifting() {
        return weightlifting;
    }

    public Date getDate() {
        return date;
    }

    public double getFoodKJ() {
        return (getBreakfast() + getLunch() + getDinner() + getSnacks());
    }

    public double getExerciseKJ() {
        return (getWeightlifting() + getCardio() + getMixed());
    }

    public double getNKI() {
        return (getFoodKJ() - getExerciseKJ());
    }
}
