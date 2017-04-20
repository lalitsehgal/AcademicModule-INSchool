package sehgal.attendancemodule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Timetable extends AppCompatActivity {
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        WebView webview=(WebView)findViewById(R.id.webview);

        webview.setWebViewClient(new WebViewClient());

        webview.getSettings().setJavaScriptEnabled(true);

        pd=new ProgressDialog(this);
        pd.setTitle("Please Wait ...");
        pd.setMessage("Loading Time Tables ");
        pd.show();

        webview.loadUrl("https://goo.gl/photos/q6rF77MvFKJj6hrQ8");


    }

    }
