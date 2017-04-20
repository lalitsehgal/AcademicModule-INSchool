package sehgal.attendancemodule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class SubmitAttendance extends AppCompatActivity {
    private ProgressDialog progress;
    ArrayList<String>stdName=new ArrayList<>();
    ArrayList<String>stdStatus=new ArrayList<>();
    ArrayList<String>stdrollno=new ArrayList<>();
    ArrayList<String>time=new ArrayList<>();
    String Sheet;
    int i=0;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_attendance);
        Date date = new Date();
        String currentDateTime = DateFormat.getDateTimeInstance().format(date);

       // stdName.add("  ");
        //stdStatus.add("  ");
        //stdrollno.add("  ");




        stdName.add("NAME");
        stdStatus.add("STATUS");
        stdrollno.add("ROLL_NUMBER");
        time.add("TIME");




       button=(Button)findViewById(R.id.button);
        button.setText("Save Attendance");
        Intent intent = getIntent();
        String [] stdname = intent.getStringArrayExtra("StudentName");
        String [] stdstatus = intent.getStringArrayExtra("StudentStatus");
        String [] rollno = intent.getStringArrayExtra("StudentRollno");
       // String Sheet1 = intent.getStringExtra("Sheetid");
        //Sheet=Sheet1;

        for(int j=1;j<stdname.length;j++) {
            stdName.add(stdname[j]);
            stdStatus.add(stdstatus[j]);
            stdrollno.add(rollno[j]);
            time.add(currentDateTime);
                    }
                    int val=stdname.length;

        stdName.add(val,"------");
        stdStatus.add(val,"------");
        stdrollno.add(val,"------");
        time.add(val,"------");

        stdName.add(val+1," ");
        stdStatus.add(val+1," ");
        stdrollno.add(val+1," ");
        time.add(val+1," ");

        Sheet=stdname[0];

        //tvName=(EditText)findViewById(R.id.input_name);
        //tvCountry=(EditText)findViewById(R.id.input_country);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Toast.makeText(getApplicationContext(), "Attendance Marked", Toast.LENGTH_LONG).show();


                new SendRequest().execute();
            }

        }   );

    }







    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {







            try {
                //Change your web app deployed URL or u can use this
                URL url = new URL("https://script.google.com/macros/s/AKfycbwdalPIkwLDsSVTKAzizcv8LrTjsC3DlQ4jvg-enlzZLU5B2jUj/exec");

                JSONObject postDataParams = new JSONObject();
               // String sh="CLASS12";

               String id = "1MCJ6ygS_iGWkKf97uRUw1NhzPAb8FMNcyNm61H2_XME";
               // String sheetName="CLASS3";

                postDataParams.put("time",time.get(i));
                postDataParams.put("rollno", stdrollno.get(i));
                postDataParams.put("name", stdName.get(i));
                postDataParams.put("status",stdStatus.get(i));
              //  postDataParams.put("section",stdName.get(i));
                i++;

               //To enter Blank Entries
                if(i==stdName.size())
                    { postDataParams.put("time"," ");
                    postDataParams.put("rollno"," ");
                    postDataParams.put("name", " ");
                    postDataParams.put("status"," ");}



                postDataParams.put("id", id);
                postDataParams.put("sheet",Sheet);






                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {

             // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {
        for(int j=0;j<=0;j++){
            new SendRequest().execute();
        }

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }

        return result.toString();



    }

}

