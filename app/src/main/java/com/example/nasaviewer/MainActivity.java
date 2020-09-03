package com.example.nasaviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button SDbtn,Gobtn;
    int year_x,month_x,day_x;
    static final int DIALOG_ID = 0;
    String date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Calendar cal = Calendar.getInstance();
        year_x= cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        SelectDateOnButtonClick();
        Gobtn = findViewById(R.id.go);
        GoToNextActivity();

    }

    public void SelectDateOnButtonClick(){
        SDbtn = (Button)findViewById(R.id.SelectDateBtn);
        SDbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);

            }
        });
    }
    @Override
    protected Dialog onCreateDialog (int id){
        if (id==DIALOG_ID)
            return new DatePickerDialog(this,dpickerListener,year_x,month_x,day_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x= year;
            month_x=month+1;
            day_x=dayOfMonth;
            Toast.makeText(MainActivity.this,year_x+"/"+month_x+"/"+day_x,Toast.LENGTH_LONG).show();
            date = year_x+"-"+month_x+"-"+day_x;
        }
    };

    public void GoToNextActivity(){
        Gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GetDetails.class);
                intent.putExtra("Date",date);
                startActivity(intent);
            }
        });
    }


}
