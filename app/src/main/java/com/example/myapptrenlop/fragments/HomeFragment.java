package com.example.myapptrenlop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.ActionBar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapptrenlop.R;
import com.example.myapptrenlop.activities.CategoryActivity;
import com.example.myapptrenlop.activities.SearchActivity;
import com.example.myapptrenlop.adapters.CategoriesAdapter;
import com.example.myapptrenlop.adapters.HomeProductAdapter;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.models.Category;
import com.example.myapptrenlop.models.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment {
    RecyclerView recycleViewCategories,recycleView1,recycleView2,recycleView3,recycleView4,recycleView5,recycleView6;
    Button btnXemthem1,btnXemthem2,btnXemthem3,btnXemthem4,btnXemthem5,btnXemthem6;
    NestedScrollView nsvHome;
    BottomNavigationView bottomNavigationView;
    Button searchView;
    ViewFlipper viewFlipper;
    List<Category> categories = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    List<Product> homeProducts1 = new ArrayList<>();
    List<Product> homeProducts2 =new ArrayList<>();
    List<Product> homeProducts3 = new ArrayList<>();
    List<Product> homeProducts4 = new ArrayList<>();
    List<Product> homeProducts5 = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int i=1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public HomeFragment() {
        // Required empty public constructor
        Log.d("constructor", "test");
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        CallApi();

        setControl(view);
        initData(view);
        actionViewFlipper();
        setEvent(view);
        nsvHome.setNestedScrollingEnabled(false);
        return view;
    }

    private void setEvent(View view) {

        btnXemthem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("CategoryID",i);
                startActivity(intent);
            }
        });
        btnXemthem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("CategoryID",i+1);
                startActivity(intent);
            }
        });
        btnXemthem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("CategoryID",i+2);
                startActivity(intent);
            }
        });
        btnXemthem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("CategoryID",i+3);
                startActivity(intent);
            }
        });
        btnXemthem6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("CategoryID",i+4);
                startActivity(intent);
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

    }
    private void actionViewFlipper() {
        ArrayList<String> quangcao = new ArrayList<>();
        quangcao.add("https://ss-hn.fptvds.vn/images/2022/online1_867x400.jpg");
        quangcao.add("https://cdn-crownx.winmart.vn/images/prod/ch%C4%83m%20s%C3%B3c%20c%C3%A1%20nh%C3%A2n_1180x400%20copy%202_05361143-5dea-47b1-bca8-d5b27de08bc5.jpg");
        quangcao.add("https://ss-hn.fptvds.vn/images/2022/main-banner-_trao-rau-cu-qua-sach-01.jpg");
        for (int i = 0; i < quangcao.size(); i++){
            ImageView imageView =new ImageView(getContext());
            Picasso.with(getContext()).load(quangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }
    private void CallApi(){

        ApiRequest.getCategorys(getContext(), new ApiRequest.VolleyResponseListener<List<Category>>() {
            @Override
            public void onResponse(List<Category> response) {
                categories=response;
                CategoriesAdapter categoriesAdapter = new CategoriesAdapter(categories, getContext());
                recycleViewCategories.setAdapter(categoriesAdapter);
            }

            @Override
            public void onError(String message) {
                System.out.println("Lỗi call API categorys");
            }
        });
        ApiRequest.getProductsCategory(getContext(), i, new ApiRequest.VolleyResponseListener<List<Product>>() {
            @Override
            public void onResponse(List<Product> response) {
                homeProducts1=response;
                HomeProductAdapter productAdapter1= new HomeProductAdapter(homeProducts1,getContext());
                recycleView2.setAdapter(productAdapter1);
            }

            @Override
            public void onError(String message) {
                System.out.println("Lỗi call API home product");
            }
        });
        ApiRequest.getProductsCategory(getContext(), i+1, new ApiRequest.VolleyResponseListener<List<Product>>() {
            @Override
            public void onResponse(List<Product> response) {
                homeProducts2=response;
                HomeProductAdapter productAdapter2= new HomeProductAdapter(homeProducts2,getContext());
                recycleView3.setAdapter(productAdapter2);
            }

            @Override
            public void onError(String message) {
                System.out.println("Lỗi call API home product");
            }
        });
        ApiRequest.getProductsCategory(getContext(), i+2, new ApiRequest.VolleyResponseListener<List<Product>>() {
            @Override
            public void onResponse(List<Product> response) {
                homeProducts3=response;
                HomeProductAdapter productAdapter3= new HomeProductAdapter(homeProducts3,getContext());
                recycleView4.setAdapter(productAdapter3);
            }

            @Override
            public void onError(String message) {
                System.out.println("Lỗi call API home product");
            }
        });
        ApiRequest.getProductsCategory(getContext(), i+3, new ApiRequest.VolleyResponseListener<List<Product>>() {
            @Override
            public void onResponse(List<Product> response) {
                homeProducts4=response;
                HomeProductAdapter productAdapter4= new HomeProductAdapter(homeProducts4,getContext());
                recycleView5.setAdapter(productAdapter4);
            }

            @Override
            public void onError(String message) {
                System.out.println("Lỗi call API home product");
            }
        });
        ApiRequest.getProductsCategory(getContext(), i+4, new ApiRequest.VolleyResponseListener<List<Product>>() {
            @Override
            public void onResponse(List<Product> response) {
                homeProducts5=response;
                HomeProductAdapter productAdapter5= new HomeProductAdapter(homeProducts5,getContext());
                recycleView6.setAdapter(productAdapter5);
            }

            @Override
            public void onError(String message) {
                System.out.println("Lỗi call API home product");
            }
        });

    }
    private void initData(View view) {


    }
    private void setControl(View view) {
        searchView= view.findViewById(R.id.svProduct);
        recycleViewCategories = (RecyclerView) view.findViewById(R.id.recycleViewListCategory);
//        recycleView1 =(RecyclerView) view.findViewById(R.id.recyclerView_home_1);
        recycleView2 =(RecyclerView) view.findViewById(R.id.recyclerView_home_2);
        recycleView3 =(RecyclerView) view.findViewById(R.id.recyclerView_home_3);
        recycleView4 =(RecyclerView) view.findViewById(R.id.recyclerView_home_4);
        recycleView5 =(RecyclerView) view.findViewById(R.id.recyclerView_home_5);
        recycleView6 =(RecyclerView) view.findViewById(R.id.recyclerView_home_6);
        btnXemthem2 = view.findViewById(R.id.btnXemThem2);
        btnXemthem3 = view.findViewById(R.id.btnXemThem3);
        btnXemthem4 = view.findViewById(R.id.btnXemthem4);
        btnXemthem5 = view.findViewById(R.id.btnXemthem5);
        btnXemthem6 = view.findViewById(R.id.btnXemthem6);
        nsvHome= view.findViewById(R.id.nsvHome);
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavigationView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recycleViewCategories.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 =new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recycleView2.setLayoutManager(layoutManager1);


        LinearLayoutManager layoutManager2 =new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recycleView3.setLayoutManager(layoutManager2);


        LinearLayoutManager layoutManager3 =new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recycleView4.setLayoutManager(layoutManager3);


        LinearLayoutManager layoutManager4 =new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recycleView5.setLayoutManager(layoutManager4);


        LinearLayoutManager layoutManager5 =new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recycleView6.setLayoutManager(layoutManager5);




        viewFlipper=view.findViewById(R.id.viewFlipper);
    }

}
