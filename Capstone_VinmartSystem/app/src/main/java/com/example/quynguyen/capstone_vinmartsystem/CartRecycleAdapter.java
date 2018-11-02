package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CartRecycleAdapter extends RecyclerView.Adapter<CartRecycleAdapter.viewHolder> {
    ArrayList<Cart> arrayList;
    Context context;

    public CartRecycleAdapter(ArrayList<Cart> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cart_item_row,parent,false);
        return new CartRecycleAdapter.viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.imageviewCartImg.setImageResource(arrayList.get(position).getProductImg());
        holder.txtCartName.setText(arrayList.get(position).getProductName());
        holder.txtCartPrice.setText(arrayList.get(position).getPrice() + " VNĐ");
        holder.quantity.setText(String.valueOf(arrayList.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView txtCartName, txtCartPrice, quantity, quantityLabel;
        ImageView imageviewCartImg;

        public viewHolder(View itemView) {
            super(itemView);
            imageviewCartImg = (ImageView) itemView.findViewById(R.id.imageviewCartImg);
            txtCartName = (TextView) itemView.findViewById(R.id.txtCartName);
            txtCartPrice = (TextView) itemView.findViewById(R.id.txtCartPrice);
            quantityLabel = (TextView) itemView.findViewById(R.id.quantityLabel);
            quantity  = (TextView) itemView.findViewById(R.id.quantity);
        }
    }
}
