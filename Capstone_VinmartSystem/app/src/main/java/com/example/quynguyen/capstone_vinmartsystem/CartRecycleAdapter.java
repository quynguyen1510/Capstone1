package com.example.quynguyen.capstone_vinmartsystem;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.*;

public class CartRecycleAdapter extends BaseAdapter {
    private List<Cart> arrayList;
    private Context context;
    private int layout;

    public CartRecycleAdapter(List<Cart> arrayList, Context context, int layout) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout = layout;
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
        TextView txtCartName;
        TextView txtCartPrice;
        TextView quantityLabel;
        TextView quantity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new CartRecycleAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.imageviewCartImg = convertView.findViewById(R.id.imageviewCartImg);
            holder.txtCartName = convertView.findViewById(R.id.txtCartName);
            holder.txtCartPrice = convertView.findViewById(R.id.txtCartPrice);
            holder.quantityLabel = convertView.findViewById(R.id.quantityLabel);
            holder.quantity = convertView.findViewById(R.id.quantity);
            convertView.setTag(holder);
        }else{
            holder = (CartRecycleAdapter.ViewHolder) convertView.getTag();
        }

        Cart cart = arrayList.get(position);
        holder.imageviewCartImg.setImageResource(cart.getProductImg());
        holder.txtCartName.setText(cart.getProductName());
        holder.txtCartPrice.setText(String.valueOf(cart.getPrice()));
        holder.quantity.setText(String.valueOf(cart.getQuantity()));
        return convertView;
    }
}
