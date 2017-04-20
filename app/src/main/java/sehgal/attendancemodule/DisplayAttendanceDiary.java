package sehgal.attendancemodule;

        import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayAttendanceDiary extends AppCompatActivity  {
    private String TAG = DisplayAttendanceDiary.class.getSimpleName();
    int attended=0;
    int total=0;
    String url;
    String Name;
    String Rollno;
    String diarymain="DIARY";
    String diary;

    private ProgressDialog pDialog;
    private ListView lv;

    ArrayList<HashMap<String, String>> contactList;
    ArrayList<HashMap<String, String>> newcontactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_attendance_diary);
        Intent myIntent = getIntent();
        String stdname = myIntent.getStringExtra("stdName");
        String stdrollno = myIntent.getStringExtra("stdRollno");
        String sheet = myIntent.getStringExtra("stdClass");
        String sheetid = myIntent.getStringExtra("sheetId");
        String decide=myIntent.getStringExtra("check");
        diary=decide;

        url="https://script.google.com/macros/s/AKfycbx_tCyq7Fr4-wWGe0mHjA7SBf1xocgzNNL4KfigFegMoDYWQTD-/exec?id="+sheetid+"&sheet="+sheet;
        Name=stdname;
        Rollno=stdrollno;

        contactList = new ArrayList<>();
        newcontactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetData().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(DisplayAttendanceDiary.this);
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

                        String name;
                        String time;
                        String rollno;
                        String message = null;
                        String subject =null;
                        String status = null;
                        String timenew=null;

                        // String id = c.getString("id");
                        name = c.getString("NAME");
                        time = c.getString("TIME");
                        rollno = c.getString("ROLL_NUMBER");

                        //to trim time fetched
                        timenew=time.substring(0,Math.min(time.length(),10));


                        if(diary.equals(diarymain)){
                            message = c.getString("MESSAGE");
                            subject = c.getString("SUBJECT");
                        }
                        else{
                            status = c.getString("STATUS"); }

                        // String name = c.getString("name");


                        if(name.equals(Name)&&rollno.equals(Rollno)) {
                            HashMap<String, String> contact = new HashMap<>();

                            // adding each child node to HashMap key => value
                            //contact.put("id", id);
                            contact.put("name", name);
                            contact.put("time", timenew);
                            contact.put("rollno", rollno);
                            if(diary.equals(diarymain)){
                                contact.put("message",message);
                                contact.put("subject", subject);

                            }
                            else{
                                contact.put("status", status);}

                            if(diary.equals(diarymain)){}


                            else if (status.equals("Present")) {
                                attended++;
                            }
                            // contact.put("name", name);
                            //contact.put("email", email);
                            //contact.put("mobile", mobile);

                            // adding contact to contact list
                            total++;
                            contactList.add(contact);
                        }


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
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


            TextView tvAttended = (TextView)findViewById(R.id.tvfirst);

            if(diary.equals(diarymain)){
                tvAttended.setText("YOU HAVE "+total+" NEW MESSAGE");

            }
            else{tvAttended.setText("YOU HAVE ATTENDED "+attended+" CLASSES OUT OF "+total+" CLASSES");}



            /**
             * Updating parsed JSON data into ListView
             * */


            for(int val=contactList.size()-1;val>=0;val--)
            {
                newcontactList.add(contactList.get(val));
            }

            Button button=(Button)findViewById(R.id.viewdetails);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListAdapter adapter;

                    if(diary.equals(diarymain)){
                        adapter = new SimpleAdapter(
                                DisplayAttendanceDiary.this, newcontactList,R.layout.list_item_attendance_diary, new String[]{"time","rollno","subject","message"}, new int[]{R.id.nameTime,R.id.rollTime,R.id.subStatus,R.id.msgRoll});
                    }

                    else {
                        adapter = new SimpleAdapter(
                                DisplayAttendanceDiary.this, newcontactList, R.layout.list_item_attendance_diary, new String[]{"name", "time", "status", "rollno"}, new int[]{R.id.nameTime,R.id.rollTime,R.id.subStatus,R.id.msgRoll});
                    }
                    lv.setAdapter(adapter);

                }

            });


        }
    }}



