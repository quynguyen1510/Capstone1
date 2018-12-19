package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceActivity extends AppCompatActivity {
    Spinner spnCusDistrict;
    EditText edtCusPhone, edtCusAddress;
    TextView txtTongTien;
    Button btnContinue;

    String USERNAME_KEY = "user";
    String PASS_KEY = "pass";
    String DISTRICT = "";
    SharedPreferences sharedPreferences;

    String urlGetUser = new Connect().urlData + "/getuser.php";
    String urlInvoice = new Connect().urlData + "/insertinvoice.php";
    Calendar getDate = Calendar.getInstance();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    String date = sf.format(getDate.getTime());




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        AnhXa();
        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("Getbundle");
        if(bundle != null){
            ArrayList<Cart> arrCart = bundle.getParcelableArrayList("GETCART");
        }
        String total = intent.getStringExtra("TotalPrice");
        List<String> listDistrict = new ArrayList<>();
        listDistrict.add("Hải Châu");
        listDistrict.add("Thanh Khê");
        listDistrict.add("Sơn Trà");
        listDistrict.add("Liên Chiểu");
        listDistrict.add("Cẩm Lệ");
        listDistrict.add("Ngũ Hành Sơn");
        listDistrict.add("Hòa Vang");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listDistrict);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCusDistrict.setAdapter(adapter);
        spnCusDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DISTRICT = spnCusDistrict.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(total.equals("") == false){
            txtTongTien.setText(total+" VNĐ");
        }
        //lấy thông tin user fill vào phone và address
        getUser(urlGetUser);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCusAddress.getText().toString().equals("") || edtCusPhone.getText().toString().equals("")){
                    Toast.makeText(InvoiceActivity.this, "Hãy điền đầy đủ thông tin để việc giao hàng được chính xác !", Toast.LENGTH_SHORT).show();
                }else {
                    addInvoice(urlInvoice);
                    if (bundle != null) {
                        ArrayList<Cart> arrCart = bundle.getParcelableArrayList("GETCART");
                        Intent intent = new Intent(InvoiceActivity.this, InvoiceConfirmActivity.class);
                        bundle.putParcelableArrayList("GETCART", arrCart);
                        intent.putExtra("PHONE", edtCusPhone.getText().toString().trim());
                        intent.putExtra("ADDRESS", edtCusAddress.getText().toString().trim()+", "+DISTRICT);
                        intent.putExtra("TOTAL", txtTongTien.getText().toString().trim());
                        intent.putExtra("CARTFORPAY", bundle);
                        startActivity(intent);
                    }
                }
            }
        });

    }
    public void AnhXa(){
        edtCusAddress = (EditText) findViewById(R.id.edtCusAddress);
        edtCusPhone = (EditText) findViewById(R.id.edtCusPhone);
        spnCusDistrict = findViewById(R.id.spnCusDistrict);
        txtTongTien = (TextView) findViewById(R.id.txtTongTien);
        btnContinue = (Button) findViewById(R.id.btnContinue);
    }

    //Add vào bảng hóa đơn
    private void addInvoice(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("complete")){
//                            Toast.makeText(InvoiceActivity.this, "Mua hàng thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(InvoiceActivity.this, "Chưa mua hàng thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InvoiceActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                Map<String,String> params = new HashMap<>();
                params.put("cus_id",String.valueOf(sharedPreferences.getInt("cus_id",0)));
                params.put("cus_phone",edtCusPhone.getText().toString().trim());
                params.put("cus_address",edtCusAddress.getText().toString().trim());
                params.put("total",txtTongTien.getText().toString().trim());
                params.put("export_date",date);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Lấy người dùng từ bảng user
    private void getUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getInt("success") == 1){
                                User objUser = new User(
                                        jsonObject.getInt("cus_id"),
                                        jsonObject.getString("fullname"),
                                        jsonObject.getString("gmail"),
                                        jsonObject.getString("user"),
                                        jsonObject.getString("pass"),
                                        jsonObject.getString("address"),
                                        jsonObject.getString("phone")
                                );
                                edtCusAddress.setText(objUser.getAddress());
                                edtCusPhone.setText(objUser.getUserPhone());
                            }else{

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InvoiceActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                params.put("user",sharedPreferences.getString(USERNAME_KEY,""));
                params.put("pass",sharedPreferences.getString(PASS_KEY,""));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
