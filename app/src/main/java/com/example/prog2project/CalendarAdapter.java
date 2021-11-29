package com.example.prog2project;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private ArrayList<String> LocArray;
    DateTimeFormatter fos = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener, ArrayList<String> locCalArray) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.LocArray = locCalArray;
    }


    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell,parent,false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.16666666);
        return new CalendarViewHolder(view, onItemListener);  //rák

    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {

        if (LocArray.contains(daysOfMonth.get(position))){
        //if(LocArray.contains(String.valueOf(position))){
            holder.dayOfMonth.setBackgroundColor(Color.parseColor("#FFAAFF"));

        }else{
            holder.dayOfMonth.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }



        holder.dayOfMonth.setText(daysOfMonth.get(position));


    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }
    public interface OnItemListener{
        void onItemClick(int position, String dayText);


    }
}
