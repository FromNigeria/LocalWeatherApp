package org.softalliance.localweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

   // Declaing my variables fields from xml layout

    TextView t1_temp, tv_city, tv_description, tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // casting my variables
        t1_temp = findViewById(R.id.temp);
        tv_city = findViewById(R.id.tempcity);
        tv_description = findViewById(R.id.tempdesc);
        tv_date = findViewById(R.id.tempdate);

        //defining my weather function or whatever method here
        findWeather();
    }

    public void findWeather(){


        String url = "http://api.openweathermap.org/data/2.5/weather?q=Ibadan,%20NG&appid=7d9552622c653b09e2b3670c01a1f5f1&units=imperial";
        //Using JSon object Request
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray myarray = response.getJSONArray("weather");
                    JSONObject object = myarray.getJSONObject(0);//starting from 0 index
                    String temp = String.valueOf(main_object.getDouble("temp")); //converting whateve tem to string
                    String description = object.getString("description");
                    String city = response.getString("name");

                    //passing in my strings
                    //t1_temp.setText(temp);
                    tv_city.setText(city);
                    tv_description.setText(description);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE-MM-DD");
                    //formating the date if i am can still recall
                    String formatted_date = sdf.format(calendar.getTime());

                    //now set tv_Date to string
                    tv_date.setText(formatted_date);

                     //so from here, i needed to convert the temp and change it to Faherenheit

                    double temp_int = Double.parseDouble(temp);
                    double centigrade = (temp_int - 32) /1.8000;
                    centigrade = Math.round(centigrade);
                    int i = (int)centigrade;
                    t1_temp.setText(String.valueOf(i));


                } catch (JSONException  hehe){
                    hehe.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        //Calling the usual requestQueue method
        RequestQueue rq = Volley.newRequestQueue(this); //passing the context whch is MainActivity
        rq.add(jor);
    }
}
