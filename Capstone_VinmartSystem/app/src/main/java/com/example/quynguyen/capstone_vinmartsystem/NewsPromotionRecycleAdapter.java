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

public class NewsPromotionRecycleAdapter extends  RecyclerView.Adapter<NewsPromotionRecycleAdapter.viewHolder> {

    ArrayList<NewsPromotion> arrayList;
    Context context;

    public NewsPromotionRecycleAdapter(ArrayList<NewsPromotion> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.news_promotion_item,parent,false);
        return new NewsPromotionRecycleAdapter.viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.imgNewsImg.setImageResource(arrayList.get(position).getImage());
        holder.txtDescription.setText(arrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener , View.OnClickListener{

        TextView txtDescription;
        ImageView imgNewsImg;
        private ItemClickListener itemClickListener;

        public viewHolder(View itemView) {
            super(itemView);
            imgNewsImg = (ImageView) itemView.findViewById(R.id.imgNewsImg);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
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
