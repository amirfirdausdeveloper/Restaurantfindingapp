package com.student.restaurantfindingapp.Owner.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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
import com.student.restaurantfindingapp.Customer.Activity.LoginCustomer;
import com.student.restaurantfindingapp.Customer.Activity.RestaurantDetails;
import com.student.restaurantfindingapp.Owner.Activity.LoginOwner;
import com.student.restaurantfindingapp.Owner.Activity.OwnerAddRestaurant;
import com.student.restaurantfindingapp.Owner.Activity.OwnerDashboard;
import com.student.restaurantfindingapp.Owner.Class.RestaurantClass;
import com.student.restaurantfindingapp.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapterOwner extends RecyclerView.Adapter<RestaurantListAdapterOwner.ProductViewHolder>  implements Filterable {

    private Context mCtx;
    Activity activity;
    public static List<RestaurantClass> productListClassList;
    public List<RestaurantClass> mData;
    private List<RestaurantClass> mDataListFiltered;

    public RestaurantListAdapterOwner(Context mCtx, List<RestaurantClass> senaraiSamanClassList, Activity activity) {
        this.mCtx = mCtx;
        this.mData = senaraiSamanClassList;
        this.activity = activity;
        this.productListClassList = senaraiSamanClassList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_adapter_restaurant_list_customer_owner, null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ProductViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final RestaurantClass restaurantClass = productListClassList.get(position);

        holder.textView_rname.setText(restaurantClass.getRname());
        holder.textView_raddress.setText(restaurantClass.getRaddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity)
                        .setCancelable(true)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Please choose edit or delete?")
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent next = new Intent(activity, OwnerAddRestaurant.class);
                                next.putExtra("status","edit");
                                next.putExtra("rname",restaurantClass.getRname());
                                next.putExtra("raddress",restaurantClass.getRaddress());
                                next.putExtra("rmenu",restaurantClass.getRmenu());
                                next.putExtra("rmeals",restaurantClass.getRmeals());
                                next.putExtra("gambar1",restaurantClass.getGambar1());
                                next.putExtra("gambar2",restaurantClass.getGambar2());
                                next.putExtra("mobile",restaurantClass.getMobile());

                                activity.startActivity(next);
                            }
                        }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRestaurant(restaurantClass.getOwnerid(),restaurantClass.getRname());
                    }
                }).show();

            }
        });

    }

    private void deleteRestaurant(String ownerid, final String rname) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("restaurant");
        mDatabase.orderByChild("ownerid").equalTo(LoginOwner.ownerEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot restaurant: dataSnapshot.getChildren()) {
                        if(rname.equals(restaurant.child("rname").getValue().toString())){
                            restaurant.getRef().removeValue();
                            Toast.makeText(activity,"Success Delete",Toast.LENGTH_LONG).show();
                            Intent next = new Intent(activity, OwnerDashboard.class);
                            activity.startActivity(next);
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

        TextView textView_rname,textView_raddress;
        public ProductViewHolder(View itemView) {
            super(itemView);

            textView_rname = itemView.findViewById(R.id.textView_rname);
            textView_raddress = itemView.findViewById(R.id.textView_raddress);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                List<RestaurantClass> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList.addAll(mData);

                    mDataListFiltered = filteredList;
                } else {
                    for (RestaurantClass row : mData) {
                        if (row.getRaddress().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mDataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productListClassList = (ArrayList<RestaurantClass>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}