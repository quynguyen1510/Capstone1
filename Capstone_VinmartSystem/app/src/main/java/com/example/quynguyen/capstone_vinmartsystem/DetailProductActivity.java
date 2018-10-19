package com.example.quynguyen.capstone_vinmartsystem;

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

public class DetailProductActivity extends AppCompatActivity {

    TextView txtProductName,txtProductDesc;
    ImageView imgProduct;
    Button btnBack,btnSearchSubNav,btnAddCart;
    EditText edtSearchSubNav;
    Fragment fragment_cart = new Fragment_Cart();

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

        final Intent intent = getIntent();
        final Product objProduct = intent.getParcelableExtra("ARRPRODUCT");
        txtProductDesc.setText(objProduct.getProductDescription());
        imgProduct.setImageResource(objProduct.getProductImg());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailProductActivity.this,DrinkProductActivity.class);
                startActivity(intent1);
            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("CARTPRODUCT",objProduct);
                Intent intent2 = new Intent(DetailProductActivity.this,MainActivity.class);
                intent2.putExtras(bundle);
                startActivity(intent2);
            }
        });

    }

}
