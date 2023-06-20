package com.example.myapptrenlop.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.adapters.FilterAdapter;
import com.example.myapptrenlop.models.Filter;
import com.example.myapptrenlop.models.Product;

import java.util.ArrayList;
import java.util.List;


public class FilterFragment extends Fragment {

    RecyclerView rvLFilters;

    List<Filter> lFilters;
    List<Product> lProduct;
    ConstraintLayout clFitler;
    RecyclerView rvCardViewOrder;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;

    public FilterFragment() {
        // Required empty public constructor
        Log.d("constructor", "test");
    }


    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        onBindViewAdapter();
        InitData();
        setControl(view);
        addDataIntoView();
        return view;
    }
    private void InitData(){
        lProduct= new ArrayList<>();
    }
    private void addDataIntoView(){
        InitData();
    }
    private void onBindViewAdapter(){
        this.lFilters = new ArrayList<>();
        this.lFilters.add(new Filter("Đang xử lý"));
        this.lFilters.add(new Filter("Đang giao"));
        this.lFilters.add(new Filter("Đã giao"));
        this.lFilters.add(new Filter("Đã hủy"));

        if(this.lFilters.size() > 0){
            this.lFilters.get(0).setSelected(true);
        }
    }

    private void setControl(@NonNull View view){
        rvLFilters = (RecyclerView) view.findViewById(R.id.rvLFilters);
        rvLFilters.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        FilterAdapter filterAdapter = new FilterAdapter(lFilters);
        rvLFilters.setAdapter(filterAdapter);

//        rvCardViewOrder = view.findViewById(R.id.rvCardViewOrder);
//        rvCardViewOrder.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        OrderAdapter orderAdapter = new OrderAdapter(lProduct,context);
//        rvCardViewOrder.setAdapter(orderAdapter);
    }
}