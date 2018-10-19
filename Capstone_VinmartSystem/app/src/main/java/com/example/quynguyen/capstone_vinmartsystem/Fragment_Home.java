package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class Fragment_Home extends Fragment {

    Button btnSearch;
    EditText edtSearch;
    GridView gvCat;
    ArrayList<Category> arrCat;
    CategoryAdapter adapterCat;
    ViewFlipper viewFlipper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      int [] images = {R.drawable.promotion1,R.drawable.promotion2,R.drawable.promotion3};
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        gvCat = (GridView) view.findViewById(R.id.gridviewCategory);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);

        //loop slide
        for(int i = 0 ; i < images.length ; i++){
            flipperImages(images[i]);
        }

        arrCat = new ArrayList<>();
        arrCat.add(new Category(R.drawable.vineco,"VinEco"));
        arrCat.add(new Category(R.drawable.nhu_yeu_pham,"Nhu yếu phẩm"));
        arrCat.add(new Category(R.drawable.thuc_uong,"Đồ uống"));
        arrCat.add(new Category(R.drawable.vincook,"VinCook"));
        arrCat.add(new Category(R.drawable.dich_vu,"Dịch vụ"));

        adapterCat = new CategoryAdapter(getActivity(),R.layout.category_line,arrCat);
        gvCat.setAdapter(adapterCat);
        gvCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(arrCat.get(position).getCatName().equals("Đồ uống")) {
                    Intent intent = new Intent(getActivity(), DrinkProductActivity.class);
                    startActivity(intent);
                }
            }
        });

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
