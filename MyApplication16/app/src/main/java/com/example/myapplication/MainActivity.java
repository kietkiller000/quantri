package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.constants.constants;
import com.example.myapplication.utils.AppUtils;
import com.example.myapplication.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
EditText emailedt,passwordedt;
CheckBox show_hide;
Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        show_hide = findViewById(R.id.show_hide_password);
        emailedt=findViewById(R.id.etemail);
        passwordedt=findViewById(R.id.etpassword);
        loginbutton=findViewById(R.id.btnlogin);
        show_hide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    passwordedt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    passwordedt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailedt.getText().toString().equals("")||passwordedt.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        constants.URL_superadminlogin,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(!jsonObject.getBoolean("error")) {
//AppUtils.setStringValue(MainActivity.this, Constant.ADMINSALOONSID, jsonObject.getString("id_adminSaloons"));
//                                        AppUtils.setStringValue(MainActivity.this, Constant.LoginKey, Constant.ADMINSALOONS);
//                                        AppUtils.setValue(MainActivity.this, Constant.ISADMINSALOONS, true);
                                        startActivity(new Intent(getApplicationContext(), Adminlistacti.class));
                                        Toast.makeText(getApplicationContext(), " Login Thành công.", Toast.LENGTH_SHORT).show();
                                        finish();

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
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("password",passwordedt.getText().toString().trim());
                        params.put("email", emailedt.getText().toString().trim());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);





















            }
        });


    }
}