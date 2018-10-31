package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsPromotionRecycleAdapter extends  RecyclerView.Adapter<NewsPromotionRecycleAdapter.viewHolder> {

    ArrayList<NewsPromotion> arrayList, arraySubCat;
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
        holder.imgNewsImg.setImageResource(arrayList.get(position).getNewsImage());
        holder.txtNewsName.setText(arrayList.get(position).getNewsName());
        holder.txtNewsDescription.setText(arrayList.get(position).getNewsDescription());
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(context,NewsPromotionDetailActivity.class);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                bundle.putParcelable("News",arrayList.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener , View.OnClickListener{

        TextView txtNewsName,txtNewsDescription,txtNewsDate;
        ImageView imgNewsImg;
        private ItemClickListener itemClickListener;

        public viewHolder(View itemView) {
            super(itemView);
            imgNewsImg = (ImageView) itemView.findViewById(R.id.imgNewsImg);
            txtNewsName = (TextView) itemView.findViewById(R.id.txtNewsName);
            txtNewsDate = (TextView) itemView.findViewById(R.id.txtNewsDate);
            txtNewsDescription = (TextView) itemView.findViewById(R.id.txtNewsDescription);
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
