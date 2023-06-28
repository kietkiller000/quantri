package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.myapplication.constants.constants;
import com.example.myapplication.models.Request;
import com.example.myapplication.models.Services;
import com.example.myapplication.models.Staffcuthair;
import com.example.myapplication.utils.AppUtils;
import com.example.myapplication.utils.Constant;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AppoitmentActivity extends AppCompatActivity {
    String User_id, highesttype;
    AppoitmentAdapater appoitmentAdapater;
    RecyclerView recyclerView;
    TextView noservice;
    String action = "";
    ImageButton recommend;
    String loginKey;
    static String key = "";

    String mUid;
    String idadminsl;
    String namesl1;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoitment);
        action = getIntent().getAction();
        getIntentData(getIntent());

        User_id = AppUtils.getStringValue(this, Constant.USERID);
        recyclerView = findViewById(R.id.req_recyclerview);
        recommend = findViewById(R.id.recommend);
        noservice = findViewById(R.id.noservice);
        ImageView btnback = findViewById(R.id.btnbackService);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        if (action.equalsIgnoreCase("user")) {
        ShowAppMent(); //sua
        appoitmentAdapater = new AppoitmentAdapater(AppoitmentActivity.this, requests, loginKey,namesl1);
        recyclerView.setAdapter(appoitmentAdapater);
//        if (key.equalsIgnoreCase(""))
//        {
//
//        }
//        else
//        {
//
//
//        }

//        recommend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),RecommendationServices.class);
//           if(key.equalsIgnoreCase(""))
//           {
//               key = "new";
//               intent.putExtra("ServiceType", key);
//               startActivity(intent);
//           }
//           else
//           {
//               intent.putExtra("ServiceType", key);
//               startActivity(intent);
//
//           }
//
//
//            }
//        });
        searchView = findViewById(R.id.searchbill); //sua92
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Nhập từ khóa tìm kiếm (sđt,tên,...)");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                appoitmentAdapater.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                appoitmentAdapater.getFilter().filter(newText);
                return false;
            }
        }); //sua92

    }



    ArrayList<Request> requests = new ArrayList<>();
    String saloonId1;

    String formShowWhere;
    public void getIntentData(Intent intent) { //sua
        saloonId1 = intent.getStringExtra("idsaloon12");
        namesl1 = intent.getStringExtra("slname1");
        formShowWhere = intent.getStringExtra("openformFromWhere");
    } //sua
    private void ShowAppMent() {
        boolean isSalon = AppUtils.getValue(AppoitmentActivity.this, Constant.ISSALON);
        loginKey = AppUtils.getStringValue(AppoitmentActivity.this, Constant.LoginKey);
//        if (isSalon) {
//
//            getListOrder();
//
//        } else {
//            getListOrder();
//        }
        getListOrder();

    }

    public void getListOrder() {

//        String loginKey = AppUtils.getStringValue(AppoitmentActivity.this, Constant.LoginKey);
//        if (loginKey.equalsIgnoreCase(Constant.SALOON)) {
            ArrayList<String> servicesArraylist = new ArrayList<String>();




            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                    constants.URL_getrequestbyidsalon,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(!jsonObject.getBoolean("error")) {


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
                                        //  Toast.makeText(Staff_order_activity.this, String.valueOf(servicelist.size()), Toast.LENGTH_SHORT).show();
                                        requests.add(request1);

                                        i++;
                                    }

                                    appoitmentAdapater = new AppoitmentAdapater(AppoitmentActivity.this, requests, loginKey,namesl1);
                                    recyclerView.setAdapter(appoitmentAdapater);
                                    appoitmentAdapater.notifyDataSetChanged();

                                    ArrayList<Request> servicelist = requests;
                                    if (servicelist.size() > 0) {
                                        for (int e = 0; e < requests.size(); e++) {
                                            ArrayList<Services> temp = (ArrayList<Services>) requests.get(e).getServices();
                                            if (temp.size() > 0) {
                                                for (int j = 0; j < temp.size(); j++) {
                                                    servicesArraylist.add(temp.get(j).getType());
                                                }
                                            }
                                        }
                                    }
                                    Log.e("tag", "size" + servicesArraylist.size());
                                    if (servicesArraylist.size() == 0) {
                                        noservice.setVisibility(TextView.VISIBLE);
                                        recyclerView.setVisibility(TextView.INVISIBLE);
                                        Toast.makeText(AppoitmentActivity.this, "No Past Orders available", Toast.LENGTH_SHORT).show();
                                    } else {
                                        noservice.setVisibility(TextView.INVISIBLE);
                                        recyclerView.setVisibility(TextView.VISIBLE);
                                        getWordFrequencies(servicesArraylist);  //sua
                                    }




















//
//                                                        for (DataSnapshot dataSnapshot1 : snapshot3.getChildren()) {
//                                                            if (dataSnapshot1.hasChild("date") == false || dataSnapshot1.hasChild("time_begin") == false || dataSnapshot1.hasChild("time_finish") == false) {
//                                                                Toast.makeText(Staff_order_activity.this, "null", Toast.LENGTH_SHORT).show();
//                                                                return;
//                                                            }
//
//
//
//                                                        }
////
//




                                }else{
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                }
//                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();

                    params.put("idsalon",saloonId1 );

                    return params;
                }
            };

            requestQueue.add(stringRequest);












