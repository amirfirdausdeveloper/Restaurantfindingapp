package com.student.restaurantfindingapp.Customer.Adapter;

import android.app.Activity;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.restaurantfindingapp.Customer.Activity.CustomerDashboard;
import com.student.restaurantfindingapp.Customer.Activity.LoginCustomer;
import com.student.restaurantfindingapp.Customer.Activity.RestaurantDetails;
import com.student.restaurantfindingapp.Customer.Class.RestaurantClass;
import com.student.restaurantfindingapp.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ProductViewHolder>  implements Filterable {

    private final DatabaseReference mDatabaseRating;
    private Context mCtx;
    Activity activity;
    public static List<RestaurantClass> productListClassList;
    public List<RestaurantClass> mData;
    private List<RestaurantClass> mDataListFiltered;
    double rating;
    int counts;
    boolean status;

    public RestaurantListAdapter(Context mCtx, List<RestaurantClass> senaraiSamanClassList, Activity activity) {
        this.mCtx = mCtx;
        this.mData = senaraiSamanClassList;
        this.activity = activity;
        this.productListClassList = senaraiSamanClassList;
        mDatabaseRating = FirebaseDatabase.getInstance().getReference("review");
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_adapter_restaurant_list_customer, null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ProductViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final RestaurantClass restaurantClass = productListClassList.get(position);

        getRatings(restaurantClass.getOwnerid(),restaurantClass.getRname(),holder.ratingBar,holder.textView_rating);
        holder.textView_rname.setText(restaurantClass.getRname());
        holder.textView_raddress.setText(restaurantClass.getRaddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoginCustomer.customerLogin == 0){
                    Intent next = new Intent(activity,LoginCustomer.class);
                    activity.startActivity(next);
                }else {
                    Intent next = new Intent(activity, RestaurantDetails.class);
                    next.putExtra("rname",restaurantClass.getRname());
                    next.putExtra("raddress",restaurantClass.getRaddress());
                    next.putExtra("rmenu",restaurantClass.getRmenu());
                    next.putExtra("rmeals",restaurantClass.getRmeals());
                    next.putExtra("ownerid",restaurantClass.getOwnerid());
                    next.putExtra("gambar1",restaurantClass.getGambar1());
                    next.putExtra("gambar2",restaurantClass.getGambar2());
                    next.putExtra("mobile",restaurantClass.getMobile());
                    activity.startActivity(next);
                }
            }
        });

    }


    private void getRatings(String ownerid, String rname, final RatingBar ratingBar, final TextView textView_rating){
        Log.d("rname", rname);
        rating = 0;
        counts = 0;
        status = false;
        mDatabaseRating.orderByChild("rname").equalTo(rname)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ratings: dataSnapshot.getChildren()) {
                    if(dataSnapshot.exists()){
                        status = true;
                        counts = counts + 1;
                        rating = rating + Double.parseDouble(ratings.child("rate").getValue().toString());
                    }else {
                        status = false;
                    }
                }

                if (status){
                    double total_ratingAll = rating / counts;
                    if(total_ratingAll >= 3){
                        Drawable drawable = ratingBar.getProgressDrawable();
                        drawable.setColorFilter(Color.parseColor("#AA8A00"), PorterDuff.Mode.SRC_ATOP);
                    }else{
                        Drawable drawable = ratingBar.getProgressDrawable();
                        drawable.setColorFilter(Color.parseColor("#BE312E"), PorterDuff.Mode.SRC_ATOP);
                    }

                    ratingBar.setRating(Float.parseFloat(String.valueOf(total_ratingAll)));
                    textView_rating.setText(String.valueOf(total_ratingAll));
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

        TextView textView_rname,textView_raddress,textView_rating;
        ImageView imageView_saman;
        RatingBar ratingBar;
        public ProductViewHolder(View itemView) {
            super(itemView);

            textView_rname = itemView.findViewById(R.id.textView_rname);
            textView_raddress = itemView.findViewById(R.id.textView_raddress);
            ratingBar = itemView.findViewById(R.id.rating);
            textView_rating = itemView.findViewById(R.id.textView_rating);

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