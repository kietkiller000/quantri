package com.example.myapplication;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
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

public class Customer_Fragment extends Fragment {
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
private AdminDashBoardActivity mactivity;
    private int mCurrentFragment = FRAGMENT_HOME;
    public static final int MY_REQUEST_CODE = 10;
LinearLayout thaythe;
    private boolean isDataLoaded = false;
    Customer_adapter Customer_adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.user_admin_dash1, container, false);
        getInit();
        getlistsaloon();
        setEvents();

        return mView;
    }
    @Override
    public void onResume() {
        super.onResume();
    }


    private  void getInit(){
        floatingAdd = mView.findViewById(R.id.floatingAdd);
        saloon1 = mView.findViewById(R.id.adminpanelmonitor);
        mactivity=(AdminDashBoardActivity)getActivity();
        saloon1.setLayoutManager(new LinearLayoutManager(getContext()));
        thaythe=mView.findViewById(R.id.thaythe);
        mlistSaloon = new ArrayList<>();
        listUser = new ArrayList<>();
        floatingAdd.setVisibility(View.VISIBLE);

     //   btnsearch = mView.findViewById(R.id.ic_search1);
        searchView1 = mView.findViewById(R.id.search1);
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
        String ad_id = AppUtils.getStringValue(getContext(), Constant.ADMINSALOONSID);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                            Customer_adapter = new Customer_adapter(getContext(), listUser, mactivity, ad_id);
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
                                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.ic_search1).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Customer_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Customer_adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.search, menu);
//
//
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.ic_search1).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                msaloonadapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                msaloonadapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        super.onCreateOptionsMenu(menu, inflater);
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        floatingAdd.setVisibility(View.GONE);
//    }


}