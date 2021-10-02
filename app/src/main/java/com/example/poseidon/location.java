package com.example.poseidon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class location extends AppCompatActivity {
    EditText lat;
    EditText lon;
    TextView text;
    Double latf;
    Double lonf;
    Double msssf;
    Double mssf;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        lat = (EditText) findViewById(R.id.lat);
        lon = (EditText) findViewById(R.id.lon);
        text = (TextView) findViewById(R.id.textView3);
    }

    public void goToNext (View view){
        String url = "https://nasa-debris-tracker.herokuapp.com/distance?lat="+Double.parseDouble(String.valueOf(lat.getText()))+"&long="+Double.parseDouble(String.valueOf(lon.getText()));
        RequestQueue queue = Volley.newRequestQueue(location.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject obj = response.getJSONObject("data");


                                latf = obj.getDouble("latitude");

                                lonf = obj.getDouble("longitude");

                                mssf= obj.getDouble("mss_anom");

                                msssf= obj.getDouble("mss_anom_scaled");

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Tag", "response is error"+error);
                        Toast.makeText(location.this, "Fail to get response", Toast.LENGTH_SHORT).show();

                    }

                });

        queue.add(jsonObjectRequest);

        text.setText("Please wait while we search for debris near your location");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), DebrisMap.class);
                intent.putExtra("lat", latf);
                intent.putExtra("lon", lonf);
                intent.putExtra("lat0", Double.parseDouble(String.valueOf(lat.getText())));
                intent.putExtra("lon0", Double.parseDouble(String.valueOf(lon.getText())));
                intent.putExtra("mssf", mssf);
                intent.putExtra("msssf", msssf);

                startActivity(intent);
            }
        }, 10000);
        //text.setText(latf.toString()+" "+lonf.toString());




    }
}
