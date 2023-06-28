package com.example.myapplication;

import static android.graphics.Color.WHITE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.constants.constants;
import com.example.myapplication.models.Services;
import com.example.myapplication.utils.AppUtils;
import com.example.myapplication.utils.Constant;

import com.orhanobut.dialogplus.DialogPlus;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>{
    //public class ServiceAdapter extends FirebaseRecyclerAdapter< ServiceAdapter.ViewHolder> {

    Context context;
    ArrayList<Services> list = new ArrayList<>();
    String value;
    int selectedPosition=-1;

    String a ; //sua
    String saloonId ;
    private Spinner spiner;
    String formShowWhere;

    ArrayList<Services> listAService = new ArrayList<>();
    private static final String[] servs = {"Hair", "Nails", "Spa", "Facial", "Skin"};
    //    public ServiceAdapter(@NonNull FirebaseRecyclerOptions<Services> options) { //sua
//        super(options);
//    } //sua
    public ServiceAdapter(Context context, ArrayList<Services> list, String value , String uid, String formShowWhere){
        this.context = context;
        this.list = list;
        this.value = value;

        this.saloonId = uid;
        this.formShowWhere = formShowWhere;
    }
    //    private void getIntentData(Context context) { //sua
//        saloonId = intent.getStringExtra(Constant.ARGS_SALOON);
//    } //sua
//    public interface onServiceAdd{
//        void onServeiceAdd(Services services, boolean isRemove);
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activelist_card,null,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Services services = list.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        holder.txtPrice.setText(decimalFormat.format(Integer.parseInt(services.getPrice())) +"đ");
        holder.txtServicename.setText(services.getName());
        holder.txtTypeSV.setText(services.getType());

//        Toast.makeText(context, saloonId + "ở đây", Toast.LENGTH_SHORT).show();
        if (services.isSelected()){
            holder.btnadd.setText("Remove");
            holder.btnadd.setTextColor(WHITE);
            holder.btnadd.setBackgroundResource(R.drawable.activebutton);
        } else if(!services.isSelected()){
            holder.btnadd.setText("Add");
            holder.btnadd.setTextColor(R.color.background_color);
            holder.btnadd.setBackgroundResource(R.drawable.loginbutton_selector);
        }
        else
        {

        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() { //sua
            String type;
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(context)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_service_info))
                        .setExpanded(true,1500)
                        .create();

                dialogPlus.show();
                View view =dialogPlus.getHolderView();
                EditText name_service =view.findViewById(R.id.name_custom_update);
                EditText price =view.findViewById(R.id.price_custom_update);
                Button btn_update = view.findViewById(R.id.btn_custom_update);

                spiner = view.findViewById(R.id.servicespinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, servs);
                int spinerselect = adapter.getPosition(services.getType());

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spiner.setAdapter(adapter);
                spiner.setSelection(spinerselect);
                spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //    Toast.makeText(context, areas[i], Toast.LENGTH_SHORT).show();
                        type = servs[i];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                name_service.setText(services.getName());
                price.setText(services.getPrice());
                dialogPlus.show();

//                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saloons").child("Services"); //sua
//                reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for(DataSnapshot datas: dataSnapshot.getChildren()){
//                            String name = datas.child("ratting").getValue().toString();
//                            String strReview = datas.child("review").getValue().toString();
//                            String struser = datas.child("userName").getValue().toString();
//                            //String type = datas.child("type").getValue().toString();
//
//                            Review review = new Review();
//                            review.setRatting(rating);
//                            review.setReview(strReview);
//                            reviewsList.add(review);
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                }); //sua

