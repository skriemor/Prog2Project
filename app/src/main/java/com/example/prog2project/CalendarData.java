package com.example.prog2project;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

public class CalendarData {
    DateFormat customDateFormatter = new SimpleDateFormat("yyyy/MM/dd hh:mm");
    DateFormat customDateParser = new SimpleDateFormat("yyyy.MM.dd.hh.mm");
    LocalDate startDate, endDate;
    String location, title, notes;

    public CalendarData(LocalDate startDate, LocalDate endDate, String location, String title, String notes) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.title = title;
        this.notes = notes;
    }

    private Date parseRawDate(String dateString){
        try {
            return customDateParser.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            Date nulldate = null;
            return nulldate;
        }

    }

    private String dateToString(Date d){
        if (d == null) return "";
        return customDateFormatter.format(d);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getNotes() {
        return notes;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
