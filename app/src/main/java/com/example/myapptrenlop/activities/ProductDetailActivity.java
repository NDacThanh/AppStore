package com.example.myapptrenlop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.models.Brand;
import com.example.myapptrenlop.models.Cart;
import com.example.myapptrenlop.models.Category;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    int productID;
    Product product;
    Category category;
    Brand brand;
    ImageView imgProducDetail,imgUser;
    ImageButton btnBack;
    Button btnPay,btnAddToCart;
    int quantity;
    int price;

    TextView txt_ProductName,txt_giaThat,txt_giaAo,txt_DanhMuc,txt_DonVi,txt_ThuongHieu,txt_MoTa,txt_DacDiem,txtNameUser;
    SpannableStringBuilder spannable = new SpannableStringBuilder("Text is spantastic!");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dtail);
        initData();
        setControl();

    }
//    public Dialog onCreateDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        // Get the layout inflater
//        LayoutInflater inflater = this.getLayoutInflater();
//        builder.setView(inflater.inflate(R.layout.fragment_bottom_sheet_dialog, null));
//
//        return builder.create();
//    }
    private void setEvent() {
        quantity=1;
        txtNameUser.setText(Constant.userCurrent.getLastName());
        txt_ProductName.setText(product.getProduct_name());
        txt_giaAo.setText(NumberFormat.getNumberInstance(Locale.US).format(product.getProduct_price())+" đ");
        txt_giaAo.setPaintFlags(txt_giaAo.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        price = (int) product.getProduct_price()*(100-product.getDiscount())/100;
        if(product.getDiscount()==0){
            txt_giaAo.setVisibility(View.INVISIBLE);
            txt_giaThat.setText( NumberFormat.getNumberInstance(Locale.US).format(product.getProduct_price())+" đ");
        }
        else {
            txt_giaThat.setText(NumberFormat.getNumberInstance(Locale.US).format(price)+" đ");
        }
        txt_DacDiem.setText(product.getSpecificaion());

        txt_ThuongHieu.setText(String.valueOf(product.getBrand_id()));
        txt_MoTa.setText(product.getProduct_description());
//        if(product.getImage()!="")
//            Picasso.with(getApplicationContext()).load(product.getImage()).into(imgProducDetail);
        if(Constant.userCurrent.getImageBase64()!=null){
            Bitmap bitmap = Constant.loadImage(Constant.userCurrent.getImageBase64());
            imgUser.setImageBitmap(bitmap);
        }
        if(product.getImage()!=null) {
            Bitmap bitmap = Constant.loadImage(product.getImage());
            imgProducDetail.setImageBitmap(bitmap);
        }
        txt_DonVi.setText(product.getCalculation_unit());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog =new Dialog(ProductDetailActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.fragment_bottom_sheet_dialog);
                Window window= dialog.getWindow();
                if(window==null)
                    return;
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity= Gravity.BOTTOM;
                window.setAttributes(windowAttributes);
                ImageView imgProduct = dialog.findViewById(R.id.imgPayDetail);
                TextView txtPay=dialog.findViewById(R.id.txtPay);
                TextView txtPayReal= dialog.findViewById(R.id.txtPayReal);
                TextView txtQuantity =dialog.findViewById(R.id.txtQuantity);
                Button btnPay=dialog.findViewById(R.id.btnPay);
                ImageButton btnPlus = dialog.findViewById(R.id.btnPlus);
                ImageButton btnSub = dialog.findViewById(R.id.btnSub);
                ImageButton btnHuy = dialog.findViewById(R.id.btnBackPayDetail);
                TextView txtKho =dialog.findViewById(R.id.txtKho);

                Bitmap bitmap = Constant.loadImage(product.getImage());
                imgProduct.setImageBitmap(bitmap);
//                Picasso.with(getApplicationContext()).load(product.getImage()).into(imgProduct);
                txtPayReal.setPaintFlags(txtPayReal.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                txtKho.setText(product.getQuantity()-product.getSold() +"");
                if(product.getDiscount()==0){
                    txtPayReal.setVisibility(View.INVISIBLE);
                    txtPay.setText( NumberFormat.getNumberInstance(Locale.US).format(price)+" đ");
                }
                else {
                    txtPay.setText(NumberFormat.getNumberInstance(Locale.US).format(price)+" đ");
                    txtPayReal.setText(NumberFormat.getNumberInstance(Locale.US).format(product.getProduct_price())+" đ");
                }
                btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        quantity+=1;
                        txtQuantity.setText(quantity+"");

                    }
                });
                btnSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(quantity>=2){
                            quantity-=1;
                            txtQuantity.setText(quantity+"");
                        }
                    }
                });

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ProductDetailActivity.this,PayActivity.class);
                        Constant.payArray.add(0,new Cart(product.getProductID(),price,product.getProduct_name(),quantity,product.getImage()));
                        intent.putExtra("PayMode","Detail");
                        intent.putExtra("PayPrice",(int)price*quantity);
                        System.out.println("price*quantity " +price*quantity);
                        startActivities(new Intent[]{intent});
                    }
                });

                dialog.show();
            }
        });
        ApiRequest.getCategory(this, product.getProductID(), new ApiRequest.VolleyResponseListener<Category>() {
            @Override
            public void onResponse(Category response) {
                category=response;
                txt_DanhMuc.setText(category.getCategoryName());
            }

            @Override
            public void onError(String message) {
                System.out.println("Call API Category detail lỗi");
            }
        });
        ApiRequest.getBrand(this, product.getBrand_id(), new ApiRequest.VolleyResponseListener<Brand>() {
            @Override
            public void onResponse(Brand response) {
                brand=response;
                txt_ThuongHieu.setText(brand.getBrandName());
            }

            @Override
            public void onError(String message) {
                System.out.println("Call API Brand detail lỗi");
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.cartArray.add(new Cart(productID,price,product.getProduct_name(),1, product.getImage()));
                Toast.makeText(ProductDetailActivity.this,"Đã thêm vào giỏ hàng",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setControl() {
        imgProducDetail = findViewById(R.id.img_productDetail);
        txt_ProductName = findViewById(R.id.txt_tensp);
        txt_giaThat =findViewById(R.id.txt_giaDiscount);
        txt_giaAo =findViewById(R.id.txt_giasp);
        txt_DanhMuc = findViewById(R.id.txt_danhmuc);
        txt_DonVi =findViewById(R.id.txt_donvi);
        txt_ThuongHieu =findViewById(R.id.txt_thuonghieu);
        txt_MoTa =findViewById(R.id.txt_mota);
        btnBack = findViewById(R.id.btn_productDetailBack);
        btnPay = findViewById(R.id.btnPay);
        txt_DacDiem =findViewById(R.id.txt_dacdiem);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        txtNameUser= findViewById(R.id.currentuser_name);
        imgUser= findViewById(R.id.currentuser_comment);
    }

    private void initData() {
        productID=getIntent().getIntExtra("ProductID",-1);
        ApiRequest.getProduct(this, productID, new ApiRequest.VolleyResponseListener<Product>() {
            @Override
            public void onResponse(Product response) {
                product=response;
                setEvent();
            }
            @Override
            public void onError(String message) {
                System.out.println("Call API product detail lỗi");
            }
        });


    }
}