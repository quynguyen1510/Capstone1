package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public  class CategoryAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, int layout, List<Category> categoryList) {
        this.context = context;
        this.layout = layout;
        this.categoryList = categoryList;
    }


    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgCatImage;
        TextView txtCatName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.imgCatImage = (ImageView) convertView.findViewById(R.id.imageviewCatImg);
            holder.txtCatName = (TextView) convertView.findViewById(R.id.txtCatName);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Category cat = categoryList.get(position);
        holder.imgCatImage.setImageResource(cat.getCatImage());
        holder.txtCatName.setText(cat.getCatName());

        return convertView;
    }

}
