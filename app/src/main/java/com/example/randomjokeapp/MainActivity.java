package com.example.randomjokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button next,finish;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.joke);
        next=findViewById(R.id.next);
        finish=findViewById(R.id.finish);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://v2.jokeapi.dev/joke/Any"; //URL for random jokes
                                                        //it gives us a JsonObject which contains joke every single time


       next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // Request a JsonObject response from the provided URL.
               JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                       new Response.Listener<JSONObject>() {

                           @Override
                           public void onResponse(JSONObject response) {
                                //we may have 2 kinds of json
                               // first type contains joke
                               //second type contains setup and delivery
                               //so we check our response set to textView
                               if(response.has("joke")){
                                   try {
                                       String joke = response.getString("joke");
                                       tv.setText(joke);

                                   }catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                               }else if(response.has("setup") && response.has("delivery")){
                                   try {
                                       String joke=response.getString("setup")+"\n \n - "+response.getString("delivery");
                                       tv.setText(joke);
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                       Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                   }

                               }

                           }
                       }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) { //when we could't get response this code will handle errors
                       tv.setText("That didn't work!");
                       Toast.makeText(MainActivity.this, "didn't work", Toast.LENGTH_SHORT).show();
                   }
               });
                //since we may have multiple response we keep them in queue
                // Add the request to the RequestQueue.
               queue.add(request);

           }
       });
       //we set exiting when user clicked enough button
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "exiting...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}