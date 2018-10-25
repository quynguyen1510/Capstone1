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

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {
    ArrayList<NewsPromotion> arrayList;
    Context context;

    public NotificationAdapter(ArrayList<NewsPromotion> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.noti_news_item,parent,false);
        return new NotificationAdapter.viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.viewHolder holder, int position) {
        holder.txtNotiTitle.setText(arrayList.get(position).getNewsName());
        holder.txtNotiDescription.setText(arrayList.get(position).getNewsDescription());
        final   Bundle bundle = new Bundle();
        final    Intent intent = new Intent(context,NewsPromotionDetailActivity.class);
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

        TextView txtNotiTitle,txtNotiDescription;
        private ItemClickListener itemClickListener;

        public viewHolder(View itemView) {
            super(itemView);
            txtNotiTitle = (TextView) itemView.findViewById(R.id.txtNotiTitle);
            txtNotiDescription = (TextView) itemView.findViewById(R.id.txtNotiDescription);
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
