package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.example.myapplication.models.Saloon;
import com.example.myapplication.models.Services;
import com.example.myapplication.models.User;
import com.example.myapplication.utils.AppUtils;
import com.example.myapplication.utils.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Listcustomeracti extends AppCompatActivity { //show dịch vụ (nhân viên chi nhánh)
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
    String saloonId = "";
    private static final int FRAGMENT_HOME = 0;
    private Listcustomeracti mactivity;
    private int mCurrentFragment = FRAGMENT_HOME;
    public static final int MY_REQUEST_CODE = 10;

    private boolean isDataLoaded = false;
    Customer_adapter Customer_adapter;
LinearLayout thaythe;


    //  FirebaseUser u ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_admin_dash1);

        getInit();
        getlistsaloon();
        setEvents();



    }











    private  void getInit(){
        floatingAdd = findViewById(R.id.floatingAdd);
        saloon1 = findViewById(R.id.adminpanelmonitor);
        mactivity=Listcustomeracti.this;
        saloon1.setLayoutManager(new LinearLayoutManager(this));
        mlistSaloon = new ArrayList<>();
        listUser = new ArrayList<>();
        thaythe=findViewById(R.id.thaythe);
        floatingAdd.setVisibility(View.VISIBLE);

        //   btnsearch = mView.findViewById(R.id.ic_search1);
        searchView1 =findViewById(R.id.search1);
    }

    // Trong Fragment A

    private void setEvents() {


        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(Customer_adapter!=null)
                {
                    Customer_adapter.getFilter().filter(query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(Customer_adapter!=null)
                {
                    Customer_adapter.getFilter().filter(newText);
                }

                return false;
            }
        });


    }

    private void getlistsaloon() {
        if (listUser != null) {
            listUser.clear();
        }
        String ad_id = AppUtils.getStringValue(this, Constant.ADMINSALOONSID);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                constants.URL_getuserbyadmin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //   Toast.makeText(SaloonBookActivity.this, String.valueOf(jsonObject), Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray=jsonObject.getJSONArray("users");


                            int i = 0;
                            while (i < jsonArray.length()) {
                                JSONObject userObject = jsonArray.getJSONObject(i);
                                String id= userObject.getString("id_user");
                                String name= userObject.getString("fullname");
                                String email= userObject.getString("email");
                                String adderess= userObject.getString("address");
                                String id_admin= userObject.getString("id_admin");
                                String mobile= userObject.getString("mobile");
                                User user=new User(name,mobile,email,adderess,id,id_admin);
                                //    Toast.makeText(ShowServiceActivity.this, type, Toast.LENGTH_SHORT).show();
                                listUser.add(user);




                                // Xử lý đối tượng obj
                                i++;
                            }
                            Customer_adapter = new Customer_adapter(Listcustomeracti.this, listUser, mactivity, ad_id);
                            saloon1.setAdapter(Customer_adapter);
                            Customer_adapter.notifyDataSetChanged();
                            if(listUser.size()==0)
                            {
                                thaythe.setVisibility(View.VISIBLE);
                            }
                            else {
                                thaythe.setVisibility(View.GONE);
                            }

                            if(!jsonObject.getBoolean("error")) {

                            }else{
                                Toast.makeText(Listcustomeracti.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            if(listUser.size()==0)
                            {
                                thaythe.setVisibility(View.VISIBLE);
                            }
                            else {
                                thaythe.setVisibility(View.GONE);
                            }
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Listcustomeracti.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idadmin",ad_id);

                return params;
            }
        };

        requestQueue.add(stringRequest);

    }



















    String formshowWhere = null;
    public void getIntentData(Intent intent) { //sua
//        SharedPreferences sharedPreferences = getSharedPreferences(Constant.LoginKey, MODE_PRIVATE);
//        String loginValue = sharedPreferences.getString(Constant.LoginKey, "");
       String loginValue = AppUtils.getStringValue(Listcustomeracti.this, Constant.LoginKey);
        if(loginValue.equals(Constant.SALOON)) {
            saloonId = intent.getStringExtra(Constant.ARGS_SALOON);
            Toast.makeText(this, saloonId, Toast.LENGTH_SHORT).show();
        }else if(loginValue.equals(Constant.ADMINSALOONS)) {
            saloonId = intent.getStringExtra("idsaloon1");
        }
        formshowWhere = intent.getStringExtra("formshowWhere");
    } //sua





}