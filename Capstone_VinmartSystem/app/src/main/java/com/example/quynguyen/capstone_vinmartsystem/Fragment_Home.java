package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Fragment_Home extends Fragment {

    Connect connect = new Connect();
    Button btnSearch, btnNotification;
    EditText edtSearch;
    ArrayList<NewsPromotion> arrNews;
    NewsPromotionRecycleAdapter newsPromotionRecycleAdapter;
    CategoryRecycleAdapter categoryRecycleAdapter;
    ArrayList<Category> arrCat;
    String urlGetCategory = connect.urlData + "/getparentcategory.php";
    String urlGetNews = connect.urlData +"/getnews.php";
    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home,container,false);

        edtSearch = view.findViewById(R.id.edtSearch);
        btnSearch = view.findViewById(R.id.btnSearchNav);
        Bundle bundle = getArguments();
        if(bundle != null){
            User user = bundle.getParcelable("Acc");
            Toast.makeText(getContext(),user.getFullName().toString(),Toast.LENGTH_LONG).show();
        }

        RecyclerView recyclerViewCat = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView recyclerViewNew = (RecyclerView) view.findViewById(R.id.recyclerViewNews);
        btnNotification = view.findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(),NotificationActivity.class);
                getActivity().startActivity(intent);
            }
        });
        recyclerViewCat.setHasFixedSize(true);


        //news recycleview
        arrNews = new ArrayList<>();
        recyclerViewNew.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewNew.setLayoutManager(layoutManager);
        ReadJSON(urlGetNews);
        newsPromotionRecycleAdapter =  new NewsPromotionRecycleAdapter(arrNews,getContext());
        recyclerViewNew.setAdapter(newsPromotionRecycleAdapter);

        //cat recycleview
        //Json
        ReadJSON(urlGetCategory);
        arrCat = new ArrayList<>();
        int spanCount = 2;//Số cột nếu thiết lập lưới đứng, số dòng nếu lưới ngang
        int orientation = GridLayoutManager.VERTICAL;//Lưới đứng

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        gridLayoutManager.setOrientation(orientation);
        recyclerViewCat.setLayoutManager(gridLayoutManager);
        categoryRecycleAdapter = new CategoryRecycleAdapter(arrCat,getContext());
        recyclerViewCat.setAdapter(categoryRecycleAdapter);

        //Search product
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtSearch.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(), "Hãy nhập sản phẩm bạn muốn tìm kiếm", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra("PRODUCTNAME", edtSearch.getText().toString().trim());
                    getActivity().startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    //Đọc dữ liệu từ database thông qua webservice
    private void ReadJSON(final String urlData){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String [ ] arrString = urlData.split("/");
        if(arrString[arrString.length-1].equals("getparentcategory.php")){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlData, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i = 0 ; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String []img = jsonObject.getString("Image").split("\\.");
                            int image = getResources().getIdentifier(img[0],"drawable",getActivity().getPackageName());
                            arrCat.add(new Category(
                                    jsonObject.getInt("ID"),
                                    image,
                                    jsonObject.getString("Name"),
                                    jsonObject.getInt("IDParent")
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    categoryRecycleAdapter.notifyDataSetChanged();
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
            );
            requestQueue.add(jsonArrayRequest);
        }else if(arrString[arrString.length-1].equals("getnews.php")){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlData, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i = 0 ; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String []img = jsonObject.getString("Image").split("\\.");
                            int image = getResources().getIdentifier(img[0],"drawable",getActivity().getPackageName());
                            arrNews.add(new NewsPromotion(
                                    jsonObject.getInt("ID"),
                                    jsonObject.getString("Name"),
//                                    getResources().getIdentifier(jsonObject.getString("Image"),"drawable",getActivity().getPackageName()),
                                    image,
                                    jsonObject.getString("Description"),
                                    jsonObject.getString("Detail"),
                                    jsonObject.getString("NewsDate")
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    newsPromotionRecycleAdapter.notifyDataSetChanged();

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
            );
            requestQueue.add(jsonArrayRequest);
        }
    }
}
