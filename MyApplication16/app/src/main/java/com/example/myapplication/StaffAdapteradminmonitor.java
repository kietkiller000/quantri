package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.constants.constants;
import com.example.myapplication.models.Request;
import com.example.myapplication.models.Staffcuthair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.dialogplus.DialogPlus;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class StaffAdapteradminmonitor extends RecyclerView.Adapter<StaffAdapteradminmonitor.Staffviewholder> implements Filterable {
    private List<Staffcuthair> mstaffcuthair;


    Activity mactivity;
    Context context;
    String saloonId;
    private Spinner spiner;

    String amount1, idstaff1;
    int SalaryS;
    int TotalSalary;
    private static final String[] work = {"Hair", "Nails", "Spa", "Facial", "Skin"};

    //    int RevenueDay, RevenueYear, Revenue1, Revenue2, Revenue3, Revenue4, Revenue5, Revenue6, Revenue7, Revenue8, Revenue9, Revenue10, Revenue11, Revenue12;
    //    String idUser; //sua
    private static final int SELECT_PHOTO = 100;
//    private static final String[] areas = {"Hà Nội", "Hải Phòng", "TP Hồ Chí Minh", "Đà Nẵng", "Cà Mau"};
    ;
    int[] images = new int[]{R.drawable.staff, R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5};

    public StaffAdapteradminmonitor(Context context, List<Staffcuthair> mListStaffcuthair, Activity mactivity, String uid) {
        this.context = context;
        mstaffcuthair = mListStaffcuthair;
//        this.mListSaloon01 = mListSaloon;
        this.saloonId = uid;
        this.mactivity = mactivity;
    }

    @NonNull
    @Override
    public Staffviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staffadminitem, parent, false);
        return new Staffviewholder(view);

    }

    public void CaculatorSalary(Staffcuthair staffcuthairCheck, String idnv, @NonNull Staffviewholder holder) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                constants.URL_getrequestbyidsalon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")) {


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
                                    Gson gson2 = new Gson();
                                    Type type1 = new TypeToken<ArrayList<Staffcuthair>>(){}.getType();
                                    ArrayList<Staffcuthair> staffList1 = gson2.fromJson(staffcuthairList1, type1);
                                    if (status.equals("Paid")) {
                                        for (Staffcuthair s : staffList1) {
                                            if (s.getIdstaff().equals(staffcuthairCheck.getIdstaff())) {
                                                if (staffList1.size() == 1) {
                                                    int amount2 = Integer.parseInt(amount);
                                                    SalaryS = SalaryS + ((amount2 * 10) / 100);
                                                } else {
                                                    int amount2 = Integer.parseInt(amount);
                                                    int sl = staffList1.size();
                                                    SalaryS = SalaryS + (((amount2 * 10) / 100) / sl);
                                                }
                                            }
                                        }
                                    }
                                    holder.tvSalary.setText(String.valueOf(SalaryS + staffcuthairCheck.getSalary() + " (Tính đến hiện tại)"));
//                                    holder.tvSalary.setText(String.valueOf(SalaryS + staffcuthairCheck.getSalary() + " (Tính đến hiện tại)"));
                                    i++;
                                }
