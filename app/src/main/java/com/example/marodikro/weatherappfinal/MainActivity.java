package com.example.marodikro.weatherappfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result;
  //  String baseUrl="https://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=d77c922ad07c23b6093a5dbf68fc4b46";
    String baseUrl="https://api.openweathermap.org/data/2.5/weather?q=";
    String API="&appid=d77c922ad07c23b6093a5dbf68fc4b46";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button)findViewById(R.id.button);
        city=(EditText)findViewById(R.id.city);
        result=(TextView)findViewById(R.id.result);

           button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (city.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please Enter City Name", Toast.LENGTH_SHORT).show();
                    } else {
                        String myUrl = baseUrl + city.getText().toString() + API;


                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.i("json", "json" + response);

                                        try {
                                            String info = response.getString("weather");
                                            JSONArray array = new JSONArray(info);
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject parObj = array.getJSONObject(i);

                                                String myWeather = parObj.getString("main");
                                                result.setText(myWeather);
                                                Log.i("ID", "ID" + parObj.getString("id"));
                                                Log.i("MAIN", "ID" + parObj.getString("main"));
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

//
//                                try {
//                                    String coor=response.getString("coord");
//                                    Log.i("Coor","Coor" +coor);
//                                    JSONObject co=new JSONObject(coor);
//
//                                    String lon=co.getString("lon");
//                                    String lat=co.getString("lat");
//
//                                    Log.i("Lon","Lon"+lon);
//                                    Log.i("Lat","Lat"+lat);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("Error", "Something Went wrong" + error);
                                    }
                                }
                        );
                        MySingleton.getmInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

                    }
                    }


            });
        }
    }

