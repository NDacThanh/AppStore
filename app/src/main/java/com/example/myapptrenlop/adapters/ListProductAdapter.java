package com.example.myapptrenlop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.activities.ActivityRepairProduct;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ListProductViewHolder>
{
    List<Product> products;
    Context context ;
    public ListProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context=context;
    }
    @NonNull
    @Override
    public ListProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewProduct = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_listproduct,parent,false);
        ListProductViewHolder listProductViewHolder = new ListProductViewHolder(viewProduct);
        return listProductViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ListProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product prd = products.get(position);
        String sProductName = prd.getProduct_name();
        int dProductPrice = prd.getProduct_price();
        Integer iProductNumber = prd.getQuantity()-prd.getSold();
        holder.txtName.setText("Tên: "+ sProductName);
        holder.txtGia.setText(NumberFormat.getNumberInstance(Locale.US).format((int)dProductPrice) + " đ");
        holder.txtSoluongTon.setText("Số lượng tồn: "+iProductNumber);
        ////        CircleImageView imageView =new CircleImageView(context);
//        try
//        {
//            Picasso.with(context).load(prd.getImage()).into(holder.imageProduct);
//        }
//        catch (Exception e)
//        {
//
//        }
        if(prd.getImage()!=null && prd.getImage().isEmpty()==false) {
            Bitmap bitmap = Constant.loadImage(prd.getImage());
            holder.imageProduct.setImageBitmap(bitmap);
        }
        holder.product_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productIntent =new Intent(context, ActivityRepairProduct.class);
                productIntent.putExtra("ProductID",  prd.getProductID());
//                productIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivities(new Intent[]{productIntent});
            }
        });
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiRequest.deleteProduct(view.getContext(), prd.getProductID(), new ApiRequest.VolleyResponseListener<Product>() {
                    @Override
                    public void onResponse(Product response) {
                        Toast.makeText(view.getContext(),"Xoá thành công",Toast.LENGTH_SHORT).show();
                        products.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(view.getContext(),"Không thể xoá sản phẩm này vì đã có đơn hàng",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
    public  class ListProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imageProduct;
        TextView txtName,txtGia,txtSoluongTon;
        Button btnXoa;
        CardView product_item;
        public ListProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = (ImageView) itemView.findViewById(R.id.img_product);
            txtName = (TextView) itemView.findViewById(R.id.tvProductName);
            txtGia = (TextView) itemView.findViewById(R.id.tvProductPrice);
            txtSoluongTon = (TextView) itemView.findViewById(R.id.tvProductNumber);
            product_item = itemView.findViewById(R.id.product_item);
            btnXoa= itemView.findViewById(R.id.btnXoa);

        }
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }


}
