package com.example.myapptrenlop.fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.adapters.OrderAdapter;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.Order;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ConstraintLayout clFitler;
    ImageButton imageButton;
    Button btnDXL,btnDG,btnYCH,btnDgG,btnDH;
    RecyclerView rvCardViewOrder;
    List<Product> lProduct;
    List<Order> orders;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        InitData();
        setControl(view);
        return view;
    }

    private List<Order> getOrderFromPayment(List<Order> orders,String status){
        List<Order> newOrders= new ArrayList<>();
        if (orders==null)
            return null;
        for(int i=0;i<orders.size();i++)
        {
            if (orders.get(i).getStatus().equals(status))
                newOrders.add(orders.get(i));
        }
        return newOrders;
    }
    private void setControl(View view) {
//        clFitler = view.findViewById(R.id.clFilter);
        rvCardViewOrder = view.findViewById(R.id.rvCardViewOrder);
//        imageButton =view.findViewById(R.id.btnOderBack);
        btnDXL= view.findViewById(R.id.btnDXL);
        btnYCH= view.findViewById(R.id.btnYCH);
        btnDgG=view.findViewById(R.id.btnDgG);
        btnDG= view.findViewById(R.id.btnDG);
        btnDH= view.findViewById(R.id.btnDH);
//        FragmentManager fragmentManager = getParentFragmentManager();
//        FilterFragment filterFragment = new FilterFragment();
//        fragmentManager.beginTransaction().replace(R.id.clFilter, filterFragment).commit();
        FragmentManagement();
        rvCardViewOrder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        OrderAdapter orderAdapter = new OrderAdapter(lProduct,getContext());
//        rvCardViewOrder.setAdapter(orderAdapter);


    }

    private void FragmentManagement() {
    }

    private void InitData() {
        lProduct= new ArrayList<>();
        System.out.println("ID "+Constant.userCurrent.getId());
        ApiService.apiService.getOrder(Constant.userCurrent.getId()).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                System.out.println("body "+ response.body().size());
                orders=response.body();
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                System.out.println("Call API order theo userId lỗi");
            }
        });
//        ApiRequest.getOrdersFromUserId(getContext(), Constant.userCurrent.getId(), new ApiRequest.VolleyResponseListener<List<Order>>() {
//            @Override
//            public void onResponse(List<Order> response) {
//                orders=response;
//            }
//
//            @Override
//            public void onError(String message) {
//                System.out.println("Call API order theo userId lỗi"+ message);
//            }
//        });

    }
    private void setEvent(){
        btnDXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDXL.setFocusable(true);
                btnDXL.setFocusableInTouchMode(true);
                btnDXL.requestFocus();
                btnDXL.setPressed(true);
                btnDXL.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                btnYCH.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDG.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDgG.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDH.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                OrderAdapter orderAdapter = new OrderAdapter(getOrderFromPayment(orders,Constant.status.get(0)),getContext());
                rvCardViewOrder.setAdapter(orderAdapter);
            }
        });
        btnYCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnYCH.setFocusable(true);
                btnYCH.setFocusableInTouchMode(true);
                btnYCH.requestFocus();
                btnYCH.setPressed(true);
                btnYCH.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                btnDXL.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDgG.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDG.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDH.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                OrderAdapter orderAdapter = new OrderAdapter(getOrderFromPayment(orders,Constant.status.get(1)),getContext());
                rvCardViewOrder.setAdapter(orderAdapter);
            }
        });
        btnDgG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDgG.setFocusable(true);
                btnDgG.setFocusableInTouchMode(true);
                btnDgG.requestFocus();
                btnDgG.setPressed(true);
                btnDgG.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                btnYCH.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDXL.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDG.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDH.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                OrderAdapter orderAdapter = new OrderAdapter(getOrderFromPayment(orders,Constant.status.get(2)),getContext());
                rvCardViewOrder.setAdapter(orderAdapter);
            }
        });
        btnDG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDG.setFocusable(true);
                btnDG.setFocusableInTouchMode(true);
                btnDG.requestFocus();
                btnDG.setPressed(true);
                btnDG.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                btnYCH.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDXL.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDgG.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDH.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                OrderAdapter orderAdapter = new OrderAdapter(getOrderFromPayment(orders,Constant.status.get(3)),getContext());
                rvCardViewOrder.setAdapter(orderAdapter);
            }
        });
        btnDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDH.setFocusable(true);
                btnDH.setFocusableInTouchMode(true);
                btnDH.requestFocus();
                btnDH.setPressed(true);
                btnDH.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                btnYCH.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDgG.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDG.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                btnDXL.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                OrderAdapter orderAdapter = new OrderAdapter(getOrderFromPayment(orders,Constant.status.get(4)),getContext());
                rvCardViewOrder.setAdapter(orderAdapter);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        setEvent();
    }
}