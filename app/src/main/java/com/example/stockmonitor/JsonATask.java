package com.example.stockmonitor;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JsonATask extends AsyncTask<String, Void, String> {

    protected MainActivity mainActivity;
    ArrayList<Stock> stocks = new ArrayList<Stock>();

    public JsonATask(MainActivity mainActivityReference)
    {
        this.mainActivity = mainActivityReference;
    }

    @Override
    protected String doInBackground(String... url_address) {
        try
        {
            URL url = new URL(url_address[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            return convertStreamToString(in);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String webContent) {
        super.onPostExecute(webContent);
        mainActivity.printHtml(webContent);
    }


    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
