package com.example.quynguyen.capstone_vinmartsystem;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Fragment_Cart extends Fragment {

    SharedPreferences sharedPreferences;
    CartRecycleAdapter cartAdapter;
    ArrayList<Cart> arrCart;
    TextView totalPrice;
    String urlData = new Connect().urlData + "/getcart.php";
    int cusID;
    int total=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container,false);
        arrCart = new ArrayList<>();
        totalPrice = view.findViewById(R.id.totalPrice);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCart);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        sharedPreferences = getActivity().getSharedPreferences("login",getContext().MODE_PRIVATE);
        cusID = sharedPreferences.getInt("cus_id",0);
        cartAdapter = new CartRecycleAdapter(arrCart,getContext());
        recyclerView.setAdapter(cartAdapter);
        if(cusID == 0){
            Toast.makeText(getContext(), "Không có sản phẩm nào", Toast.LENGTH_LONG).show();
            totalPrice.setText("");
        }else {
            getCartData(urlData);
        }
        return view;
    }

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

}
