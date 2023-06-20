package com.example.myapptrenlop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.Address;
import com.example.myapptrenlop.models.District;
import com.example.myapptrenlop.models.Province;
import com.example.myapptrenlop.models.User;
import com.example.myapptrenlop.models.Ward;
import com.example.myapptrenlop.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class CreateAccountActivity extends AppCompatActivity {

    EditText edtTenDN,edtGmail,edtSDT,edtPass,edtPass2,edtHo,edtTen,edtDiaChi;
    Button btnDangKi;
    Spinner spnTinh,spnHuyen,spnXa;
    List<String> provinceName = new ArrayList<>();
    List<Integer> provinceCode = new ArrayList<>();
    int prvCode=0;
    static List<Province> provinces=new ArrayList<>();
    List<String> districtName = new ArrayList<>();
    List<Integer> districtCode = new ArrayList<>();
    int disCode=0;
    static List<District> districts=new ArrayList<>();
    List<String> wardName = new ArrayList<>();
    List<Integer> wardCode = new ArrayList<>();
    String tinh,huyen,xa;
    static List<Ward> wards=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_acount);
        setControl();
        ApiRequest.getProvinces(this, new ApiRequest.VolleyResponseListener<List<Province>>() {
            @Override
            public void onResponse(List<Province> response) {
                provinces=response;
                for(int i=0;i<provinces.size();i++){
                    provinceName.add(provinces.get(i).getName());
                    provinceCode.add(provinces.get(i).getCode());
                }
                ArrayAdapter<String> adapterProvince = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, provinceName);
                adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTinh.setAdapter(adapterProvince);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(),"Call API Tỉnh lỗi",Toast.LENGTH_SHORT).show();
            }
        });
        spnTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prvCode =  provinceCode.get((int)adapterView.getItemIdAtPosition(i));
                tinh = adapterView.getItemAtPosition(i).toString();
                System.out.println("Spinner Tỉnh :"+ tinh);
                ApiRequest.getDistrits(getApplicationContext(), new ApiRequest.VolleyResponseListener<List<District>>() {
                    @Override
                    public void onResponse(List<District> response) {
                        districtName.removeAll(districtName);
                        districtCode.removeAll(districtCode);
                        districts=response;
                        for(int i=0;i<districts.size();i++){
                            if(districts.get(i).getProvince_code()==prvCode)
                            {
                                districtName.add(districts.get(i).getName());
                                districtCode.add(districts.get(i).getCode());
                            }

                        }
                        ArrayAdapter<String> adapterDistrict = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, districtName);
                        adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnHuyen.setAdapter(adapterDistrict);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(),"Call API Huyện lỗi",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                prvCode =  0;
                System.out.println("Spinner Tỉnh :"+ prvCode);
            }
        });
        spnHuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disCode =  districtCode.get((int)adapterView.getItemIdAtPosition(i));
                huyen = adapterView.getItemAtPosition(i).toString();
                System.out.println("Spinner Huyện :"+ huyen);
                ApiRequest.getWards(getApplicationContext(), new ApiRequest.VolleyResponseListener<List<Ward>>() {
                    @Override
                    public void onResponse(List<Ward> response) {
                        wardName.removeAll(wardName);
                        wardCode.removeAll(wardCode);
                        wards=response;
                        for(int i=0;i<wards.size();i++){
                            if(wards.get(i).getDistrict_code()==disCode){
                                wardName.add(wards.get(i).getName());
                                wardCode.add(wards.get(i).getCode());
                            }

                        }
                        ArrayAdapter<String> adapterWard = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, wardName);
                        adapterWard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnXa.setAdapter(adapterWard);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(),"Call API Xã lỗi",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnXa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                xa = adapterView.getItemAtPosition(i).toString();

                System.out.println("Spinner Xã :"+ xa);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPass.getText().toString().equals(edtPass2.getText().toString())==false) {
                    Toast.makeText(CreateAccountActivity.this,"Pass phải giống nhau",Toast.LENGTH_SHORT ).show();
                    System.out.println("Pass phải giống nhau");
                }
                else if(edtTenDN.getText().toString().isEmpty()) {
                    edtTenDN.setError("Bạn chưa nhập tên đăng nhập");
                }
                else if(edtSDT.getText().toString().isEmpty()) {
                    edtSDT.setError("Bạn chưa nhập số điện thoại");
                }
                else if(edtGmail.getText().toString().isEmpty()) {
                    edtGmail.setError("Bạn chưa nhập gmail");
                }
                else if(edtDiaChi.getText().toString().isEmpty()) {
                    edtDiaChi.setError("Bạn chưa nhập địa chỉ");
                }
                else if(edtHo.getText().toString().isEmpty()) {
                    edtHo.setError("Bạn chưa nhập tên họ");
                }
                else if(edtTen.getText().toString().isEmpty()) {
                    edtTen.setError("Bạn chưa nhập tên ");
                }
                else {
                        Date date=java.util.Calendar.getInstance().getTime();
//                    Address address = (new Address(null,wardName.get(wadCode)+", "+districtName.get(disCode)
//                            +provinceName.get(prvCode),edtDiaChi.getText().toString(),null));
                    System.out.println("Địa chỉ chọn" + xa+", "+huyen+", "+tinh);
                        Address address = (new Address(null, xa+", "+huyen+", "+tinh,edtDiaChi.getText().toString(),null));
                        Gson gson = new Gson();
                        String json = gson.toJson(address);
                        List<Integer> addressIds =null;
                        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
                        User user = new User(null,edtTenDN.getText().toString(),edtPass.getText().toString(),
                                edtHo.getText().toString(),edtTen.getText().toString(),1,edtGmail.getText().toString(),
                                edtSDT.getText().toString(), date,"Active",address,2, addressIds,null);
                        ApiService.apiService.addUser(user).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User user1 =response.body();
                                Toast.makeText(CreateAccountActivity.this,"Đăng kí thành công",Toast.LENGTH_SHORT ).show();
                                System.out.println("Thêm User thành công");
//                                finish();
//                            System.out.println("Thêm Address thành công"+ addressIds.get(0));
//                            Calendar calendar = Calendar.getInstance();
//                            User user = new User(2,edtTenDN.getText().toString(),edtPass.getText().toString(),
//                                    edtHo.getText().toString(),edtTen.getText().toString(),1,edtGmail.getText().toString(),
//                                    edtSDT.getText().toString(),calendar.getTime(),"Active",addressIds,2, Constant.BASE64);
//                            ApiService.apiService.addUser( user).enqueue(new Callback<User>() {
//                                @Override
//                                public void onResponse(Call<User> call, Response<User> response) {
//                                    Toast.makeText(CreateAccountActivity.this,"Đăng kí thành công",Toast.LENGTH_SHORT ).show();
//                                    System.out.println("Thêm User thành công");
//                                }
//
//                                @Override
//                                public void onFailure(Call<User> call, Throwable t) {
//
//                                }
//                            });
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                System.out.println("Thêm User lỗi "+ t.toString());
                            }
                        });

                    }
                }

        });

    }




    private void setControl() {
        edtTenDN=findViewById(R.id.edtUserName);
        edtGmail =findViewById(R.id.edtGmail);
        edtSDT = findViewById(R.id.edtPhone);
        edtPass = findViewById(R.id.edtPass);
        edtPass2 = findViewById(R.id.edtPass2);
        edtHo = findViewById(R.id.edtFirstName);
        edtTen= findViewById(R.id.edtLastName);
        edtDiaChi =findViewById(R.id.edtDuong);
        btnDangKi=findViewById(R.id.btnDangKi);
        spnTinh =findViewById(R.id.spnTinh);
        spnHuyen=findViewById(R.id.spnHuyen);
        spnXa =findViewById(R.id.spnXa);

    }
}