package com.example.myapptrenlop.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.activities.AccountManagerActivity;
import com.example.myapptrenlop.activities.ActivityListProduct;
import com.example.myapptrenlop.activities.AddProductActivity;
import com.example.myapptrenlop.activities.LoginActivity;
import com.example.myapptrenlop.activities.StatisticalActivity;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.User;
import com.example.myapptrenlop.utils.Constant;
import com.github.dhaval2404.imagepicker.ImagePicker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageButton imgAdmin;
    Button btnQLND,btnQLDSSP,btnThemSP,btnThongKe,btnDangXuat;
    TextView txtNameAdmin;
    public AdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminFragment newInstance(String param1, String param2) {
        AdminFragment fragment = new AdminFragment();
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
        View view= inflater.inflate(R.layout.activity_admin, container, false);
        setControl(view);
        return view;
    }


    private void setControl(@NonNull View view) {
        imgAdmin = view.findViewById(R.id.imgAdmin);
        btnQLND = view.findViewById(R.id.btnQLND);
        btnQLDSSP = view.findViewById(R.id.btnQLDSSP);
        btnThemSP = view.findViewById(R.id.btnThemSP);
        btnThongKe = view.findViewById(R.id.btnThongKe);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        txtNameAdmin = view.findViewById(R.id.txtNameAdmin);
        try{
            Bitmap bitmap = Constant.loadImage(Constant.userCurrent.getImageBase64());
            imgAdmin.setImageBitmap(bitmap);
        }
        catch (Exception e){

        }
        txtNameAdmin.setText(Constant.userCurrent.getFirstName()+" "+Constant.userCurrent.getLastName());
        imgAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AdminFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });
        btnQLND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AccountManagerActivity.class);
                startActivity(intent);
            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StatisticalActivity.class);
                startActivity(intent);
            }
        });
        btnQLDSSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityListProduct.class);
                startActivity(intent);
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.userCurrent=null;
                Intent intent= new Intent(getContext(), LoginActivity.class);
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
            imgAdmin.setImageURI(uri);
            String style = url.substring(url.lastIndexOf(".")+1) ;
            System.out.println("URl "+ style);
            String image= Constant.encodeToBase64(url);
            Bitmap bitmap = Constant.loadImage(image);
            imgAdmin.setImageBitmap(bitmap);
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
}
