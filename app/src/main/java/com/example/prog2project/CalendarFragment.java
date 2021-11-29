package com.example.prog2project;

import static android.widget.Toast.makeText;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;


public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {
    public static final String SAVE_FILE_NAME = "calPrefs.txt";
    private static final int REQUEST_CODE = 111;




    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    public LocalDate selectedFullDate;
    public  ArrayList<CalendarData> calArrayList = new ArrayList<>();
    DateTimeFormatter fos = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public ArrayList<String> thisMonthDayList = new ArrayList<>();


    String title="",note="",location="",
            startDateP="", endDateP="";


    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View calFragView = inflater.inflate(R.layout.fragment_calendar, container, false);
        Button prevMonthButton = (Button) calFragView.findViewById(R.id.prevButton);
        Button nextMonthButton = (Button) calFragView.findViewById(R.id.nextButton);
        Button addDateEventButton = (Button) calFragView.findViewById(R.id.addDateEventButton);
        Button dispDatesButton = (Button) calFragView.findViewById(R.id.dispDatesButton);
        Button remEventButton = (Button) calFragView.findViewById(R.id.removeEventButton);

        dispDatesButton.setOnClickListener(view -> homeListDatesEvent());
        prevMonthButton.setOnClickListener(view -> prevButtonAction());
        nextMonthButton.setOnClickListener(view -> nextButtonAction());
        addDateEventButton.setOnClickListener(view -> addDateEvent());
        remEventButton.setOnClickListener(view -> delEvent());
        return calFragView;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            calArrayInit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initWidgets();
        selectedDate = LocalDate.now(); //vegre helyreteve
        setMonthView();





    }

    private void setMonthView() {

        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        initThisMonthDayList();
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this, thisMonthDayList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);


    }
/*
Ez a metodus a standard naptar-het-szamitashoz van,
sajnos vasarnapot tekinti a het elso napjanak,
dehat standardizalt *shrugs*

 */
    public void initThisMonthDayList(){
        try {
            calArrayInit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        thisMonthDayList.clear();
        for (CalendarData c : calArrayList)
            if(monthYearFromDate(selectedDate).equals(monthYearFromDate(c.getStartDate())))
                thisMonthDayList.add(String.valueOf(c.getStartDate().getDayOfMonth()));

    }
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }


    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM YYYY");
        return date.format(formatter);
    }

    private void initWidgets() {
        calendarRecyclerView = getView().findViewById(R.id.CalendarRecycleView);
        monthYearText = getView().findViewById(R.id.monthText);
    }


    public void prevButtonAction() {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextButtonAction() {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }


    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            String message = "Selected date: " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            selectedFullDate = selectedDate.withDayOfMonth(1).plusDays(Integer.parseInt(dayText) - 1);

        }
    }

    private void addDateEvent() {
        Toast.makeText(getActivity(), selectedFullDate.toString(), Toast.LENGTH_LONG).show();
        try {
             calArrayInit();
        } catch (FileNotFoundException e) {
            calArrayList = new ArrayList<CalendarData>();
        }

        startDateP = selectedFullDate.toString();
        Intent intent = new Intent(getActivity(), AddEventActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("location",location);
        intent.putExtra("note",note);
        intent.putExtra("startDate",startDateP);
        intent.putExtra("endDate",endDateP);


        startActivityForResult(intent, REQUEST_CODE);



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == 111) {
                    calArrayList.add(new CalendarData(
                            LocalDate.parse(data.getStringExtra("startDate"),fos),
                            LocalDate.parse(data.getStringExtra("endDate"),fos),
                            data.getStringExtra("location"),
                            data.getStringExtra("title"),
                            data.getStringExtra("note")));
                    try {
                        writeStorage();
                    }catch(Exception e){}

                }
                break;
        }

    }

    public void homeListDatesEvent()  {
        try {
             calArrayInit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> kekw = new ArrayList<>();
        kekw.clear();
        for (CalendarData hehe:calArrayList) kekw.add(hehe.getStartDate().toString());
        Toast.makeText(getActivity(), kekw.toString() , Toast.LENGTH_LONG).show();


    }
    public void delEvent(){
        try {
            calArrayInit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i< calArrayList.size();i++) {
            if (calArrayList.get(i).getStartDate().equals(selectedFullDate))
                calArrayList.remove(i);

        }
        try {
            writeStorage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    setMonthView();
    }

    public void writeStorage() throws IOException {

            FileOutputStream output = getContext().openFileOutput(SAVE_FILE_NAME, Context.MODE_PRIVATE);
            DataOutputStream dout = new DataOutputStream(output);
            dout.writeInt(calArrayList.size());
            for (CalendarData line : calArrayList) {
                dout.writeUTF(CalDataToString(line));
            }
            dout.flush();
            dout.close();


    }

    public String CalDataToString(CalendarData calD) {
        String vv =
                calD.getStartDate().toString()
                        + " " +
                        calD.getEndDate().toString()
                        + " " +
                        calD.getLocation()
                        + " " +
                        calD.getTitle()
                        + " " +
                        calD.getNotes()
                        + "\n";
        return vv;
    }

    public void calArrayInit() throws FileNotFoundException {

        FileInputStream input = getContext().openFileInput(SAVE_FILE_NAME);
        DataInputStream din = new DataInputStream(input);
        int sz = 0;
        try {
            sz = din.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        calArrayList.clear();
        String[] stringArrayTemp;
        for (int i = 0; i < sz; i++) {


            String line = null;
            try {
                line = din.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringArrayTemp = line.split(" ");
            try {
               calArrayList.add(new CalendarData(
                        LocalDate.parse(stringArrayTemp[0], fos),
                        LocalDate.parse(stringArrayTemp[1], fos),
                        stringArrayTemp[2],
                        stringArrayTemp[3],
                        stringArrayTemp[4])
                );
            } catch (Exception e) {
            }
        }
        try {
            din.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
