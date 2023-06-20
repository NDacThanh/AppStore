package com.example.myapptrenlop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.Cart;
import com.example.myapptrenlop.models.Order;
import com.example.myapptrenlop.utils.Constant;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticalActivity extends AppCompatActivity {
    ImageButton imageButton;
    List<Order> orders= new ArrayList<>();
    int[] dem= new int[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);
        for(int i=0;i<5;i++)    dem[i]=0;
        PieChart pieChart =findViewById(R.id.pcOder);
        ApiService.apiService.getFullOrder("").enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orders=response.body();
                for(int i=0;i<orders.size();i++)
                {
                    if(orders.get(i).getPaymentMode().equals(Constant.status.get(0)))dem[0]+=1;
                    if(orders.get(i).getPaymentMode().equals(Constant.status.get(1)))dem[1]+=1;
                    if(orders.get(i).getPaymentMode().equals(Constant.status.get(2)))dem[2]+=1;
                    if(orders.get(i).getPaymentMode().equals(Constant.status.get(3)))dem[3]+=1;
                    if(orders.get(i).getPaymentMode().equals(Constant.status.get(4)))dem[4]+=1;
                }
                pieChart.addPieSlice(new PieModel(Constant.status.get(0),dem[0], Color.parseColor("#C51717")));
                pieChart.addPieSlice(new PieModel(Constant.status.get(1),dem[1], Color.parseColor("#1C8E20")));
                pieChart.addPieSlice(new PieModel(Constant.status.get(2),dem[2], Color.parseColor("#092DEC")));
                pieChart.addPieSlice(new PieModel(Constant.status.get(3),dem[3], Color.parseColor("#EC9A21")));
                pieChart.addPieSlice(new PieModel(Constant.status.get(4),dem[4], Color.parseColor("#631713")));

            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });



//        pieChart.setInnerValueColor(Color.BLACK);
//        pieChart.setInnerValueSize(16f);

        BarChart barChart = findViewById(R.id.bcOder);
        String color = "#CC1B1B";
        barChart.addBar(new BarModel("Ngày 1",20, Color.parseColor(color)));
        barChart.addBar(new BarModel("Ngày 2",15, Color.parseColor(color)));
        barChart.addBar(new BarModel("Ngày 3",40, Color.parseColor(color)));
        barChart.addBar(new BarModel("Ngày 4",10, Color.parseColor(color)));
        barChart.addBar(new BarModel("Ngày 5",0, Color.parseColor(color)));
        barChart.addBar(new BarModel("Ngày 6",14, Color.parseColor(color)));
        barChart.addBar(new BarModel("Ngày 7",23, Color.parseColor(color)));

        imageButton =findViewById(R.id.btn_TK_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}