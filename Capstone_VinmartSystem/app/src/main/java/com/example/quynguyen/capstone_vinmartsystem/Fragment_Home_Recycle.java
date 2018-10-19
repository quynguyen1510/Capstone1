package com.example.quynguyen.capstone_vinmartsystem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class Fragment_Home_Recycle extends Fragment {

    Button btnSearch;
    EditText edtSearch;
    GridView gvCat;
    ArrayList<Category> arrCat;
    CategoryAdapter adapterCat;
    ViewFlipper viewFlipper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_recycle,container,false);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
        int [] images = {R.drawable.promotion1,R.drawable.promotion2,R.drawable.promotion3};
        //loop slide
        for(int i = 0 ; i < images.length ; i++){
            flipperImages(images[i]);
        }
        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView1.setLayoutManager(layoutManager);
        ArrayList<Category> arrCat = new ArrayList<>();
        arrCat.add(new Category(R.drawable.vineco,"VinEco"));
        arrCat.add(new Category(R.drawable.nhu_yeu_pham,"Nhu yếu phẩm"));
        arrCat.add(new Category(R.drawable.thuc_uong,"Đồ uống"));
        arrCat.add(new Category(R.drawable.vincook,"VinCook"));
        arrCat.add(new Category(R.drawable.dich_vu,"Dịch vụ"));
        CategoryRecycleAdapter categoryRecycleAdapter = new CategoryRecycleAdapter(arrCat,getContext());
        recyclerView1.setAdapter(categoryRecycleAdapter);
        return view;
    }

    public void flipperImages(int img){
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(img);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000); //3sec
        viewFlipper.setAutoStart(true);

        //animation
        viewFlipper.setInAnimation(getActivity(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getActivity(),android.R.anim.slide_out_right);
    }

}
