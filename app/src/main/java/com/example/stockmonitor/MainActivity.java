package com.example.stockmonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listViewStocks = null;
    private RequestQueue mQueue;
    ArrayList<Stock> stockList = new ArrayList<Stock>();
    StockListAdapter stockListAdapter;
    Button button = null;
    EditText editTextname = null;
    EditText editTextId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.buttonAdd);
        button.setOnClickListener(this);

        editTextname = (EditText) findViewById(R.id.editTextName);
        editTextId = (EditText) findViewById(R.id.editTextId);
        JsonATask task = new JsonATask(MainActivity.this);
        task.execute("https://financialmodelingprep.com/api/company/price/FB,NOK,AAPL,GOOGL");

        mQueue = Volley.newRequestQueue(this);
        //jsonParse();
    }


    public void printHtml(String content)
    {
        String JSONstr = content.replaceAll("<pre>", "");
        Log.d("LOL", JSONstr);

        try
        {
            JSONObject object = new JSONObject(JSONstr);
            Log.d("LOL", object.toString());

            //FACEBOOK
            JSONObject fbObject = (JSONObject) object.get("FB");
            Log.d("LOL", fbObject.toString());
            double fbPrice = fbObject.getDouble("price");
            Log.d("LOL", " " + fbPrice);
            Stock fb = new Stock("FACEBOOK", fbPrice);

            //NOKIA
            JSONObject nokObject = (JSONObject) object.get("NOK");
            double nokPrice = nokObject.getDouble("price");
            Stock nok = new Stock("NOKIA", nokPrice);

            //APPLE
            JSONObject aaplObject = (JSONObject) object.get("AAPL");
            double aaplPrice = aaplObject.getDouble("price");
            Stock aapl = new Stock("APPLE", aaplPrice);

            //GOOGLE
            JSONObject googlObject = (JSONObject) object.get("GOOGL");
            double googlPrice = googlObject.getDouble("price");
            Stock googl = new Stock("GOOGLE", googlPrice);


            ListView listViewStocks = (ListView) findViewById(R.id.listViewStock);

            stockList.add(fb);
            stockList.add(nok);
            stockList.add(aapl);
            stockList.add(googl);
            listViewStocks.setAdapter(new StockListAdapter(this, stockList));
        }
        catch (JSONException e) {
            Log.d("LOL", e.toString());
        }
    }

    public void jsonParse(String name, String id) {
        String url = "https://financialmodelingprep.com/api/company/price/" + id;
        RequestQueue queue = Volley.newRequestQueue(this);
        final String stockName = name;
        final String stockId = id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String JSONstr = response.replaceAll("<pre>", "");
                        Log.d("LOL", JSONstr);
                        JSONObject object = null;

                        try
                        {
                            object = new JSONObject(JSONstr);
                            Log.d("LOL", object.toString() + "LOLOLOL");

                            JSONObject stockObject = (JSONObject) object.get(stockId);
                            double stockPrice = stockObject.getDouble("price");
                            Stock stock = new Stock(stockName, stockPrice);
                            Log.d("LOL", stock.getCompanyName() + stock.getPrice());

                            ListView listViewStocks = (ListView) findViewById(R.id.listViewStock);

                            stockList.add(stock);
                            listViewStocks.setAdapter(new StockListAdapter(MainActivity.this, stockList));
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Log.d("LOL", e.toString());
                        }
                        Log.d("LOL", object.toString());
                    }
                },

                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOL", error.toString());
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.buttonAdd))
        {
            String name = editTextname.getText().toString();
            String id = editTextId.getText().toString();
            jsonParse(name, id);
        }
    }
}
