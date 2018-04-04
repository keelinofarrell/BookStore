package com.example.keelinofarrell.bookstore.CustomerRecyclerInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;

import java.util.List;

import static com.example.keelinofarrell.bookstore.R.layout.customerinfo;

/**
 * Created by keelin.ofarrell on 31/03/2018.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerViewHolders> {

    public List<CustomerObject>custList;
    private Context context;

    public  CustomerAdapter(List<CustomerObject>custList, Context context){
        this.custList = custList;
        this.context = context;

    }

    public void updateList(List<CustomerObject> list){
        custList = list;
        notifyDataSetChanged();
    }






    @Override
    public CustomerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(customerinfo , null , false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        CustomerViewHolders hvh = new CustomerViewHolders(layoutView);
        return hvh;
    }

    @Override
    public void onBindViewHolder(CustomerViewHolders holder, int position) {
        holder.mCustomerId.setText(custList.get(position).getCustomerId());
        if(custList.get(position).getFirstname() != null){
            holder.mFirstname.setText(custList.get(position).getFirstname());
        }
        if(custList.get(position).getLastname() != null){
            holder.mLastname.setText(custList.get(position).getLastname());
        }
        if(custList.get(position).getEmail() != null){
            holder.mEmail.setText(custList.get(position).getEmail());
        }
        if(custList.get(position).getProfileImageUrl()!= null){
            holder.mProfileImageName.setText(custList.get(position).getProfileImageUrl());
            Glide.with(context).load(custList.get(position).getProfileImageUrl()).into(holder.mProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return this.custList.size();
    }
}