//                                holder.tvSalary.setText(String.valueOf(SalaryS + " (Tính đến hiện tại)"));
                            }else{
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("idsalon",saloonId);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBindViewHolder(@NonNull Staffviewholder holder, @SuppressLint("RecyclerView") int position) {
        Staffcuthair staffcuthair = mstaffcuthair.get(position);
//        ShowRevenue(mListSaloon.get(position).getId_saloon());
//        Toast.makeText(context.getApplicationContext(),"revenue là" + Revenue12, Toast.LENGTH_SHORT).show();
        if (staffcuthair == null) {
            return;
        }
        idstaff1 = staffcuthair.getIdstaff();
        // mDatabase = FirebaseDatabase.getInstance().getReference().child("R_Request");
        holder.tvName.setText(staffcuthair.getName());
        holder.tvAddress.setText(staffcuthair.getAddress());
        holder.tvemail.setText(staffcuthair.getEmail());
        holder.tvmobile.setText(staffcuthair.getMobile());
        holder.tvSalaryBasic.setText(String.valueOf(staffcuthair.getSalary())+"đ");
        holder.tvexp.setText(staffcuthair.getExperience());

        CaculatorSalary(staffcuthair, staffcuthair.getIdstaff(), holder);
//        Toast.makeText(context.getApplicationContext(), "salary tổng là " + SalaryS, Toast.LENGTH_SHORT).show();
//        holder.tvSalary.setText(String.valueOf(TotalSalary));

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Getting Data");
        progressDialog.setMessage("Please wait");

        int min = 0;
        int max = 4;

        if (staffcuthair.getImage() == null) {


            //   Toast.makeText(context, "aka", Toast.LENGTH_SHORT).show();
            Random r = new Random();
            final int i1 = r.nextInt(max - min + 1) + min;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.sphoto.setImageDrawable(context.getDrawable(images[i1]));

                // Toast.makeText(context, "akssssa", Toast.LENGTH_SHORT).show();
            }

        } else {
//
//            Picasso.get()
//                    .load(staffcuthair.getImage())
//                    .placeholder(R.drawable.icon_loading)
//                    .error(R.drawable.staff)
//                    .into(holder.sphoto);

        }
        progressDialog.dismiss();

//        holder.sphoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (staffcuthair.getImage() == null) {
//                    Intent intent = new Intent(context, Changeimage.class);
//                    intent.putExtra("photouri", "");
//                    intent.putExtra("saloonid", staffcuthair.);
//                    view.getContext().startActivity(intent);
//                    return;
//                }
//                Intent intent = new Intent(context, Changeimage.class);
//
//                intent.putExtra("photouri", saloon.getImage());
//                intent.putExtra("saloonid", saloon.getId_saloon());
//
//
//                view.getContext().startActivity(intent);
//            }
//        });

//        holder.btnHistoryWork.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, WorkscheduleaOfeachStaffctivity.class);
//                intent.putExtra("staff1", staffcuthair);
//                context.startActivity(intent);
//            }
//        });

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tvName.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Delete data cant be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                                constants.URL_DeleteWorkStaffByID,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            Toast.makeText(context.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(AddSaloonActivity.this, Adminsalonprofile.class));
//                                progressDialog.dismiss();
                                            mstaffcuthair.remove(position);
                                            notifyDataSetChanged();
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
                                        if (error instanceof TimeoutError) {
                                            Toast.makeText(context.getApplicationContext(), "timeout", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof NoConnectionError) {
                                            Toast.makeText(context.getApplicationContext(), "noconnect", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof AuthFailureError) {
                                            Toast.makeText(context.getApplicationContext(), "authfailure", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof NetworkError) {
                                            Toast.makeText(context.getApplicationContext(), "network", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof ServerError) {
                                            Toast.makeText(context.getApplicationContext(), "sever", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof ParseError) {
                                            Toast.makeText(context.getApplicationContext(), "parse", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("idstaff", staffcuthair.getIdstaff());
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.tvName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, WorkscheduleaOfeachStaffctivity.class);
//                intent.putExtra("staff1", staffcuthair);
//                context.startActivity(intent);
//            }
//        });

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            String type;

            @Override
            public void onClick(View v) {

                int n = Resources.getSystem().getDisplayMetrics().heightPixels;
                //  Toast.makeText(mactivity, String.valueOf(n), Toast.LENGTH_SHORT).show();
                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_staff_infor))
                        .setExpanded(true, n * 6/7)
                        .create();
                dialogPlus.show();
                View view = dialogPlus.getHolderView();
                spiner = view.findViewById(R.id.spinnerwork);
                EditText name = view.findViewById(R.id.name_staff_update);
                EditText mobile = view.findViewById(R.id.mobile_staff_update);
                EditText address = view.findViewById(R.id.staff_address_update);
                EditText email = view.findViewById(R.id.staff_email_update);
                EditText password = view.findViewById(R.id.password_staff_update);
                EditText salary = view.findViewById(R.id.staff_salary_update);
                EditText experience = view.findViewById(R.id.staff_experience_update);
                EditText idsalon = view.findViewById(R.id.idsaloon_staff_update);

                Button btnCancel = view.findViewById(R.id.bt_cancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPlus.dismiss();
                    }
                });
                Button btnupdate = view.findViewById(R.id.btn_saloon_update);
                name.setText(staffcuthair.getName());
                mobile.setText(staffcuthair.getMobile());
                address.setText(staffcuthair.getAddress());
                email.setText(staffcuthair.getEmail());
                password.setText(staffcuthair.getPassword());
                salary.setText(String.valueOf(staffcuthair.getSalary()));
                experience.setText(staffcuthair.getExperience());
                idsalon.setText(staffcuthair.getIdsaloon());
                idsalon.setEnabled(false);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, work);
                int spinerselect = adapter.getPosition(staffcuthair.getWork());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spiner.setAdapter(adapter);
                spiner.setSelection(spinerselect);
                spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //    Toast.makeText(context, areas[i], Toast.LENGTH_SHORT).show();
                        type = work[i];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

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
//                        progressDialog.show();
//                        progressDialog.setTitle("Update Data");
//                        progressDialog.setMessage("Please wait");
//                        progressDialog.setCancelable(false);
                        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                                constants.URL_updateInfoWorkStaff,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        holder.tvName.setText(name.getText());
                                        holder.tvAddress.setText(address.getText());
                                        holder.tvemail.setText(email.getText());
                                        holder.tvmobile.setText(mobile.getText());
                                        holder.tvSalaryBasic.setText(String.valueOf(salary.getText())+"đ");
                                        holder.tvexp.setText(experience.getText());
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if (jsonObject.getBoolean("error")==false) {
                                                Toast.makeText(context.getApplicationContext(), "Update thành công !", Toast.LENGTH_LONG).show();
                                                progressDialog.dismiss();
//                                            Intent intent = new Intent(getApplicationContext(), Staffmanageractivity.class);
//                                            intent.putExtra("uidsaloon", uidsaloon);
//                                            startActivity(intent);
//                                            finishAffinity();
                                            }else {
                                                Toast.makeText(context.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                                progressDialog.dismiss();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        if (error instanceof TimeoutError) {
                                            Toast.makeText(context.getApplicationContext(), "timeout", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof NoConnectionError) {
                                            Toast.makeText(context.getApplicationContext(), "noconnect", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof AuthFailureError) {
                                            Toast.makeText(context.getApplicationContext(), "authfailure", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof NetworkError) {
                                            Toast.makeText(context.getApplicationContext(), "network", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof ServerError) {
                                            Toast.makeText(context.getApplicationContext(), "sever", Toast.LENGTH_LONG).show();
                                        } else if (error instanceof ParseError) {
                                            Toast.makeText(context.getApplicationContext(), "parse", Toast.LENGTH_LONG).show();
                                        }
                                        Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                        Toast.makeText(context.getApplicationContext(), staffcuthair.getIdstaff(), Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("idstaff",staffcuthair.getIdstaff());
                                params.put("name", name.getText().toString());
                                params.put("email", email.getText().toString());
                                params.put("address", address.getText().toString());
                                params.put("mobile", mobile.getText().toString());
                                params.put("password", password.getText().toString());
                                params.put("experience", experience.getText().toString());
                                params.put("salary", salary.getText().toString());
                                params.put("idsaloon", idsalon.getText().toString());
                                params.put("work", type);
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);


                    }
                });


            }
        });


    }

    //    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String strSearch = charSequence.toString();
//                if (strSearch.isEmpty()) {
//                    mListSaloon = mListSaloon01;
//                } else {
//                    List<Saloon> list = new ArrayList<>();
//                    for (Saloon saloon : mListSaloon01) {
//                        if (saloon.getName().toLowerCase(Locale.ROOT).contains((strSearch.toLowerCase()))) {
//                            list.add(saloon);
//                        }
//                    }
//                    mListSaloon = list;
//                }
//                FilterResults fiterresult = new FilterResults();
//                fiterresult.values = mListSaloon;
//
//                return fiterresult;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                mListSaloon = (List<Saloon>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
    ArrayList<Request> requests = new ArrayList<>();


    @Override
    public int getItemCount() {
        if (mstaffcuthair != null) {
            return mstaffcuthair.size();
        }
        return 0;

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class Staffviewholder extends RecyclerView.ViewHolder {

        TextView tvName, tvAddress, tvemail, tvmobile, tvSalary,tvSalaryBasic,tvexp;
        CircleImageView sphoto;
        ImageView btnDetail, btndelete;
        Button btnHistoryWork;

        public Staffviewholder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txtstaffname);
            tvAddress = itemView.findViewById(R.id.txtaddressstaff);
            tvemail = itemView.findViewById(R.id.txtemailstaff);
            tvmobile = itemView.findViewById(R.id.txtmobilestaff);
            tvSalaryBasic = itemView.findViewById(R.id.txtSalaryBasic);
            tvSalary = itemView.findViewById(R.id.txtSalary);
            tvexp = itemView.findViewById(R.id.txtexp);
            sphoto = itemView.findViewById(R.id.profile_image_staff);
            btnDetail = itemView.findViewById(R.id.btndetailstaff);
            btndelete = itemView.findViewById(R.id.btndeletestaff);

        }
    }

}