package com.example.quynguyen.capstone_vinmartsystem;

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

public class Fragment_Delivery extends Fragment {
    EditText edtIdOrderConfirm;
    Button btnConfirmGetOrder;
    SharedPreferences sharedPreferences;
    String urlGetDelivery = new Connect().urlData + "/getdeliverybyid.php";
    String urlConfirmOrder = new Connect().urlData + "/updateconfirmorder.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery,container,false);
        //Ánh xạ
        edtIdOrderConfirm = view.findViewById(R.id.edtConfirmIdOrder);
        btnConfirmGetOrder = view.findViewById(R.id.btnConfirmGetOrder);

        btnConfirmGetOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtIdOrderConfirm.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(), "Hãy nhập thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    getDelivery(urlGetDelivery);
                }
            }
        });

        return view;
    }

    //Check confirm đơn hàng có đúng là người mua hay không
    private void getDelivery(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            sharedPreferences = getActivity().getSharedPreferences("login",getActivity().MODE_PRIVATE);
                            if(jsonObject.getInt("success") == 1){
                               int id = sharedPreferences.getInt("cus_id",0);
                               int customerID = jsonObject.getInt("cusID");
                               if(id != customerID){
                                   Toast.makeText(getContext(), "Xác thực nhận hàng không đúng người mua hàng! ", Toast.LENGTH_SHORT).show();
                               }else{
                                   confirmOrder(urlConfirmOrder);
                                   Toast.makeText(getContext(), "Cảm ơn quý khách đã sử dụng dịch vụ Vinmart!", Toast.LENGTH_LONG).show();
                               }
                            }else{
                                Toast.makeText(getActivity(), "Không lấy được thông tin", Toast.LENGTH_SHORT).show();
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
                params.put("invoice_id",edtIdOrderConfirm.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Nếu đơn hàng đc nhận sẽ cập nhật delivery thành 1 = hàng đã đc giao
    private void confirmOrder(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("complete")){
                            Toast.makeText(getActivity(), "Đơn hàng đã đc giao", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Chưa cập nhật thành công!", Toast.LENGTH_SHORT).show();
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
                params.put("order_id",edtIdOrderConfirm.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
