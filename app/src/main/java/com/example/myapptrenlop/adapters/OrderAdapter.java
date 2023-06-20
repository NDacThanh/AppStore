package com.example.myapptrenlop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.Order;
import com.example.myapptrenlop.models.OrderDetail;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    List<Order> orders;
    Context context;
    List<OrderDetail> orderDetails =new ArrayList<>();
    public OrderAdapter(List<Order> lProduct,Context context) {

        this.orders = lProduct;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_list_oder, parent, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(orderView);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        orderDetails= orders.get(position).getOrderDetailList();
        String sProductName =Constant.getName(orderDetails.get(0).getProductId());
        for (int i=1;i<orderDetails.size();i++){
            sProductName += "\n" +Constant.getName(orderDetails.get(i).getProductId());
        }
        int dProductPrice = orders.get(position).getTotalPrice();
        String iProductNumber = orders.get(position).getPaymentMode();
        String payMent = orders.get(position).getStatus().toString();
//        String imageProduct = orders.get(position).get();
        holder.tvProductName.setText(sProductName);
        holder.tvProductPrice.setText(NumberFormat.getNumberInstance(Locale.US).format( dProductPrice)+ " đ");
        holder.tvProductNumber.setText(iProductNumber);
        if(payMent.equals(Constant.status.get(0))){
            holder.btn.setText("Huỷ đơn hàng");
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String statusTemp= null;
                    if(payMent.equals(Constant.status.get(0))){
                        statusTemp=Constant.status.get(1);
                    }

                    Order order = new Order(orders.get(position).getId(),orders.get(position).getOrderDate(),Constant.status.get(1),
                            orders.get(position).getTotalPrice(), orders.get(position).getPaymentMode(),
                            orders.get(position).getUserId(),orderDetails);
                    System.out.println(order.toString());
                    ApiService.apiService.updateOrder(orders.get(position).getId(),order).enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            Toast.makeText(context, "Đã cập nhật đơn hàng", Toast.LENGTH_SHORT).show();
                            System.out.println("Đã cập nhật đơn hàng");
                            orders.remove(orders.get(position));
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {
                            System.out.println("Cập nhật đơn hàng lỗi :"+ t);
                        }
                    });
                }
            });
        }
        if(payMent.equals(Constant.status.get(1))){
            holder.btn.setVisibility(View.GONE);
        }
        if(payMent.equals(Constant.status.get(2))){
            holder.btn.setVisibility(View.GONE);
        }
        if(payMent.equals(Constant.status.get(3))){
            holder.btn.setVisibility(View.GONE);
//            holder.btn.setText("Đã nhận");
//            holder.btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    orders.remove(orders.get(position));
//                }
//            });
        }
        if(payMent.equals(Constant.status.get(4))){
            holder.btn.setVisibility(View.GONE);
        }
//        if(imageProduct!="")
//        {
//            Picasso.with(context).load(imageProduct).into(holder.img_product);
//        }
        if(orderDetails.isEmpty()==false){
            Bitmap bitmap = Constant.loadImage(Constant.getImage(orderDetails.get(0).getProductId()));
            holder.img_product.setImageBitmap(bitmap);
        }


    }

    @Override
    public int getItemCount() {
        if(orders==null)
            return 0;
        return this.orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView tvProductName;
        TextView tvProductPrice;
        TextView tvProductNumber;
        ImageView img_product;
        Button btn;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tvProductPrice);
            tvProductNumber = (TextView) itemView.findViewById(R.id.tvProductNumber);
            img_product = itemView.findViewById(R.id.img_product);
            btn= itemView.findViewById(R.id.huyhang);
        }
    }
}
