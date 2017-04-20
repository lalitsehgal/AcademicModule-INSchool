package sehgal.attendancemodule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class StudentForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        Button button=(Button)findViewById(R.id.btn);
        final TextView tvName=(EditText)findViewById(R.id.name);
        final TextView tvRollno=(EditText)findViewById(R.id.rollno);
        final TextView tvClass=(EditText)findViewById(R.id.class1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  name1 = tvName.getText().toString();
                String  rollno1=tvRollno.getText().toString();
                String class1=tvClass.getText().toString();

                Intent intent=new Intent(getApplicationContext(),EventDecide.class);
                intent.putExtra("StdName", name1);
                intent.putExtra("StdRollno", rollno1);
                intent.putExtra("StdClass", class1);

                intent.putExtra("StdAttendance","VIEW ATTENDANCE");
                intent.putExtra("StdDiary", "VIEW DIARY");
                intent.putExtra("StdResult","VIEW RESULT");
                intent.putExtra("StdTimetable","VIEW TIME TABLE");

                startActivity(intent);

            }

        });
    }
}

