package com.example.prog2project;

import static android.widget.Toast.makeText;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View calFragView = inflater.inflate(R.layout.fragment_calendar, container, false);
        Button prevMonthButton = (Button) calFragView.findViewById(R.id.prevButton);
        Button nextMonthButton = (Button) calFragView.findViewById(R.id.nextButton);

        prevMonthButton.setOnClickListener(view -> prevButtonAction());
        nextMonthButton.setOnClickListener(view -> nextButtonAction());

        return calFragView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initWidgets();
        selectedDate = LocalDate.now(); //vegre helyreteve
        setMonthView();
    }

    private void setMonthView() {

        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        for (int i = 1; i<=42; i++) {
            if( i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                daysInMonthArray.add("");
            }
            else{
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }


    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM YYYY");
        return date.format(formatter);
    }

    private void initWidgets() {
        calendarRecyclerView = getView().findViewById(R.id.CalendarRecycleView);
        monthYearText = getView().findViewById(R.id.monthText);
    }


    public void prevButtonAction(){
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }
    public void nextButtonAction(){
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if(dayText.equals("")){
            String message = "Selected date: "+dayText+" "+monthYearFromDate(selectedDate);
            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();

        }
    }
}