package com.example.keelinofarrell.bookstore.ReviewRecyclerInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import java.util.List;
import static com.example.keelinofarrell.bookstore.R.layout.reviewlayout;

/**
 * Created by keelin.ofarrell on 16/04/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolders> {

    public List<ReviewObject> itemList;
    private Context context;

    public ReviewAdapter(List<ReviewObject> itemList, Context context){
        this.itemList = itemList;
        this.context = context;
    }

    public void updateList(List<ReviewObject> list){
        itemList = list;
        notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(reviewlayout , null , false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ReviewViewHolders hvh = new ReviewViewHolders(layoutView);
        return hvh;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolders holder, final int position) {
        if(itemList.get(position).getFirstname()!=null){
            holder.mFirstname.setText(itemList.get(position).getFirstname());
        }
        if(itemList.get(position).getLastname()!=null){
            holder.mLastname.setText(itemList.get(position).getLastname());
        }
        if(itemList.get(position).getDate()!=null){
            holder.mDate.setText(itemList.get(position).getDate());
        }
        if(itemList.get(position).getRating()!=null){
            holder.mRating.setText(itemList.get(position).getRating());
        }
            if(itemList.get(position).getComment()!=null){
            holder.mComment.setText(itemList.get(position).getComment());
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
