
package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.constants.constants;
import com.example.myapplication.models.AdminSaloons;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Adminlistacti extends AppCompatActivity {
    private static final int REQUEST_CODE1 = 1;
    private static final int SELECT_PHOTO = 100;


    private ArrayList<AdminSaloons> mlistadmin;
AdminSaloonAdapter adminSaloonAdapter;
    //  String saloonId = "";
    private SearchView searchView;
    Activity mactivity;
    double count;
    Double value;
    Double avgrat;
    String saloonId = "";

    Toolbar toolbar; //su141
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    BottomNavigationView bottomNavigationView; //sua141

RecyclerView rec1;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blank);

rec1=findViewById(R.id.salonlist);



        mlistadmin=new ArrayList<>();

        event();




















//         getInit();
//          setEvents();
//          getlistsaloon();



    }
    public void event(){
        mlistadmin.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                constants.URL_getlistadmin,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0;i<response.length();i++){
                                JSONObject object = response.getJSONObject(i);


                                String id_adminSaloons = object.getString("id_adminSaloons");
                                String firstname = object.getString("firstname");
                                String lastName = object.getString("lastName");
                                String mobile = object.getString("mobile");
                                String state = object.getString("state");
                                String password = object.getString("password");
                                String city = object.getString("city");
                                String area = object.getString("area");
                                String userName = object.getString("userName");
                                String email = object.getString("email");


//                                    Saloon s = new Saloon(name,name,address,mobileNo,email,password,image,id_AdminSaloons);
                                mlistadmin.add(new AdminSaloons(firstname, lastName, mobile, state, city, area,  userName, email,  password,  id_adminSaloons));


                            }
                            rec1.setLayoutManager(new LinearLayoutManager(Adminlistacti.this));
                            adminSaloonAdapter= new AdminSaloonAdapter(Adminlistacti.this, mlistadmin,Adminlistacti.this); //sua
                            rec1.setAdapter(adminSaloonAdapter);

                            adminSaloonAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                });
        requestQueue.add(jsonArrayRequest);

    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE1 && resultCode == Activity.RESULT_OK) {
//            super.onRestart();
//        }
//    }



//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }

    @Override
    protected void onPause() {
        super.onPause();

        // Toast.makeText(this, "da dung", Toast.LENGTH_SHORT).show();
    }
//



//    private void setEvents() {
//        floatingAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminDashBoardActivity.this, AddSaloonActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

//    private void getInit() {
//        floatingAdd = findViewById(R.id.floatingAdd);
//        saloon1 = findViewById(R.id.adminpanelmonitor);
//
//        saloon1.setLayoutManager(new LinearLayoutManager(this));
//        mlistSaloon = new ArrayList<>();
//        mactivity = AdminDashBoardActivity.this;
//
//
//    }


    String idUser;

//sua

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Adminlistacti.this);
        builder.setTitle("Are you sure?");
        builder.setMessage("Bạn có muốn thoát thật không ?");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent1 = new Intent(Adminlistacti.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Adminlistacti.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();


  }


}