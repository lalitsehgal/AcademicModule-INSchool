package sehgal.attendancemodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EventDecide extends AppCompatActivity {

    String attendance="VIEW ATTENDANCE";
    String diary="VIEW DIARY";
    String result="VIEW RESULT";
    String timetable="VIEW TIME TABLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_decide);
        ImageButton attendancebtn = (ImageButton) findViewById(R.id.btnAttendance);
        ImageButton diarybtn = (ImageButton) findViewById(R.id.btnDiary);
        ImageButton resultbtn = (ImageButton) findViewById(R.id.btnresult);
        ImageButton timetablebtn = (ImageButton) findViewById(R.id.btntimetable);

        final TextView  tvAttendance=(TextView)findViewById(R.id.tvattendance);
        final TextView  tvdiary=(TextView)findViewById(R.id.tvdiary);
        final TextView tvresult=(TextView)findViewById(R.id.tvresult);
        final TextView  tvtimetable=(TextView)findViewById(R.id.tvtimetable);

       // ************** FROM STUDENT FORM

        Intent intent = getIntent();
        final String stdname = intent.getStringExtra("StdName");
        final String stdclass = intent.getStringExtra("StdClass");
        final String stdrollno = intent.getStringExtra("StdRollno");

        final String tvattendancename = intent.getStringExtra("StdAttendance");
        final String tvdiaryname = intent.getStringExtra("StdDiary");
        final String tvresultname = intent.getStringExtra("StdResult");
        final String tvtimetablename = intent.getStringExtra("StdTimetable");

        if(attendance.equals(tvattendancename)){
            tvAttendance.setText(tvattendancename);
            tvdiary.setText(tvdiaryname);
            tvresult.setText(tvresultname);
            tvtimetable.setText(tvtimetablename);
                    }


        attendancebtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(attendance.equals(tvattendancename)){
                    Intent intent = new Intent(getApplicationContext(),DisplayAttendanceDiary.class);
                    intent.putExtra("stdName",stdname);
                    intent.putExtra("stdClass",stdclass);
                    intent.putExtra("stdRollno",stdrollno);
                    intent.putExtra("check","ATTENDANCE");
                    intent.putExtra("sheetId","1MCJ6ygS_iGWkKf97uRUw1NhzPAb8FMNcyNm61H2_XME");

                    startActivity(intent);
                }
             else{
              Intent intent = new Intent(getApplicationContext(),FirstPage.class);
                intent.putExtra("EventDecide","SELECT CLASS TO MARK ATTENDANCE");
                startActivity(intent);}

            }
        });

           diarybtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(diary.equals(tvdiaryname)){
                    Intent intent = new Intent(getApplicationContext(),DisplayAttendanceDiary.class);
                    intent.putExtra("stdName",stdname);
                    intent.putExtra("stdClass",stdclass);
                    intent.putExtra("stdRollno",stdrollno);
                    intent.putExtra("check","DIARY");
                    intent.putExtra("sheetId","1t_5sEXmg6NNC1Ug6ClWLTQjA2q1EdXDDVdm7WL7YpWg");

                    startActivity(intent);
                }
                else{
                Intent intent = new Intent(getApplicationContext(),FirstPage.class);
                intent.putExtra("EventDecide","SELECT CLASS TO WRITE IN STUDENT DIARY");
                startActivity(intent);}

            }
        });

        resultbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(result.equals(tvresultname)){
                    Intent intent = new Intent(getApplicationContext(),Result.class);
                    intent.putExtra("stdName",stdname);
                    intent.putExtra("stdClass",stdclass);
                    intent.putExtra("stdRollno",stdrollno);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),FirstPage.class);
                    intent.putExtra("EventDecide","SELECT CLASS TO VIEW RESULT");
                    startActivity(intent);
                }

            }
        });

        timetablebtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(),BeforeTimeTable.class);
                    startActivity(intent);


            }
        });
    }





}
