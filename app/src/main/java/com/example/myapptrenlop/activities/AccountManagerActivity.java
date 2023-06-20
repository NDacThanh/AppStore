package com.example.myapptrenlop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapptrenlop.MainActivity;
import com.example.myapptrenlop.R;
import com.example.myapptrenlop.adapters.UserAdapter;
import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.models.User;
import com.example.myapptrenlop.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountManagerActivity extends AppCompatActivity {

    ImageButton imageButton;
    List<User> users =new ArrayList<>();
    RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        imageButton = findViewById(R.id.btn_user_back);
        recyclerView= findViewById(R.id.rcvListUser);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ApiService.apiService.getUsers("").enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users=response.body();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                UserAdapter userAdapter = new UserAdapter(users, getApplicationContext());
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println("Lỗi call API users");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}