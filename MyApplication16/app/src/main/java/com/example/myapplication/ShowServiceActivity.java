package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.constants.constants;
import com.example.myapplication.models.Services;
import com.example.myapplication.utils.AppUtils;
import com.example.myapplication.utils.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowServiceActivity extends AppCompatActivity { //show dịch vụ (nhân viên chi nhánh)
    public static String uid = "";

    RecyclerView activelistview;
    String saloonId = "";
    String AdminSaloonsID;
    ArrayList<Services> servicesList = new ArrayList<>();
    ServiceAdapter adapter;

    Button btndelete,btnEdit; //sua //them btnEdit
    FloatingActionButton btnAddservice;
    ProgressDialog progressDialog;
LinearLayout thaythe;
    //  FirebaseUser u ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_servicelist_admin_mannager);
        btnAddservice=findViewById(R.id.addservicebtn);
        thaythe=findViewById(R.id.thaythe);
        getIntentData(getIntent()); //sua
        getInit();
        getData();

        ImageView btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    String formshowWhere = null;
    public void getIntentData(Intent intent) { //sua
//        SharedPreferences sharedPreferences = getSharedPreferences(Constant.LoginKey, MODE_PRIVATE);
//        String loginValue = sharedPreferences.getString(Constant.LoginKey, "");
       String loginValue = AppUtils.getStringValue(ShowServiceActivity.this, Constant.LoginKey);
        if(loginValue.equals(Constant.SALOON)) {
            saloonId = intent.getStringExtra(Constant.ARGS_SALOON);

        }else if(loginValue.equals(Constant.ADMINSALOONS)) {
            saloonId = intent.getStringExtra("idsaloon1");
        }
        formshowWhere = intent.getStringExtra("formshowWhere");
    } //sua
    private void getData() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Getting Data");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        if(servicesList!=null){
            servicesList.clear();
        }
        progressDialog.dismiss();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                constants.URL_getallservices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray=jsonObject.getJSONArray("services");


                            int i = 0;
                            while (i < jsonArray.length()) {
                                JSONObject userObject = jsonArray.getJSONObject(i);
                                String id= userObject.getString("idservice");
                                String nameservice= userObject.getString("nameservice");
                                String type= userObject.getString("type");
                                String price= userObject.getString("price");
                                String id_salon= userObject.getString("id_salon");
                                Services services=new Services(id,type,nameservice,price,id_salon);

                                //    Toast.makeText(ShowServiceActivity.this, type, Toast.LENGTH_SHORT).show();
                                servicesList.add(services);


                                // Xử lý đối tượng obj
                                i++;
                            }
                            if(servicesList.size()==0)
                            {
                                thaythe.setVisibility(View.VISIBLE);
                            }
                            else {
                                thaythe.setVisibility(View.GONE);
                            }
                            adapter = new ServiceAdapter(ShowServiceActivity.this,servicesList,"uki", saloonId,formshowWhere);
                            activelistview.setAdapter(adapter);
                            progressDialog.dismiss();
                            if(!jsonObject.getBoolean("error")) {


//                                        Toast.makeText(LoginActivity.this,jsonObject.getString("id_adminSaloons"), Toast.LENGTH_SHORT).show();
                                //  Toast.makeText(ShowServiceActivity.this, String.valueOf(jsonObject), Toast.LENGTH_SHORT).show();
                                new CountDownTimer(10000, 100) {

                                    public void onTick(long millisUntilFinished) {
                                        // Toast.makeText(ShowServiceActivity.this, String.valueOf(servicesList), Toast.LENGTH_SHORT).show();
                                        // millisUntilFinished là số millisecond còn lại cho đến khi hết thời gian đếm ngược
                                        // cập nhật giao diện với giá trị mới của millisUntilFinished
                                    }

                                    public void onFinish() {
                                        // Đã kết thúc đếm ngược, thực hiện hành động tương ứng
                                    }
                                }.start();


                            }else{
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
//                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                                if(jsonObject.getString("error").equals(false)) {
////                                        startActivity(new Intent(getApplicationContext(), SaloonDashboard.class));
////                                        Toast.makeText(getApplicationContext(), " Login Thành công.", Toast.LENGTH_SHORT).show();
////                                        finish();
//                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                                }
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
                params.put("idsalon", saloonId);

                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void getInit() {
//        saloonId = getIntent().getStringExtra(Constant.ARGS_SALOON);
        activelistview = findViewById(R.id.activelistview);
        activelistview.setLayoutManager(new LinearLayoutManager(this));
        btnEdit=findViewById(R.id.btnedit); //sua //them

    }


}