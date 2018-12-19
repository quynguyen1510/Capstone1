package com.example.quynguyen.capstone_vinmartsystem;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap maps;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btnInvoiceList, btnShipperLogout, btnReload;
    LatLng cusPosition;
    Delivery objDelivery;
    Bundle bundle;
    NotificationBadge mBadge;

    String urlCount = new Connect().urlData + "/countdelivery.php";

    public NotificationCompat.Builder notBuilder;
    public static final int MY_NOTIFICATION_ID = 1000;
    public static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        AnhXa();
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
        bundle = getIntent().getBundleExtra("GET_INVOICE");
        mBadge.setNumber(0);
        countDelivery(urlCount);
        if (bundle != null) {
            objDelivery = bundle.getParcelable("INVOICE");
            cusPosition = getLocationFromAddress(this, objDelivery.getCusAddress());
        } else {
            cusPosition = getLocationFromAddress(this, "114 Quang Trung, Hải Châu");
        }

        //List hóa đơn mà shipper phải giao
        btnInvoiceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, ListOrderActivity.class);
                startActivity(intent);
            }
        });

        //Đăng xuất khỏi màn hình shipper
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
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDelivery(urlCount);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        maps = googleMap;
        LatLng cusAddress = new LatLng(cusPosition.latitude, cusPosition.longitude);

        maps.moveCamera(CameraUpdateFactory.newLatLngZoom(cusAddress, 15));
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
        btnReload = findViewById(R.id.btnReload);
        mBadge = findViewById(R.id.badge);
    }

    private void countDelivery(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getInt("success") == 1){
                               mBadge.setNumber(jsonObject.getInt("count"));
                            }else{
                                mBadge.setNumber(0);
                                Toast.makeText(MapActivity.this, "Lỗi không tìm thấy", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                params.put("staff_id",String.valueOf(sharedPreferences.getInt("cus_id",0)));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
