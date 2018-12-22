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
import android.util.Log;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    Bundle bundle;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("vinmart-delivery-224708");

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
            // lấy đữ liệu từ firebase
            reference.child("Cart"+cusID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot cart : dataSnapshot.getChildren()){
                        String carID = cart.child("cartID").getValue().toString();
                        String cusID =cart.child("cusID").getValue().toString();
                        String price = cart.child("price").getValue().toString();
                        String productImg = cart.child("productImg").getValue().toString();
                        String productID = cart.child("productID").getValue().toString();
                        String productName = cart.child("productName").getValue().toString();
                        String quantity = cart.child("quantity").getValue().toString();
                        arrCart.add(new Cart(Integer.parseInt(carID),productName,Integer.parseInt(productImg),Integer.parseInt(productID),Integer.parseInt(quantity),Integer.parseInt(price),Integer.parseInt(cusID)));
                    }
                    for(int i = 0 ; i< arrCart.size() ; i++){
                        total += arrCart.get(i).getQuantity()*arrCart.get(i).getPrice();
                    }
                    totalPrice.setText(String.valueOf(total));
                    cartAdapter = new CartRecycleAdapter(arrCart,getContext(),R.layout.cart_item_row,totalPrice);
                    gridViewCart.setAdapter(cartAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        //Xóa sản phẩm trong cart
        gridViewCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirmDelete(arrCart.get(position).getProductName(),arrCart.get(position));
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
                        String total = totalPrice.getText().toString();
                        Intent intent = new Intent(getActivity(), InvoiceActivity.class);
                        intent.putExtra("TotalPrice",total);
                        intent.putExtra("Getbundle",bundle);
                        getActivity().startActivity(intent);
                    }else{
                        Toast.makeText(getContext(), "Hãy shopping nào", Toast.LENGTH_LONG).show();
                    }

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


    public void confirmDelete(final String name, final Cart objCart){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("Bạn có muốn xóa "+name+" không?");
        sharedPreferences = getActivity().getSharedPreferences("login",getContext().MODE_PRIVATE);
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reference.child("Cart"+sharedPreferences.getInt("cus_id",0)).child(name).removeValue();
                arrCart.remove(objCart);
                total = 0;
                for(int i = 0 ; i < arrCart.size() ; i++){
                    total += arrCart.get(i).getQuantity()*arrCart.get(i).getPrice();
                }
                totalPrice.setText(String.valueOf(total));
                cartAdapter = new CartRecycleAdapter(arrCart,getContext(),R.layout.cart_item_row,totalPrice);
                gridViewCart.setAdapter(cartAdapter);
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
