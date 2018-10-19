package com.example.quynguyen.capstone_vinmartsystem;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

public class DrinkProductActivity extends AppCompatActivity {
    public static final String NAME = "title";
    public static final String DESCRIPTION = "description";
    public static final int IMAGE = 1;
    public static final int PRICE = 1;
    public final String ARR = "arr";
    GridView gvProduct;
    ArrayList<Product> arrProduct;
    ProductAdapter productAdapter;
    Button btnBack,btnSearchSubNav;
    EditText edtSearchSubNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_product);

        btnBack = (Button) findViewById(R.id.btnBackMain);
        btnSearchSubNav = (Button) findViewById(R.id.btnSearchSubNav);
        edtSearchSubNav = (EditText) findViewById(R.id.edtSearchSubNav);

        gvProduct = (GridView) findViewById(R.id.gridviewDrink);
        arrProduct = new ArrayList<>();
        arrProduct.add(new Product(R.drawable.slurpee,"Slurpee","Thức uống có ga",20000,"15/10/2018"));
        arrProduct.add(new Product(R.drawable.cafe,"Cà phê","Cà phê Việt Nam truyền thống",15000,"15/10/2018"));
        arrProduct.add(new Product(R.drawable.tea," Trà","Trà xanh matcha",20000,"15/10/2018"));
        productAdapter = new ProductAdapter(this,R.layout.product_line,arrProduct);
        gvProduct.setAdapter(productAdapter);

        gvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(arrProduct.get(position).getProductName().equals("Cà phê")){
                    Intent intent = new Intent(DrinkProductActivity.this,DetailProductActivity.class);
                    intent.putExtra("ARRPRODUCT",arrProduct.get(position));
                    startActivity(intent);
                }
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
}
