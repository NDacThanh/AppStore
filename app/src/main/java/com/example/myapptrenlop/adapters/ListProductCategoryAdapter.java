package com.example.myapptrenlop.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class ListProductCategoryAdapter extends RecyclerView.Adapter<ListProductCategoryAdapter.ListProductViewHolder> implements Filterable
{
    List<Product> products;
    List<Product> productsOld;
    Context context ;
    public ListProductCategoryAdapter(List<Product> products,Context context) {
        this.products = products;
        this.productsOld =products;
        this.context=context;
    }
    @NonNull
    @Override
    public ListProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewProduct = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_product,parent,false);
        ListProductViewHolder listProductViewHolder = new ListProductViewHolder(viewProduct);
        return listProductViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ListProductViewHolder holder, int position) {
        Product prd = products.get(position);
        String sProductName = prd.getProduct_name();
        int dProductPrice = prd.getProduct_price();
        int discount = prd.getDiscount();
        String iProductNumber = prd.getProduct_description();
        dProductPrice= dProductPrice*(100-discount)/100;
        holder.txtName.setText(sProductName);
        holder.txtGia.setText(NumberFormat.getNumberInstance(Locale.US).format((int)dProductPrice) + " đ");
        holder.txtMota.setText(iProductNumber);
        ////        CircleImageView imageView =new CircleImageView(context);
//        if(prd.getImage()!="")
//        {
//            Picasso.with(context).load(prd.getImage()).into(holder.imageProduct);

        if(prd.getImage()!=null && prd.getImage().isEmpty()==false){
            Bitmap bitmap = Constant.loadImage(prd.getImage());
            holder.imageProduct.setImageBitmap(bitmap);
        }





        holder.product_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productIntent =new Intent(context,com.example.myapptrenlop.activities.ProductDetailActivity.class);
                productIntent.putExtra("ProductID",prd.getProductID());
                productIntent.putExtra("ProductIMG",prd.getImage());
                productIntent.putExtra("ProductName",prd.getProduct_name());
                productIntent.putExtra("ProductPrice",prd.getProduct_price());
                productIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivities(new Intent[]{productIntent});
            }
        });

    }



    public  class ListProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imageProduct;
        TextView txtName,txtGia,txtMota;
        CardView product_item;
        public ListProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = (ImageView) itemView.findViewById(R.id.img_product);
            txtName = (TextView) itemView.findViewById(R.id.txt_productName);
            txtGia = (TextView) itemView.findViewById(R.id.txt_productPrice);
            txtMota = (TextView) itemView.findViewById(R.id.txt_productMota);
            product_item = itemView.findViewById(R.id.product_item);

        }
    }

    @Override
    public int getItemCount() {
        if(products!=null)
        return products.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                List<Product> list= new ArrayList<>();
                if(strSearch.isEmpty()){
                    products=productsOld;
                }
                else {
                    for(Product product: productsOld){
                        if(product.getProduct_name().toLowerCase().contains(strSearch.toLowerCase() )){
                            list.add(product);
                        }
                    }
                    products=list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values =products;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                products.clear();
//                products.addAll((Collection<? extends Product>) filterResults.values);
                products= (List<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
