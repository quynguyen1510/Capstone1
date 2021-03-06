package com.example.quynguyen.capstone_vinmartsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvoiceConfirmActivity extends AppCompatActivity {
    TextView txtCusPhone, txtCusAddress , txtID;
    TextView txtTongTien;
    Button btnDone;
    ArrayList<Cart> arrayList;
    String USERNAME_KEY = "user";
    String PASS_KEY = "pass";

    SharedPreferences sharedPreferences;
    String urlInsertDetail = new Connect().urlData + "/insertdetailinvoice.php";
    String urlGetInvoiceID = new Connect().urlData + "/getidinvoice.php";
    String urlDelete = new Connect().urlData + "/deletecartbyuser.php";
    String urlGetUser = new Connect().urlData + "/getuser.php";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("vinmart-delivery-224708");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_confirm);
        AnhXa();
        Intent intent = getIntent();
        Bundle bundle = getIntent().getBundleExtra("CARTFORPAY");
        txtCusAddress.setText(intent.getStringExtra("ADDRESS"));
        txtCusPhone.setText(intent.getStringExtra("PHONE"));
        txtTongTien.setText(intent.getStringExtra("TOTAL"));
        txtID.setText("");
        if(bundle != null){
            arrayList = bundle.getParcelableArrayList("GETCART");
            getInvoiceID(urlGetInvoiceID);
        }

        //Hoàn thành mua hàng in ra chi tiết hóa đơn
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getInvoiceID(urlGetInvoiceID);
                for (int i = 0 ; i < arrayList.size() ; i++){
                        addInvoice(arrayList.get(i));
                }
                confirmMessage();
            }
        });


    }
    public void AnhXa(){
        txtCusAddress = findViewById(R.id.txtCusAddres);
        txtCusPhone = findViewById(R.id.txtCusPhone);
        txtTongTien = findViewById(R.id.txtTongTien);
        txtID = findViewById(R.id.txtID);
        btnDone = findViewById(R.id.btnDone);
    }
    //Lấy ID của Invoice sau đó insert cho Detail_Invoice
    private void getInvoiceID(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getInt("success") == 1){
                                int orderID = jsonObject.getInt("invoice_id");
                                txtID.setText(String.valueOf(orderID));
                            }else{
                                Toast.makeText(InvoiceConfirmActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InvoiceConfirmActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    //Add vào bảng detail_invoice
    private void addInvoice(final Cart objCart){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,urlInsertDetail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("complete")){
                            Toast.makeText(InvoiceConfirmActivity.this, "Hoàn tất mua hàng", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(InvoiceConfirmActivity.this, "Chưa mua hàng thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InvoiceConfirmActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                params.put("cus_id",String.valueOf(sharedPreferences.getInt("cus_id",0)));
                params.put("order_id",txtID.getText().toString().trim());
                params.put("productimg",String.valueOf(objCart.getProductImg()));
                params.put("product_id",String.valueOf(objCart.getProductID()));
                params.put("product_quantity",String.valueOf(objCart.getQuantity()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Xóa cart sau khi đã thanh toán
    public void deleteCart(){
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        reference.child("Cart"+sharedPreferences.getInt("cus_id",0)).removeValue();
    }

    //Tin nhắn thông báo mua hàng thành công và chờ shipper
    public void confirmMessage(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Cảm ơn bạn đã mua hàng shipper sẽ liên lạc với bạn trong vòng 3 phút");
        dialog.setPositiveButton("Kết thúc", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCart();
                checkUser(urlGetUser);
            }
        });
        dialog.show();
    }

    private void checkUser(String url){
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
                                Intent intent = new Intent(InvoiceConfirmActivity.this,MainActivity.class);
                                intent.putExtra("user", objUser);
                                startActivity(intent);
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
                        Toast.makeText(InvoiceConfirmActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
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
