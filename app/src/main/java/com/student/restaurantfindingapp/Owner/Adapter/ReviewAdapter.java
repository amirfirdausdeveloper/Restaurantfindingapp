package com.student.restaurantfindingapp.Owner.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.restaurantfindingapp.Owner.Activity.LoginOwner;
import com.student.restaurantfindingapp.Owner.Activity.OwnerDashboard;
import com.student.restaurantfindingapp.Owner.Class.RestaurantClass;
import com.student.restaurantfindingapp.Owner.Class.ReviewClass;
import com.student.restaurantfindingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ProductViewHolder> {

    private Context mCtx;
    Activity activity;
    public static List<ReviewClass> productListClassList;
    public List<ReviewClass> mData;
    private List<ReviewClass> mDataListFiltered;

    public ReviewAdapter(Context mCtx, List<ReviewClass> senaraiSamanClassList, Activity activity) {
        this.mCtx = mCtx;
        this.mData = senaraiSamanClassList;
        this.activity = activity;
        this.productListClassList = senaraiSamanClassList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_adapter_owner_review, null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ProductViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final ReviewClass reviewClass = productListClassList.get(position);

        holder.textView_rname.setText(reviewClass.getRname());
        holder.textView_comment.setText(reviewClass.getComment());
        holder.rating.setRating(Float.parseFloat(reviewClass.getRate()));

    }

    @Override
    public int getItemCount() {
        return productListClassList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        RatingBar rating;
        TextView textView_rname,textView_comment;
        public ProductViewHolder(View itemView) {
            super(itemView);

            textView_rname = itemView.findViewById(R.id.textView_rname);
            textView_comment = itemView.findViewById(R.id.textView_comment);
            rating = itemView.findViewById(R.id.rating);

        }
    }


}