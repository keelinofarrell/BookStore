package com.example.keelinofarrell.bookstore.PurchasesRecyclerInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.keelinofarrell.bookstore.BookRecyclerInfo.BookObject;
import com.example.keelinofarrell.bookstore.BookRecyclerInfo.BookViewHolders;

import java.util.List;

import static com.example.keelinofarrell.bookstore.R.layout.bookinfo;

/**
 * Created by keelin.ofarrell on 16/04/2018.
 */

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseViewHolders> {

    public List<PurchaseObject> itemList;
    private Context context;

    public PurchaseAdapter(List<PurchaseObject> itemList, Context context){
        this.itemList = itemList;
        this.context = context;
    }

    public void updateList(List<PurchaseObject> list){
        itemList = list;
        notifyDataSetChanged();
    }

    @Override
    public PurchaseViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(bookinfo , null , false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        PurchaseViewHolders hvh = new PurchaseViewHolders(layoutView);
        return hvh;
    }

    @Override
    public void onBindViewHolder(PurchaseViewHolders holder, final int position) {
        holder.mBookId.setText(itemList.get(position).getBookId());
        if(itemList.get(position).getTitle()!=null){
            holder.mTitle.setText(itemList.get(position).getTitle());
        }
        if(itemList.get(position).getAuthor()!=null){
            holder.mAuthor.setText(itemList.get(position).getAuthor());
        }
        if(itemList.get(position).getPrice()!=null){
            holder.mPrice.setText(itemList.get(position).getPrice());
        }
        if(itemList.get(position).getProfileImageUrl()!= null){
            holder.mBookImageName.setText(itemList.get(position).getProfileImageUrl());
            Glide.with(context).load(itemList.get(position).getProfileImageUrl()).into(holder.mBookImage);
        }
        if(itemList.get(position).getDate()!=null){
            holder.mDate.setText(itemList.get(position).getDate());
        }

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

}
