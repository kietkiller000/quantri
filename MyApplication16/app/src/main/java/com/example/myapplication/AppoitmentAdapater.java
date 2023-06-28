package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.myapplication.utils.AppUtils;
import com.example.myapplication.utils.Constant;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AppoitmentAdapater extends RecyclerView.Adapter<AppoitmentAdapater.ViewHolder> implements Filterable {
    Context context;
    ArrayList<Request> list = new ArrayList<>();
    ArrayList<Request> listrequest1 = new ArrayList<>();
    //    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users"); //sua

    String loginKey;
    String nameUser;
    String loginId;
    String namesl1;

    public AppoitmentAdapater(Context context, ArrayList<Request> list, String loginKey, String namesl) {
        this.context = context;
        this.list = list;
        this.listrequest1 = list;
        this.loginKey = loginKey;
        this.namesl1 = namesl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appo_card, null, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Request request = list.get(position);
//        String un = getUsername(request.getId_order());
        holder.txtuser.setText(request.getName()); //sua
        holder.txtamount.setText(AppUtils.convertMoney(request.getAmount()) + "đ");
        holder.txtTime.setText(request.getTime_begin() + " đến " + request.getTime_finish());
        holder.status.setText(request.getStatus());
        holder.txtsaloonname.setText(namesl1);
        holder.txtmobile.setText(request.getMobile());
        holder.txtrectime.setText(request.getNote());
        holder.txtDate.setText(request.getDate());
//        holder.paystatus.setText(request.get);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, BillDetail_activity.class);
//                intent.putExtra("name_staff", request.getName());
//                intent.putExtra("email_staff", request.getEmail());
//                intent.putExtra("mobile_staff", request.getMobile());
//                intent.putExtra("address_staff", request.getAddres());
//
//                intent.putExtra("howorder", request.getOrder());
//                intent.putExtra("howpayment", request.getPayment());
//                //       intent.putExtra("selectServicesList",selectServicesList);
//                intent.putExtra("status", request.getStatus());
//                intent.putExtra("Saloonid", request.getId_saloon());
////                intent.putExtra("Adminid", );
////                intent.putExtra("Workinghr", request.get);
//                intent.putExtra("date", request.getDate());
//
//                intent.putExtra("time_begin", request.getTime_begin());
//                intent.putExtra("time_finish", request.getTime_finish());
//                intent.putExtra("selectServicesList", (Serializable) request.getServices());
//                intent.putExtra("totalmoney", request.getAmount());
//                context.startActivity(intent);
//            }
//        });

        holder.rvServices.setLayoutManager(new LinearLayoutManager(context));
        ServiceAdapter adapter = new ServiceAdapter(context, request.getServices(), "u", null, "saloonBook"); //cái dịch vụ
        adapter.notifyDataSetChanged();
        holder.rvServices.setAdapter(adapter);
//        //sua
//        String iduser_order = request.getUserID();
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(request.getUserID());
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User u = snapshot.getValue(User.class);
//               nameUser = u.getUserName().toString();
////                Toast.makeText(context.getApplicationContext(), nameUser, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        //sua

        if (loginKey.equalsIgnoreCase(Constant.USER)) {
            holder.llOptions.setVisibility(View.GONE);
//            holder.txtuser.setText(request.getSaloon().getName());
//            String a = getUsername(request);
//            holder.txtuser.setText(a);
        } else {
            if (request.getStatus().equalsIgnoreCase("Pending")) {
                holder.btnDone.setVisibility(View.GONE);
                holder.btnInPosition.setVisibility(View.VISIBLE);
                holder.btnCancel.setVisibility(View.VISIBLE);

//                String a = getUsername(request);
//                holder.txtuser.setText(a);
            }
//            else if(request.getStatus().equalsIgnoreCase("Pending")) {
//
//                holder.llOptions.setVisibility(View.VISIBLE);
//                holder.btnConfirm.setVisibility(View.VISIBLE);
//                holder.btnDone.setVisibility(View.GONE);
//                holder.btnCancel.setVisibility(View.GONE);
//
////                String a = getUsername(request);
////                holder.txtuser.setText(a);
//            }
            else if (request.getStatus().equalsIgnoreCase("InPosition")) {

                holder.llOptions.setVisibility(View.VISIBLE);
                holder.btnDone.setVisibility(View.VISIBLE);
                holder.btnInPosition.setVisibility(View.GONE);
                holder.btnCancel.setVisibility(View.VISIBLE);

            }
//            else if (request.getStatus().equalsIgnoreCase("Decline"))
//            {    holder.btnDone.setVisibility(View.GONE);
//                holder.btnCancel.setVisibility(View.GONE);
//                holder.btnConfirm.setVisibility(View.GONE);
////                holder.btnDecline.setVisibility(View.VISIBLE);
////                holder.btnDecline.setEnabled(false);
//
////                holder.btnDecline.setText("Declined");
////                holder.llOptions.setVisibility(View.VISIBLE);
//
////                String a = getUsername(request);
////                holder.txtuser.setText(a);
//            }
            else if (request.getStatus().equalsIgnoreCase("Paid")) {
                holder.btnDone.setVisibility(View.VISIBLE);
                holder.btnCancel.setVisibility(View.GONE);
                holder.btnInPosition.setVisibility(View.GONE);
                holder.btnDone.setText("Customer Paid");
            } else if (request.getStatus().equalsIgnoreCase("Cancel")) {
                holder.btnCancel.setVisibility(View.VISIBLE);
                holder.btnDone.setVisibility(View.GONE);
                holder.btnInPosition.setVisibility(View.GONE);
                holder.btnCancel.setText("Customer Canceled");
                holder.btnCancel.setEnabled(false);
            }
        }
        holder.btnDeleteBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginKey = AppUtils.getStringValue(context, Constant.LoginKey);
//                mDatabase = FirebaseDatabase.getInstance().getReference().child("R_Request").child(request.getId_order()).removeValue();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Delete data cant be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean isSaloon = AppUtils.getValue(context.getApplicationContext(), Constant.ISSALON);
                        if (!loginKey.equals("Adminsaloons")) {
//                            loginId = AppUtils.getStringValue(context.getApplicationContext(), Constant.USERID);
//                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Saloons").child(loginId);
//                            mDatabase.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataS) {
//                                    if (dataS.hasChild("SaloonName") == false || dataS.hasChild("idAdminSaloons") == false || dataS.hasChild("Area") == false || dataS.hasChild("Mobile") == false || dataS.hasChild("SaloonAddress") == false || dataS.hasChild("Email") == false || dataS.hasChild("Password") == false || dataS.hasChild("Idsaloon") == false) {
//                                        return;
//                                    }
//                                    String idadminsl = dataS.child("idAdminSaloons").getValue().toString();
//                                    mDatabase1.child("R_Request").child(idadminsl).child(request.getId_saloon()).child(request.getId_order()).removeValue()
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void unused) {
//                                                    Toast.makeText(builder.getContext(), "Delete Sucess !!", Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
                            Toast.makeText(builder.getContext(), "Bạn không có quyền này !", Toast.LENGTH_SHORT).show();
                        } else {
                            loginId = AppUtils.getStringValue(context.getApplicationContext(), Constant.ADMINSALOONSID);
                            Toast.makeText(context.getApplicationContext(), loginId + "và" + loginKey, Toast.LENGTH_SHORT).show();
                            RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                                    constants.URL_DeleteRequestByID,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                Toast.makeText(context.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                                list.remove(position);
                                                notifyDataSetChanged();
//                                startActivity(new Intent(AddSaloonActivity.this, Adminsalonprofile.class));
//                                progressDialog.dismiss();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context.getApplicationContext(), "Lỗi", Toast.LENGTH_LONG).show();
//                            progressDialog.dismiss();
                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id_order", request.getId_order());
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
//                        FirebaseDatabase.getInstance().getReference().child("R_Request")
//                                .child(request.getId_order().toString()).removeValue()
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        Toast.makeText(builder.getContext(), "Delete Sucess !!", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(builder.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });



        //                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(); //sua
//        boolean isSaloon = AppUtils.getValue(context.getApplicationContext(), Constant.ISSALON);
//        if(isSaloon){
//            loginId = AppUtils.getStringValue(context.getApplicationContext(), Constant.USERID);
//            mDatabase = FirebaseDatabase.getInstance().getReference().child("Saloons").child(loginId);
//            mDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataS) {
//                    if (dataS.hasChild("SaloonName") == false || dataS.hasChild("idAdminSaloons") == false || dataS.hasChild("Area") == false || dataS.hasChild("Mobile") == false || dataS.hasChild("SaloonAddress") == false || dataS.hasChild("Email") == false || dataS.hasChild("Password") == false || dataS.hasChild("Idsaloon") == false) {
//                        return;
//                    }
//                    String idadminsl = dataS.child("idAdminSaloons").getValue().toString();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//        else{
//            loginId = AppUtils.getStringValue(context.getApplicationContext(), Constant.USERID);
//        }




//        holder.btnDecline.setOnClickListener(new View.OnClickListener()
//
//    {
//        @Override
//        public void onClick (View v){
//        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        request.setStatus("Decline");
//
//
//        //mDatabase.child("R_Request").child(request.getUserID()).child(request.getKey()).setValue(request);
//        holder.btnConfirm.setVisibility(View.GONE);
//        holder.btnDecline.setText("Declined");
//
//            LayoutInflater li = LayoutInflater.from(v.getRootView().getContext());
//            View promptsView = li.inflate(R.layout.rectime, null);
//
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                    v.getRootView().getContext());
//            alertDialogBuilder.setTitle("Next Recommend Time");
//            alertDialogBuilder.setMessage("Enter Time and Date");
//            // set alert_dialog.xml to alertdialog builder
//            alertDialogBuilder.setView(promptsView);
//
//            final EditText rect = (EditText) promptsView.findViewById(R.id.etrectime);
//            //alertDialog.setView(input);
//
//            // set dialog message
//            alertDialogBuilder
//                    .setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // get user input and set it to result
//                            // edit text
//                            request.setRecommendtime(rect.getText().toString());
//                            mDatabase.child("R_Request").child(request.getUserID()).child(request.getKey()).setValue(request);
//
//                            //Toast.makeText(context.getApplicationContext(), "Entered: "+rect.getText().toString(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            // show it
//            alertDialog.show();
//
//    }
//
//    });
//        holder.txtrectime.setText(request.getRecommendtime());
    }

    public void setStatuss(Request requ) {
//        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference();
//        user = FirebaseAuth.getInstance().getCurrentUser();
        boolean isSaloon = AppUtils.getValue(context.getApplicationContext(), Constant.ISSALON);
        if (isSaloon) {
            loginId = AppUtils.getStringValue(context.getApplicationContext(), Constant.USERID);











            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                    constants.URL_updaterequest,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(!jsonObject.getBoolean("error")) {









//
//                                                        for (DataSnapshot dataSnapshot1 : snapshot3.getChildren()) {
//                                                            if (dataSnapshot1.hasChild("date") == false || dataSnapshot1.hasChild("time_begin") == false || dataSnapshot1.hasChild("time_finish") == false) {
//                                                                Toast.makeText(Staff_order_activity.this, "null", Toast.LENGTH_SHORT).show();
//                                                                return;
//                                                            }
//
//
//
//                                                        }
////
//




                                }else{
                                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                }
//                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();

                    params.put("idrequest",requ.getId_order() );
                    params.put("status",requ.getStatus() );

                    return params;
                }
            };

            requestQueue.add(stringRequest);










//            mDatabase = FirebaseDatabase.getInstance().getReference().child("Saloons").child(loginId);
//            mDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataS) {
//                    if (dataS.hasChild("SaloonName") == false || dataS.hasChild("idAdminSaloons") == false || dataS.hasChild("Area") == false || dataS.hasChild("Mobile") == false || dataS.hasChild("SaloonAddress") == false || dataS.hasChild("Email") == false || dataS.hasChild("Password") == false || dataS.hasChild("Idsaloon") == false) {
//                        return;
//                    }
//                    String idadminsl = dataS.child("idAdminSaloons").getValue().toString();
//                    mDatabase1.child("R_Request").child(idadminsl).child(requ.getId_saloon()).child(requ.getId_order()).setValue(requ);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//            mDatabase.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataS, @Nullable String previousChildName) {
//                    if (dataS.hasChild("SaloonName") == false || dataS.hasChild("idAdminSaloons") == false || dataS.hasChild("Area") == false || dataS.hasChild("Mobile") == false || dataS.hasChild("SaloonAddress") == false || dataS.hasChild("Email") == false || dataS.hasChild("Password") == false || dataS.hasChild("Idsaloon") == false) {
//                        return;
//                    }
//                    String idadminsl = dataS.child("idAdminSaloons").getValue().toString();
//                    mDatabase1.child("R_Request").child(idadminsl).child(requ.getId_saloon()).child(requ.getId_order()).setValue(requ);
//                    if(list == null || list.isEmpty()){
//                        return;
//                    }
//                    for(int i=0;i<list.size();i++){
//                        if(requ.getId_order() == list.get(i).getId_order()){
//                            list.set(i,requ);
//                        }
//                    }
//                   notifyDataSetChanged();
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
        } else {
            loginId = AppUtils.getStringValue(context.getApplicationContext(), Constant.ADMINSALOONSID);

            // mDatabase1.child("R_Request").child(user.getUid()).child(requ.getId_saloon()).child(requ.getId_order()).setValue(requ);
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    list = listrequest1;
                } else {
                    ArrayList<Request> list1 = new ArrayList<>();
                    for (Request request : listrequest1) {
                        if (request.getName().toLowerCase(Locale.ROOT).contains((strSearch.toLowerCase()))) {
                            list1.add(request);
                        }else if(String.valueOf(request.getMobile()).contains(strSearch)){
                            list1.add(request);
                        }
                    }
                    list = list1;
                }
                FilterResults fiterresult = new FilterResults();
                fiterresult.values = list;

                return fiterresult;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<Request>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

//    public String getName(String id){
//        //sua
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User u = snapshot.getValue(User.class);
//                nameUser = u.getUserName().toString();
////                Toast.makeText(context.getApplicationContext(), nameUser, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(context.getApplicationContext(), "Không load đc Name User", Toast.LENGTH_SHORT).show();
//            }
//        });
//        //sua
//        return  nameUser;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtuser, txtamount,txtDate, txtTime, status, paystatus, txtrectime, txtsaloonname,txtmobile;
        RecyclerView rvServices;
        Button btnInPosition, btnDone, btnCancel;
        ImageButton btnDeleteBill;
        LinearLayout llOptions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtuser = itemView.findViewById(R.id.user);
            txtamount = itemView.findViewById(R.id.amount);
            txtTime = itemView.findViewById(R.id.time);
            txtDate = itemView.findViewById(R.id.txtDate);
            paystatus = itemView.findViewById(R.id.paystatus);
            rvServices = itemView.findViewById(R.id.rvServices);
            status = itemView.findViewById(R.id.status);
            txtrectime = itemView.findViewById(R.id.recomendtime);
            txtmobile = itemView.findViewById(R.id.mobile);
            txtsaloonname = itemView.findViewById(R.id.txtsaloonname);
            btnDeleteBill = itemView.findViewById(R.id.btn_deletebill);
            btnInPosition = itemView.findViewById(R.id.btnInPosition);
            btnDone = itemView.findViewById(R.id.btnDone);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            llOptions = itemView.findViewById(R.id.llOption);

        }
    }
}
