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
import com.student.restaurantfindingapp.Admin.Activity.DashboardAdmin;
import com.student.restaurantfindingapp.Owner.Activity.LoginOwner;
import com.student.restaurantfindingapp.Owner.Activity.OwnerAddRestaurant;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoginOwner.ownerEmail.equals("") || LoginOwner.ownerEmail.equals(null)){
                    new AlertDialog.Builder(activity)
                            .setCancelable(true)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Are you sure want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteRestaurant(reviewClass.getCustomerid(),reviewClass.getOwnerid(),reviewClass.getComment());
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            }
        });

    }

    private void deleteRestaurant(String customerid, final String ownerid, final String comment) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("review");
        mDatabase.orderByChild("customerid").equalTo(customerid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot restaurant: dataSnapshot.getChildren()) {
                        if(ownerid.equals(restaurant.child("ownerid").getValue().toString()) && comment.equals(restaurant.child("comment").getValue().toString())){
                            restaurant.getRef().removeValue();
                            Toast.makeText(activity,"Success Delete",Toast.LENGTH_LONG).show();

                            if(LoginOwner.ownerEmail.equals("") || LoginOwner.ownerEmail.equals(null)){
                                Intent next = new Intent(activity, DashboardAdmin.class);
                                activity.startActivity(next);
                            }else {
                                Intent next = new Intent(activity, OwnerDashboard.class);
                                activity.startActivity(next);
                            }

                        }
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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