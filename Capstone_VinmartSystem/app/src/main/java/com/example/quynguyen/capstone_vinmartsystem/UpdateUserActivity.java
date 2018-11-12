package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText edtUpdateName,edtUpdatePass,edtPass,edtUpdateAddress;
    Button btnUpdate, btnCancle;
    User user;
    String PASS_KEY = "pass";
    String urlUpdate = new Connect().urlData + "/updateuser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        AnhXa();
        Bundle bundle = getIntent().getExtras();
        //Kiểm tra detail_login có gửi dữ liệu qua hay ko
        if(bundle != null){
            user = bundle.getParcelable("UpdateAcc");
            edtUpdateName.setText(user.getFullName());
            edtPass.setText(user.getPassWord());
            edtUpdateAddress.setText(user.getAddress());
        }
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putInt("loginForCart",2);
                editor.commit();
                Intent intent = new Intent(UpdateUserActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(urlUpdate);
                sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

                user.setFullName(edtUpdateName.getText().toString());
                user.setAddress(edtUpdateAddress.getText().toString());
                if(edtUpdatePass.getText().toString().equals("")){
                    user.setPassWord(edtPass.getText().toString());
                }else{
                    user.setPassWord(edtUpdatePass.getText().toString());
                    editor = sharedPreferences.edit();
                    editor.putString(PASS_KEY,user.getPassWord());
                    editor.commit();
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("user",user);
                Intent intent = new Intent(UpdateUserActivity.this,MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void AnhXa(){
        edtUpdateName = (EditText) findViewById(R.id.edtUpdateName);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtUpdatePass = (EditText) findViewById(R.id.edtUpdatePass);
        edtUpdateAddress = (EditText) findViewById(R.id.edtUpdateAddress);
        btnCancle = (Button) findViewById(R.id.btnCancel);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
    }
    private void updateUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("complete")){
                            Toast.makeText(UpdateUserActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UpdateUserActivity.this, "Chưa cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateUserActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                if(edtUpdatePass.getText().toString().equals("")){
                    params.put("pass",edtPass.getText().toString().trim());
                }else{
                    params.put("pass",edtUpdatePass.getText().toString().trim());
                }
                params.put("cus_id",String.valueOf(user.getCusID()));
                params.put("name",edtUpdateName.getText().toString().trim());
                params.put("address",edtUpdateAddress.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
