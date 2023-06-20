package com.example.myapptrenlop.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.adapters.ListProductAdapter;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityListProduct extends AppCompatActivity {

    ImageButton btnBack;
    RecyclerView recyclerView;

    List<Product> productList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiService.apiService.getProducts("").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
//                Constant.products = productList;
                setContentView(R.layout.activity_list_product);
                setControl();
                setEvent();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
//        ApiRequest.getProducts(this, new ApiRequest.VolleyResponseListener<List<Product>>() {
//            @Override
//            public void onResponse(List<Product> response) {
//                // Xử lý danh sách sản phẩm và hiển thị trên giao diện của ứng dụng
//                productList = response;
////                Constant.products = productList;
//                setContentView(R.layout.activity_list_product);
//                setControl();
//                setEvent();
//            }
//
//            @Override
//            public void onError(String message) {
//                // Xử lý lỗi và hiển thị thông báo lỗi trên giao diện của ứng dụng
//            }
//        });

    }

    private void setEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setControl() {
        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.rvListProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ListProductAdapter listProductAdapter = new ListProductAdapter(productList, this);
        recyclerView.setAdapter(listProductAdapter);
    }
}