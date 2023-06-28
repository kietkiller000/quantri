package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.SaloonAdapter;
import com.example.myapplication.constants.constants;
import com.example.myapplication.models.Saloon;
import com.example.myapplication.utils.AppUtils;
import com.example.myapplication.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
SaloonAdapter saloonAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rec1;
    private View mView;
    private ArrayList<Saloon> mlistSaloon;
    LinearLayout thaythe;
    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_blank, container, false);
        // Inflate the layout for this fragment
rec1=mView.findViewById(R.id.salonlist);
        rec1.setLayoutManager(new LinearLayoutManager(getContext()));
thaythe=mView.findViewById(R.id.thaythe);

        mlistSaloon=new ArrayList<>();
        event();



        return mView;
    }

    public  void event(){
        mlistSaloon.clear();
        String ad_id = AppUtils.getStringValue(getContext(), Constant.ADMINSALOONSID);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                constants.URL_GetListSalonByidadmin,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0;i<response.length();i++){
                                JSONObject object = response.getJSONObject(i);
                                if (object.getString("id_AdminSaloons").equals(ad_id)) {
                                    int id_salon = object.getInt("id_saloon");
                                    String name = object.getString("name");
                                    String address = object.getString("address");
                                    String mobileNo = object.getString("mobileNo");
                                    String area = object.getString("area");
                                    String workingHR = object.getString("workingHr");
                                    String email = object.getString("email");
                                    String password = object.getString("password");
                                    String image = object.getString("image");
                                    String id_AdminSaloons = object.getString("id_AdminSaloons");

//                                    Saloon s = new Saloon(name,name,address,mobileNo,email,password,image,id_AdminSaloons);
                                    mlistSaloon.add(new Saloon(String.valueOf(id_salon),name,address,mobileNo,workingHR,area,email,password,image,id_AdminSaloons));

                                }
                            }
if(mlistSaloon.size()==0)
{
    thaythe.setVisibility(View.VISIBLE);
}
                            saloonAdapter = new SaloonAdapter(getContext(), mlistSaloon,BlankFragment.this); //sua
                            rec1.setAdapter(saloonAdapter);
                            saloonAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getContext(),"lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);

    }
}