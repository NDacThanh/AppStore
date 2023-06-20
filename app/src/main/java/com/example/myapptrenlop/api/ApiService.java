package com.example.myapptrenlop.api;

import com.example.myapptrenlop.models.Address;
import com.example.myapptrenlop.models.Cart;
import com.example.myapptrenlop.models.CartApi;
import com.example.myapptrenlop.models.Category;
import com.example.myapptrenlop.models.Order;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    ApiService apiService = new Retrofit.Builder().baseUrl(ApiRequest.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);

    @POST("users/login")
    Call<User> login(
            @Query("name") String username,
            @Query("password") String password);
    @GET("users/")
    Call<List<User>> getUsers(@Query("") String query);
    @PUT("users/{id}")
    Call<User> updateUser(@Path("id")int id,@Body User user);
    @GET("orders/")
    Call<List<Order>> getFullOrder(@Query("") String query);
    @PUT("orders/{id}")
    Call<Order> updateOrder(@Path("id") int id,@Body Order order);
    @POST("orders")
    Call<Order> postOrder(@Body Order order);
    @GET("orders/user/{id}")
    Call<List<Order>> getOrder(@Path("id") int id);
    @GET("carts/{id}")
    Call<List<CartApi>> getCartsApi(@Path("id")int id);

    @GET("products")
    Call<List<Product>> getProducts(@Query("")String query);
    @POST("products")
    Call<Product> addProduct(@Body Product product);
    @PUT("products/{id}")
    Call<Product> updateProduct(@Path("id")int id,@Body Product product);
    @GET("products/{id}")
    Call<Product> getProductFromId(@Path("id")int id);
    @POST("users/mobile")
    Call<User> addUser(@Body User user);
    @POST("addresses")
    Call<Address> addAddress(@Body Address address);
    @GET("addresses/{id}")
    Call<Address> getAddressFromID(@Path("id")int id);
    @PUT("addresses/{id}")
    Call<Address> updateAddress(@Path("id")int id,@Body Address address);
    @FormUrlEncoded
    @POST("users/forgotpass?")
    Call<String> forgotPass(@Field("mail") String mail);

}
