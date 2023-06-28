package com.example.myapplication.dataController;

import android.content.Context;
import android.widget.Toast;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class data {

    public static ArrayList<Request> getListOrder(Context context, String idsalon) {
        ArrayList<Request> requests = new ArrayList<>();
        ArrayList<String> servicesArraylist = new ArrayList<String>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                                        requests.add(request1);
                                    i++;
                                }

                            }else{
                                Toast.makeText(context, "lỗi", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "lỗi", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idsalon",idsalon );
                return params;
            }
        };
        requestQueue.add(stringRequest);
        return requests;
    }
}
