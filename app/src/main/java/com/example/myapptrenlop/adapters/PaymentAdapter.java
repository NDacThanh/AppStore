package com.example.myapptrenlop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.models.Cart;
import com.example.myapptrenlop.models.EventBus.TotalPayEvent;
import com.example.myapptrenlop.models.EventBus.TotalPriceEvent;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>{

    List<Cart> carts;
    Context context;
    public PaymentAdapter(List<Cart> carts,Context context) {

        this.carts = carts;
        this.context =context;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_list_payment, parent, false);
        PaymentViewHolder orderViewHolder = new PaymentViewHolder(orderView);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NumberFormat formatter = new DecimalFormat("#0");
        String sProductName = carts.get(position).getProduct_name();
        int dProductPrice = carts.get(position).getProduct_price();
        String imageProduct =carts.get(position).getImage();
        String iProductNumber = String.valueOf(carts.get(position).getQuantity());
        holder.tvProductName.setText(sProductName);
        holder.tvProductPrice.setText("Giá: "+NumberFormat.getNumberInstance(Locale.US).format( dProductPrice*carts.get(position).getQuantity()) + "đ");
        holder.tvProductNumber.setText(String.valueOf(iProductNumber));
//        if(imageProduct!="")
//        {
//            Picasso.with(context).load(imageProduct).resize(68,53).into();
//        }
        Bitmap bitmap = Constant.loadImage(imageProduct);
        holder.imgProduct.setImageBitmap(bitmap);

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                for (int i=0;i<Constant.payArray.size();i++)
//                {
//                    if(Constant.payArray.get(i).getProductID()==products.get(position).getProductID())
//                        Constant.payArray.remove(i);
//                }
                carts.remove(position);
                notifyDataSetChanged();
                EventBus.getDefault().postSticky(new TotalPayEvent());
            }

        });
//        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int quantity =products.get(position).getQuantity()+1 ;
//                holder.tvProductNumber.setText(String.valueOf(quantity));
//                products.get(position).setQuantity(quantity);
//                holder.tvProductPrice.setText(NumberFormat.getNumberInstance(Locale.US).format( dProductPrice*products.get(position).getQuantity()) + "đ");
//                EventBus.getDefault().postSticky(new TotalPayEvent());
//            }
//        });
//        holder.btnSub.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int quantity =products.get(position).getQuantity();
//                if(  quantity >=2){
//                    quantity-=1;
//                    holder.tvProductNumber.setText(String.valueOf(quantity));
//                    products.get(position).setQuantity(quantity);
//                    holder.tvProductPrice.setText(NumberFormat.getNumberInstance(Locale.US).format( dProductPrice*products.get(position).getQuantity()) + "đ");
//                    EventBus.getDefault().postSticky(new TotalPayEvent());
//                }
//            }
//        });
//        holder.tvProductNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                int quantity = Integer.parseInt(editable.toString());
//                products.get(position).setQuantity(quantity);
//                holder.tvProductPrice.setText(NumberFormat.getNumberInstance(Locale.US).format( dProductPrice*products.get(position).getQuantity()) + "đ");
//                EventBus.getDefault().postSticky(new TotalPayEvent());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return this.carts.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView tvProductName;
        TextView tvProductPrice;
        TextView tvProductNumber;
        ImageButton btnXoa,btnPlus,btnSub;
        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvProductPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            tvProductNumber = (TextView) itemView.findViewById(R.id.txtQuantity);
            imgProduct = (ImageView) itemView.findViewById(R.id.img_product_payment);
            btnXoa = itemView.findViewById(R.id.btnDelete);
//            btnPlus = itemView.findViewById(R.id.btnPlus);
//            btnSub = itemView.findViewById(R.id.btnSub);

        }
    }
}
