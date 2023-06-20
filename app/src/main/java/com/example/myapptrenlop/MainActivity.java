package com.example.myapptrenlop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.myapptrenlop.api.ApiService;
import com.example.myapptrenlop.fragments.AdminFragment;
import com.example.myapptrenlop.fragments.CartFragment;
import com.example.myapptrenlop.fragments.HomeFragment;
import com.example.myapptrenlop.fragments.OrderFragment;
import com.example.myapptrenlop.fragments.UserFragment;
import com.example.myapptrenlop.models.Address;
import com.example.myapptrenlop.models.CartApi;
import com.example.myapptrenlop.utils.Constant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment fragmentHome = new HomeFragment();
    Fragment fragmentAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_activitya2);
        setControl();
        if(Constant.userCurrent.getPermissionId()==1) {
            loadFragment(new AdminFragment());
        }
        else {
            loadFragment(fragmentHome);
        }

        setEvent();
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void setControl(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

    }

    private void setEvent(){
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_order:
                        System.out.println("Chuyen man Oder");
                        Fragment fragmentOrder = new OrderFragment();
                        loadFragment(fragmentOrder);
                        break;
                    case R.id.navigation_cart:
                        System.out.println("Chuyen man Cart");
                        Fragment fragmentCart = new CartFragment();

                        loadFragment(fragmentCart);
                        break;
                    case R.id.navigation_home:
                        System.out.println("Chuyen man Home");
                        loadFragment(fragmentHome);

                        break;
                    case R.id.navigation_user:
                        System.out.println("Chuyen man admin");
                        if (Constant.userCurrent.getPermissionId() == 2) {
                            fragmentAdmin = new UserFragment();
                        } else {
                            fragmentAdmin = new AdminFragment();
                        }
                        loadFragment(fragmentAdmin);
                        break;
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
//        ApiService.apiService.getCartsApi(Constant.userCurrent.getId()).enqueue(new Callback<List<CartApi>>() {
//            @Override
//            public void onResponse(Call<List<CartApi>> call, Response<List<CartApi>> response) {
//                Constant.cartArray.addAll(response);
//            }
//
//            @Override
//            public void onFailure(Call<List<CartApi>> call, Throwable t) {
//
//            }
//        });
        ApiService.apiService.getAddressFromID(Constant.userCurrent.getAddressIds   ().get(0)).enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                Constant.userAddress =response.body();
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                System.out.println("Call Address theo id lỗi "+t);
            }
        });
    }
}