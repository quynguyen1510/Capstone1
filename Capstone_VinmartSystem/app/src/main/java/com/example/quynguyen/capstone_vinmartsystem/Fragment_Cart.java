package com.example.quynguyen.capstone_vinmartsystem;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Fragment_Cart extends Fragment {

    CartRecycleAdapter cartAdapter;
    ArrayList<Product> arrProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container,false);
        Product objProduct = null;

        arrProduct = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCart);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        Bundle bundle = getArguments();
        if( bundle != null){
            Product product = bundle.getParcelable("CART");
            arrProduct.add(product);
        }
        arrProduct.add(new Product(R.drawable.slurpee,"Slurpee","Thức uống có ga",20000,"15/10/2018"));
        cartAdapter = new CartRecycleAdapter(arrProduct,getContext());
        recyclerView.setAdapter(cartAdapter);
        return view;
    }


}
