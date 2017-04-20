package sehgal.attendancemodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WriteDiary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);
        Intent intent = getIntent();

        final String stddiary = intent.getStringExtra("classSheet");
        TextView tvclassdiary=(TextView) findViewById(R.id.classDiary);
        tvclassdiary.setText("STUDENT DIARY OF "+stddiary);

        final EditText subjectfetch  =(EditText)findViewById(R.id.etSubject);
        final EditText messagefetch=(EditText)findViewById(R.id.etMessage);

        Button btnselectstd = (Button) findViewById(R.id.btnselectstd);
               btnselectstd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String subject = subjectfetch.getText().toString();
                String message = messagefetch.getText().toString();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("Subject",subject);
                intent.putExtra("Message",message);
                intent.putExtra("classSheet",stddiary);
                intent.putExtra("buttonTAG","SELECTED STUDENTS");
                startActivity(intent);

            }
        });

    }
}
