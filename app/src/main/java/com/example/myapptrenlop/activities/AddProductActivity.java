package com.example.myapptrenlop.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
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
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.utils.Constant;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    ImageButton imageButton;
    EditText edtTen,edtGia,edtDonVi,edtMoTa,edtGiamGia,edtSoLuong,edtDacDiem;
    Button btnThem;
    Spinner spnDanhMuc,spnThuongHieu;
    ImageButton imageProduct;
    Product product;
    String pickitem = "";
    int ctgID;
    int brdID;
    String imgProduct;
    Map<Integer, String> spnCate= new HashMap<>();
    Map<Integer, String> spnBra= new HashMap<>();

    List<String> categoryName ;
    List<Integer>categoryID = new ArrayList<>();
    List<String> brandName ;
    List<Integer>brandID = new ArrayList<>();
    String image;
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
        setContentView(R.layout.activity_add_product);

        setControl();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddProductActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
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
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(edtDacDiem.getText().toString().isEmpty()||edtDonVi.getText().toString().isEmpty()||edtGia.getText().toString().isEmpty()||edtSoLuong.getText().toString().isEmpty()||
//                        edtGiamGia.getText().toString().isEmpty()||edtMoTa.getText().toString().isEmpty()||edtTen.getText().toString().isEmpty()) {
//                    Toast.makeText(AddProductActivity.this,"Hãy nhập đủ các trường",Toast.LENGTH_SHORT ).show();
//                }
                if(edtTen.getText().toString().isEmpty()){
                    edtTen.setError("Chưa nhập tên");
                }
                else if (edtGia.getText().toString().isEmpty()) {
                    edtGia.setError("Chưa nhập giá");
                }
                else if (edtSoLuong.getText().toString().isEmpty()) {
                    edtSoLuong.setError("Chưa nhập số lượng");
                }
                else if (edtDonVi.getText().toString().isEmpty()) {
                    edtDonVi.setError("Chưa nhập đơn vị");
                }
                else if (edtGiamGia.getText().toString().isEmpty()||Integer.parseInt(edtGiamGia.getText().toString())>100) {
                    edtGiamGia.setError("Giảm giá < 100");
                }
                else if (edtDacDiem.getText().toString().isEmpty()) {
                    edtGia.setError("Chưa nhập đặc điểm");
                }
                else if (edtMoTa.getText().toString().isEmpty()) {
                    edtMoTa.setError("Chưa nhập đặc điểm");
                }
                else {
                    try{
                        System.out.println("ID"+brdID+"\n CateID"+ctgID);
                        int[]imageIDs =null;

                        product = new Product(null,edtMoTa.getText().toString(),edtTen.getText().toString(),"1",Integer.parseInt(edtGia.getText().toString()) ,
                                edtDacDiem.getText().toString(),edtDonVi.getText().toString(),Integer.parseInt(edtGiamGia.getText().toString()),0,Integer.parseInt(edtSoLuong.getText().toString()),image, brdID,ctgID,imageIDs);
                        System.out.println(product.toString());
//                        ApiRequest.addProduct(getApplicationContext(), product, new ApiRequest.VolleyResponseListener<Product>() {
//                            @Override
//                            public void onResponse(Product response) {
//                                System.out.println("Đã thêm sản phẩm");
//                                Toast.makeText(AddProductActivity.this,"Đã thêm",Toast.LENGTH_SHORT ).show();
//                                finish();
//                            }
//
//                            @Override
//                            public void onError(String message) {
//                                System.out.println("Lỗi thêm sản phẩm");
//                                Toast.makeText(AddProductActivity.this,"Lỗi thêm sản phẩm",Toast.LENGTH_SHORT ).show();
//                            }
//                        });
                        ApiService.apiService.addProduct(product).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                System.out.println("Đã thêm sản phẩm");
                                Toast.makeText(AddProductActivity.this,"Đã thêm",Toast.LENGTH_SHORT ).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable t) {
                                System.out.println("Lỗi thêm sản phẩm. Lỗi "+t);
                                Toast.makeText(AddProductActivity.this,"Lỗi thêm sản phẩm",Toast.LENGTH_SHORT ).show();
                            }
                        });

                    }catch (Exception e)
                    {
                        return;
                    }
                }

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
            image= Constant.encodeToBase64(url);
        }
        catch (Exception e){

        }

//        Constant.BASE64=image;
//        System.out.println("image :"+ image);
//        image ="data:image/"+style+";base64,"+image;
//        System.out.println("image :"+ image);
    }

    private void setControl() {
        imageButton = findViewById(R.id.btn_add_back);
        spnDanhMuc = findViewById(R.id.spn_danhmuc);
        edtTen = findViewById(R.id.edt_tensp);
        edtGiamGia = findViewById(R.id.edt_giamgia);
        imageProduct = findViewById(R.id.image_add);
        edtGia = findViewById(R.id.edt_giatien);
        edtMoTa= findViewById(R.id.edt_mota);
        edtDonVi= findViewById(R.id.edt_donvi);
        spnThuongHieu = findViewById(R.id.spn_thuonghieu);
        btnThem = findViewById(R.id.btn_them);
        edtSoLuong= findViewById(R.id.edt_soluong);
        edtDacDiem=findViewById(R.id.edt_dacdiem);
    }
}
