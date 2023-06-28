package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.constants.constants;
import com.example.myapplication.models.Staffcuthair;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Staffmanageractivity extends AppCompatActivity {
    public Uri photoUri;
    FloatingActionButton floatingAdd;
    RecyclerView saloon1;
    private List<Staffcuthair> mlistStaff;
    private SearchView searchView;
    Activity mactivity;
    private NavigationView navigationView;

    BottomNavigationView bottomNavigationView; //sua141

    String saloonId;
    String saloonidnew;

    private boolean isDataLoaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);



        getIntentData(getIntent()); //sua
        getInit();

        getlistStaffWork();
        isDataLoaded = true;
        ImageView btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bottomNavigationView = findViewById(R.id.navigation_view);
        bottomNavigationView.setVisibility(View.GONE);

    }





    private void getInit() {
        floatingAdd = findViewById(R.id.floatingAdd);
        saloon1 = findViewById(R.id.adminpanelmonitor);
        saloon1.setLayoutManager(new LinearLayoutManager(this));
        mlistStaff = new ArrayList<>();
        mactivity = Staffmanageractivity.this;


    }


    String staffname, staffaddress, staffemail, staffmobile,staffwork,staffexperience,avgrate, staffpassword,image, idstaff,idsaloonofstaff;
    int staffsalary;


    private void getlistStaffWork() {
        if(mlistStaff!=null){
            mlistStaff.clear();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                constants.URL_GetListWorkStaff,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0;i<response.length();i++){
                                JSONObject object = response.getJSONObject(i);
                                if (object.getString("idsaloon").equals(saloonId)) {
                                    idstaff = object.getString("idstaff");
                                    staffname = object.getString("name");
                                    staffaddress = object.getString("address");
                                    staffemail = object.getString("email");
                                    staffpassword = object.getString("password");
                                    staffmobile = object.getString("mobile");
                                    staffexperience = object.getString("experience");
                                    staffwork = object.getString("work");
                                    staffsalary = object.getInt("salary");
                                    avgrate = object.getString("avgrate");
                                    image = object.getString("image");
                                    idsaloonofstaff = object.getString("idsaloon");
                                    Staffcuthair staffcuthair = new Staffcuthair(staffname, staffemail, staffpassword, "", staffmobile, staffaddress, staffexperience, staffwork, staffsalary, "",idsaloonofstaff, image, idstaff);
                                    mlistStaff.add(staffcuthair);
//                                    Saloon s = new Saloon(name,name,address,mobileNo,email,password,image,id_AdminSaloons);
//                                    mlistSaloon.add(new Saloon(String.valueOf(id_salon),name,address,mobileNo,area,email,password,image,id_AdminSaloons));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        StaffAdapteradminmonitor staffAdapteradminmonitor = new StaffAdapteradminmonitor(Staffmanageractivity.this, mlistStaff, mactivity, saloonId);
                        saloon1.setAdapter(staffAdapteradminmonitor);
                        staffAdapteradminmonitor.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        // Xử lý lỗi
                        if (error instanceof TimeoutError) {
                            Toast.makeText(getApplicationContext(), "timeout", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "noconnect", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "authfailure", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "network", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "sever", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "parse", Toast.LENGTH_LONG).show();
                        }
                    }
                }) ;
//        { //do cai nay sinh loi parse
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("idsaloon", saloonId);
//                return params;
//            }
//        };
        requestQueue.add(jsonArrayRequest);
    }


    String idUser;


    public void getIntentData(Intent intent) { //sua
        saloonId = intent.getStringExtra("uidsaloon");
        //   idUser = intent.getStringExtra(Constant.ARGS_USER);
    } //sua

//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(Staffmanageractivity.this);
//        builder.setTitle("Are you sure?");
//        builder.setMessage("Bạn có muốn thoát thật không ?");
//        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent1 = new Intent(Staffmanageractivity.this, LoginActivity.class);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent1);
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(Staffmanageractivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.show();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isDataLoaded) {
            getlistStaffWork();
            isDataLoaded = true;
        }
        // Load lại dữ liệu và cập nhật giao diện nếu cần thiết
    }

}