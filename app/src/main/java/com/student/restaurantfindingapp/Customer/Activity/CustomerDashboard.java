package com.student.restaurantfindingapp.Customer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.restaurantfindingapp.Customer.Adapter.RestaurantListAdapter;
import com.student.restaurantfindingapp.Customer.Class.RestaurantClass;
import com.student.restaurantfindingapp.MainActivity;
import com.student.restaurantfindingapp.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerDashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    List<RestaurantClass> restaurantClasses;
    private RestaurantListAdapter adapter;
    private DatabaseReference mDatabaseSaman;
    ImageView imageView_myAcc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        //SET FIREBASE
        mDatabaseSaman = FirebaseDatabase.getInstance().getReference("restaurant");

        //DECLARE
        recyclerView = findViewById(R.id.recyclerView);
        imageView_myAcc = findViewById(R.id.imageView_myAcc);

        //UTK SEARCH
        //SEARCHING
        androidx.appcompat.widget.SearchView simpleSearchView = findViewById(R.id.searchView);
        simpleSearchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SUBMIT",query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        //GO TO MY ACCOUNT
        imageView_myAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoginCustomer.customerLogin == 0){
                    Intent next = new Intent(getApplicationContext(),LoginCustomer.class);
                    startActivity(next);
                }else {
                    Intent next = new Intent(getApplicationContext(),AccountActivity.class);
                    startActivity(next);
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDetails();
    }

    private void getDetails(){
        recyclerView.setHasFixedSize(false);
        restaurantClasses = new ArrayList<>();
        adapter = new RestaurantListAdapter(getApplicationContext(), restaurantClasses,CustomerDashboard.this);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(adapter);

        mDatabaseSaman.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                    restaurant.child("gambar2").getValue().toString()

                            ));

                            adapter = new RestaurantListAdapter(getApplicationContext(), restaurantClasses, CustomerDashboard.this);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }
}
