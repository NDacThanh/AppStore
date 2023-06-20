package com.example.myapptrenlop.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.activities.AddProductActivity;
import com.example.myapptrenlop.activities.LoginActivity;
import com.example.myapptrenlop.activities.RepairAddressActivity;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.Address;
import com.example.myapptrenlop.models.District;
import com.example.myapptrenlop.models.Province;
import com.example.myapptrenlop.models.User;
import com.example.myapptrenlop.models.Ward;
import com.example.myapptrenlop.utils.Constant;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.IOException;
import java.security.DigestException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btnSuaEmail,btnSuaSDT,btnSuaDiaChi,btnDangXuat,btnSuaThongTin,btnDoiMatKhau;
    TextView txtTenUser;
    List<String> provinceName = new ArrayList<>();
    List<Integer> provinceCode = new ArrayList<>();
    int prvCode;
    static List<Province> provinces=new ArrayList<>();
    ImageButton imgUser;

    List<String> districtName = new ArrayList<>();
    List<Integer> districtCode = new ArrayList<>();
    int disCode;
    static List<District> districts=new ArrayList<>();
    List<String> wardName = new ArrayList<>();
    List<Integer> wardCode = new ArrayList<>();
    int wadCode;
    static List<Ward> wards=new ArrayList<>();
    Spinner spnTinh,spnHuyen,spnXa;

    public UserFragment() {
        // Required empty public constructor
    }
    private void openDialog(String NameDialog){
        final Dialog dialog =new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_user);
        Window window= dialog.getWindow();
        if(window==null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity= Gravity.CENTER;
        window.setAttributes(windowAttributes);
        EditText edtDuLieuCu=dialog.findViewById(R.id.edtDuLieuCu);
        EditText edtDuLieuMoi= dialog.findViewById(R.id.edtDuLieuMoi);
        EditText edtPass =dialog.findViewById(R.id.edtPass);
        Button btnHuy=dialog.findViewById(R.id.btnHuy);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        TextView txtNameDialog= dialog.findViewById(R.id.txtNameDialog);
        if(NameDialog.equals("số điện thoại")||NameDialog.equals("email")){
            txtNameDialog.setText("Thay đổi "+NameDialog);
            edtDuLieuMoi.setHint("Nhập "+NameDialog +" mới");
            edtDuLieuCu.setHint("Nhập "+ NameDialog+" cũ");
        }else if(NameDialog.equals("sửa thông tin")){
            txtNameDialog.setText("Thay đổi thông tin");
            edtDuLieuMoi.setHint("Nhập tên");
            edtDuLieuCu.setHint("Nhập họ");
        }else if(NameDialog.equals("đổi passWord")){
            txtNameDialog.setText("Thay đổi mật khẩu");
            edtDuLieuMoi.setHint("Nhập lại mật khẩu mới");
            edtDuLieuCu.setHint("Nhập mật khẩu mới");
        }

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NameDialog.equals("số điện thoại")){
                    if(edtDuLieuCu.getText().toString().equals(Constant.userCurrent.getPhone())&&edtPass.getText().toString().equals(Constant.userCurrent.getPassword())){
                        Constant.userCurrent.setPhone(edtDuLieuMoi.getText().toString());
//                        try {
//                            Constant.userCurrent.setCreatedAt(Constant.formatDate(Constant.userCurrent.getCreatedAt()));
//                        } catch (ParseException e) {
//                            throw new RuntimeException(e);
//                        }
                        System.out.println(Constant.userCurrent.toString());
                        ApiService.apiService.updateUser(Constant.userCurrent.getId(),Constant.userCurrent).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                System.out.println("Thay đổi "+NameDialog +" thành công");
                                Toast.makeText(getContext(), "Thay đổi "+NameDialog +" thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                System.out.println("Thay đổi "+NameDialog +" thất bại. Lỗi "+t);
                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(), "Mật khẩu hoặc số điện thoại cũ không khớp", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(NameDialog.equals("email")){
                    System.out.println("Thay đổi "+NameDialog);
                    if(edtDuLieuCu.getText().toString().equals(Constant.userCurrent.getEmail())&&edtPass.getText().toString().equals(Constant.userCurrent.getPassword())){
                        Constant.userCurrent.setEmail(edtDuLieuMoi.getText().toString());
                        ApiService.apiService.updateUser(Constant.userCurrent.getId(),Constant.userCurrent).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Toast.makeText(getContext(), "Thay đổi "+NameDialog +" thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                System.out.println("Thay đổi "+NameDialog +" thất bại. Lỗi "+t);
                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(), "Mật khẩu hoặc email cũ không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(NameDialog.equals("sửa thông tin")){
                    if(edtDuLieuCu.getText().toString().isEmpty()==false&&edtDuLieuMoi.getText().toString().isEmpty()==false
                            &&edtPass.getText().toString().equals(Constant.userCurrent.getPassword())){
                        Constant.userCurrent.setFirstName(edtDuLieuCu.getText().toString());
                        Constant.userCurrent.setLastName(edtDuLieuMoi.getText().toString());
                        ApiService.apiService.updateUser(Constant.userCurrent.getId(),Constant.userCurrent).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Toast.makeText(getContext(), "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                System.out.println("Thay đổi thông tin thất bại. Lỗi "+t);
                            }
                        });
                    }
                    else{
                        Toast.makeText(getContext(), "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }

                }
                else if(NameDialog.equals("đổi passWord")){
                    if(edtPass.getText().toString().equals(Constant.userCurrent.getPassword())&&
                            edtDuLieuCu.getText().toString().equals(edtDuLieuMoi.getText().toString())){
                        Constant.userCurrent.setPassword(edtDuLieuMoi.getText().toString());
                        ApiService.apiService.updateUser(Constant.userCurrent.getId(),Constant.userCurrent).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Toast.makeText(getContext(), "Thay đổi mật khẩu thành công bạn hãy đăng nhập lại", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                System.out.println("Thay đổi mật khẩu thất bại. Lỗi "+t);
                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(), "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        dialog.show();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.activity_user, container, false);
        setControl(view);
        return view;
    }

    private void setControl(View view) {
        btnSuaEmail = view.findViewById(R.id.btnEmail);
        btnSuaDiaChi= view.findViewById(R.id.btnDiaChi);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        imgUser= view.findViewById(R.id.imgUser);
        btnSuaThongTin= view.findViewById(R.id.btnThongTin);
        btnDoiMatKhau =view.findViewById(R.id.btnDoiMatKhau);
        txtTenUser=view.findViewById(R.id.txtNameUser);
        btnSuaSDT=view.findViewById(R.id.btnSDT);
        String phone,email;
        phone = Constant.userCurrent.getPhone();
        email =Constant.userCurrent.getEmail();
        email= email.substring(0, 4) + "***********";
        phone ="******"+ phone.substring(phone.length()-4);
//        System.out.println("SDT"+ phone);
        btnSuaSDT.setText("SDT: "+phone);
        btnSuaEmail.setText("Email: "+email);
        btnSuaDiaChi.setText("Địa chỉ: "+ Constant.userAddress.getAddressSpecific()+", "+Constant.userAddress.getAddressGeneral());
        txtTenUser.setText(Constant.userCurrent.getFirstName()+" "+Constant.userCurrent.getLastName());
        try{
            Bitmap bitmap = Constant.loadImage(Constant.userCurrent.getImageBase64());
            imgUser.setImageBitmap(bitmap);
        }
        catch (Exception e){

        }
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(UserFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        btnSuaSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog("số điện thoại");
            }
        });
        btnSuaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog("email");
            }
        });
        btnSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog("sửa thông tin");
            }
        });
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog("đổi passWord");
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
        btnSuaDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialogAddress().show();
                Intent intent = new Intent(getContext(), RepairAddressActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Chạy picker");
        try{
            Uri uri = data.getData();
            String url =uri.getPath();
            imgUser.setImageURI(uri);
            String style = url.substring(url.lastIndexOf(".")+1) ;
            System.out.println("URl "+ style);
            String image= Constant.encodeToBase64(url);

                Bitmap bitmap = Constant.loadImage(image);
                imgUser.setImageBitmap(bitmap);

            Constant.userCurrent.setImageBase64(image);
            ApiService.apiService.updateUser(Constant.userCurrent.getId(),Constant.userCurrent).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    System.out.println("Update ảnh user thành công");
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println("Update ảnh user thất bại");
                }
            });
        }
        catch (Exception e){

        }
    }

    private Dialog dialogAddress(){
        final Dialog dialog =new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_address);
        Window window= dialog.getWindow();
        if(window==null)
            return null;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity= Gravity.CENTER;
        window.setAttributes(windowAttributes);
        Spinner spnTinh =dialog.findViewById(R.id.spnTinh);
        Spinner spnHuyen=dialog.findViewById(R.id.spnHuyen);
        Spinner spnXa =dialog.findViewById(R.id.spnXa);
        Button btnXacNhan=dialog.findViewById(R.id.btnXacNhan);
        EditText edtDiaChi =dialog.findViewById(R.id.edtDiaChi);
        spnTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prvCode =  provinceCode.get((int)adapterView.getItemIdAtPosition(i));
                System.out.println("Spinner Category :"+ prvCode);
                ApiRequest.getDistrits(getContext(), new ApiRequest.VolleyResponseListener<List<District>>() {
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
                        ArrayAdapter<String> adapterDistrict = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, districtName);
                        adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnHuyen.setAdapter(adapterDistrict);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(),"Call API Huyện lỗi",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                prvCode =  0;
                System.out.println("Spinner Category :"+ prvCode);
            }
        });
        spnHuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disCode =  districtCode.get((int)adapterView.getItemIdAtPosition(i));
                System.out.println("Spinner Category :"+ disCode);
                ApiRequest.getWards(getContext(), new ApiRequest.VolleyResponseListener<List<Ward>>() {
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
                        ArrayAdapter<String> adapterWard = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, wardName);
                        adapterWard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnXa.setAdapter(adapterWard);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(),"Call API Xã lỗi",Toast.LENGTH_SHORT).show();
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
                wadCode =  wardCode.get((int)adapterView.getItemIdAtPosition(i));
                System.out.println("Spinner Category :"+ wadCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xu li api
                Address address= new Address(null,wardName.get(wadCode)+", "+districtName.get(disCode)
                        +provinceName.get(prvCode),edtDiaChi.getText().toString(), Constant.userCurrent.getId());
                ApiService.apiService.addAddress(address).enqueue(new Callback<Address>() {
                    @Override
                    public void onResponse(Call<Address> call, Response<Address> response) {

                    }

                    @Override
                    public void onFailure(Call<Address> call, Throwable t) {

                    }
                });
            }
        });
        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
    }
}