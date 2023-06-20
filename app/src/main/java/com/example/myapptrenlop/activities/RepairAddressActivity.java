package com.example.myapptrenlop.activities;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepairAddressActivity extends AppCompatActivity {

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
    Button btnXacNhan;
    EditText edtDiaChi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_address);
        setControl();
        setEvent();
    }

    private void setEvent() {
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
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Address address= new Address(Constant.userCurrent.getAddressIds().get(0),xa+ ", "+huyen+", "+tinh,edtDiaChi.getText().toString(),Constant.userCurrent.getId());
                ApiService.apiService.updateAddress(Constant.userCurrent.getAddressIds().get(0),address).enqueue(new Callback<Address>() {
                    @Override
                    public void onResponse(Call<Address> call, Response<Address> response) {
                        System.out.println("Cập nhật địa chỉ thành công");
                        Toast.makeText(getApplicationContext(), "Cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Address> call, Throwable t) {
                        System.out.println("Cập nhật địa chỉ thất bại" +t);
                    }
                });
//                ApiService.apiService.up(address).enqueue(new Callback<Address>() {
//                    @Override
//                    public void onResponse(Call<Address> call, Response<Address> response) {
//                        List <Integer> addressList =null;
//                        addressList.add(new Integer(response.body().getId()));
//                        User user = new User(Constant.userCurrent.getId(),Constant.userCurrent.getName(),Constant.userCurrent.getFirstName(),
//                                Constant.userCurrent.getFirstName(),Constant.userCurrent.getLastName(),Constant.userCurrent.getImageId(),
//                                Constant.userCurrent.getEmail(),Constant.userCurrent.getPhone(),Constant.userCurrent.getCreatedAt(),
//                                Constant.userCurrent.getStatus(),Constant.userCurrent.getAddress(),Constant.userCurrent.getPermissionId(),
//                                addressList, Constant.userCurrent.getImageBase64());
//                        ApiService.apiService.updateUser(Constant.userCurrent.getId(),user).enqueue(new Callback<User>() {
//                            @Override
//                            public void onResponse(Call<User> call, Response<User> response) {
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<User> call, Throwable t) {
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onFailure(Call<Address> call, Throwable t) {
//
//                    }
//                });

            }
        });
    }

    private void setControl() {
        spnTinh =findViewById(R.id.spnTinh);
        spnHuyen=findViewById(R.id.spnHuyen);
        spnXa =findViewById(R.id.spnXa);
        btnXacNhan=findViewById(R.id.btnXacNhan);
        edtDiaChi =findViewById(R.id.edtDuong);
    }
}