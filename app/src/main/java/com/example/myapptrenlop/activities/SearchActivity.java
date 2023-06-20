package com.example.myapptrenlop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.adapters.ListProductAdapter;
import com.example.myapptrenlop.adapters.ListProductCategoryAdapter;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    ImageButton btnBack;
    SearchView svSearch;
    RecyclerView rcvSreach;
    ListProductCategoryAdapter listProductAdapter ;
    List<Product> productList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home);
        rcvSreach=findViewById(R.id.rcvSearch);
        btnBack = findViewById(R.id.btnBackSearch);
        svSearch=findViewById(R.id.svProduct);
        initData();
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                listProductAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listProductAdapter.getFilter().filter(s);
                return false;
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initData() {
//        rcvSreach.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
//        listProductAdapter = new ListProductCategoryAdapter(Constant.products, getApplicationContext());
//        rcvSreach.setAdapter(listProductAdapter);
//        RecyclerView.ItemDecoration itemDecoration =new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
//        rcvSreach.addItemDecoration(itemDecoration);
        ApiService.apiService.getProducts("").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                rcvSreach.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
                listProductAdapter = new ListProductCategoryAdapter(productList, getApplicationContext());
                rcvSreach.setAdapter(listProductAdapter);
                RecyclerView.ItemDecoration itemDecoration =new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
                rcvSreach.addItemDecoration(itemDecoration);
                System.out.println("Thêm list product search thành công");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println("Thêm list product vào search thất bại"+ t);
            }
        });
//        ApiRequest.getProducts(this, new ApiRequest.VolleyResponseListener<List<Product>>() {
//            @Override
//            public void onResponse(List<Product> response) {
//                // Xử lý danh sách sản phẩm và hiển thị trên giao diện của ứng dụng
//                productList = response;
//                rcvSreach.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
//                listProductAdapter = new ListProductCategoryAdapter(productList, getApplicationContext());
//                rcvSreach.setAdapter(listProductAdapter);
//                RecyclerView.ItemDecoration itemDecoration =new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
//                rcvSreach.addItemDecoration(itemDecoration);
//            }
//
//            @Override
//            public void onError(String message) {
//                // Xử lý lỗi và hiển thị thông báo lỗi trên giao diện của ứng dụng
//            }
//        });
//        int[] id=new int[10];
//        productList.add(new Product(1,"","gà","",10000,"",""
//        ,10,0,10,"",1,1,id));
//        productList.add(new Product(1,"","heo","",10000,"",""
//                ,10,0,10,"",1,1,id));
//        productList.add(new Product(1,"","cá","",10000,"",""
//                ,10,0,10,"",1,1,id));
//        productList.add(new Product(1,"","ngỗng","",10000,"",""
//                ,10,0,10,"",1,1,id));productList.add(new Product(1,"","gà","",10000,"",""
//                ,10,0,10,"",1,1,id));
//        productList.add(new Product(1,"","bit","",10000,"",""
//                ,10,0,10,"",1,1,id));
//        productList.add(new Product(1,"","gà","",10000,"",""
//                ,10,0,10,"",1,1,id));productList.add(new Product(1,"","gà","",10000,"",""
//                ,10,0,10,"",1,1,id));
//        rcvSreach.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
//        listProductAdapter = new ListProductCategoryAdapter(productList, getApplicationContext());
//                rcvSreach.setAdapter(listProductAdapter);
//                RecyclerView.ItemDecoration itemDecoration =new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
//                rcvSreach.addItemDecoration(itemDecoration);



    }

}