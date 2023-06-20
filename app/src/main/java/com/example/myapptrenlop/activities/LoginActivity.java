package com.example.myapptrenlop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapptrenlop.MainActivity;
import com.example.myapptrenlop.R;
import com.example.myapptrenlop.api.ApiRequest;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.Address;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.models.User;
import com.example.myapptrenlop.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtLoginName,edtLoginPass;
    TextView txtForgotPass;
    List<User> users= new ArrayList<>();
    Button btnLogin,btnCreateAccout;
    public static boolean checkLogin = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
//        ApiRequest.getProducts(this, new ApiRequest.VolleyResponseListener<List<Product>>() {
//            @Override
//            public void onResponse(List<Product> response) {
//                // Xử lý danh sách sản phẩm và hiển thị trên giao diện của ứng dụng
//                Constant.products = response;
//                System.out.println("Thêm list product vào Constant thành công");
////                Constant.products = productList;
//            }
//
//            @Override
//            public void onError(String message) {
//                // Xử lý lỗi và hiển thị thông báo lỗi trên giao diện của ứng dụng
//                System.out.println("Thêm list product vào Constant thất bại"+ message);
//            }
//        });
        ApiService.apiService.getProducts("").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Constant.products = response.body();
                System.out.println("Thêm list product vào Constant thành công");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println("Thêm list product vào Constant thất bại"+ t);
            }
        });

    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtLoginName.getText().length()!=0&& edtLoginPass.getText().length()!=0){
                    String userName =edtLoginName.getText().toString();
                    String pass =edtLoginPass.getText().toString();
                    ApiService.apiService.getUsers("").enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            users=response.body();
                            for(int i=0;i<users.size();i++){
                                if(users.get(i).getName().equals(userName)&&users.get(i).getPassword().equals(pass)){
                                    Constant.userCurrent=users.get(i);
                                    Toast.makeText(LoginActivity.this,
                                            "Xin chào "+Constant.userCurrent.getFirstName()+" "+Constant.userCurrent.getLastName()
                                            ,Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    return;
                                }
                                if(i==users.size()-1){
                                    Toast.makeText(LoginActivity.this,
                                            "Bạn nhập sai tên tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                                }
                            }



                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            System.out.println("Lỗi call API users");
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this,"Bạn phải nhập đủ tài khoản và mật khẩu",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnCreateAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,CreateAccountActivity.class);
                startActivity(intent);
            }
        });
        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,ForgotPassActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setControl() {
        edtLoginName = findViewById(R.id.edtUserName);
        edtLoginPass =findViewById(R.id.edtPass);
        txtForgotPass=findViewById(R.id.txtForgotPass);
        btnLogin =findViewById(R.id.btnLogin);
        btnCreateAccout=findViewById(R.id.btnCreateAcc);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setEvent();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
