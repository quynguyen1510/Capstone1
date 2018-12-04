package com.example.quynguyen.capstone_vinmartsystem;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap maps;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    Location mLastLocation;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btnInvoiceList, btnShipperLogout;
    LatLng cusPosition;
    Delivery objDelivery;
    Bundle bundle;

    public static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        AnhXa();
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
        bundle = getIntent().getBundleExtra("GET_INVOICE");
        if (bundle != null) {
            objDelivery = bundle.getParcelable("INVOICE");
            cusPosition = getLocationFromAddress(this, objDelivery.getCusAddress());
        } else {
            cusPosition = getLocationFromAddress(this, "114 Quang Trung, Hải Châu");
        }

        btnInvoiceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, ListOrderActivity.class);
                startActivity(intent);
            }
        });
        btnShipperLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        maps = googleMap;
        LatLng cusAddress = new LatLng(cusPosition.latitude, cusPosition.longitude);

        maps.moveCamera(CameraUpdateFactory.newLatLngZoom(cusAddress, 18));
        if (bundle != null) {
            maps.addMarker(new MarkerOptions().title(objDelivery.getCusAddress()).snippet("Địa chỉ khách hàng").position(cusAddress));
        } else {
            maps.addMarker(new MarkerOptions().title("Vinmart Hải Châu").snippet("Hệ thống cửa hàng Vinmart").position(cusAddress));
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        maps.setMyLocationEnabled(true);
    }

    //Lấy vị trí từ address khách hàng
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public void AnhXa(){
        btnInvoiceList = findViewById(R.id.btnInvoiceList);
        btnShipperLogout = findViewById(R.id.btnShipperLogout);
    }
}
