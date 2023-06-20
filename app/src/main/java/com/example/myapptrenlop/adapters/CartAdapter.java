package com.example.myapptrenlop.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.fragments.CartFragment;
import com.example.myapptrenlop.models.Cart;
import com.example.myapptrenlop.models.EventBus.TotalPriceEvent;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    List<Cart> carts;
    Context context;
    int tien=0;
    String sProductName;
    int dProductPrice;
    String image;
    public int getTien() {
        return tien;
    }
    public CartAdapter(List<Cart> carts, Context context) {
        this.carts = carts;
        this.context = context;
    }
    private Product initData(int id){
        Product product = null;
        ApiService.apiService.getProductFromId(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();


            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
        return product;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_list_cart, parent, false);
        CartViewHolder orderViewHolder = new CartViewHolder(orderView);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cart = carts.get(position);
//        Product product =initData(cart.getProductID());
        sProductName = cart.getProduct_name();
        dProductPrice = cart.getProduct_price();
        image = cart.getImage();
        holder.txtName.setText(sProductName);
        holder.txtGia.setText(NumberFormat.getNumberInstance(Locale.US).format(dProductPrice*carts.get(position).getQuantity())+ " đ");
        holder.txtSoluong.setText(String.valueOf(cart.getQuantity()));
        Bitmap bitmap = Constant.loadImage(image);
        holder.imgProduct.setImageBitmap(bitmap);
        for (int i=0;i<Constant.cartArray.size();i++)
            if(cart.getProductID()==Constant.cartArray.get(i).getProductID())
                holder.cbCheck.setChecked(true);//giữ nút check
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity =cart.getQuantity()+1 ;
                holder.txtSoluong.setText(String.valueOf(quantity));
                holder.txtGia.setText(NumberFormat.getNumberInstance(Locale.US).format(dProductPrice*quantity)+ " đ");
                carts.get(position).setQuantity(quantity);
                EventBus.getDefault().postSticky(new TotalPriceEvent());
            }
        });
        holder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity =cart.getQuantity() ;
                if(  quantity >=2){
                    quantity-=1;
                    holder.txtSoluong.setText(String.valueOf(quantity));
                    holder.txtGia.setText(NumberFormat.getNumberInstance(Locale.US).format(dProductPrice*quantity)+ " đ");
                    carts.get(position).setQuantity(quantity);
                    EventBus.getDefault().postSticky(new TotalPriceEvent());
                }
            }
        });
       holder.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b){
                   Constant.cartArray.add(cart);
                   EventBus.getDefault().postSticky(new TotalPriceEvent());
               }
               else {
                   for (int i=0; i<Constant.cartArray.size();i++)
                       if(Constant.cartArray.get(i).getProductID()==cart.getProductID())
                           Constant.cartArray.remove(i);
                   EventBus.getDefault().postSticky(new TotalPriceEvent());
               }
           }
       });
       holder.btnXoa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                       carts.remove(position);
                       notifyDataSetChanged();
                       for (int j=0; j<Constant.cartArray.size();j++)
                           if(Constant.cartArray.get(j).getProductID()==cart.getProductID())
                               Constant.cartArray.remove(j);
                       EventBus.getDefault().postSticky(new TotalPriceEvent());
                   }

       });
       holder.txtSoluong.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
               int quantity = Integer.parseInt(editable.toString());
               carts.get(position).setQuantity(quantity);
               EventBus.getDefault().postSticky(new TotalPriceEvent());
           }
       });
    }

    @Override
    public int getItemCount() {
        return this.carts.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        CheckBox cbCheck;
        ImageView imgProduct;
        TextView txtName,txtGia,txtSoluong;
        Button btnXoa;
        ImageButton btnSub,btnPlus;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCheck = (CheckBox) itemView.findViewById(R.id.cbCart);
            txtName = (TextView) itemView.findViewById(R.id.txtCartProductName);
            txtGia = (TextView) itemView.findViewById(R.id.txtCartProductPrice);
            txtSoluong = (TextView)itemView.findViewById(R.id.txtCartProductQuantity);
            imgProduct = itemView.findViewById(R.id.img_product);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnSub = itemView.findViewById(R.id.btnSub);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
