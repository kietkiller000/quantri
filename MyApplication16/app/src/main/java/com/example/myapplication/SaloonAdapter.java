
package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.example.myapplication.Fragment.BlankFragment;
import com.example.myapplication.constants.constants;
import com.example.myapplication.models.Saloon;
import com.orhanobut.dialogplus.DialogPlus;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SaloonAdapter extends RecyclerView.Adapter<SaloonAdapter.ViewHolder> {

    private static final String[] areas = {"Hà Nội", "Hải Phòng", "TP Hồ Chí Minh", "Đà Nẵng", "Cà Mau"};
    String iduser;
    Context context;
    ArrayList<Saloon> list = new ArrayList<>();
    int i1 = 5;
BlankFragment blankFragment;
    public SaloonAdapter(Context context, ArrayList<Saloon> list, BlankFragment blankFragment) {
        this.context = context;
        this.list = list;
this.blankFragment=blankFragment;
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
        Saloon saloon = list.get(position);
        holder.semail.setText(saloon.getEmail());
        holder.sname.setText(saloon.getName());
       holder.thislayout.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {





               LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
               View dialogView = inflater.inflate(R.layout.menusalon, null);

               AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
               builder.setView(dialogView);

               AlertDialog alertDialog = builder.create();
               Button but1,but2,but3,but4,but5;
               but1=dialogView.findViewById(R.id.edittalon);
               but2=dialogView.findViewById(R.id.deletesalon);
               but3=dialogView.findViewById(R.id.editrequest);
               but4=dialogView.findViewById(R.id.editservice);
               but5 =dialogView.findViewById(R.id.editstaff);

but5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, Staffmanageractivity.class);
        intent.putExtra("uidsaloon", saloon.getId_saloon());
        context.startActivity(intent);
    }
});
but4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ShowServiceActivity.class);
        intent.putExtra("idsaloon1", saloon.getId_saloon());
        context.startActivity(intent);
    }
});
               but3.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {


                       Intent intent = new Intent(context, AppoitmentActivity.class);
                       intent.putExtra("idsaloon12", saloon.getId_saloon());
                       intent.putExtra("slname1", saloon.getName());
                       context.startActivity(intent);


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
                               .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.admin_update_saloon_infor))
                               .setExpanded(true, n * 6 / 7)
                               .create();
                       dialogPlus.show();
                       View view = dialogPlus.getHolderView();
                       spiner = view.findViewById(R.id.spinner);
                       EditText name = view.findViewById(R.id.name_saloon_update);
                       EditText mobile = view.findViewById(R.id.mobile_saloon_update);
                       EditText address = view.findViewById(R.id.address_saloon_update);
                       EditText email = view.findViewById(R.id.email_saloon_update);
                       EditText password = view.findViewById(R.id.password_saloon_update);
                       Button btnCancel = view.findViewById(R.id.bt_cancel);
                       Button btnupdate = view.findViewById(R.id.btn_saloon_update);


                       name.setText(saloon.getName());
                       mobile.setText(saloon.getMobileNo());
                       address.setText(saloon.getAddress());
                       email.setText(saloon.getEmail());
                       password.setText(saloon.getPassword());


                       ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                               android.R.layout.simple_spinner_item, areas);
                       int spinerselect = adapter.getPosition(saloon.getArea());
                       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       spiner.setAdapter(adapter);
                       spiner.setSelection(spinerselect);
                       spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                           @Override
                           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                               //    Toast.makeText(context, areas[i], Toast.LENGTH_SHORT).show();
                               type[0] = areas[i];
                           }

                           @Override
                           public void onNothingSelected(AdapterView<?> adapterView) {

                           }
                       });
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
                               String name1 = name.getText().toString().trim();
                               String email1 = email.getText().toString().trim();
                               String mobile1 = mobile.getText().toString().trim();
                               String password1 = password.getText().toString().trim();
                               String address1 = address.getText().toString().trim();
                               saloon.setName(name1);
                               saloon.setAddress(address1);
                               saloon.setArea(address1);
                               saloon.setEmail(email1);
                               saloon.setPassword(password1);
                               saloon.setMobileNo(mobile1);
                               saloon.setArea(type[0]);

                               if (name1.equals("") || email1.equals("") || mobile1.equals("") || password1.equals("") || address1.equals("")) {
                                   Toast.makeText(context, "Vui lòng nhập tất cả các trường thông tin", Toast.LENGTH_SHORT).show();

                               } else {

//                    if(email1.equals(saloon.getEmail().toString())||mobile1.equals(saloon.getMobileNo().toString()))
                                   if (email.getText().toString().trim().matches(emailPattern)) {
                                       RequestQueue requestQueue = Volley.newRequestQueue(context);
                                       StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                                               constants.URL_updateInfoSalon,
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
                                                           blankFragment.event();

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
                                               params.put("id_saloon", saloon.getId_saloon());
                                               params.put("name", name.getText().toString().trim());
                                               params.put("address", address.getText().toString().trim());
                                               params.put("mobileNo", mobile.getText().toString().trim());
                                               params.put("workingHr",saloon.getWorkingHr());
                                               params.put("area", type[0]);
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

    public void update(ArrayList<Saloon> saloonList) {
        this.list.clear();
        this.list = saloonList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sname, semail;
       LinearLayout thislayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sname = itemView.findViewById(R.id.namesalon);
           semail=itemView.findViewById(R.id.emailsalon);
           thislayout=itemView.findViewById(R.id.thislay);


        }
    }

}