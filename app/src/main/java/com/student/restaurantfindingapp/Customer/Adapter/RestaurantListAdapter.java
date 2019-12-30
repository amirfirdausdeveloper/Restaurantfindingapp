package com.student.restaurantfindingapp.Customer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.student.restaurantfindingapp.Customer.Class.RestaurantClass;
import com.student.restaurantfindingapp.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ProductViewHolder>  implements Filterable {

    private Context mCtx;
    Activity activity;
    public static List<RestaurantClass> productListClassList;
    public List<RestaurantClass> mData;
    private List<RestaurantClass> mDataListFiltered;

    public RestaurantListAdapter(Context mCtx, List<RestaurantClass> senaraiSamanClassList, Activity activity) {
        this.mCtx = mCtx;
        this.mData = senaraiSamanClassList;
        this.activity = activity;
        this.productListClassList = senaraiSamanClassList;
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

        holder.textView_rname.setText(restaurantClass.getRname());
        holder.textView_raddress.setText(restaurantClass.getRaddress());
    }



    @Override
    public int getItemCount() {
        return productListClassList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textView_rname,textView_raddress;
        ImageView imageView_saman;

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