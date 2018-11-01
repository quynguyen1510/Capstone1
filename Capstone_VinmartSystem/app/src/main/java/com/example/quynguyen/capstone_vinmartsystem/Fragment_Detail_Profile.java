package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Fragment_Detail_Profile extends Fragment {

    TextView txtEmail, txtAddress, txtUserName;
    User user;
    Connect connect = new Connect();
    String url = connect.urlData + "/getuser.php";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String USERNAME_KEY = "user";
    String PASS_KEY = "pass";
    String userName = "";
    String pass = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_profile,container,false);

        txtUserName = (TextView) view.findViewById(R.id.txtUserName);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        txtAddress = (TextView) view.findViewById(R.id.txtAddress);

        Bundle bundle = getArguments();
        sharedPreferences = getActivity().getSharedPreferences("loginAcc",getContext().MODE_PRIVATE);
        //Kiểm tra user từ fragment_profile gửi qua có hay không
        if(bundle != null){
            user = bundle.getParcelable("user");
            editor = sharedPreferences.edit();
            editor.putString("user",user.getUserName());
            editor.putString("pass",user.getPassWord());
            userName = user.getUserName();
            pass = user.getPassWord();
        }
        checkUser(url);
        //Lưu login
        userName = sharedPreferences.getString(USERNAME_KEY,"");
        pass = sharedPreferences.getString(PASS_KEY,"");
        return view;
    }

    //check user từ database nếu có thì giữ login không có thì out login
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
                                txtUserName.setText(objUser.getFullName());
                                txtEmail.setText(objUser.getEmail());
                                txtAddress.setText(objUser.getAddress());
                                editor = sharedPreferences.edit();
                                editor.putString(USERNAME_KEY,objUser.getUserName());
                                editor.putString(PASS_KEY,objUser.getPassWord());
                                editor.commit();
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
                params.put("user",userName);
                params.put("pass",pass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
