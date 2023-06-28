
package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.constants.constants;
import com.example.myapplication.models.AdminSaloons;
import com.example.myapplication.models.Saloon;
import com.example.myapplication.utils.AppUtils;
import com.example.myapplication.utils.Constant;
import com.orhanobut.dialogplus.DialogPlus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminSaloonAdapter extends RecyclerView.Adapter<AdminSaloonAdapter.ViewHolder> {

    private static final String[] areas = {"Hà Nội", "Hải Phòng", "TP Hồ Chí Minh", "Đà Nẵng", "Cà Mau"};
    String iduser;
    Context context;
    ArrayList<AdminSaloons> list = new ArrayList<>();
    int i1 = 5;
    Adminlistacti acti;
    public AdminSaloonAdapter(Context context, ArrayList<AdminSaloons> list,Adminlistacti acti) {
        this.context = context;
        this.list = list;
this.acti=acti;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsalon, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        AdminSaloons adminSaloons = list.get(position);
        holder.semail.setText(adminSaloons.getEmail());
        holder.sname.setText(adminSaloons.getMobile());
       holder.thislayout.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {





               LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
               View dialogView = inflater.inflate(R.layout.menuadminsalon, null);

               AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
               builder.setView(dialogView);

               AlertDialog alertDialog = builder.create();
               Button but1,but2,but3,but4,but5;
               but1=dialogView.findViewById(R.id.editadmin);
               but2=dialogView.findViewById(R.id.deleteadmin);
               but3=dialogView.findViewById(R.id.resetpassword);
               but4=dialogView.findViewById(R.id.editsalon);
               but5 =dialogView.findViewById(R.id.editcustomer);
but2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {




        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure?");
        builder.setMessage("Bạn có muốn xóa admin này không  ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                        constants.URL_deleteadminsalon,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
//                                            Toast.makeText(getApplicationContext(), "Update Info Success !", Toast.LENGTH_LONG).show();
                                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                                    acti.event();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Lỗi", Toast.LENGTH_LONG).show();
//                            progressDialog.dismiss();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
//                                String ad_id = AppUtils.getStringValue(getApplicationContext(), Constant.ADMINSALOONSID);
                        Map<String, String> params = new HashMap<>();
                        params.put("id_adminSaloons", adminSaloons.getId_adminSaloons());

//                                params.put("image", mobile.getText().toString().trim());
//                                        params.put("id_AdminSaloons", ad_id);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);




            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();







    }
});
but5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        AppUtils.setStringValue(context, Constant.ADMINSALOONSID, adminSaloons.getId_adminSaloons());
        AppUtils.setStringValue(context, Constant.LoginKey, Constant.ADMINSALOONS);
        AppUtils.setValue(context, Constant.ISADMINSALOONS, true);
        Intent intent = new Intent(context, Listcustomeracti.class);

        context.startActivity(intent);
    }
});
but4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AppUtils.setStringValue(context, Constant.ADMINSALOONSID, adminSaloons.getId_adminSaloons());
                                        AppUtils.setStringValue(context, Constant.LoginKey, Constant.ADMINSALOONS);
                                        AppUtils.setValue(context, Constant.ISADMINSALOONS, true);
        Intent intent = new Intent(context, AdminDashBoardActivity.class);
        intent.putExtra("idsaloon1", adminSaloons.getId_adminSaloons());
        context.startActivity(intent);
    }
});
               but3.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {









                       AlertDialog.Builder builder = new AlertDialog.Builder(context);
                       builder.setTitle("Are you sure?");
                       builder.setMessage("Bạn có muốn reset mật khẩu  ?");
                       builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {



                               RequestQueue requestQueue = Volley.newRequestQueue(context);
                               StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                                       constants.URL_resetpasswordadmin,
                                       new Response.Listener<String>() {
                                           @Override
                                           public void onResponse(String response) {

                                               try {
                                                   JSONObject jsonObject = new JSONObject(response);
//                                            Toast.makeText(getApplicationContext(), "Update Info Success !", Toast.LENGTH_LONG).show();
                                                   Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                                                   acti.event();
                                               } catch (JSONException e) {
                                                   e.printStackTrace();
                                               }
                                           }
                                       },
                                       new Response.ErrorListener() {
                                           @Override
                                           public void onErrorResponse(VolleyError error) {
                                               Toast.makeText(context, "Lỗi", Toast.LENGTH_LONG).show();
//                            progressDialog.dismiss();
                                           }
                                       }) {
                                   @Override
                                   protected Map<String, String> getParams() throws AuthFailureError {
//                                String ad_id = AppUtils.getStringValue(getApplicationContext(), Constant.ADMINSALOONSID);
                                       Map<String, String> params = new HashMap<>();
                                       params.put("id_adminSaloons", adminSaloons.getId_adminSaloons());

//                                params.put("image", mobile.getText().toString().trim());
//                                        params.put("id_AdminSaloons", ad_id);
                                       return params;
                                   }
                               };
                               requestQueue.add(stringRequest);




                           }
                       });
                       builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                           }
                       });
                       builder.show();









                   }
               });
               but1.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       final String[] type = new String[1];
