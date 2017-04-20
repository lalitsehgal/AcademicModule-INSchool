package sehgal.attendancemodule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Result extends AppCompatActivity {

    private String TAG = Result.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    String url;
    String url1;
    String url2;
    String Name;
    String Rollno;
    String check;
    String checkMain="ALL";

    // URL to get contacts JSON
    private static String urltemp = "https://script.google.com/macros/s/AKfycbx_tCyq7Fr4-wWGe0mHjA7SBf1xocgzNNL4KfigFegMoDYWQTD-/exec?id=1v-0jDCkFDVdkEpoJBLx1EbclfiyOz0_rjKk5z9YGbHs&sheet=";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        contactList = new ArrayList<>();

        //From First Page
        Intent intent = getIntent();
        String sheet1 = intent.getStringExtra("classSheet");
        check = intent.getStringExtra("Check");
        url1=urltemp + sheet1;

        //From Event Decide
        Intent myIntent = getIntent();
        String stdname = myIntent.getStringExtra("stdName");
        String stdrollno = myIntent.getStringExtra("stdRollno");
        String sheet = myIntent.getStringExtra("stdClass");

        url2=urltemp + sheet;


        Name=stdname;
        Rollno=stdrollno;


        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Result.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr;
            if((checkMain.equals(check))){
                jsonStr = sh.makeServiceCall(url1);
            }
            else{ jsonStr = sh.makeServiceCall(url2);
            }


            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("records");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String rollno = c.getString("ROLL_NUMBER");
                        String name = c.getString("NAME");
                        String subject = c.getString("SUBJECT");
                        String marks  = c.getString("MARKS");
                        String examtype  = c.getString("EXAM");

                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value

                        if(checkMain.equals(check)) {

                            contact.put("rollno", rollno);
                            contact.put("name", name);
                            contact.put("subject", subject);
                            contact.put("marks", marks);
                            contact.put("exam", examtype);

                            // adding contact to contact list
                            contactList.add(contact);
                        }
                        if(!(checkMain.equals(check))){
                            if(Name.equals(name)&& Rollno.equals(rollno)){
                                contact.put("rollno",rollno);
                                contact.put("name", name);
                                contact.put("subject",subject);
                                contact.put("marks", marks);
                                contact.put("exam", examtype);

                                // adding contact to contact list
                                contactList.add(contact);
                            }
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
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    Result.this, contactList,R.layout.item_list_result, new String[]{"rollno","name", "subject",
                    "marks","exam"}, new int[]{R.id.rollno,R.id.name,
                    R.id.subject, R.id.marks,R.id.exam});

            lv.setAdapter(adapter);
        }

    }
}

