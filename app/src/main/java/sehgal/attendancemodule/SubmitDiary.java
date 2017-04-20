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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class SubmitDiary extends AppCompatActivity {


    private ProgressDialog progress;
        ArrayList<String> stdNamelist=new ArrayList<>();
        ArrayList<String>stdrollnolist=new ArrayList<>();
        ArrayList<String>timelist=new ArrayList<>();
        ArrayList<String>messagelist=new ArrayList<>();
        ArrayList<String>subjectlist=new ArrayList<>();
        ArrayList<String>classlist=new ArrayList<>();

    String Sheet;
        int i=0;
        Button button;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_submit_diary);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = df.getDateTimeInstance().format(new Date());

            // stdName.add("  ");
            //stdStatus.add("  ");
            //stdrollno.add("  ");




            stdNamelist.add("NAME");
            messagelist.add("MESSAGE");
            stdrollnolist.add("ROLL_NUMBER");
            timelist.add("TIME");
            subjectlist.add("SUBJECT");
            classlist.add("CLASS");




            button=(Button)findViewById(R.id.submitDiary);
            button.setText("SEND MESSAGE");
            Intent intent = getIntent();
            ArrayList<String> stdname=intent.getStringArrayListExtra("StudentName");
            ArrayList<String> stdrollno=intent.getStringArrayListExtra("StudentRollno");
            String classname=intent.getStringExtra("ClassName");
            String subject=intent.getStringExtra("Subject");
            String message=intent.getStringExtra("Message");


            // String Sheet1 = intent.getStringExtra("Sheetid");
            //Sheet=Sheet1;

            for(int j=1;j<stdname.size();j++) {
                stdNamelist.add(stdname.get(j));
                messagelist.add(message);
                subjectlist.add(subject);
                stdrollnolist.add(stdrollno.get(j));
                timelist.add(currentDateTime);
                classlist.add(classname);
            }
            int val=stdname.size();

            stdNamelist.add(val,"------");
            messagelist.add(val,"------");
            stdrollnolist.add(val,"------");
            classlist.add(val,"------");
            subjectlist.add(val,"------");
            timelist.add(val,"------");

            stdNamelist.add(val+1," ");
            messagelist.add(val+1," ");
            classlist.add(val+1," ");
            stdrollnolist.add(val+1," ");
            timelist.add(val+1," ");
            subjectlist.add(val+1," ");

            Sheet=stdname.get(0);

            //tvName=(EditText)findViewById(R.id.input_name);
            //tvCountry=(EditText)findViewById(R.id.input_country);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(getApplicationContext(), "MESSAGE SENT", Toast.LENGTH_LONG).show();


                    new SendRequest().execute();
                }

            }   );

        }







        public class SendRequest extends AsyncTask<String, Void, String> {


            protected void onPreExecute(){}

            protected String doInBackground(String... arg0) {







                try {
                    //Change your web app deployed URL or u can use this
                    URL url = new URL("https://script.google.com/macros/s/AKfycbxn6cluWtzzThvfM7oLMCpYb0NTaAaPdVguUOipC1VhZxiauDE/exec");

                    JSONObject postDataParams = new JSONObject();
                    // String sh="CLASS12";

                    String id = "1t_5sEXmg6NNC1Ug6ClWLTQjA2q1EdXDDVdm7WL7YpWg";
                    // String sheetName="CLASS3";


                    postDataParams.put("time",timelist.get(i));
                    postDataParams.put("class",classlist.get(i));
                    postDataParams.put("rollno", stdrollnolist.get(i));
                    postDataParams.put("name", stdNamelist.get(i));
                    postDataParams.put("subject",subjectlist.get(i));
                    postDataParams.put("message",messagelist.get(i));
                    //  postDataParams.put("section",stdName.get(i));
                    i++;

                    if(i==stdNamelist.size())
                    { postDataParams.put("time"," ");
                        postDataParams.put("class"," ");
                        postDataParams.put("rollno", " ");
                        postDataParams.put("name", " ");
                        postDataParams.put("subject"," ");
                        postDataParams.put("message"," ");}



                    postDataParams.put("id", id);
                    postDataParams.put("sheet",Sheet);


                    // postDataParams.put("sheetname",sheetName);



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

