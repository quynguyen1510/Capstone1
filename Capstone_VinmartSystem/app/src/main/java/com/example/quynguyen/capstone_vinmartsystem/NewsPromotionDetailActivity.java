package com.example.quynguyen.capstone_vinmartsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsPromotionDetailActivity extends AppCompatActivity {
    TextView txtDescriptionNews, txtDetailNews;
    ImageView imgNews;
    Button btnGoStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_promotion_detail);
        imgNews = (ImageView) findViewById(R.id.imgNews);
        txtDescriptionNews = (TextView) findViewById(R.id.txtDescriptionNews);
        txtDetailNews = (TextView) findViewById(R.id.txtDetailNews);
        btnGoStore = (Button) findViewById(R.id.btnGoStore);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            NewsPromotion objNews = bundle.getParcelable("News");
            imgNews.setImageResource(objNews.getImage());
            txtDescriptionNews.setText(objNews.getDescription());
            txtDetailNews.setText(objNews.getDetail());
        }else{
            txtDetailNews.setText("Không có dữ liệu");
        }
        btnGoStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goStore = new Intent(NewsPromotionDetailActivity.this,MainActivity.class);
                startActivity(goStore);
            }
        });
    }
}
