package com.example.myapptrenlop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.adapters.ListProductAdapter;
import com.example.myapptrenlop.adapters.ListProductCategoryAdapter;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.models.Category;
import com.example.myapptrenlop.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    Toolbar tbCategory;
    RecyclerView recyclerView;
    int categoryID;
    Category category;
    List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_activity);
//        FragmentManager fragmentManager = getSupportFragmentManager();

        initData();
        setControl();

    }

    private void setEvent() {

        ListProductCategoryAdapter listProductAdapter = new ListProductCategoryAdapter(products,this);
        recyclerView.setAdapter(listProductAdapter);
        ActionToolBar();
    }

    private void ActionToolBar() {
        setSupportActionBar(tbCategory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setControl() {
        recyclerView =(RecyclerView) findViewById(R.id.rvCardViewCategory);
        tbCategory = findViewById(R.id.tbCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    private void initData() {
        categoryID = getIntent().getIntExtra("CategoryID", -1);
        ApiRequest.getProductsCategory(this, categoryID, new ApiRequest.VolleyResponseListener<List<Product>>() {
            @Override
            public void onResponse(List<Product> response) {
                products=response;
                setEvent();
            }

            @Override
            public void onError(String message) {
                System.out.println("Call API product theo category fail");
            }
        });
        ApiRequest.getCategory(this, categoryID, new ApiRequest.VolleyResponseListener<Category>() {
            @Override
            public void onResponse(Category response) {
                category=response;
                tbCategory.setTitle(category.getCategoryName());
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}