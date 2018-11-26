package com.example.quynguyen.capstone_vinmartsystem;

import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.HashMap;
import java.util.Map;

public class DetailProductActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView txtProductName,txtProductDesc;
    ImageView imgProduct;
    Button btnBack,btnSearchSubNav,btnAddCart;
    EditText edtSearchSubNav;
    ElegantNumberButton btnElegent;
    Fragment fragment_cart = new Fragment_Cart();
    Product objProduct;
    String urlData  = new Connect().urlData + "/insertcart.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        txtProductName = (TextView) findViewById(R.id.txtProductName);
        txtProductDesc = (TextView) findViewById(R.id.txtProductDesc);
        imgProduct = (ImageView) findViewById(R.id.imgProduct);

        btnBack = (Button) findViewById(R.id.btnBackMain);
        btnSearchSubNav = (Button) findViewById(R.id.btnSearchSubNav);
        edtSearchSubNav = (EditText) findViewById(R.id.edtSearchSubNav);
        btnAddCart = (Button) findViewById(R.id.btnAddCart);
        btnElegent = (ElegantNumberButton) findViewById(R.id.btnElegent);

        Intent intent = getIntent();
        objProduct = intent.getParcelableExtra("ARRPRODUCT");
        txtProductDesc.setText(objProduct.getProductDescription());
        imgProduct.setImageResource(objProduct.getProductImg());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProductActivity.this,DrinkProductActivity.class);
                intent.putExtra("catID",objProduct.getProductCatID());
                startActivity(intent);
            }
        });


        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                if(sharedPreferences.getInt("cus_id",0) == 0){
                    sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putInt("loginForCart",1);
                    editor.commit();
                    Intent intent = new Intent(DetailProductActivity.this,MainActivity.class);
                    startActivity(intent);
                }else if(btnElegent.getNumber().equals("0")){
                    Toast.makeText(DetailProductActivity.this, "Hãy chọn số lượng", Toast.LENGTH_SHORT).show();
                }else{
                    addCart(urlData);
                }
            }
        });

    }

    private void addCart(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("complete")){
                            Toast.makeText(DetailProductActivity.this, "Thêm vào giỏ thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(DetailProductActivity.this, "Chưa thêm thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailProductActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                params.put("product_name",objProduct.getProductName());
                params.put("product_img",String.valueOf(objProduct.getProductImg()));
                params.put("product_id",String.valueOf(objProduct.getProductID()));
                params.put("quantity",btnElegent.getNumber());
                params.put("price",String.valueOf(objProduct.getProductPrice() * Integer.parseInt(btnElegent.getNumber())));
                params.put("cus_id",String.valueOf(sharedPreferences.getInt("cus_id",0)));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
