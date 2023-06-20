package com.example.myapptrenlop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.adapters.PaymentAdapter;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.Address;
import com.example.myapptrenlop.models.Cart;
import com.example.myapptrenlop.models.EventBus.TotalPayEvent;
import com.example.myapptrenlop.models.EventBus.TotalPriceEvent;
import com.example.myapptrenlop.models.Order;
import com.example.myapptrenlop.models.OrderDetail;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {

    ImageButton imageButton;
    RecyclerView rvCardViewOrder;
    TextView txtTamTinh,txtTongTien;
    Button btnThem,btnXacNhan;
    EditText edtDiaChi;
    List<Product> products;
    List<Cart> carts;
    Address address;
    List<OrderDetail> orderDetails = new ArrayList<>();
    int totalPrice;
    String payMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        setControl();
        addDataIntoView();
        ApiService.apiService.getAddressFromID(Constant.userCurrent.getAddressIds().get(0)).enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                        edtDiaChi.setText(response.body().getAddressSpecific()+", "+
                                response.body().getAddressGeneral());
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                System.out.println("Call Address theo id lỗi "+t);
            }
        });
    }
    private void TotalPay(){
        totalPrice=0;
        System.out.println("Mode  "+payMode);
        if(payMode.equals("Cart")) {
            for (int i = 0; i < Constant.payArray.size(); i++)
                totalPrice += Constant.payArray.get(i).getProduct_price() * Constant.payArray.get(i).getQuantity();

        }
        else if(payMode.equals("Detail")) {
            totalPrice=getIntent().getIntExtra("PayPrice",-1);
        }
        System.out.println("Total Pay: "+ totalPrice);
        txtTamTinh.setText(NumberFormat.getNumberInstance(Locale.US).format(totalPrice) + " đ");
        txtTongTien.setText(NumberFormat.getNumberInstance(Locale.US).format(totalPrice + 30000) + " đ");
    }
    private void InitData(){


       payMode = getIntent().getStringExtra("PayMode");
        if(payMode.equals("Cart")) {
            Constant.payArray.removeAll(Constant.payArray);
            Constant.payArray.addAll(Constant.cartArray);
        }
        else if(payMode.equals("Detail")){
            for (int i = 1; i < Constant.payArray.size(); i++)
                Constant.payArray.remove(i);
        }
//        ApiService.apiService.getAddressFromID(Constant.userCurrent.getAddress().getId()).enqueue(new Callback<Address>() {
//            @Override
//            public void onResponse(Call<Address> call, Response<Address> response) {
//                address=response.body();
//                edtDiaChi.setHint(address.getAddressSpecific()+ address.getAddressGeneral());
//            }
//
//            @Override
//            public void onFailure(Call<Address> call, Throwable t) {
//
//            }
//        });
//        edtDiaChi.setHint(Constant.userCurrent.getAddress().getAddressSpecific()+ Constant.userCurrent.getAddress().getAddressGeneral());
    }

    private void setControl(){
        rvCardViewOrder = findViewById(R.id.rvPayment);
        imageButton = findViewById(R.id.btnPaymentBack);
        txtTamTinh = findViewById(R.id.txtTamTinh);
        txtTongTien = findViewById(R.id.txtTongTien);
        edtDiaChi =findViewById(R.id.edtDiaChi);
        btnThem = findViewById(R.id.btnThem);
        btnXacNhan =findViewById(R.id.btnXacNhan);

    }
    private void setEvent() {
//        edtDiaChi.setText(Constant.userCurrent.getAddress().getAddressSpecific()+", "+
//                Constant.userCurrent.getAddress().getAddressGeneral());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TotalPay();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                finish();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<Constant.payArray.size();i++){
                    orderDetails.add(new OrderDetail(Constant.payArray.get(i).getProductID(),
                            Constant.payArray.get(i).getQuantity(),
                            Constant.payArray.get(i).getProduct_price()));
                }

                Order order =new Order(null,null,Constant.status.get(0),totalPrice,
                        "Thanh toán khi nhận hàng",Constant.userCurrent.getId(),orderDetails);
                ApiService.apiService.postOrder(order).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        Constant.cartArray.removeAll(Constant.payArray);
                        Toast.makeText(getApplicationContext(),"Thanh toán thành công",Toast.LENGTH_SHORT).show();
                        System.out.println("Thanh toán thành công");
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Đã xảy ra lỗi",Toast.LENGTH_SHORT).show();
                        System.out.println("Thanh toán thất bại. Lỗi "+ t);
                    }
                });

            }
        });
    }

    private void addDataIntoView(){
        InitData();
        rvCardViewOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        PaymentAdapter paymentAdapter = new PaymentAdapter(Constant.payArray,getApplicationContext());
        rvCardViewOrder.setAdapter(paymentAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        setEvent();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void evenTotalPay(TotalPayEvent event){
        if(event!=null){
            TotalPay();
        }
    }
}