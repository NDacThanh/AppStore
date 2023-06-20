package com.example.myapptrenlop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassActivity extends AppCompatActivity {
    EditText edtGmail;
    Button btnXacNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        edtGmail =findViewById(R.id.edtForgotGmail);
        btnXacNhan = findViewById(R.id.btnForgotPass);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtGmail.getText().toString().trim().isEmpty()){
                    String email=edtGmail.getText().toString().trim();
                    email = email.replaceAll("@","%40");
                    System.out.println("Email:"+ email);
                    ApiService.apiService.forgotPass(email).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            System.out.println("Call API quên mật khẩu thành công");
                            Toast.makeText(ForgotPassActivity.this, "Tài khoản và mật khẩu đẵ gửi về email của bạn", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            System.out.println("Call API quên mật khẩu lỗi "+t);
                            Toast.makeText(ForgotPassActivity.this, "Email bạn nhập không đúng", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}