Spinner spiner;
alertDialog.dismiss();
                       int n = Resources.getSystem().getDisplayMetrics().heightPixels;
                       //  Toast.makeText(mactivity, String.valueOf(n), Toast.LENGTH_SHORT).show();
                       final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                               .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.admin_update_infor))
                               .setExpanded(true, n * 6 / 7)
                               .create();
                       dialogPlus.show();
                       View view = dialogPlus.getHolderView();

                       EditText firstname = view.findViewById(R.id.firstnameadmin);
                       EditText lastname = view.findViewById(R.id.lastnameadmin);
                       EditText mobile = view.findViewById(R.id.mobileadminupdate);
                       EditText state = view.findViewById(R.id.stateadmin);
                       EditText city = view.findViewById(R.id.cityadmin);
                       EditText area = view.findViewById(R.id.areaadmin);
                       EditText username = view.findViewById(R.id.usernameadmin);
                       EditText email = view.findViewById(R.id.email_saloon_update);
                       EditText password = view.findViewById(R.id.password_saloon_update);

                       Button btnCancel = view.findViewById(R.id.bt_cancel);
                       Button btnupdate = view.findViewById(R.id.btn_saloon_update);


                       firstname.setText(adminSaloons.getFirstname());
                       lastname.setText(adminSaloons.getLastName());
                       mobile.setText(adminSaloons.getMobile());
                       state.setText(adminSaloons.getState());
                       city.setText(adminSaloons.getCity());
                       area.setText(adminSaloons.getArea());
                       username.setText(adminSaloons.getUserName());
                       email.setText(adminSaloons.getEmail());
                       password.setText(adminSaloons.getPassword());




                       btnCancel.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               dialogPlus.dismiss();
                           }
                       });


                       dialogPlus.getHolderView().setOnKeyListener(new View.OnKeyListener() {
                           @Override
                           public boolean onKey(View v, int keyCode, KeyEvent event) {

                               if (keyCode == KeyEvent.KEYCODE_BACK) {
                                   dialogPlus.dismiss();
                                   return true;
                               }
                               return false;
                           }
                       });

                       btnupdate.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
//                progressDialog.show();
//                progressDialog.setTitle("Update Data");
//                progressDialog.setMessage("Please wait");
//                progressDialog.setCancelable(false);
                               String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                               String firstname1 = firstname.getText().toString().trim();
                               String lastname1 = lastname.getText().toString().trim();
                               String mobile1 = mobile.getText().toString().trim();
                               String state1 = state.getText().toString().trim();
                               String city1 = city.getText().toString().trim();
                               String area1 = area.getText().toString().trim();
                               String username1 = username.getText().toString().trim();
                               String email1 = email.getText().toString().trim();
                               String password1 = password.getText().toString().trim();
                              // AdminSaloons adminSaloons1=new AdminSaloons(firstname1, lastname1, mobile1, state1, city1, area1,  username1, email1,  password1,  adminSaloons.getId_adminSaloons());



                               if (firstname1.equals("") ||lastname1.equals("")|| email1.equals("") || mobile1.equals("") || password1.equals("") || state1.equals("")||city1.equals("")||area1.equals("")||username1.equals("")) {
                                   Toast.makeText(context, "Vui lòng nhập tất cả các trường thông tin", Toast.LENGTH_SHORT).show();

                               } else {

//                    if(email1.equals(saloon.getEmail().toString())||mobile1.equals(saloon.getMobileNo().toString()))
                                   if (email.getText().toString().trim().matches(emailPattern)) {
                                       RequestQueue requestQueue = Volley.newRequestQueue(context);
                                       StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                                               constants.URL_updateinforadmin1,
                                               new Response.Listener<String>() {
                                                   @Override
                                                   public void onResponse(String response) {

                                                       try {
                                                           JSONObject jsonObject = new JSONObject(response);
//                                            Toast.makeText(getApplicationContext(), "Update Info Success !", Toast.LENGTH_LONG).show();
                                                           Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(AddSaloonActivity.this, Adminsalonprofile.class));
//                                progressDialog.dismiss();
//                                            ReloadSalon();
                                                           dialogPlus.dismiss();
                                                           acti.event();
                                                       } catch (JSONException e) {
                                                           e.printStackTrace();
                                                       }
                                                   }
                                               },
                                               new Response.ErrorListener() {
                                                   @Override
                                                   public void onErrorResponse(VolleyError error) {
                                                       Toast.makeText(context, "Lỗi", Toast.LENGTH_LONG).show();
//                            progressDialog.dismiss();
                                                   }
                                               }) {
                                           @Override
                                           protected Map<String, String> getParams() throws AuthFailureError {
//                                String ad_id = AppUtils.getStringValue(getApplicationContext(), Constant.ADMINSALOONSID);
                                               Map<String, String> params = new HashMap<>();
                                               params.put("id_adminSaloons", adminSaloons.getId_adminSaloons());
                                               params.put("firstname", firstname.getText().toString().trim());
                                               params.put("lastname", lastname.getText().toString().trim());
                                               params.put("mobile",mobile.getText().toString().trim());
                                               params.put("state", state.getText().toString().trim());
                                               params.put("city", city.getText().toString().trim());
                                               params.put("area",area.getText().toString().trim());
                                               params.put("userName", username.getText().toString().trim());
                                               params.put("email", email.getText().toString().trim());
                                               params.put("password", password.getText().toString().trim());
//                                params.put("image", mobile.getText().toString().trim());
//                                        params.put("id_AdminSaloons", ad_id);
                                               return params;
                                           }
                                       };
                                       requestQueue.add(stringRequest);


                                   }else{
                                       Toast.makeText(context, "Email không hợp lệ!", Toast.LENGTH_LONG).show();
                                   }
                               }


                           }
                       });

                   }
               });








