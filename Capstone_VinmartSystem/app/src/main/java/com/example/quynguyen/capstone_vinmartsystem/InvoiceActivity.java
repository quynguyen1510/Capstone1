package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Map;

public class InvoiceActivity extends AppCompatActivity {
    EditText edtCusPhone, edtCusAddress;
    TextView txtTongTien;
    Button btnContinue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String urlInvoice = new Connect().urlData + "/insertinvoice.php";
    String urlInsertDetail = new Connect().urlData + "/insertdetailinvoice.php";
    String urlGetInvoiceID = new Connect().urlData + "/getidinvoice.php";
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
        String total = intent.getStringExtra("TotalPrice");
        if(total.equals("") == false){
            txtTongTien.setText(total+" VNĐ");
        }
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCusAddress.getText().toString().equals("") || edtCusPhone.getText().toString().equals("")){
                    Toast.makeText(InvoiceActivity.this, "Hãy điền đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                }else {
                    addInvoice(urlInvoice);
                    if (bundle != null) {
                        ArrayList<Cart> arrCart = bundle.getParcelableArrayList("GETCART");
                        Intent intent = new Intent(InvoiceActivity.this, InvoiceConfirmActivity.class);
                        bundle.putParcelableArrayList("GETCART", arrCart);
                        intent.putExtra("PHONE", edtCusPhone.getText().toString().trim());
                        intent.putExtra("ADDRESS", edtCusAddress.getText().toString().trim());
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
                            Toast.makeText(InvoiceActivity.this, "Mua hàng thành công", Toast.LENGTH_SHORT).show();
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


}
