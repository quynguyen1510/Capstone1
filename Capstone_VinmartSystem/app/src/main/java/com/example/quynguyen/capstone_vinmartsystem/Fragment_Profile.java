package com.example.quynguyen.capstone_vinmartsystem;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class Fragment_Profile extends Fragment {

    Connect connect = new Connect();
    Button btnLogin;
    EditText edtFullname, edtUser , edtEmail, edtPassword;
    TextView txtRegister;
    Fragment_Home fragment_home;
    User user;
    String url = connect.urlData + "/getuser.php";
    public static final int REQUEST_CODE = 1997;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile,container,false);

        edtUser = view.findViewById(R.id.edtUserLogin);
        edtPassword = view.findViewById(R.id.edtPasswordLogin);
        btnLogin = view.findViewById(R.id.btnLogin);
        txtRegister = view.findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RegisterActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser(url);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                User user = data.getExtras().getParcelable("Account");
                edtUser.setText(user.getUserName());
                edtPassword.setText(user.getPassWord());
            }
        }
    }

    private void checkUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                                Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                if(jsonObject.getInt("role") == 4){
                                    Intent intent = new Intent(getActivity(),MapActivity.class);
                                    getActivity().startActivity(intent);
                                }else {
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.putExtra("user", objUser);
                                    getActivity().startActivity(intent);
                                }
                            }else{
                                Toast.makeText(getActivity(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user",edtUser.getText().toString().trim());
                params.put("pass",edtPassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
