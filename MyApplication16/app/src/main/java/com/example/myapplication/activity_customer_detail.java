package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.dataController.data;
import com.example.myapplication.models.Request;
import com.example.myapplication.models.User;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class activity_customer_detail extends AppCompatActivity {
    ArrayList<Request> requests = new ArrayList<>();
    String action = "";
    String loginKey;
    static String key = "";
    private User customerInfo;
    TextView name,address,mobile,email;
    String id_cus;
    private String mobile_cus;
    static int countBillOFcus,TotalMoneyPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        action = getIntent().getAction();
        getIntentData(getIntent());
        getintentdata(getIntent());
//        loginKey = AppUtils.getStringValue(activity_customer_detail.this, Constant.LoginKey);
////        recyclerView = findViewById(R.id.req_recyclerview);
        ImageView btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        requests = data.getListOrder(getApplicationContext(),"0");
//        rc_listBillKH = findViewById(R.id.rc_listBillKH);
//        rc_listBillKH.setLayoutManager(new LinearLayoutManager(this));
//        if (action.equalsIgnoreCase("user")) {
//        ShowAppMent(); //sua
//        ArrayList<Services> servicelist11 = new ArrayList<>();
//        ArrayList<Staffcuthair> staffList111 = new ArrayList<>();
//        Request request1 = new Request("1", "mobile", "email", "addres", "note", "order_method", servicelist11, "date", "time_begin", "time_finish", staffList111, "id_order","id_saloon", "status", "payment", 1);
//        requests.add(request1);
//        appoitmentAdapater = new AppoitmentAdapater(activity_customer_detail.this, requests, loginKey,"namesl1");
//        rc_listBillKH.setAdapter(appoitmentAdapater);
//       name = findViewById(R.id.namecus);
//       address = findViewById(R.id.address_cus);
//       mobile = findViewById(R.id.mobilecus);
//       email = findViewById(R.id.emailcus);
//       LinearLayout layout_list = findViewById(R.id.layout_list);
//        RelativeLayout info = findViewById(R.id.info);
//        ImageView btnBill = findViewById(R.id.btn_showbill);
//        getListOrder();
//        ShowAppMent();
//        btnBill.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                info.setVisibility(View.GONE);
////                layout_list.setVisibility(View.VISIBLE);
////                rc_listBillKH.setVisibility(View.VISIBLE);
////                layout_list.addView(rc_listBillKH);
//            }
//        });
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        MyPagerAdapterCustomerInfo pagerAdapter = new MyPagerAdapterCustomerInfo(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

// Đặt tiêu đề cho từng tab
        tabLayout.getTabAt(0).setText("Thông tin khách hàng");
        tabLayout.getTabAt(1).setText("Đơn hàng đã đặt");


    }
    public User getCustomerInfo(){
        return customerInfo;
    }
    public String getMobileCus() {
        return mobile_cus;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }
    public int getCountBillOFcus() {
        return countBillOFcus;
    }

    public void setCountBillOFcus(int countBillOFcus) {
       this.countBillOFcus = countBillOFcus;
    }

    public void setTotalMoneyPay(int totalMoneyPay) {
        this.TotalMoneyPay = totalMoneyPay;
    }

    public int getTotalMoneyPay() {
        return TotalMoneyPay;
    }

    String formShowWhere;
    public void getIntentData(Intent intent) { //sua
//        saloonId1 = intent.getStringExtra("idsaloon12");
//        namesl1 = intent.getStringExtra("slname1");
//        formShowWhere = intent.getStringExtra("openformFromWhere");
    } //sua


    public static Map<String, Integer> getWordFrequencies(List<String> words) {
        Map<String, Integer> wordFrequencies = new LinkedHashMap<String, Integer>();
        if (words != null) {
            for (String word : words) {
                if (word != null) {
                    word = word.trim();
                    if (!wordFrequencies.containsKey(word)) {
                        wordFrequencies.put(word, 0);
                    }
                    int count = wordFrequencies.get(word);
                    wordFrequencies.put(word, ++count);
                }
            }
        }


        Map.Entry<String, Integer> entry = wordFrequencies.entrySet().iterator().next();


        key = entry.getKey();
        Integer value = entry.getValue();


        return wordFrequencies;
    }

//    public void getListOrder() {
//        countBillOFcus = 0;
//        TotalMoneyPay = 0;
//        ArrayList<String> servicesArraylist = new ArrayList<String>();
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
//                constants.URL_getrequest,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
////                            Toast.makeText(activity_customer_detail.this,"vào chưa", Toast.LENGTH_SHORT).show();
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if(jsonObject.getBoolean("error")==false) {
////                                    Toast.makeText(activity_customer_detail.this,"vào chưa1", Toast.LENGTH_SHORT).show();
//                                JSONArray jsonArray1=jsonObject.getJSONArray("request");
//                                int i=0;
//                                while(i<jsonArray1.length())
//                                {
//                                    JSONObject requestObject = jsonArray1.getJSONObject(i);
//                                    String id_order= requestObject.getString("id_order");
//                                    String name= requestObject.getString("name");
//                                    String email= requestObject.getString("email");
//                                    String addres= requestObject.getString("addres");
//                                    String mobile= requestObject.getString("mobile");
//                                    String order_method= requestObject.getString("order_method");
//                                    String note= requestObject.getString("note");
//                                    String serviceslist= requestObject.getString("serviceslist");
//                                    String time_begin= requestObject.getString("time_begin");
//                                    String time_finish= requestObject.getString("time_finish");
//                                    String date= requestObject.getString("date");
//                                    String staffcuthairList1= requestObject.getString("staffcuthairList");
//                                    String status= requestObject.getString("status");
//                                    String id_saloon= requestObject.getString("id_saloon");
//                                    String payment= requestObject.getString("payment");
//                                    String amount= requestObject.getString("amount");
//
//                                    Gson gson1 = new Gson();
//                                    Type type = new TypeToken<ArrayList<Services>>(){}.getType();
//                                    ArrayList<Services> servicelist = gson1.fromJson(serviceslist, type);
//
//
//                                    Gson gson2 = new Gson();
//                                    Type type1 = new TypeToken<ArrayList<Staffcuthair>>(){}.getType();
//                                    ArrayList<Staffcuthair> staffList1 = gson2.fromJson(staffcuthairList1, type1);
//                                    Request request1 = new Request(name, mobile, email, addres, note, order_method, servicelist, date, time_begin, time_finish, staffList1, id_order,id_saloon, status, payment, Integer.parseInt(amount));
//                                    if(mobile.equals(customerInfo.getMobile())) {
//                                        requests.add(request1);
//                                        countBillOFcus++;
//                                        TotalMoneyPay += (Integer.parseInt(amount));
//                                    }
//                                    i++;
//                                }
//                            }else{
//                                Toast.makeText(getApplicationContext(), "lỗi", Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), "lỗi", Toast.LENGTH_LONG).show();
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("idsalon","16" );
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
//    @Override
//    public void onBackPressed() {
//        if(formShowWhere!=null && formShowWhere.equals("openApoitmentFromSuccessBook")) {
//            super.onBackPressed();
//            Intent intent = new Intent(activity_customer_detail.this, SaloonDashboard.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//        }else {
//            super.onBackPressed();
//        }
//    }

    private void getintentdata(Intent intent) {
        id_cus = intent.getStringExtra("id_cus");
        mobile_cus = intent.getStringExtra("mobile_cus");
        customerInfo = (User) intent.getSerializableExtra("CustomerInfo");
    }


}