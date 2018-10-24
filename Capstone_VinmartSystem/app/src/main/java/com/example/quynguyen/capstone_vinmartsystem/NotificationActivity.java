package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    NotificationAdapter adapter;
    ArrayList<NewsPromotion> arrnewsPromotions;
    Button btnBackMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNoti);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        btnBackMain = (Button) findViewById(R.id.btnBackMain);
        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        arrnewsPromotions = new ArrayList<>();
        for (int i = 0 ; i<5 ; i++) {
            arrnewsPromotions.add(new NewsPromotion(R.drawable.news1, "Deal giảm giá sốc mỗi tuần mang lại giá trị tốt nhất" ,"Giảm giá cực sốc mỗi tuần với hàng ngàn cơ hội trúng thưởng từ nay đến cuối năm , Vinmart tung ra chương trình giải thưởng thường niên đến mọi nhà"));
            arrnewsPromotions.add(new NewsPromotion(R.drawable.news2, "Giảm giá cuối năm với nhiều ưu đãi tốt nhất","Giảm giá cực sốc mỗi tuần với hàng ngàn cơ hội trúng thưởng từ nay đến cuối năm , Vinmart tung ra chương trình giải thưởng thường niên đến mọi nhà"));
            arrnewsPromotions.add(new NewsPromotion(R.drawable.news3, "VinID sở hữu thẻ đa dụng với nhiều ưu đãi hấp dẫn ","Giảm giá cực sốc mỗi tuần với hàng ngàn cơ hội trúng thưởng từ nay đến cuối năm , Vinmart tung ra chương trình giải thưởng thường niên đến mọi nhà"));
        }

        adapter =  new NotificationAdapter(arrnewsPromotions,this);
        recyclerView.setAdapter(adapter);

    }
}
