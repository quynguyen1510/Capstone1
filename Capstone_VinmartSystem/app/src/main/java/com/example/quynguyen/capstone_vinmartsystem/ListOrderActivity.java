package com.example.quynguyen.capstone_vinmartsystem;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class ListOrderActivity extends AppCompatActivity {
    DeliveryAdapter deliveryAdapter;
    ArrayList<Delivery> arrayList;
    SharedPreferences sharedPreferences;
    String urlGetInvoice = new Connect().urlData + "/getinvoiceforshipper.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewShipper);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
//        arrayList.add(new Delivery(1,95,"20-11-2018","254 Nguyễn Văn Linh","076012013"));
        deliveryAdapter =  new DeliveryAdapter(arrayList,this);
        recyclerView.setAdapter(deliveryAdapter);
        getInvoice(urlGetInvoice);
    }

    //Lấy hóa đơn
    private void getInvoice(String urlData){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        urlData = urlData+"?delivery_by="+sharedPreferences.getInt("cus_id",0);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlData, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0 ; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        arrayList.add(new Delivery(
                                jsonObject.getInt("ID"),
                                jsonObject.getInt("Invoice_ID"),
                                jsonObject.getString("Delivery_Date"),
                                jsonObject.getString("Address"),
                                jsonObject.getString("Phone")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(arrayList.size() < 0){
                    Toast.makeText(ListOrderActivity.this, "Không có đơn hàng nào", Toast.LENGTH_SHORT).show();
                }
                deliveryAdapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListOrderActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
