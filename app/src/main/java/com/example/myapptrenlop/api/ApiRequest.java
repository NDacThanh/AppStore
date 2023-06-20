package com.example.myapptrenlop.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapptrenlop.models.Address;
import com.example.myapptrenlop.models.Brand;
import com.example.myapptrenlop.models.Category;
import com.example.myapptrenlop.models.District;
import com.example.myapptrenlop.models.Order;
import com.example.myapptrenlop.models.OrderDetail;
import com.example.myapptrenlop.models.Product;
import com.example.myapptrenlop.models.Province;
import com.example.myapptrenlop.models.User;
import com.example.myapptrenlop.models.Ward;
import com.example.myapptrenlop.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiRequest {
    public static final String BASE_URL = "http://192.168.2.21:8080/";
    public static final String GET_PRODUCTS_URL = BASE_URL + "products";
    public static final String GET_PRODUCT_URL = BASE_URL + "products/";

    public static final String POST_PRODUCT_URL = BASE_URL + "products";
    public static final String PUT_PRODUCT_URL = BASE_URL + "products/";
    public static final String DELETE_PRODUCT_URL = BASE_URL + "products/";
    public static final String GET_BRANDS_URL = BASE_URL + "brands";
    public static final String GET_BRAND_URL = BASE_URL + "brands/";
    public static final String GET_CATEGORIES_URL = BASE_URL + "categories";
    public static final String GET_CATEGORIE_URL = BASE_URL + "categories/";
    public static final String POST_USER_LOGIN = BASE_URL +"users/login?name=";
    public static final String POST_USER_URL =BASE_URL +"users/";
    public static final String POST_ADDRESS_URL = BASE_URL +"addresses";
    public static final String GET_ORDERS_URL = BASE_URL +"orders/user/";
    public static final String BASE_URL_PROVINCE ="https://provinces.open-api.vn/api/";
    public static final String GET_PROVINCES = "p/";
    public static final String GET_DISTRICTS = "d/";
    public static final String GET_WARDS = "w/";


    public interface VolleyResponseListener<T> {
        void onResponse(T response);
        void onError(String message);
    }

    public static Product parseProductFromJson (JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String productDescription = jsonObject.getString("productDescription");
        String productName = jsonObject.getString("productName");
//        String status = jsonObject.getString("status");
        int price = jsonObject.getInt("price");

        String specification = jsonObject.getString("specification");
        String calculationUnit = jsonObject.getString("calculationUnit");

        int discount = jsonObject.getInt("discount");
        int sold = jsonObject.getInt("sold");
        int quantity = jsonObject.getInt("quantity");
        String image = jsonObject.getString("imageBase64");
        int brandId = jsonObject.getInt("brandId");
        int categoryId = jsonObject.getInt("categoryId");
        int[] imageIDs = new int[0];
        // Tạo đối tượng Product và thêm vào danh sách
        Product product = new Product(id, productDescription,  productName, "status" , price, specification,calculationUnit, discount, sold, quantity,image , brandId, categoryId, imageIDs);
        return product;
    }
    public static Order parseOrderFromJson (JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String date = jsonObject.getString("date");

        String status = jsonObject.getString("status");
        int totalPrice = jsonObject.getInt("totalPrice");
        String paymentMode = jsonObject.getString("paymentMode");
        int userId = jsonObject.getInt("userId");
        JSONArray  jsonArray =(JSONArray) jsonObject.getJSONArray("orderDetailList");
        List<OrderDetail>  orderDetails =new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            OrderDetail orderDetail = new Gson().fromJson((JsonElement) jsonArray.get(i),OrderDetail.class);
            orderDetails.add(orderDetail);
        }
        Order order =new Order(id,date,status,totalPrice,paymentMode,userId,orderDetails);
        return order;
    }
    public static User parseUserFromJson (JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        int imageId = jsonObject.getInt("imageId");
        String email = jsonObject.getString("email");
        String phone = jsonObject.getString("phone");
        Date createDate = Constant.convertStringToDate(jsonObject.getString("createDate"));
        String status = jsonObject.getString("status");
//        JSONObject addressIds = jsonObject.getJSONObject("address") ;
//        int permissionId = jsonObject.getInt("permissionId");
//        String imageBase64 = jsonObject.getString("imageBase64");
//
//        // Tạo đối tượng Product và thêm vào danh sách
//        User user = new User(id, userName,  password, firstName , lastName, imageId,email, phone, createDate,
//                status,addressIds , permissionId, imageBase64);
        return null;
    }
    public static Category parseCategoryFromJson(JSONObject jsonObject) throws  JSONException{
        int id = jsonObject.getInt("id");
        String categoryName =jsonObject.getString("name");
//        byte[] image = jsonObject.getString("imageBase64").getBytes();
        String image = jsonObject.getString("imageBase64");
        String note = jsonObject.getString("note");
        Category category = new Category(id,categoryName,image,note);
        return category;
    }
    public static Brand parseBrandFromJson(JSONObject jsonObject) throws  JSONException{
        int id = jsonObject.getInt("id");
        String brandName =jsonObject.getString("name");
        String brandDecription = jsonObject.getString("descipttion");
        Brand brand = new Brand(id,brandName,brandDecription);
        return brand;
    }
    public static Province parseProvinceFromJson(JSONObject jsonObject) throws JSONException{
        String name =jsonObject.getString("name");
        int code = jsonObject.getInt("code");
        String divisionType =jsonObject.getString("division_type");
        String codeName = jsonObject.getString("codename");
        int phoneCode = jsonObject.getInt("phone_code");
        Province province = new Province(name,code,divisionType,codeName,phoneCode);
        return province;
    }
    public static District parseDistrictFromJson(JSONObject jsonObject) throws JSONException{
        String name =jsonObject.getString("name");
        int code = jsonObject.getInt("code");
        String divisionType =jsonObject.getString("division_type");
        String codeName = jsonObject.getString("codename");
        int phoneCode = jsonObject.getInt("province_code");
        District district = new District(name,code,divisionType,codeName,phoneCode);
        return district;
    }
    public static Ward parseWardFromJson(JSONObject jsonObject) throws JSONException{
        String name =jsonObject.getString("name");
        int code = jsonObject.getInt("code");
        String divisionType =jsonObject.getString("division_type");
        String codeName = jsonObject.getString("codename");
        int phoneCode = jsonObject.getInt("district_code");
        Ward ward = new Ward(name,code,divisionType,codeName,phoneCode);
        return ward;
    }
    public static Address parseAddressFromJson(JSONObject jsonObject) throws JSONException{

        int id = jsonObject.getInt("id");
        String addressGeneral =jsonObject.getString("addressGeneral");
        String addressSpecific = jsonObject.getString("addressSpecific");
        int userId = jsonObject.getInt("userId");
        Address address = new Address(id,addressGeneral,addressSpecific,userId);
        return address;
    }

    public static void getProducts(Context context, final VolleyResponseListener<List<Product>> listener) {
        String url = GET_PRODUCTS_URL;

        // Tạo đối tượng request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Product> products = new ArrayList<>();
                        try {
                            // Lặp qua các đối tượng JSON để tạo danh sách sản phẩm
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Tạo đối tượng Product và thêm vào danh sách
                                Product product = parseProductFromJson(jsonObject);
                                products.add(product);
                            }
                            // Trả về danh sách sản phẩm thông qua listener
                            listener.onResponse(products);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Trả về lỗi nếu không thể tạo danh sách sản phẩm
                            listener.onError("Error parsing JSON data "+e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Trả về lỗi nếu không thể kết nối tới API
                        listener.onError("Error connecting to API "+error);
                    }
                });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }
    public static void getOrdersFromUserId(Context context,int userId ,final VolleyResponseListener<List<Order>> listener) {
        String url = GET_ORDERS_URL +userId;

        // Tạo đối tượng request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Order> orders = new ArrayList<>();
                        try {
                            // Lặp qua các đối tượng JSON để tạo danh sách sản phẩm
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Tạo đối tượng Product và thêm vào danh sách
                                Order order = parseOrderFromJson(jsonObject);
                                orders.add(order);
                            }
                            // Trả về danh sách sản phẩm thông qua listener
                            listener.onResponse(orders);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Trả về lỗi nếu không thể tạo danh sách sản phẩm
                            listener.onError("Error parsing JSON data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Trả về lỗi nếu không thể kết nối tới API
                        listener.onError("Error connecting to API");
                    }
                });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }
    public static void getProvinces(Context context, final VolleyResponseListener<List<Province>> listener) {
        String url = BASE_URL_PROVINCE+GET_PROVINCES;

        // Tạo đối tượng request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Province> provinces = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Tạo đối tượng Product và thêm vào danh sách
                                Province province = parseProvinceFromJson(jsonObject);
                                provinces.add(province);
                            }
                            listener.onResponse(provinces);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError("Error parsing JSON data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        listener.onError("Error connecting to API Province");
                    }
                });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }
    public static void getDistrits(Context context, final VolleyResponseListener<List<District>> listener) {
        String url = BASE_URL_PROVINCE+GET_DISTRICTS;

        // Tạo đối tượng request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<District> districts = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Tạo đối tượng Product và thêm vào danh sách
                                District district = parseDistrictFromJson(jsonObject);
                                districts.add(district);
                            }
                            listener.onResponse(districts);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError("Error parsing JSON data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        listener.onError("Error connecting to API Province");
                    }
                });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }
    public static void getWards(Context context, final VolleyResponseListener<List<Ward>> listener) {
        String url = BASE_URL_PROVINCE+GET_WARDS;

        // Tạo đối tượng request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Ward> wards = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Tạo đối tượng Product và thêm vào danh sách
                                Ward ward = parseWardFromJson(jsonObject);
                                wards.add(ward);
                            }
                            listener.onResponse(wards);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError("Error parsing JSON data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        listener.onError("Error connecting to API Province");
                    }
                });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }
    public static void getCategorys(Context context, final VolleyResponseListener<List<Category>> listener) {
        String url = GET_CATEGORIES_URL;

        // Tạo đối tượng request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Category> categories = new ArrayList<>();
                        try {
                            // Lặp qua các đối tượng JSON để tạo danh sách category
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Tạo đối tượng Category và thêm vào danh sách
                                Category category = parseCategoryFromJson(jsonObject);
                                categories.add(category);
                            }
                            // Trả về danh sách category thông qua listener
                            listener.onResponse(categories);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Trả về lỗi nếu không thể tạo danh sách category
                            listener.onError("Error parsing JSON data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Trả về lỗi nếu không thể kết nối tới API
                        listener.onError("Error connecting to API");
                    }
                });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void getProduct(Context context, int productId, final VolleyResponseListener<Product> listener) {
        String url = GET_PRODUCT_URL + productId;

        // Tạo đối tượng request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Lấy thông tin sản phẩm từ đối tượng JSON
                            Product product = parseProductFromJson(response);
                            // Tạo đối tượng Product và trả về thông qua listener
                            listener.onResponse(product);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Trả về lỗi nếu không thể lấy thông tin sản phẩm
                            listener.onError("Error parsing JSON data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Trả về lỗi nếu không thể kết nối tới API
                        listener.onError("Error connecting to API");
                    }
                });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
    public static void getCategory(Context context, int categoryID, final VolleyResponseListener<Category> listener) {
        String url = GET_CATEGORIE_URL + categoryID;
        // Tạo đối tượng request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            // Lấy thông tin category từ đối tượng JSON
                            Category category = parseCategoryFromJson(response);
                            // Tạo đối tượng category và trả về thông qua listener
                            listener.onResponse(category);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Trả về lỗi nếu không thể lấy thông tin category
                            listener.onError("Error parsing JSON data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Trả về lỗi nếu không thể kết nối tới API
                        listener.onError("Error connecting to API");
                    }
                });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
    public static void getBrand(Context context, int brandID, final VolleyResponseListener<Brand> listener) {
        String url = GET_BRAND_URL + brandID;

        // Tạo đối tượng request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            // Lấy thông tin category từ đối tượng JSON
                            Brand brand = parseBrandFromJson(response);
                            // Tạo đối tượng category và trả về thông qua listener
                            listener.onResponse(brand);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Trả về lỗi nếu không thể lấy thông tin category
                            listener.onError("Error parsing JSON data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Trả về lỗi nếu không thể kết nối tới API
                        listener.onError("Error connecting to API");
                    }
                });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
    public static void getProductsCategory(Context context, int category,final VolleyResponseListener<List<Product>> listener) {
        String url = GET_PRODUCT_URL+ category +"/productsincategory";

        // Tạo đối tượng request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Product> products = new ArrayList<>();
                        try {
                            // Lặp qua các đối tượng JSON để tạo danh sách sản phẩm
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Tạo đối tượng Product và thêm vào danh sách
                                Product product = parseProductFromJson(jsonObject);
                                products.add(product);
                            }
                            // Trả về danh sách sản phẩm thông qua listener
                            listener.onResponse(products);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Trả về lỗi nếu không thể tạo danh sách sản phẩm
                            listener.onError("Error parsing JSON data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Trả về lỗi nếu không thể kết nối tới API
                        listener.onError("Error connecting to API");
                    }
                });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void putProductParams (JSONObject params, Product product) {
        try {
            params.put("id", product.getProductID());
            params.put("productDescription", product.getProduct_description());
            params.put("productName", product.getProduct_name());
            params.put("status", product.getStatus());
            params.put("price", product.getProduct_price());
            params.put("specification", product.getSpecificaion());
            params.put("calculationUnit", product.getCalculation_unit());
            params.put("discount", product.getDiscount());
            params.put("sold", product.getSold());
            params.put("quantity", product.getQuantity());
            params.put("brandId", product.getBrand_id());
            params.put("categoryId", product.getCategory_id());
            params.put("imageBase64", product.getImage());
            System.out.println(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void putAddressParams (JSONObject params, Address address) {
        try {
            params.put("id", address.getId());
            params.put("addressGeneral", address.getAddressGeneral());
            params.put("addressSpecific", address.getAddressSpecific());
            params.put("userId", address.getUserId());
            System.out.println(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void putUserParams (JSONObject params, User user) {
        try {
            params.put("id", user.getId());
            params.put("userName", user.getName());
            params.put("password", user.getPassword());
            params.put("firstName", user.getFirstName());
            params.put("lastName", user.getLastName());
            params.put("imageId", user.getImageId());
            params.put("email", user.getEmail());
            params.put("phone", user.getPhone());
            params.put("createDate", user.getCreatedAt());
            params.put("status", user.getStatus());
//            params.put("addressIds", user.getAddressIds());
            params.put("permissionId", user.getPermissionId());
            params.put("imageBase64", user.getImageBase64());
            System.out.println(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//    public static void putOrderParams (JSONObject params, Order order) {
//        try {
//            params.put("id", order.getId());
//            params.put("date", order.getName());
//            params.put("status", order.getPassword());
//            params.put("totalPrice", order.getFirstName());
//            params.put("paymentMode", order.getLastName());
//            params.put("userId", order.getImageId());
//
//            params.put("email", order.getEmail());
//            params.put("phone", order.getPhone());
//            params.put("createDate", order.getCreatedAt());
//            params.put("status", order.getStatus());
////            params.put("addressIds", user.getAddressIds());
//            params.put("permissionId", order.getPermissionId());
//            params.put("imageBase64", order.getImageBase64());
//            System.out.println(params);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
    public static void addProduct (Context context, Product product, final VolleyResponseListener<Product> listener) {
        String url = POST_PRODUCT_URL;
        JSONObject params = new JSONObject();
        putProductParams(params, product);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Lấy thông tin sản phẩm từ đối tượng JSON
                    Product product = parseProductFromJson(response);
                    // Tạo đối tượng Product và trả về thông qua listener
                    listener.onResponse(product);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Trả về lỗi nếu không thể lấy thông tin sản phẩm
                    listener.onError("Error parsing JSON data");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Trả về lỗi nếu không thể lấy thông tin sản phẩm
                listener.onError("Error connecting to API");

            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
    public static void addUser(Context context, User user, final VolleyResponseListener<User> listener) {
        String url = POST_USER_URL;
        JSONObject params = new JSONObject();
        putUserParams(params, user);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Lấy thông tin sản phẩm từ đối tượng JSON
                    User user = parseUserFromJson(response);
                    // Tạo đối tượng Product và trả về thông qua listener
                    listener.onResponse(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Trả về lỗi nếu không thể lấy thông tin sản phẩm
                    listener.onError("Error parsing JSON data");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Trả về lỗi nếu không thể lấy thông tin sản phẩm
                listener.onError("Error connecting to API");

            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
    public static void addAddress(Context context, Address address, final VolleyResponseListener<Address> listener) {
        String url = POST_ADDRESS_URL;
        JSONObject params = new JSONObject();
        putAddressParams(params, address);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Lấy thông tin sản phẩm từ đối tượng JSON
                    Address address = parseAddressFromJson(response);
                    // Tạo đối tượng Product và trả về thông qua listener
                    listener.onResponse(address);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Trả về lỗi nếu không thể lấy thông tin sản phẩm
                    listener.onError("Error parsing JSON data");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Trả về lỗi nếu không thể lấy thông tin sản phẩm
                listener.onError("Error connecting to API");

            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
//    public static void login (Context context, User user, final VolleyResponseListener<Product> listener) {
//        String url = POST_PRODUCT_URL;
//        JSONObject params = new JSONObject();
//        putUserParams(params, user);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url ,params, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    User user = parseUserFromJson(response);
//                    listener.onResponse(user);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                    listener.onError("Error parsing JSON data");
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                // Trả về lỗi nếu không thể lấy thông tin sản phẩm
//                listener.onError("Error connecting to API");
//
//            }
//
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(jsonObjectRequest);
//    }



    public static void updateProduct (Context context, Product product, final VolleyResponseListener<Product> listener) {
        String url = PUT_PRODUCT_URL + product.getProductID();
        JSONObject params = new JSONObject();
        putProductParams(params, product);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Lấy thông tin sản phẩm từ đối tượng JSON
                    Product product = parseProductFromJson(response);
                    // Tạo đối tượng Product và trả về thông qua listener
                    listener.onResponse(product);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Trả về lỗi nếu không thể lấy thông tin sản phẩm
                    listener.onError("Error parsing JSON data");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Trả về lỗi nếu không thể lấy thông tin sản phẩm
                listener.onError("Error connecting to API");

            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public static void deleteProduct(Context context, int productId, final VolleyResponseListener<Product> listener) {
        String url = DELETE_PRODUCT_URL + productId;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onResponse(null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Trả về lỗi nếu không thể kết nối tới API
                listener.onError("Error connecting to API");
            }
        });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void getMapIdName(Context context, String url, final VolleyResponseListener<Map<Integer, String>> listener) {
        url=BASE_URL+url;
        // Tạo đối tượng request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Map<Integer, String> data = new HashMap<Integer, String>();
                        try {
                            // Lặp qua các đối tượng JSON để tạo danh sách sản phẩm
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Tạo đối tượng Product và thêm vào danh sách
                                data.put(jsonObject.getInt("id"), jsonObject.getString("name"));
                            }
                            // Trả về danh sách sản phẩm thông qua listener
                            listener.onResponse(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Trả về lỗi nếu không thể tạo danh sách sản phẩm
                            listener.onError("Error parsing JSON data");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Trả về lỗi nếu không thể kết nối tới API
                        listener.onError("Error connecting to API");
                    }
                });

        // Thêm request vào RequestQueue để gửi request đến API
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

}

