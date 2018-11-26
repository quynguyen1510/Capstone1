package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.viewHolder> {
    ArrayList<Delivery> arrayList;
    Context context;

    public DeliveryAdapter(ArrayList<Delivery> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_order_row,parent,false);
        return new DeliveryAdapter.viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.txtInvoiceID.setText("Mã đơn hàng: "+String.valueOf(arrayList.get(position).getInvoiceID()));
        holder.txtCusAddress.setText(arrayList.get(position).getCusAddress());
        holder.txtCusPhone.setText(arrayList.get(position).getCusPhone());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("INVOICE",arrayList.get(position));
                Intent intent = new Intent(context,MapActivity.class);
                intent.putExtra("GET_INVOICE",bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener , View.OnClickListener{

        TextView txtInvoiceID,txtCusAddress,txtCusPhone;
        private ItemClickListener itemClickListener;

        public viewHolder(View itemView) {
            super(itemView);
            txtInvoiceID = (TextView) itemView.findViewById(R.id.txtInvoiceID);
            txtCusAddress = (TextView) itemView.findViewById(R.id.txtCusAddress);
            txtCusPhone = itemView.findViewById(R.id.txtCusPhone);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
}
