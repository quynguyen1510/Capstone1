package com.example.quynguyen.capstone_vinmartsystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button btnCreateAcc, btnCancel;
    EditText edtFullname, edtUser , edtEmail, edtPassword,edtRepassword, edtAddress;
    User user ;
    Fragment_Profile fragment_profile = new Fragment_Profile();
    String urlInsertUser = new Connect().urlData + "/insertuser.php";
    String urlData = new Connect().urlData + "/checkuser.php";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Validation validation = new Validation();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AnhXa();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUser.getText().toString().equals("") || edtPassword.getText().toString().equals("") || edtFullname.getText().toString().equals("")
                    || edtEmail.getText().toString().equals("") || edtAddress.getText().toString().equals("")    ){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if(edtRepassword.getText().toString().equals(edtPassword.getText().toString().trim()) == false){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập lại đúng password", Toast.LENGTH_SHORT).show();
                }else if (edtPassword.getText().toString().length() < 2) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải từ 8 đến 20 ký tự", Toast.LENGTH_SHORT).show();
                }else if(validation.checkEmail(edtEmail.getText().toString().trim()) == false){
                    Toast.makeText(RegisterActivity.this, "Bạn phải nhập đúng định dạng Email", Toast.LENGTH_SHORT).show();
                }else if(edtUser.getText().toString().length() < 4){
                    Toast.makeText(RegisterActivity.this, "Username phải từ 4 đến 16 ký tự", Toast.LENGTH_SHORT).show();
                }else if(edtFullname.getText().toString().length() < 10){
                    Toast.makeText(RegisterActivity.this, "Họ và tên phải từ 10 đến 80 ký tự", Toast.LENGTH_SHORT).show();
                }else if(validation.checkNumberInString(edtFullname.getText().toString().trim()) == false){
                    Toast.makeText(RegisterActivity.this, "Họ và tên không chứa ký tự số", Toast.LENGTH_SHORT).show();
                }else if(validation.checkSpecialKey(edtFullname.getText().toString().trim()) == false){
                    Toast.makeText(RegisterActivity.this, "Họ và tên không chứa ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                }else if(validation.checkSpecialKey(edtUser.getText().toString().trim()) == false){
                    Toast.makeText(RegisterActivity.this, "Tên đăng nhập không chứa ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                }else if(validation.checkSpecialKey(edtPassword.getText().toString().trim()) == false){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không chứa ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                }else
                    {
                        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                        checkUser(urlData);
                        if(sharedPreferences.getInt("EXISTUSER",0) == 1){
                            Toast.makeText(RegisterActivity.this, "Tài khoản đã có người sử dụng", Toast.LENGTH_SHORT).show();
                        }else {
                            addUser(urlInsertUser);
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            user = new User(0, edtFullname.getText().toString(), edtEmail.getText().toString(), edtUser.getText().toString(), edtPassword.getText().toString(), edtAddress.getText().toString());
                            bundle.putParcelable("Account", user);
                            fragment_profile.setArguments(bundle);
                            intent.putExtras(bundle);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
            }
        });

    }

    public void AnhXa(){
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        edtRepassword = findViewById(R.id.edtRePassword);
        edtFullname = findViewById(R.id.edtFullname);
        edtEmail = findViewById(R.id.edtEmail);
        edtAddress = findViewById(R.id.edtAddress);
        btnCreateAcc = findViewById(R.id.btnCreateAcc);
        btnCancel = findViewById(R.id.btnCancel);
    }

    //Add user nếu thành công
    private void addUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("complete")){
                            Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Chưa tạo thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",edtUser.getText().toString().trim());
                params.put("pass",edtPassword.getText().toString().trim());
                params.put("name",edtFullname.getText().toString().trim());
                params.put("address",edtAddress.getText().toString().trim());
                params.put("gmail",edtEmail.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Kiểm tra username đã có hay chưa
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
                                        jsonObject.getString("address")
                                );
                                //Nếu user đã tồn tại thì set = 1 để kiểm tra
                                if(objUser != null) {
                                    sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                                    editor = sharedPreferences.edit();
                                    editor.putInt("EXISTUSER", 1);
                                    editor.commit();
                                    edtUser.setText("");
                                }else{
                                    // ngược lại không có set = 0
                                    sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                                    editor = sharedPreferences.edit();
                                    editor.putInt("EXISTUSER",0);
                                    editor.commit();
                                    Toast.makeText(RegisterActivity.this, "Chưa có ai đăng ký", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putInt("EXISTUSER",0);
                                editor.commit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user",edtUser.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
