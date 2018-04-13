package com.example.keelinofarrell.bookstore.ShoppingRecyclerInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.keelinofarrell.bookstore.BookRecyclerInfo.BookViewHolders;

import java.util.List;

import static com.example.keelinofarrell.bookstore.R.layout.bookinfo;
import static com.example.keelinofarrell.bookstore.R.layout.cartitem;

/**
 * Created by keelin.ofarrell on 13/04/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolders> {

    public List<ProductObject>itemList;
    private Context context;

    public ProductAdapter(List<ProductObject>itemList, Context context){
        this.itemList = itemList;
        this.context = context;
    }

    public void updateList(List<ProductObject> list){
        itemList = list;
        notifyDataSetChanged();
    }



    @Override
    public ProductViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(cartitem , null , false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ProductViewHolders hvh = new ProductViewHolders(layoutView);
        return hvh;
    }

    @Override
    public void onBindViewHolder(ProductViewHolders holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
