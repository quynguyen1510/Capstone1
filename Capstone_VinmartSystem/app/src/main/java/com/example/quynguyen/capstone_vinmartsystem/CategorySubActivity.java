package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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


public class CategorySubActivity extends AppCompatActivity {

    Connect connect = new Connect();
    CategorySubAdapter adapter;
    ArrayList<Category> arrSubCat;
    String urlGetSubCat = connect.urlData + "/getsubcategory.php";
    Category objCat;
    Button btnBackMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_sub);
        int spanCount = 2;//Số cột nếu thiết lập lưới đứng, số dòng nếu lưới ngang
        int orientation = GridLayoutManager.VERTICAL;//Lưới đứng
        Intent intent = getIntent();

        //Lấy object từ Cat của Fragment_Home
        objCat = intent.getParcelableExtra("Cat");

        btnBackMain = (Button) findViewById(R.id.btnBackMain);
        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backMain = new Intent(CategorySubActivity.this,MainActivity.class);
                startActivity(backMain);
            }
        });

        //arrSubCategory
        arrSubCat = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSubCat);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        gridLayoutManager.setOrientation(orientation);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new CategorySubAdapter(arrSubCat,this);
        recyclerView.setAdapter(adapter);
        //Read Json and add to arrSubCat
        ReadJSON(urlGetSubCat);
    }

    private void ReadJSON(String urlData) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        urlData = urlData + "?parentid=" + objCat.getCatID();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlData, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String []img = jsonObject.getString("Image").split("\\.");
                        int image = getResources().getIdentifier(img[0], "drawable", getPackageName());
                        arrSubCat.add(new Category(
                                jsonObject.getInt("ID"),
                                image,
                                jsonObject.getString("Name"),
                                jsonObject.getInt("IDParent")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(arrSubCat.size() == 0){
                    Intent intent = new Intent(CategorySubActivity.this,DrinkProductActivity.class);
                    intent.putExtra("catID",objCat.getCatID());
                    startActivity(intent);
                }
                adapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CategorySubActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
