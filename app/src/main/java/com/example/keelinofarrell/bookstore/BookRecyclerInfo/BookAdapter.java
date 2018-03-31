package com.example.keelinofarrell.bookstore.BookRecyclerInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import static com.example.keelinofarrell.bookstore.R.layout.bookinfo;

/**
 * Created by keelin.ofarrell on 28/03/2018.
 */

public class BookAdapter extends RecyclerView.Adapter<BookViewHolders> {

    public List<BookObject>itemList;
    private Context context;

    public BookAdapter(List<BookObject> itemList, Context context){
        this.itemList = itemList;
        this.context = context;
    }

    public void updateList(List<BookObject> list){
        itemList = list;
        notifyDataSetChanged();
    }

    @Override
    public BookViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(bookinfo , null , false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        BookViewHolders hvh = new BookViewHolders(layoutView);
        return hvh;
    }

    @Override
    public void onBindViewHolder(BookViewHolders holder, final int position) {
        holder.mBookId.setText(itemList.get(position).getIsbn());
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

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
