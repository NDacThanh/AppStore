package com.example.myapptrenlop.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.activities.PayActivity;
import com.example.myapptrenlop.adapters.CartAdapter;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.Cart;
import com.example.myapptrenlop.models.CartApi;
import com.example.myapptrenlop.models.EventBus.TotalPayEvent;
import com.example.myapptrenlop.models.EventBus.TotalPriceEvent;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static List<Cart> carts = new ArrayList<>();
    static List<CartApi> cartApis= new ArrayList<>();
    String tmpImage,tmpName;
    RecyclerView rvCart;
    TextView txtTien;
    Button btnThanhToan;
    ProgressDialog progressDialog;
    Context context;
    int totalPrice ;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }
    private void TotalPrice(){
        totalPrice=0;
        for(int i=0;i< Constant.cartArray.size();i++)
            totalPrice+= Constant.cartArray.get(i).getProduct_price()*Constant.cartArray.get(i).getQuantity();
        txtTien.setText(NumberFormat.getNumberInstance(Locale.US).format(totalPrice)+" đ");
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        InitData();
        setControl(view);
        setEvent();
        return view;
    }

    private void setControl(@NonNull View view) {
        rvCart = view.findViewById(R.id.recycleViewListCategory);
        txtTien =view.findViewById(R.id.txtSumTotal);
        btnThanhToan=view.findViewById(R.id.btnThanhToan);

        rvCart.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        CartAdapter cartAdapter = new CartAdapter(Constant.cartArray, context);
        rvCart.setAdapter(cartAdapter);
        TotalPrice();
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(), PayActivity.class);
                intent.putExtra("PayMode","Cart");
                Constant.payArray.remove(Constant.payArray);
                Constant.payArray.addAll(Constant.cartArray);
                EventBus.getDefault().postSticky(new TotalPayEvent());
                startActivity(intent);
            }
        });




    }

    private void setEvent() {

    }

    private void InitData() {


//        Constant.cartArray.remove(Constant.cartArray);
//        carts.clear();
//        ApiService.apiService.getCartsApi(Constant.userCurrent.getId()).enqueue(new Callback<List<CartApi>>() {
//            @Override
//            public void onResponse(Call<List<CartApi>> call, Response<List<CartApi>> response) {
//                cartApis = response.body();
//                Product[] product =new Product[cartApis.size()];
//                product=callProduct(cartApis);
//                for (int i = 0; i < cartApis.size(); i++) {
//                    carts.add(new Cart(cartApis.get(i).getProductId(), product[i].getProduct_price()*(100-product[i].getDiscount())/100, product[i].getProduct_name(), cartApis.get(i).getQuantity(), product[i].getImage()));
//                    CartAdapter cartAdapter = new CartAdapter(carts, context);
//                    rvCart.setAdapter(cartAdapter);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<CartApi>> call, Throwable t) {
//                System.out.println("Lỗi call Carts theo userId");
//            }
//        });




    }
    private Product[] callProduct(List<CartApi> cartApis){
        final Product[] product = new Product[cartApis.size()];
        for (int i = 0; i < cartApis.size(); i++) {
            int finalI = i;
            ApiService.apiService.getProductFromId(cartApis.get(i).getProductId()).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product[finalI] = response.body();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }

        });
        }
        return product;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void evenTotalPrice(TotalPriceEvent event){
        if(event!=null){
            TotalPrice();
        }
    }
}