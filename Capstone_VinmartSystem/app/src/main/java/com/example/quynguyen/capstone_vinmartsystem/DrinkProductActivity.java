package com.example.quynguyen.capstone_vinmartsystem;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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

public class DrinkProductActivity extends AppCompatActivity {
    GridView gvProduct;
    ArrayList<Product> arrProduct;
    ProductAdapter productAdapter;
    Button btnBack,btnSearchSubNav;
    EditText edtSearchSubNav;
    Category objCat;
    Connect connect = new Connect();
    int catID;
    String url = connect.urlData + "/getproductbyid.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_product);

        btnBack = (Button) findViewById(R.id.btnBackMain);
        btnSearchSubNav = (Button) findViewById(R.id.btnSearchSubNav);
        edtSearchSubNav = (EditText) findViewById(R.id.edtSearchSubNav);
        Intent intent = getIntent();
        objCat = intent.getParcelableExtra("Cat");
        catID = intent.getIntExtra("catID",0);
        if(catID == 0 ) {
            catID = objCat.getCatID();
        }
        gvProduct = (GridView) findViewById(R.id.gridviewDrink);
        arrProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(this,R.layout.product_line,arrProduct);
        gvProduct.setAdapter(productAdapter);

        //Read Json từ Server
        ReadJSON(url);

        gvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(DrinkProductActivity.this,DetailProductActivity.class);
                    intent.putExtra("ARRPRODUCT",arrProduct.get(position));
                    startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinkProductActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void ReadJSON(String urlData) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        urlData = urlData + "?cat_id=" + catID;
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlData, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
                        Toast.makeText(DrinkProductActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
