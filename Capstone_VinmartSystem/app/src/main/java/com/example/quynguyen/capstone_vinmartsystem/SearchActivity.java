package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
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

public class SearchActivity extends AppCompatActivity {
    GridView gvProduct;
    ArrayList<Product> arrProduct;
    ProductAdapter productAdapter;
    String productName ="";
    Button btnBack;
    String url = new Connect().urlData + "/searchproduct.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AnhXa();
        arrProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(this,R.layout.product_line,arrProduct);
        gvProduct.setAdapter(productAdapter);
        //Nhận biến Name để tìm kết quả
        Intent intent = getIntent();
        productName   = intent.getStringExtra("PRODUCTNAME");
        ReadJSON(url);

        //Event click vào sản phẩm
        gvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this,DetailProductActivity.class);
                intent.putExtra("ARRPRODUCT",arrProduct.get(position));
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
    public void AnhXa(){
        gvProduct =  findViewById(R.id.gridviewDrink);
        btnBack = findViewById(R.id.btnBackMain);
    }

    //Lấy biến Name và đổ dữ liệu nếu có
    private void ReadJSON(String urlData) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        urlData = urlData + "?product_name=" + productName;
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlData, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() == 0){
                    Toast.makeText(SearchActivity.this, "Không tìm thấy sản phẩm nào", Toast.LENGTH_LONG).show();
                }
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String []img = jsonObject.getString("Image").split("\\.");
                        int image = getResources().getIdentifier(img[0], "drawable", getPackageName());
                        arrProduct.add(new Product(
                                jsonObject.getInt("Pro_ID"),
                                image,
                                jsonObject.getString("Pro_Name"),
                                jsonObject.getString("Desc"),
                                jsonObject.getInt("Price"),
                                jsonObject.getInt("Cat_ID"),
                                jsonObject.getString("Pro_EXP")
                        ));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                productAdapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
