package sehgal.attendancemodule;

        import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FirstPage extends AppCompatActivity {
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String Attendance="SELECT CLASS TO MARK ATTENDANCE";
    String  Diary="SELECT CLASS TO WRITE IN STUDENT DIARY";
    String Result="SELECT CLASS TO VIEW RESULT";
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        spinner=(Spinner)findViewById(R.id.spinner);
        adapter=ArrayAdapter.createFromResource(this,R.array.class_name,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //from Event Decide
        Intent intent = getIntent();
        final String eventname = intent.getStringExtra("EventDecide");

       TextView tvEventDecideName=(TextView) findViewById(R.id.tvEventDecideName);
        tvEventDecideName.setText(eventname);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos=position;

                if (pos==0){}

                else{Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" SELECTED ",Toast.LENGTH_SHORT).show();}

                if(eventname.equals(Attendance)){

                Intent i = new Intent(getApplicationContext(),MainActivity.class);

                if(pos==1) {

                    i.putExtra("classSheet","CLASS1");
                    startActivity(i);
                }
                if(pos==2) {

                    i.putExtra("classSheet","CLASS2");
                    startActivity(i);

                }
                if(pos==3) {

                    i.putExtra("classSheet","CLASS3");
                    startActivity(i);
                }
                if(pos==4) {

                    i.putExtra("classSheet","CLASS4");
                    startActivity(i);
                }
                if(pos==5) {

                    i.putExtra("classSheet","CLASS5");
                    startActivity(i);
                }
                if(pos==6) {

                    i.putExtra("classSheet","CLASS6");
                    startActivity(i);
                }
                if(pos==7) {

                    i.putExtra("classSheet","CLASS7");
                    startActivity(i);
                }
                if(pos==8) {

                    i.putExtra("classSheet","CLASS8");
                    startActivity(i);
                }
                if(pos==9) {

                    i.putExtra("classSheet","CLASS9");
                    startActivity(i);
                }
                if(pos==10) {

                    i.putExtra("classSheet","CLASS10");
                    startActivity(i);
                } if(pos==11) {

                    i.putExtra("classSheet","CLASS11");
                    startActivity(i);
                } if(pos==12) {

                    i.putExtra("classSheet","CLASS12");
                    startActivity(i);
                }}

                else if(eventname.equals(Diary)){
                    Intent i = new Intent(getApplicationContext(),WriteDiary.class);

                    if(pos==1) {

                        i.putExtra("classSheet","CLASS1");
                        startActivity(i);
                    }
                    if(pos==2) {

                        i.putExtra("classSheet","CLASS2");
                        startActivity(i);

                    }
                    if(pos==3) {

                        i.putExtra("classSheet","CLASS3");
                        startActivity(i);
                    }
                    if(pos==4) {

                        i.putExtra("classSheet","CLASS4");
                        startActivity(i);
                    }
                    if(pos==5) {

                        i.putExtra("classSheet","CLASS5");
                        startActivity(i);
                    }
                    if(pos==6) {

                        i.putExtra("classSheet","CLASS6");
                        startActivity(i);
                    }
                    if(pos==7) {

                        i.putExtra("classSheet","CLASS7");
                        startActivity(i);
                    }
                    if(pos==8) {

                        i.putExtra("classSheet","CLASS8");
                        startActivity(i);
                    }
                    if(pos==9) {

                        i.putExtra("classSheet","CLASS9");
                        startActivity(i);
                    }
                    if(pos==10) {

                        i.putExtra("classSheet","CLASS10");
                        startActivity(i);
                    } if(pos==11) {

                        i.putExtra("classSheet","CLASS11");
                        startActivity(i);
                    } if(pos==12) {

                        i.putExtra("classSheet","CLASS12");
                        startActivity(i);
                    }}

                if(eventname.equals(Result)){

                    Intent i = new Intent(getApplicationContext(),Result.class);

                    if(pos==1) {

                        i.putExtra("classSheet","CLASS1");
                        i.putExtra("Check","ALL");
                        startActivity(i);
                    }
                    if(pos==2) {

                        i.putExtra("classSheet","CLASS2");
                        i.putExtra("Check","ALL");
                        startActivity(i);

                    }
                    if(pos==3) {

                        i.putExtra("classSheet","CLASS3");
                        i.putExtra("Check","ALL");
                        startActivity(i);
                    }
                    if(pos==4) {

                        i.putExtra("classSheet","CLASS4");
                        i.putExtra("Check","ALL");
                        startActivity(i);
                    }
                    if(pos==5) {

                        i.putExtra("classSheet","CLASS5");
                        i.putExtra("Check","ALL");
                        startActivity(i);
                    }
                    if(pos==6) {

                        i.putExtra("classSheet","CLASS6");
                        i.putExtra("Check","ALL");
                        startActivity(i);
                    }
                    if(pos==7) {

                        i.putExtra("classSheet","CLASS7");
                        i.putExtra("Check","ALL");
                        startActivity(i);
                    }
                    if(pos==8) {

                        i.putExtra("classSheet","CLASS8");
                        i.putExtra("Check","ALL");
                        startActivity(i);
                    }
                    if(pos==9) {

                        i.putExtra("classSheet","CLASS9");
                        i.putExtra("Check","ALL");
                        startActivity(i);
                    }
                    if(pos==10) {

                        i.putExtra("classSheet","CLASS10");
                        i.putExtra("Check","ALL");
                        startActivity(i);
                    } if(pos==11) {

                        i.putExtra("classSheet","CLASS11");
                        i.putExtra("Check","ALL");
                        startActivity(i);
                    } if(pos==12) {

                        i.putExtra("classSheet","CLASS12");
                        i.putExtra("Check","ALL");
                        startActivity(i);
                    }}


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
