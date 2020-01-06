package com.student.restaurantfindingapp.Owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.restaurantfindingapp.Customer.Activity.LoginCustomer;
import com.student.restaurantfindingapp.Owner.Class.RestaurantClass;
import com.student.restaurantfindingapp.Owner.Adapter.RestaurantListAdapterOwner;
import com.student.restaurantfindingapp.R;

import java.util.ArrayList;
import java.util.List;

public class OwnerViewRestaurant extends AppCompatActivity {
    RecyclerView recyclerView;
    List<RestaurantClass> restaurantClasses;
    private RestaurantListAdapterOwner adapter;
    private DatabaseReference mDatabaseSaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_view_restaurant);

        //SET FIREBASE
        mDatabaseSaman = FirebaseDatabase.getInstance().getReference("restaurant");

        //DECLARE
        recyclerView = findViewById(R.id.recyclerView);

        //BACK
        ImageView imageView_back = findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDetails();
    }

    //GET LIST RESTAURANT
    private void getDetails(){
        recyclerView.setHasFixedSize(false);
        restaurantClasses = new ArrayList<>();
        adapter = new RestaurantListAdapterOwner(getApplicationContext(), restaurantClasses, OwnerViewRestaurant.this);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(adapter);

        mDatabaseSaman.orderByChild("ownerid").equalTo(LoginOwner.ownerEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot restaurant: dataSnapshot.getChildren()) {
                    restaurantClasses.add(new RestaurantClass(
                            restaurant.child("rname").getValue().toString(),
                            restaurant.child("raddress").getValue().toString(),
                            restaurant.child("rmenu").getValue().toString(),
                            restaurant.child("rmeals").getValue().toString(),
                            restaurant.child("ownerid").getValue().toString(),
                            restaurant.child("gambar1").getValue().toString(),
                            restaurant.child("gambar2").getValue().toString(),
                            restaurant.child("mobile").getValue().toString()

                    ));

                    adapter = new RestaurantListAdapterOwner(getApplicationContext(), restaurantClasses, OwnerViewRestaurant.this);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
