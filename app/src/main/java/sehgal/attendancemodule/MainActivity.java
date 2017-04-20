package sehgal.attendancemodule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();
    private static String url;
    String Sheetid;
    String classnameheading;
    String subject;
    String message;
    String mybuttonTAG;
    String buttonTAG = "SELECTED STUDENTS";

    private ProgressDialog pDialog;
    // URL to get contacts JSON
    private static String urltemp = "https://script.google.com/macros/s/AKfycbx_tCyq7Fr4-wWGe0mHjA7SBf1xocgzNNL4KfigFegMoDYWQTD-/exec?id=1m2Y66cfiFL_LurgsZBeFVxuFz8vR9HraEFUuO6r4yEE&sheet=";

    MyCustomAdapter dataAdapter = null;
    MyCustomAdapter dataAdapter1 = null;
    ArrayList<Student> studentsList = new ArrayList<Student>();
    ArrayList<Student> studentsListfinal = new ArrayList<Student>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = getIntent();
        String sheet = myIntent.getStringExtra("classSheet");
        url = urltemp + sheet;
        Sheetid = sheet;

        Intent intent = getIntent();
        final String subject1 = intent.getStringExtra("Subject");
        final String message1 = intent.getStringExtra("Message");
        String mybuttonTAG1 = intent.getStringExtra("buttonTAG");
        String sheet1 = intent.getStringExtra("classSheet");
        url = urltemp + sheet1;
        Sheetid = sheet1;

        subject = subject1;
        message = message1;
        mybuttonTAG = mybuttonTAG1;
        classnameheading=Sheetid;

        setContentView(R.layout.activity_main);

        new GetData().execute();

        checkButtonClick();


    }

    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("records");


                    // looping through All Contacts

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        // String id = c.getString("id");
                        String name = c.getString("Name");
                        String rollno = c.getString("Rollno");

                        if (!(name.equals("") && rollno.equals(""))) {
                            Student student = new Student(rollno, name, true);
                            studentsList.add(student);
                        }


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Error in data fetching: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Error in data fetching: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get data from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get data from server. Check Internet Connection!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /*
             * Updating parsed JSON data into ListView
             */
            for (int i = 1; i < studentsList.size(); i++) {
                studentsListfinal.add(studentsList.get(i));
            }

            dataAdapter = new MyCustomAdapter(getApplicationContext(), R.layout.student_info, studentsList);
            dataAdapter1 = new MyCustomAdapter(getApplicationContext(), R.layout.student_info, studentsListfinal);
            final ListView listView = (ListView) findViewById(R.id.listView1);
            // Assign adapter to ListView
            listView.setAdapter(dataAdapter1);

     /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text


               Student student = (Student) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Student: " + student.getName(),
                        Toast.LENGTH_SHORT).show();

                if (view != null) {
                    CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox1);
                    checkBox.setChecked(!checkBox.isChecked());
                }


            }
        }); */

        }

    }


    private class MyCustomAdapter extends ArrayAdapter<Student> {

        private ArrayList<Student> studentList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Student> studentList) {
            super(context, textViewResourceId, studentList);
            this.studentList = new ArrayList<Student>();
            this.studentList.addAll(studentList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
            ListView list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.student_info, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                    holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Student student = (Student) cb.getTag();


                       /* Toast.makeText(getApplicationContext(),"Student: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_SHORT).show();*/
                        student.setSelected(cb.isChecked());
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Student student = studentList.get(position);

            if (!(student.getName().equals("") && student.getCode().equals(""))) {
                holder.code.setText(" (" + student.getCode() + ")");
                holder.name.setText(student.getName());
                holder.name.setChecked(student.isSelected());
                holder.name.setTag(student);
            }

            return convertView;

        }

    }

    private void checkButtonClick() {

        TextView mainHeading=(TextView)findViewById(R.id.mainHeading);
        mainHeading.setText("STUDENT LIST OF "+classnameheading);

        Button selectedstudent = (Button) findViewById(R.id.findSelected);
        Button sendbutton = (Button) findViewById(R.id.nextpage);

        if (buttonTAG.equals(mybuttonTAG)) {
            selectedstudent.setText(mybuttonTAG);
        }



        selectedstudent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (buttonTAG.equals(mybuttonTAG)) {
                    StringBuffer responseText = new StringBuffer();
                    responseText.append("Selected Students of");

                    ArrayList<Student> countryList = dataAdapter.studentList;
                    for (int i = 0; i < countryList.size(); i++) {
                        Student student = countryList.get(i);
                        //To add Class Name
                        if(i==0){responseText.append(" "+ student.getName()); }

                           else if (student.isSelected()) {
                            responseText.append("\n" + student.getName());
                        }

                    }

                    Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();
                } else {
                    StringBuffer responseText = new StringBuffer();
                    responseText.append("Absent Students of");

                    ArrayList<Student> countryList = dataAdapter.studentList;
                    for (int i = 0; i < countryList.size(); i++) {
                        Student student = countryList.get(i);
                        //To Add Class Name
                        if(i==0){responseText.append(" "+ student.getName()); }

                        else if(student.isSelected() == false) {
                            responseText.append("\n" + student.getName());
                        }

                    }

                    Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();

                }

            }
        });



                 sendbutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (buttonTAG.equals(mybuttonTAG)) {

                    ArrayList<Student> studentList = dataAdapter.studentList;
                    ArrayList<String> stdname = new ArrayList<String>();
                    ArrayList<String> rollno = new ArrayList<String>();
                    String classname = Sheetid;


                    for (int i = 0; i < studentList.size(); i++) {
                        Student student = studentList.get(i);
                        if (student.isSelected()) {
                            stdname.add(student.getName());
                            rollno.add(student.getCode());
                        }

                    }
                    if (stdname.isEmpty()) { Toast.makeText(getApplicationContext(), "Data not Loaded Check Internet Connection!!", Toast.LENGTH_SHORT).show();
                         }

                     else {
                        Intent intent = new Intent(getApplicationContext(), SubmitDiary.class);

                        intent.putStringArrayListExtra("StudentName", stdname);
                        intent.putStringArrayListExtra("StudentRollno", rollno);
                        intent.putExtra("ClassName", classname);
                        intent.putExtra("Subject", subject);
                        intent.putExtra("Message", message);

                        startActivity(intent);
                    }
                }

                else {
                    ArrayList<Student> studentList = dataAdapter.studentList;
                    String[] stdname = new String[studentList.size()];
                    String[] stdstatus = new String[studentList.size()];
                    String[] rollno = new String[studentList.size()];

                    for (int i = 0; i < studentList.size(); i++) {
                        Student student = studentList.get(i);
                        if (student.isSelected()) {
                            stdname[i] = (student.getName());
                            stdstatus[i] = ("Present");
                            rollno[i] = (student.getCode());
                        } else {
                            stdname[i] = (student.getName());
                            stdstatus[i] = ("Absent");
                            rollno[i] = (student.getCode());

                        }
                    }
                    if (stdname.length <= 0) {
                        Toast.makeText(getApplicationContext(), "Data not Loaded Please Check Internet Connection!!", Toast.LENGTH_SHORT).show();
                    } else {

                        Intent intent = new Intent(getApplicationContext(), SubmitAttendance.class);

                        intent.putExtra("StudentName", stdname);
                        intent.putExtra("StudentStatus", stdstatus);
                        intent.putExtra("StudentRollno", rollno);
                        startActivity(intent);

                    }
                }

            }
        });
    }
}



