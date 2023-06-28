package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.constants.constants;
import com.example.myapplication.models.Request;
import com.example.myapplication.models.Saloon;
import com.example.myapplication.models.Services;
import com.example.myapplication.models.Staffcuthair;
import com.example.myapplication.models.User;
import com.example.myapplication.utils.AppUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerInfoFragment extends Fragment {
    private static final int REQUEST_CODE_ACTIVITY_B = 1;
    private View mView; public Uri photoUri;
    private static final int SELECT_PHOTO = 100;
    ImageButton btnsearch;
    SearchView searchView1;
    private static final int STORAGE_PERMISSION = 111;
    FloatingActionButton floatingAdd;
    RecyclerView saloon1;
    private List<Saloon> mlistSaloon;
    List<User> listUser;

    //  String saloonId = "";
    private SearchView searchView;
    TextView name,address,mobile,email,countbill,totalmoney;
    private static final int FRAGMENT_HOME = 0;
private AdminDashBoardActivity mactivity;
    private int mCurrentFragment = FRAGMENT_HOME;
    private User customer1;
    int countBillOFcus,TotalMoneyPay;
    ArrayList<Request> requests = new ArrayList<Request>();
    activity_customer_detail activity_customer_detail1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.customer_info_fragment, container, false);
        activity_customer_detail1 = (activity_customer_detail) getActivity();
        customer1 = activity_customer_detail1.getCustomerInfo();
        name = mView.findViewById(R.id.namecus);
       address = mView.findViewById(R.id.address_cus);
       mobile = mView.findViewById(R.id.mobilecus);
       email = mView.findViewById(R.id.emailcus);
       name.setText(customer1.getFullname());
       address.setText(customer1.getAdderess());
       email.setText(customer1.getEmail());
       mobile.setText(customer1.getMobile());
       countbill = mView.findViewById(R.id.countbill);
        totalmoney = mView.findViewById(R.id.TotalMoney);
//       requests = data.getListOrder(getContext(),"16");
       getListOrder();
        CustomerListBillFragment c = new CustomerListBillFragment();
//       countbill.setText(String.valueOf(activity_customer_detail1.getCountBillOFcus()));
//       totalmoney.setText(AppUtils.convertMoney(c.getTotalMoneyPay())+"đ");
        return mView;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    public void getListOrder() {
        countBillOFcus = 0;
        TotalMoneyPay = 0;
        ArrayList<String> servicesArraylist = new ArrayList<String>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                constants.URL_getrequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                            Toast.makeText(activity_customer_detail.this,"vào chưa", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error")==false) {
//                                    Toast.makeText(activity_customer_detail.this,"vào chưa1", Toast.LENGTH_SHORT).show();
                                JSONArray jsonArray1=jsonObject.getJSONArray("request");
                                int i=0;
                                while(i<jsonArray1.length())
                                {
                                    JSONObject requestObject = jsonArray1.getJSONObject(i);
                                    String id_order= requestObject.getString("id_order");
                                    String name= requestObject.getString("name");
                                    String email= requestObject.getString("email");
                                    String addres= requestObject.getString("addres");
                                    String mobile= requestObject.getString("mobile");
                                    String order_method= requestObject.getString("order_method");
                                    String note= requestObject.getString("note");
                                    String serviceslist= requestObject.getString("serviceslist");
                                    String time_begin= requestObject.getString("time_begin");
                                    String time_finish= requestObject.getString("time_finish");
                                    String date= requestObject.getString("date");
                                    String staffcuthairList1= requestObject.getString("staffcuthairList");
                                    String status= requestObject.getString("status");
                                    String id_saloon= requestObject.getString("id_saloon");
                                    String payment= requestObject.getString("payment");
                                    String amount= requestObject.getString("amount");

                                    Gson gson1 = new Gson();
                                    Type type = new TypeToken<ArrayList<Services>>(){}.getType();
                                    ArrayList<Services> servicelist = gson1.fromJson(serviceslist, type);


                                    Gson gson2 = new Gson();
                                    Type type1 = new TypeToken<ArrayList<Staffcuthair>>(){}.getType();
                                    ArrayList<Staffcuthair> staffList1 = gson2.fromJson(staffcuthairList1, type1);
                                    Request request1 = new Request(name, mobile, email, addres, note, order_method, servicelist, date, time_begin, time_finish, staffList1, id_order,id_saloon, status, payment, Integer.parseInt(amount));

                                    if(mobile.equals(customer1.getMobile())) {
                                        requests.add(request1);
                                        countBillOFcus++;
                                        countbill.setText(String.valueOf(countBillOFcus));
                                        TotalMoneyPay += (Integer.parseInt(amount));
                                        totalmoney.setText(AppUtils.convertMoney(TotalMoneyPay)+"đ");
                                    }
                                    i++;
                                }
                            }else{
                                Toast.makeText(getContext(), "lỗi", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "lỗi", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idsalon","16" );
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }


}