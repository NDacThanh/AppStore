package com.example.myapptrenlop.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.HomeProductViewHolder>{

    List<Product> homeProducts;

    Context context ;

    public HomeProductAdapter(List<Product> homeProducts, Context context){
        this.homeProducts =homeProducts;
        this.context =context;
    }


    @NonNull
    @Override
    public HomeProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHomeProduct = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_tem_category,parent,false);
        HomeProductViewHolder homeProductViewHolder = new HomeProductViewHolder(viewHomeProduct);
        return homeProductViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductViewHolder holder, int position) {
        Product hprd= homeProducts.get(position);
        holder.txtName.setText(hprd.getProduct_name());
        System.out.println(hprd.getProduct_name());
        if(hprd.getDiscount()==0){
            holder.txtPrice.setVisibility(View.INVISIBLE);
            holder.txtPriceDiscount.setText(NumberFormat.getNumberInstance(Locale.US).format(hprd.getProduct_price())+"đ");

        }
        else {
            int price = (int) Math.ceil(hprd.getProduct_price()*(100-hprd.getDiscount())/100);
            holder.txtPrice.setText(0-hprd.getDiscount()+"%");
            holder.txtPriceDiscount.setText(NumberFormat.getNumberInstance(Locale.US).format(price)+"đ");
        }
//        try {
//            Picasso.with(context).load(hprd.getImage()).into(holder.imageView);
//        }catch (Exception e){
//
//        };url.substring(url.lastIndexOf(".")+1)

        Bitmap bitmap = Constant.loadImage(hprd.getImage());
        holder.imageView.setImageBitmap(bitmap);


        holder.homeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productIntent =new Intent(context,com.example.myapptrenlop.activities.ProductDetailActivity.class);
                productIntent.putExtra("ProductID",hprd.getProductID());
//                productIntent.putExtra("ProductIMG",hprd.getImage());
//                productIntent.putExtra("ProductName",hprd.getProduct_name());
//                productIntent.putExtra("ProductPrice",hprd.getProduct_price());
                productIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivities(new Intent[]{productIntent});
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.homeProducts.size();
    }

    public class HomeProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtPriceDiscount,txtPrice,txtName;
        Button btnMua;
        CardView homeProduct;
        public HomeProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgHomeProduct);
            txtName = itemView.findViewById(R.id.txtHomeProductName);
            txtPriceDiscount= itemView.findViewById(R.id.txtHomeProductPrice);
            txtPrice = itemView.findViewById(R.id.txtHomeProductPrice2);
//            btnMua = itemView.findViewById(R.id.btnHomeProduct);
            homeProduct = itemView.findViewById(R.id.homeProduct);

        }
    }
}
