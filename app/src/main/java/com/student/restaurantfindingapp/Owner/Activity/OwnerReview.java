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
import com.student.restaurantfindingapp.Owner.Adapter.RestaurantListAdapterOwner;
import com.student.restaurantfindingapp.Owner.Adapter.ReviewAdapter;
import com.student.restaurantfindingapp.Owner.Class.RestaurantClass;
import com.student.restaurantfindingapp.Owner.Class.ReviewClass;
import com.student.restaurantfindingapp.R;

import java.util.ArrayList;
import java.util.List;

public class OwnerReview extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ReviewClass> reviewClasses;
    private ReviewAdapter adapter;
    private DatabaseReference mDatabaseSaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_review);

        //SET FIREBASE
        mDatabaseSaman = FirebaseDatabase.getInstance().getReference("review");

        //DECLARE
        recyclerView = findViewById(R.id.rc);

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
        reviewClasses = new ArrayList<>();
        adapter = new ReviewAdapter(getApplicationContext(), reviewClasses, OwnerReview.this);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(adapter);

        mDatabaseSaman.orderByChild("ownerid").equalTo(LoginOwner.ownerEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot restaurant: dataSnapshot.getChildren()) {
                    reviewClasses.add(new ReviewClass(
                            restaurant.child("comment").getValue().toString(),
                            restaurant.child("customerid").getValue().toString(),
                            restaurant.child("ownerid").getValue().toString(),
                            restaurant.child("rate").getValue().toString(),
                            restaurant.child("rname").getValue().toString()

                    ));

                    adapter = new ReviewAdapter(getApplicationContext(), reviewClasses, OwnerReview.this);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
