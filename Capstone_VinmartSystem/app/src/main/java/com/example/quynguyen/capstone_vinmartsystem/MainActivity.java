package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences sharedPreferences;
    EditText edtSearch;
    Fragment_Cart fragment_cart;
    Fragment_Home fragment_home;
    Fragment_Profile fragment_profile;
    Fragment_Detail_Profile fragment_detail_profile;
    Bundle bundle;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bundle = getIntent().getExtras();
        bottomNavigationView = findViewById(R.id.bottom_nav);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        //Nhận dữ liệu từ Login hoặc Update đổ vào Detail_Profile
        if(bundle != null){
            User objUser = bundle.getParcelable("user");
            bundle.putParcelable("user",objUser);
            fragment_detail_profile = new Fragment_Detail_Profile();
            fragment_detail_profile.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment_detail_profile).commit();
        }else if(sharedPreferences.getInt("loginForCart",0) == 1) {
            //bundle == null tức chưa login thì kiểm tra user có addCart không nếu có phải Login
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Profile()).commit();
        }else{
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
                        case R.id.nav_delivery:
                            selectedFragment = new Fragment_Delivery();
                            break;
                        case R.id.nav_cart:
                            selectedFragment = new Fragment_Cart();
                            break;
                        case R.id.nav_profile:
                            if(bundle != null) {
                                selectedFragment = new Fragment_Detail_Profile();
                            }else{
                                selectedFragment = new Fragment_Profile();
                            }
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return  true;
                }
            };

}
