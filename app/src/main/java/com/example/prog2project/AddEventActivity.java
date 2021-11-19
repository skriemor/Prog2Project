package com.example.prog2project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.time.LocalDate;

public class AddEventActivity extends Activity {

    String loco,noto,titlo,startDato,endDato;
    Button doneAddEventButton ;
    EditText locTB ;
    EditText noteTB ;
    EditText titleTB;
    EditText endDateTB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        endDateTB = this.findViewById(R.id.endDateTB);
        noteTB = this.findViewById(R.id.noteTB);
        titleTB = this.findViewById(R.id.titleTB);
        locTB  = findViewById(R.id.locTB);
        doneAddEventButton = findViewById(R.id.doneAddEventButton);


        Intent inti = getIntent();
        loco=inti.getStringExtra("location");
        noto=inti.getStringExtra("note");
        titlo=inti.getStringExtra("title");
        startDato= inti.getStringExtra("startDate");
        endDato = inti.getStringExtra("endDate");





        doneAddEventButton.setOnClickListener(view -> cockaroo());

        TextView startDateTV = findViewById(R.id.startDateTV);
        startDateTV.setText(startDato);


    }


    private void cockaroo(){


        endDato = endDateTB.getText().toString();
        titlo = titleTB.getText().toString();
        noto = noteTB.getText().toString();
        loco = locTB.getText().toString();
       // if (endDato.isEmpty() || titlo.isEmpty() || noto.isEmpty() || loco.isEmpty()) return;


        Intent intent = new Intent();
        intent.putExtra("endDate", endDato);
        intent.putExtra("startDate", startDato);
        intent.putExtra("title", titlo);
        intent.putExtra("note", noto);
        intent.putExtra("loco", loco);
        setResult(111, intent);
        finish();
    }
}