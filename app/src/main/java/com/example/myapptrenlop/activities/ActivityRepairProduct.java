package com.example.myapptrenlop.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.Category;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRepairProduct extends AppCompatActivity {
    ImageButton btnBack;
    EditText edtTen,edtGia,edtDonVi,edtMoTa,edtGiamGia,edtDacDiem;
    Button btnSua;
    Spinner spnDanhMuc,spnThuongHieu;
    ImageButton imageProduct;
    int ctgID,sold,quantity,brdID;

    Product product =null ;
    String imgProduct;
    Map<Integer, String> spnCate= new HashMap<>();
    Map<Integer, String> spnBra= new HashMap<>();

    List<String> categoryName ;
    List<Integer>categoryID = new ArrayList<>();
    List<String> brandName ;
    List<Integer>brandID = new ArrayList<>();
    int[]imageIDs =null;
    public static Integer getKeyByValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int prd= getIntent().getIntExtra("ProductID",-1);
        System.out.println("ProductID"+ prd);
        setContentView(R.layout.activity_repair_product);
        setControl();
        ApiRequest.getProduct(this, prd, new ApiRequest.VolleyResponseListener<Product>(){
            @Override
            public void onResponse(Product response) {
                product = response;
                edtTen.setHint(product.getProduct_name());
                edtGia.setHint(String.valueOf(product.getProduct_price()));
                edtDonVi.setHint(String.valueOf(product.getCalculation_unit()));
                edtGiamGia.setHint(String.valueOf(product.getDiscount()));
                edtDacDiem.setHint(product.getSpecificaion());
                try{
                    Bitmap bitmap = Constant.loadImage(product.getImage());
                    imageProduct.setImageBitmap(bitmap);
                    System.out.println(product.getImage());
                }catch (Exception e)
                {

                }

                edtMoTa.setHint(product.getProduct_description());
                ctgID= product.getCategory_id();
                brdID = product.getBrand_id();
                sold =product.getSold();
                quantity=product.getQuantity();
//                imgProduct=product.getImage();

            }
            @Override
            public void onError(String message) {

            }
        });
        ApiRequest.getMapIdName(this, "categories", new ApiRequest.VolleyResponseListener<Map<Integer, String>>() {
            @Override
            public void onResponse(Map<Integer, String> response) {
                spnCate= response;
                categoryName = new ArrayList<String>(spnCate.values()) ;
                for(int i=0;i<categoryName.size();i++){
                    categoryID.add(getKeyByValue(spnCate,categoryName.get(i)));
                }

                ArrayAdapter<String> adapterCatrgory = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, categoryName);
                adapterCatrgory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnDanhMuc.setAdapter(adapterCatrgory);
                spnDanhMuc.setSelection(adapterCatrgory.getPosition(spnCate.get(ctgID)));
            }

            @Override
            public void onError(String message) {

            }
        });
        ApiRequest.getMapIdName(this, "brands", new ApiRequest.VolleyResponseListener<Map<Integer, String>>() {
            @Override
            public void onResponse(Map<Integer, String> response) {
                spnBra= response;
                brandName = new ArrayList<String>(spnBra.values()) ;
                for(int i=0;i<brandName.size();i++){
                    brandID.add(getKeyByValue(spnBra,brandName.get(i)));
                }
                ArrayAdapter<String> adapterBrand = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, brandName);
                adapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnThuongHieu.setAdapter(adapterBrand);
                spnThuongHieu.setSelection(adapterBrand.getPosition(spnBra.get(brdID)));
            }

            @Override
            public void onError(String message) {

            }
        });


        spnDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ctgID =  categoryID.get((int)adapterView.getItemIdAtPosition(i));
                System.out.println("Spinner Category :"+ ctgID);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnThuongHieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                brdID= brandID.get((int)adapterView.getItemIdAtPosition(i));
                System.out.println("Spinner Brand :"+ brdID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ActivityRepairProduct.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            Uri uri = data.getData();
            String url =uri.getPath();
            imageProduct.setImageURI(uri);
            String style = url.substring(url.lastIndexOf(".")+1) ;
            System.out.println("URl "+ style);
            imgProduct= Constant.encodeToBase64(url);
        }catch (Exception e){

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTen.getText().toString().isEmpty()){
                    edtTen.setText(edtTen.getHint());
                }
                if (edtGia.getText().toString().isEmpty()) {
                    edtGia.setText(edtGia.getHint());
                }
                if (edtDonVi.getText().toString().isEmpty()) {
                    edtDonVi.setText(edtDonVi.getHint());
                }
                if (edtGiamGia.getText().toString().isEmpty()) {
                    edtGiamGia.setText(edtGiamGia.getHint());
                }
                if (edtDacDiem.getText().toString().isEmpty()) {
                    edtDacDiem.setText(edtDacDiem.getHint());
                }
                if (edtMoTa.getText().toString().isEmpty()) {
                    edtMoTa.setText(edtMoTa.getHint());
                }
                if (Integer.parseInt(edtGiamGia.getText().toString())>100) {
                    edtGiamGia.setError("Giảm giá < 100");
                }
                else {
                    try{
                        Product product1 = new Product(product.getProductID(),edtMoTa.getText().toString(),edtTen.getText().toString(),"1",Integer.parseInt(edtGia.getText().toString()) ,
                                edtDacDiem.getText().toString(),edtDonVi.getText().toString(),Integer.parseInt(edtGiamGia.getText().toString()),product.getSold(),product.getQuantity(),imgProduct, brdID,ctgID,product.getImageIds());
                        System.out.println(product1.toString());
//
                        ApiService.apiService.updateProduct(product1.getProductID(),product1).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                System.out.println("Đã update");
                                Toast.makeText(ActivityRepairProduct.this,"Đã update",Toast.LENGTH_SHORT ).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable t) {
                                System.out.println("Chưa update");
                                Toast.makeText(ActivityRepairProduct.this,"Chưa update",Toast.LENGTH_SHORT ).show();
                            }
                        });
                    }catch (Exception e){
                        return;
                    }
                }

            }
        });
    }

    private void setControl() {
        btnBack = findViewById(R.id.btn_add_back);
        spnDanhMuc = findViewById(R.id.spn_danhmuc);
        edtTen = findViewById(R.id.edt_tensp);
        edtGiamGia = findViewById(R.id.edt_giamgia);
        imageProduct = findViewById(R.id.image_add);
        edtGia = findViewById(R.id.edt_giatien);
        edtMoTa= findViewById(R.id.edt_mota);
        edtDonVi= findViewById(R.id.edt_donvi);
        spnThuongHieu = findViewById(R.id.spn_thuonghieu);
        btnSua= findViewById(R.id.btn_sua);
        edtDacDiem =findViewById(R.id.edt_dacdiem);
    }
}