//                Toast.makeText(context, services.getIdservice() + "ở đây", Toast.LENGTH_SHORT).show();


                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(type.equals(services.getType())&&price.getText().toString().equals(services.getPrice())&&name_service.getText().toString().equals(services.getName()))
                        {
                            dialogPlus.dismiss();
                            return;
                        }








                        RequestQueue requestQueue = Volley.newRequestQueue(holder.itemView.getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                constants.URL_updateservice,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(response);


                                            if(!jsonObject.getBoolean("error")) {


//                                        Toast.makeText(LoginActivity.this,jsonObject.getString("id_adminSaloons"), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(holder.itemView.getContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                                                services.setName(name_service.getText().toString());
                                                services.setPrice(price.getText().toString());
                                                services.setType(type);
                                                notifyDataSetChanged();
                                                dialogPlus.dismiss();


                                            }else{
                                                Toast.makeText(holder.itemView.getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                            }

                                        } catch (JSONException e) {
                                            dialogPlus.dismiss();
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        dialogPlus.dismiss();
                                        Toast.makeText(holder.itemView.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("nameservice", name_service.getText().toString());
                                params.put("price", price.getText().toString());
                                params.put("type", type);
                                params.put("id_service", services.getIdservice());

                                return params;
                            }
                        };

                        requestQueue.add(stringRequest);
                    }
                });
            }
        }); //sua
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(holder.txtServicename.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Delete data cant be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        FirebaseDatabase.getInstance().getReference().child("Saloons").child(saloonId).child("Services")
//                                .child(services.getIdservice().toString()).removeValue();





                        RequestQueue requestQueue = Volley.newRequestQueue(holder.itemView.getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                constants.URL_deleteservice,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(response);

//                                            list.remove(services);
                                            if(!jsonObject.getBoolean("error")) {


//                                        Toast.makeText(LoginActivity.this,jsonObject.getString("id_adminSaloons"), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(holder.itemView.getContext(), "thanh cong", Toast.LENGTH_SHORT).show();

                                                list.remove(position);
                                                notifyDataSetChanged();



                                            }else{
                                                Toast.makeText(holder.itemView.getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                            }

                                        } catch (JSONException e) {

                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Toast.makeText(holder.itemView.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("id_service", services.getIdservice());

                                return params;
                            }
                        };

                        requestQueue.add(stringRequest);



                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.txtServicename.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
//        holder.btnaddserviceAdMGR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, AddServiceActivity.class);
//                intent.putExtra(Constant.ARGS_SALOON, saloonId);
//                context.startActivity(intent);
//            }
//        });
//        if (!services.isSelected()){ //sua
//            holder.btnadd.setText("Add");
//            holder.btnadd.setBackgroundResource(R.drawable.loginbutton_selector);
//        } else {
//            holder.btnadd.setText("Remove");
//            holder.btnadd.setBackgroundResource(R.drawable.activebutton);
//            holder.btnadd.setTextColor(WHITE);
//        }
//
//        if(selectedPosition==position) {
//            holder.btnadd.setText("Added");
//            holder.btnadd.setEnabled(false);
//        } else {
//            holder.btnadd.setText("Add");
//        } //sua

        holder.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  selectedPosition=position;
                boolean isRemove = false;
                if (holder.btnadd.getText().equals("Remove")){
                    isRemove = true;
                    services.setSelected(false);
                    holder.btnadd.setText("Add");
                } else {
                    isRemove = false;
                    services.setSelected(true);
                    holder.btnadd.setText("Remove");
                }

                notifyDataSetChanged();

            }
        });
        switch (services.getType()){
            case Constant.Hair:
                holder.sphoto.setImageDrawable(context.getDrawable(R.drawable.hair));
                break;
            case Constant.Spa:
                holder.sphoto.setImageDrawable(context.getDrawable(R.drawable.spa));
                break;
            case Constant.Facial:
                holder.sphoto.setImageDrawable(context.getDrawable(R.drawable.facial));
                break;
            case Constant.Nails:
                holder.sphoto.setImageDrawable(context.getDrawable(R.drawable.nails));
                break;
            case Constant.Skin:
                holder.sphoto.setImageDrawable(context.getDrawable(R.drawable.skin));
                break;
        }

//        if (value.equals("user")){
//            holder.llAdd.setVisibility(View.VISIBLE);
//            holder.llEditDelete.setVisibility(View.GONE);
//        } else if (value.equals("saloon")){
//            holder.llAdd.setVisibility(View.GONE);
////            holder.llEditDelete.setVisibility(View.GONE);
//            holder.llEditDelete.setVisibility(View.VISIBLE);
//        } else if (value.equals("app")){
//            holder.llAdd.setVisibility(View.GONE);
////            holder.llEditDelete.setVisibility(View.GONE);
//            holder.llEditDelete.setVisibility(View.VISIBLE);
//        }

        if (value.equals("u")==false){
            holder.btnEdit.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);
            if(formShowWhere!=null && formShowWhere.equals("saloonBook")){

            }
            if(formShowWhere!=null && formShowWhere.equals("fromSalonDash")){
                holder.llAdd.setVisibility(View.GONE);
            }
            if(formShowWhere!=null && formShowWhere.equals("fromSalonDashtoBook")){
                holder.llEditDelete.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtServicename;
        TextView txtPrice,txtTypeSV;
        ImageView sphoto;
        Button btnadd,btnEdit,btnDelete,btnaddserviceAdMGR;
        LinearLayout llAdd,llEditDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtServicename = itemView.findViewById(R.id.txtServicename);
            txtTypeSV = itemView.findViewById(R.id.txttype);

            sphoto = itemView.findViewById(R.id.sphoto);
            llAdd = itemView.findViewById(R.id.llAdd);
            llEditDelete = itemView.findViewById(R.id.llEditDelete);
            btnadd = itemView.findViewById(R.id.btnadd);
            btnEdit = itemView.findViewById(R.id.btnedit); //sua
            btnDelete = itemView.findViewById(R.id.btndelete);
//            btnaddserviceAdMGR = itemView.findViewById(R.id.addservicebtn); //sua141

        }
    }
}
