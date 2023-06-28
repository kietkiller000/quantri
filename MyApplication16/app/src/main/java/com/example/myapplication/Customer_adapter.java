
package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.User;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Customer_adapter extends RecyclerView.Adapter<Customer_adapter.CustomerViewholder> implements Filterable {
    private List<User> mlistuserchoose;
    private List<User> mlistuserchoose1;

    private String idadmin;
    Context context;
    Activity mactivity;
    public Customer_adapter(List<User> mlistuserchoose) {
        this.mlistuserchoose = mlistuserchoose;

        this.mlistuserchoose1=mlistuserchoose;
    }

    public Customer_adapter(Context context, List<User> mListUser, Activity mactivity, String uid) {
        this.context = context;
        this.mlistuserchoose = mListUser;
        this.mlistuserchoose1=mListUser;
        this.idadmin = uid;
        this.mactivity = mactivity;
    }

    @NonNull
    @Override
    public CustomerViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userinforform2,parent,false);
        return new CustomerViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewholder holder, int position) {
        User user=mlistuserchoose.get(position);
        if(user==null)
        {
            return;
        }

        holder.name.setText(user.getFullname());
        holder.mobile.setText(user.getMobile());
//        holder.address.setText(user.getAdderess());
        holder.email.setText(user.getEmail());
//        holder.choosebutton.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, activity_customer_detail.class);
                intent.putExtra("id_cus",user.getId_user() );
                intent.putExtra("mobile_cus",user.getMobile() );
                intent.putExtra("CustomerInfo",user);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(mlistuserchoose!=null)
        {
            return mlistuserchoose.size();
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
                    mlistuserchoose= mlistuserchoose1;
                } else {
                    List<User> list = new ArrayList<>();
                    for (User user : mlistuserchoose1) {
                        if (user.getFullname().toLowerCase(Locale.ROOT).contains((strSearch.toLowerCase()))
                        || user.getMobile().toLowerCase(Locale.ROOT).contains((strSearch.toLowerCase()))
                        || user.getEmail().toLowerCase(Locale.ROOT).contains((strSearch.toLowerCase()))) {
                            list.add(user);
                        }
                    }
                    mlistuserchoose = list;
                }
                FilterResults fiterresult = new FilterResults();
                fiterresult.values = mlistuserchoose;

                return fiterresult;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mlistuserchoose = (List<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class CustomerViewholder extends RecyclerView.ViewHolder {
        TextView name, email, mobile, address;
        ImageButton btnsearch;
        public CustomerViewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txtusername);
            mobile=itemView.findViewById(R.id.txtmobile);
            email = itemView.findViewById(R.id.email);
            btnsearch = itemView.findViewById(R.id.ic_search1);
        }
    }
}
