package com.example.quynguyen.capstone_vinmartsystem;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.String.*;

public class CartRecycleAdapter extends BaseAdapter {
    private List<Cart> arrayList;
    private Context context;
    private int layout;
    int newQuantity;
    TextView totalView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("vinmart-delivery-224708");
    SharedPreferences sharedPreferences;

    public CartRecycleAdapter(List<Cart> arrayList, Context context, int layout,TextView totalView) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout = layout;
        this.totalView = totalView;
    }
    public int TongTien(){
        int total = 0;
        for(int i = 0 ; i<arrayList.size() ; i++){
            total += arrayList.get(i).getQuantity()*arrayList.get(i).getPrice();
        }
        return total;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        ImageView imageviewCartImg;
        ImageView imgMinus;
        ImageView imgPlus;
        TextView txtCartName;
        TextView txtCartPrice;
        TextView quantityLabel;
        TextView quantity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new CartRecycleAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.imageviewCartImg = convertView.findViewById(R.id.imageviewCartImg);
            holder.txtCartName = convertView.findViewById(R.id.txtCartName);
            holder.txtCartPrice = convertView.findViewById(R.id.txtCartPrice);
            holder.quantityLabel = convertView.findViewById(R.id.quantityLabel);
            holder.quantity = convertView.findViewById(R.id.quantity);
            holder.imgMinus = convertView.findViewById(R.id.imgMinus);
            holder.imgPlus = convertView.findViewById(R.id.imgPlus);
            convertView.setTag(holder);
        }else{
            holder = (CartRecycleAdapter.ViewHolder) convertView.getTag();
        }

        Cart cart = arrayList.get(position);
        holder.imageviewCartImg.setImageResource(cart.getProductImg());
        holder.txtCartName.setText(cart.getProductName());
        holder.txtCartPrice.setText(String.valueOf(cart.getPrice()));
        holder.quantity.setText(String.valueOf(cart.getQuantity()));
        sharedPreferences = context.getSharedPreferences("login",MODE_PRIVATE);
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQuantity = Integer.parseInt(holder.quantity.getText().toString().trim());
                if(newQuantity == 1){
                    Toast.makeText(context, "Số lượng không được nhỏ hơn 1", Toast.LENGTH_SHORT).show();
                }else {
                    //Lấy số lượng mới và update lên firebase
                    newQuantity = Integer.parseInt(holder.quantity.getText().toString().trim()) - 1;
                    Map<String, Object> setQuantity = new HashMap<String, Object>();
                    setQuantity.put("quantity", String.valueOf(newQuantity));
                    reference.child("Cart"+sharedPreferences.getInt("cus_id",0)).child(arrayList.get(position).getProductName()).updateChildren(setQuantity);
                    //------------------
                    arrayList.get(position).setQuantity(newQuantity);
                    holder.quantity.setText(String.valueOf(newQuantity));
                    totalView.setText(String.valueOf(TongTien()));
                }
            }
        });
        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy dữ liệu mới và add lên firebase
                newQuantity = Integer.parseInt(holder.quantity.getText().toString().trim()) + 1;
                Map<String, Object> setQuantity = new HashMap<String, Object>();
                setQuantity.put("quantity", String.valueOf(newQuantity));
                reference.child("Cart"+sharedPreferences.getInt("cus_id",0)).child(arrayList.get(position).getProductName()).updateChildren(setQuantity);
                //---------

                arrayList.get(position).setQuantity(newQuantity);
                holder.quantity.setText(String.valueOf(newQuantity));
                totalView.setText(String.valueOf(TongTien()));
            }
        });


        return convertView;

    }
    public ArrayList<Cart> getArrayList() {
        return (ArrayList<Cart>) arrayList;
    }
}
