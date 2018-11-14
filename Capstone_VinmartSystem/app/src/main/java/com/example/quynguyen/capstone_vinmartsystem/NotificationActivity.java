package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    NotificationAdapter adapter;
    ArrayList<NewsPromotion> arrnewsPromotions;
    Button btnBackMain;
    String urlGetNews = new Connect().urlData +"/getnews.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNoti);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        btnBackMain = (Button) findViewById(R.id.btnBackMain);
        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        arrnewsPromotions = new ArrayList<>();
        //getData
        ReadJSON(urlGetNews);
        adapter =  new NotificationAdapter(arrnewsPromotions,this);
        recyclerView.setAdapter(adapter);

    }
    private void ReadJSON(final String urlData){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlData, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0 ; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int image = getResources().getIdentifier(jsonObject.getString("Image"),"drawable",getPackageName());
                        arrnewsPromotions.add(new NewsPromotion(
                                jsonObject.getInt("ID"),
                                jsonObject.getString("Name"),
                                image,
                                jsonObject.getString("Description"),
                                jsonObject.getString("Detail"),
                                jsonObject.getString("NewsDate")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NotificationActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
