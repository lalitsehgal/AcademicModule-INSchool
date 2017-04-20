package sehgal.attendancemodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        ImageButton btnteacher = (ImageButton) findViewById(R.id.btnteacher);
        ImageButton btnstudent = (ImageButton) findViewById(R.id.btnstudent);

       btnteacher.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),EventDecide.class);
                startActivity(intent);

            }
        });

        btnstudent.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),StudentForm.class);
                startActivity(intent);

            }
        });

    }
}