// Thiết lập các sự kiện cho các nút hoặc thành phần trong layout






               alertDialog.show();







               return false;
           }
       });





//        if(saloon.getImage().trim()==null||saloon.getImage().equals("")){
//
//
//            //   Toast.makeText(context, "aka", Toast.LENGTH_SHORT).show();
//            Random r = new Random();
//             i1 = r.nextInt(max - min + 1) + min;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                holder.sphoto.setImageDrawable(context.getDrawable(images[i1]));
//                // Toast.makeText(context, "akssssa", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//        if (saloon.getImage() != null && !saloon.getImage().isEmpty()) {
//
//            Picasso.get()
//                    .load(saloon.getImage())
//                    .placeholder(R.mipmap.ic_launcher)
//                    .into(holder.sphoto);
//            //   Toast.makeText(context, "key1", Toast.LENGTH_SHORT).show();
//
//        } else {
//            Random r = new Random();
//            i1 = r.nextInt(max - min + 1) + min;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                holder.sphoto.setImageDrawable(context.getDrawable(images[i1]));
//                //   Toast.makeText(context, "akssssa", Toast.LENGTH_SHORT).show();
//            }
//        }

//        holder.btnbook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, SaloonViewActivity.class);
//                intent.putExtra("sname",saloon.getName());
//                intent.putExtra("semail",saloon.getEmail());
//                intent.putExtra("sadd",saloon.getAddress());
//                intent.putExtra("smobile",saloon.getMobileNo());
//                intent.putExtra("workingHr",saloon.getWorkingHr());
//                intent.putExtra("sarea",saloon.getArea());
//                intent.putExtra("id",saloon.getId());
//                intent.putExtra("logo",saloon.getImage());
//                intent.putExtra("iduser",iduser);
//
//
//                context.startActivity(intent);
//
//            }
//        });

//        StorageReference storageRef =
//                FirebaseStorage.getInstance().getReference();
//        storageRef.child("images/").child(saloon.getName() +"."+ saloon.getExt()).getDownloadUrl()
//                .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Glide.with(context).load(uri).into(holder.sphoto);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e("ERROR","  "+position + "  "+e.getLocalizedMessage());
//            }
//        });


//        FirebaseStorage storageDisplayImg;
//        StorageReference storageRef;
//        FirebaseAuth auth;
//
//        storageDisplayImg=FirebaseStorage.getInstance();
//        auth = FirebaseAuth.getInstance();
//        FirebaseUser userConnect = auth.getCurrentUser();
//        String id_user=userConnect.getUid();
//        storageRef = storageDisplayImg.getReference().child("  images/" + saloon.getName() + "." + saloon.getExt()); // return gs://mydreambook-32321.appspot.com/images/test23-03-2017_16:46:55
//
//            Glide.with(context)
//                    .load(storageRef)
//                    .into(holder.sphoto);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(ArrayList<AdminSaloons> adminSaloons) {
        this.list.clear();
        this.list = adminSaloons;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sname, semail,thongtin;
       LinearLayout thislayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
thongtin=itemView.findViewById(R.id.thongtin);
thongtin.setText("Mobile");
            sname = itemView.findViewById(R.id.namesalon);
           semail=itemView.findViewById(R.id.emailsalon);
           thislayout=itemView.findViewById(R.id.thislay);


        }
    }

}