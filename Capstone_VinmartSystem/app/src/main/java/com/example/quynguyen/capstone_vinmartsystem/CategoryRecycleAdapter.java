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

public class CategoryRecycleAdapter extends RecyclerView.Adapter<CategoryRecycleAdapter.viewHolder> {
    ArrayList<Category> arrayList;
    Context context;

    public CategoryRecycleAdapter(ArrayList<Category> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.category_item_row,parent,false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.txtCatNameRecycle.setText(arrayList.get(position).getCatName());
        holder.imgviewCatImg.setImageResource(arrayList.get(position).getCatImage());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView txtCatNameRecycle;
        ImageView imgviewCatImg;

        public viewHolder(View itemView) {
            super(itemView);
            txtCatNameRecycle = (TextView) itemView.findViewById(R.id.txtCatNameRecycle);
            imgviewCatImg = (ImageView) itemView.findViewById(R.id.imgviewCatImg);
        }
    }
}
