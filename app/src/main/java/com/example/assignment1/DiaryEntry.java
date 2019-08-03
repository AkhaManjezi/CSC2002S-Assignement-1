package com.example.assignment1;

import java.util.Calendar;

public class DiaryEntry implements Comparable<DiaryEntry> {
    private String breakfast;
    private String lunch;
    private String dinner;
    private String snacks;
    private String weightlifting;
    private String cardio;
    private String mixed;
    private Calendar date;

    public DiaryEntry() {
        this.breakfast = "0";
        this.lunch = "0";
        this.dinner = "0";
        this.snacks = "0";
        this.weightlifting = "0";
        this.cardio = "0";
        this.mixed = "0";
        this.date = Calendar.getInstance();

    }

    public DiaryEntry(String breakfast, String lunch, String dinner, String snacks, String weightlifting, String cardio, String mixed, Calendar date) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.snacks = snacks;
        this.weightlifting = weightlifting;
        this.cardio = cardio;
        this.mixed = mixed;
        this.date = date;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = (breakfast.length() > 1 && breakfast.startsWith("0")) ? breakfast.substring(1) : breakfast;
    }

    public void setLunch(String lunch) {
        this.lunch = (lunch.length() > 1 && lunch.startsWith("0")) ? lunch.substring(1) : lunch;
    }

    public void setDinner(String dinner) {
        this.dinner = (dinner.length() > 1 && dinner.startsWith("0")) ? dinner.substring(1) : dinner;
    }

    public void setSnacks(String snacks) {
        this.snacks = (snacks.length() > 1 && snacks.startsWith("0")) ? snacks.substring(1) : snacks;
    }

    public void setWeightlifting(String weightlifting) {
        this.weightlifting = (weightlifting.length() > 1 && weightlifting.startsWith("0")) ? weightlifting.substring(1) : weightlifting;
    }

    public void setCardio(String cardio) {
        this.cardio = (cardio.length() > 1 && cardio.startsWith("0")) ? cardio.substring(1) : cardio;
    }

    public void setMixed(String mixed) {
        this.mixed = (mixed.length() > 1 && mixed.startsWith("0")) ? mixed.substring(1) : mixed;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public String getSnacks() {
        return snacks;
    }

    public String getCardio() {
        return cardio;
    }

    public String getMixed() {
        return mixed;
    }

    public String getWeightlifting() {
        return weightlifting;
    }

    public Calendar getDate() {
        return date;
    }

    public String getFoodKJ() {
        return (Double.parseDouble(getBreakfast()) + Double.parseDouble(getLunch()) + Double.parseDouble(getDinner()) + Double.parseDouble(getSnacks())) + "";
    }

    public String getExerciseKJ() {
        return (Double.parseDouble(getWeightlifting()) + Double.parseDouble(getCardio()) + Double.parseDouble(getMixed())) + "";
    }

    public String getNKI() {
        return (Double.parseDouble(getFoodKJ()) - Double.parseDouble(getExerciseKJ())) + "";
    }

    @Override
    public int compareTo(DiaryEntry toCompare) {
        return toCompare.getDate().compareTo(this.getDate());
    }

    @Override
    public String toString() {
        return "DiaryEntry{" +
                "breakfast='" + breakfast + '\'' +
                ", lunch='" + lunch + '\'' +
                ", dinner='" + dinner + '\'' +
                ", snacks='" + snacks + '\'' +
                ", weightlifting='" + weightlifting + '\'' +
                ", cardio='" + cardio + '\'' +
                ", mixed='" + mixed + '\'' +
                ", date=" + date +
                '}';
    }
}
