package com.example.quynguyen.capstone_vinmartsystem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class Fragment_Home extends Fragment {

    Button btnSearch;
    EditText edtSearch;
    ViewFlipper viewFlipper;
    ArrayList<NewsPromotion> arrNews;
    NewsPromotionRecycleAdapter newsPromotionRecycleAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        RecyclerView recyclerViewCat = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView recyclerViewNew = (RecyclerView) view.findViewById(R.id.recyclerViewNews);

        recyclerViewCat.setHasFixedSize(true);

          //news recycleview
        arrNews = new ArrayList<>();
        recyclerViewNew.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewNew.setLayoutManager(layoutManager);
        for (int i = 0 ; i<5 ; i++) {
            arrNews.add(new NewsPromotion(R.drawable.news1, "Deal giảm giá sốc mỗi tuần mang lại giá trị tốt nhất"));
            arrNews.add(new NewsPromotion(R.drawable.news2, "Giảm giá cuối năm với nhiều ưu đãi tốt nhất"));
            arrNews.add(new NewsPromotion(R.drawable.news3, "VinID sở hữu thẻ đa dụng với nhiều ưu đãi hấp dẫn "));
        }
        newsPromotionRecycleAdapter =  new NewsPromotionRecycleAdapter(arrNews,getContext());
        recyclerViewNew.setAdapter(newsPromotionRecycleAdapter);

        //cat recycleview
        final ArrayList<Category> arrCat = new ArrayList<>();
        arrCat.add(new Category(R.drawable.vineco, "VinEco"));
        arrCat.add(new Category(R.drawable.nhu_yeu_pham, "Nhu yếu phẩm"));
        arrCat.add(new Category(R.drawable.thuc_uong, "Đồ uống"));
        arrCat.add(new Category(R.drawable.vincook, "VinCook"));
        arrCat.add(new Category(R.drawable.dich_vu, "Dịch vụ"));
        int spanCount = 2;//Số cột nếu thiết lập lưới đứng, số dòng nếu lưới ngang
        int orientation = GridLayoutManager.VERTICAL;//Lưới ngang

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerViewCat.setLayoutManager(gridLayoutManager);
        CategoryRecycleAdapter categoryRecycleAdapter = new CategoryRecycleAdapter(arrCat,getContext());
        recyclerViewCat.setAdapter(categoryRecycleAdapter);

        return view;
    }

}
