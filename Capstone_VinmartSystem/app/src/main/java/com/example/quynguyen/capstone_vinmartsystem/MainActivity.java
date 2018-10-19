package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    ViewFlipper viewFlipper;
    Fragment_Cart fragment_cart = new Fragment_Cart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        if(bundle != null){
            Product objProduct = bundle.getParcelable("CARTPRODUCT");
            bundle.putParcelable("CART",objProduct);
            fragment_cart.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment_cart).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(navLister);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navLister =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment= null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new Fragment_Home();
                            break;
                        case R.id.nav_cart:
                            selectedFragment = new Fragment_Cart();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new Fragment_Profile();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return  true;
                }
            };

}