//        }

//        if (mDatabase != null) {
//            mDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot datas) {
//                    if (requests != null) {
//                        requests.clear();
//                    }
//                    String loginKey = AppUtils.getStringValue(AppoitmentActivity.this, Constant.LoginKey);
//                    if (loginKey.equalsIgnoreCase(Constant.SALOON)) {
//                        ArrayList<String> servicesArraylist = new ArrayList<String>();
//                        mDatabase = FirebaseDatabase.getInstance().getReference().child("R_Request").child(idadminsl).child(User_id);
//                        for (DataSnapshot data : datas.getChildren()) {
////                            Toast.makeText(AppoitmentActivity.this, idadminsl, Toast.LENGTH_SHORT).show();
////                            Toast.makeText(AppoitmentActivity.this, "w " + data.getValue().toString(), Toast.LENGTH_SHORT).show();
//                            if (data.hasChildren() == false) {
////                                Toast.makeText(AppoitmentActivity.this, "wwwww2 là " + data.getValue().toString(), Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            Request request = data.getValue(Request.class);
//                            if (AppUtils.getValue(AppoitmentActivity.this, Constant.ISSALON) &&
//                                    request.getId_saloon().equals(User_id)) {
//                                requests.add(request);
//                            }
//                            appoitmentAdapater = new AppoitmentAdapater(AppoitmentActivity.this, requests, loginKey,namesl1);
//                            recyclerView.setAdapter(appoitmentAdapater);
//                            appoitmentAdapater.notifyDataSetChanged();
//
//                        }
//
//                        ArrayList<Request> servicelist = requests;
//                        if (servicelist.size() > 0) {
//                            for (int i = 0; i < requests.size(); i++) {
//                                ArrayList<Services> temp = (ArrayList<Services>) requests.get(i).getServices();
//                                if (temp.size() > 0) {
//                                    for (int j = 0; j < temp.size(); j++) {
//                                        servicesArraylist.add(temp.get(j).getType());
//                                    }
//                                }
//                            }
//                        }
//                        Log.e("tag", "size" + servicesArraylist.size());
//                        if (servicesArraylist.size() == 0) {
//                            noservice.setVisibility(TextView.VISIBLE);
//                            recyclerView.setVisibility(TextView.INVISIBLE);
//                            Toast.makeText(AppoitmentActivity.this, "No Past Orders available", Toast.LENGTH_SHORT).show();
//                        } else {
//                            noservice.setVisibility(TextView.INVISIBLE);
//                            recyclerView.setVisibility(TextView.VISIBLE);
//                            getWordFrequencies(servicesArraylist);  //sua
//                        }
//
//                    } else {
////                        ArrayList<String> servicesArraylist = new ArrayList<String>();
////                        for (DataSnapshot data : datas.getChildren()) {
////                            for (DataSnapshot datass : data.getChildren()) {
//////                            if (datass.hasChild("addres") == false
//////                                    || datass.hasChild("date") == false
//////                                    || datass.hasChild("email") == false
//////                                    || datass.hasChild("id_order") == false || datass.hasChild("id_saloon") == false
//////                                    || datass.hasChild("mobile") == false || datass.hasChild("name") == false
//////                                    || datass.hasChild("payment") == false || datass.hasChild("saloon_Id") == false
//////                                    || datass.hasChild("status") == false || datass.hasChild("order") == false
//////                                    || datass.hasChild("services") == false || datass.hasChild("staffcuthairList") == false
//////                                    || datass.hasChild("time_begin") == false || datass.hasChild("time_finish") == false)
//////                            {
////                                if (datass.hasChildren() == false) {
////                                    Toast.makeText(AppoitmentActivity.this, "wwwww2", Toast.LENGTH_SHORT).show();
////                                    return;
////                                }
//////                            for (DataSnapshot dataSnapshot : datass.getChildren()) {
////                                Request request = datass.getValue(Request.class);
//////                                if (AppUtils.getValue(AppoitmentActivity.this, Constant.ISUSER) &&
//////                                        request.getUserID().equals(User_id)) {
//////                                    requests.add(request);
//////                                    Log.e("TAGTAG", "  " + requests.size() + " ");
//////                                } else if (AppUtils.getValue(AppoitmentActivity.this, Constant.ISSALON) &&
//////                                        request.getSaloon_Id().equals(User_id)) {
////                                requests.add(request);
//////                                }
////                                appoitmentAdapater = new AppoitmentAdapater(AppoitmentActivity.this, requests, loginKey);
////                                recyclerView.setAdapter(appoitmentAdapater);
////                                appoitmentAdapater.notifyDataSetChanged();
////
//////                            }
////                            }
////                        }
////                        for (DataSnapshot data : datas.getChildren()) {
//////                            Toast.makeText(AppoitmentActivity.this, idadminsl, Toast.LENGTH_SHORT).show();
//////                            Toast.makeText(AppoitmentActivity.this, "w " + data.getValue().toString(), Toast.LENGTH_SHORT).show();
////                            if (data.hasChildren() == false) {
//////                                Toast.makeText(AppoitmentActivity.this, "wwwww2 là " + data.getValue().toString(), Toast.LENGTH_SHORT).show();
////                                return;
////                            }
////                            Request request = data.getValue(Request.class);
////                            requests.add(request);
////                            appoitmentAdapater = new AppoitmentAdapater(AppoitmentActivity.this, requests, loginKey,namesl1 );
////                            recyclerView.setAdapter(appoitmentAdapater);
////                            appoitmentAdapater.notifyDataSetChanged();
////
////                        }
////
////                        ArrayList<Request> servicelist = requests;
////                        if (servicelist.size() > 0) {
////                            for (int i = 0; i < requests.size(); i++) {
////                                ArrayList<Services> temp = (ArrayList<Services>) requests.get(i).getServices();
////                                if (temp.size() > 0) {
////                                    for (int j = 0; j < temp.size(); j++) {
////                                        servicesArraylist.add(temp.get(j).getType());
////                                    }
////                                }
////                            }
////                        }
////                        Log.e("tag", "size" + servicesArraylist.size());
////                        if (servicesArraylist.size() == 0) {
////                            noservice.setVisibility(TextView.VISIBLE);
////                            recyclerView.setVisibility(TextView.INVISIBLE);
////                            Toast.makeText(AppoitmentActivity.this, "No Past Orders available", Toast.LENGTH_SHORT).show();
////                        } else {
////                            noservice.setVisibility(TextView.INVISIBLE);
////                            recyclerView.setVisibility(TextView.VISIBLE);
////                            getWordFrequencies(servicesArraylist);  //sua
////                        }
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Toast.makeText(AppoitmentActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
    }


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




}