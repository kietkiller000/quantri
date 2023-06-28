package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class CustomerListBillFragment extends Fragment {
    private static final int REQUEST_CODE_ACTIVITY_B = 1;
    private View mView; public Uri photoUri;
    AppoitmentAdapater appoitmentAdapater;
    TextView noservice;
    String action = "";
    String loginKey;
    static String key = "";
    static int countBillOFcus,TotalMoneyPay;
    String id_cus,mobile_cus;
    RecyclerView rc_listBillKH;
    ArrayList<Request> requests = new ArrayList<>();
    activity_customer_detail activity_customer_detail1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.customer_listbill_fragment, container, false);
        rc_listBillKH = mView.findViewById(R.id.rc_listBillKH);
        rc_listBillKH.setLayoutManager(new LinearLayoutManager(getContext()));
        activity_customer_detail1 = (activity_customer_detail) getActivity();
        getListOrder();
        return mView;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
//    private void getintentdata(Intent intent) {
//        id_cus = intent.getStringExtra("id_cus");
//        mobile_cus = intent.getStringExtra("mobile_cus");
//    }

    public int getCountBillOFcus() {
        return countBillOFcus;
    }

    public void setCountBillOFcus(int countBillOFcus) {
        this.countBillOFcus = countBillOFcus;
    }

    public static void setTotalMoneyPay(int totalMoneyPay) {
        TotalMoneyPay = totalMoneyPay;
    }

    public int getTotalMoneyPay() {
        return TotalMoneyPay;
    }

    public void getListOrder() {
        setCountBillOFcus(0);
        setTotalMoneyPay(0);
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

                                    if(mobile.equals(activity_customer_detail1.getMobileCus())) {
                                        requests.add(request1);
                                        setCountBillOFcus(getCountBillOFcus()+1);
                                       setTotalMoneyPay(getTotalMoneyPay()+(Integer.parseInt(amount)));
                                    }
                                    i++;
                                }
//                                    ArrayList<Services> servicelist11 = new ArrayList<>();
//                                    ArrayList<Staffcuthair> staffList111 = new ArrayList<>();
//                                    Request request1 = new Request("1", "mobile", "email", "addres", "note", "order_method", servicelist11, "date", "time_begin", "time_finish", staffList111, "id_order","id_saloon", "status", "payment", 1);
//                                    requests.add(request1);

                                appoitmentAdapater = new AppoitmentAdapater(getContext(), requests, "1","namesl1");
                                rc_listBillKH.setAdapter(appoitmentAdapater);
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
//                                        noservice.setVisibility(TextView.VISIBLE);
                                    rc_listBillKH.setVisibility(TextView.INVISIBLE);
                                    Toast.makeText(getContext(), "No Past Orders available", Toast.LENGTH_SHORT).show();
                                } else {
//                                        noservice.setVisibility(TextView.INVISIBLE);
                                    rc_listBillKH.setVisibility(TextView.VISIBLE);
                                    getWordFrequencies(servicesArraylist);  //sua
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




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
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