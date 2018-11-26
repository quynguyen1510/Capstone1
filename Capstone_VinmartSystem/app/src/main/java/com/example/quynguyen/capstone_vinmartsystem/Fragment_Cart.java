package com.example.quynguyen.capstone_vinmartsystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_Cart extends Fragment{

    GridView gridViewCart;
    SharedPreferences sharedPreferences;
    CartRecycleAdapter cartAdapter;
    ArrayList<Cart> arrCart;
    TextView totalPrice;
    Button btnPayment,btnNotification;
    String urlData = new Connect().urlData + "/getcart.php";
    String urlDelete = new Connect().urlData + "/deletecart.php";
    Bundle bundle;

    int cusID;
    int total=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container,false);
        totalPrice = view.findViewById(R.id.totalPrice);
        btnPayment = view.findViewById(R.id.btnPayment);
        btnNotification = view.findViewById(R.id.btnNotification);
        sharedPreferences = getActivity().getSharedPreferences("login",getContext().MODE_PRIVATE);
        cusID = sharedPreferences.getInt("cus_id",0);

        arrCart = new ArrayList<>();
        gridViewCart =  view.findViewById(R.id.gridViewCart);
        cartAdapter = new CartRecycleAdapter(arrCart,getContext(),R.layout.cart_item_row,totalPrice);
        gridViewCart.setAdapter(cartAdapter);

        if(cusID == 0){
            Toast.makeText(getContext(), "Không có sản phẩm nào", Toast.LENGTH_LONG).show();
            totalPrice.setText("");
        }else {
            getCartData(urlData);
        }

        //Xóa sản phẩm trong cart
        gridViewCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirmDelete(arrCart.get(position).getProductName(),arrCart.get(position).getCartID());
                return false;
            }
        });
        //Xác nhận thanh toán
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cusID == 0){
                    Toast.makeText(getContext(), "Hãy shopping nào", Toast.LENGTH_LONG).show();
                    totalPrice.setText("");
                }else {
                    ArrayList<Cart> newCart = cartAdapter.getArrayList();
                    if(newCart.size() > 0) {
                        bundle = new Bundle();
                        bundle.putParcelableArrayList("GETCART",newCart);
                    }
                    String total = totalPrice.getText().toString();
                    Intent intent = new Intent(getActivity(), InvoiceActivity.class);
                    intent.putExtra("TotalPrice",total);
                    intent.putExtra("Getbundle",bundle);
                    getActivity().startActivity(intent);
                }
            }
        });
        //Nhảy qua notification
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NotificationActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
    public void deleteCart(final int idCart){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("complete")){
                            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Chưa xóa thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi server", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id_cart",String.valueOf(idCart));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    // Lấy dữ liệu giỏ hàng
    private void getCartData(String urlCart) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        urlCart = urlData + "?cus_id=" + cusID;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlCart, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        arrCart.add(new  Cart(
                                jsonObject.getInt("cartID"),
                                jsonObject.getString("productName"),
                                jsonObject.getInt("productImg"),
                                jsonObject.getInt("productID"),
                                jsonObject.getInt("quantity"),
                                jsonObject.getInt("price"),
                                jsonObject.getInt("cusID")
                        ));
                        total = total + (arrCart.get(i).getPrice() * arrCart.get(i).getQuantity());
                        totalPrice.setText(String.valueOf(total));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                cartAdapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    public void confirmDelete(String name, final int cartID){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("Bạn có muốn xóa "+name+" không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCart(cartID);
                cartAdapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

}
