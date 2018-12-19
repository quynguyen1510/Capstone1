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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    //Thư viện firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("vinmart-delivery-224708");


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

                    Cart objCart = new Cart(0,objProduct.getProductName(),objProduct.getProductImg(),objProduct.getProductID(),Integer.parseInt(btnElegent.getNumber()),objProduct.getProductPrice(),sharedPreferences.getInt("cus_id",0));
                    // trỏ dến thư mục Cart+ cusid trên firebase sau đó add sản phẩm vào
                    reference.child("Cart"+sharedPreferences.getInt("cus_id",0)).child(objCart.getProductName()).setValue(objCart, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError == null){
                                Toast.makeText(DetailProductActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(DetailProductActivity.this, "Thêm vào giỏ hàng không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }


}
