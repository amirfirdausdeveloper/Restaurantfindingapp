package com.student.restaurantfindingapp.Owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.restaurantfindingapp.Owner.Class.OwnerClass;
import com.student.restaurantfindingapp.Owner.Class.RestaurantClass;
import com.student.restaurantfindingapp.R;

public class OwnerAddRestaurant extends AppCompatActivity {

    ImageView imageView_back;
    EditText editText_name,editText_address,editText_menu,editText_meals;
    Button button_add;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_restaurant);

        //FIREBASE DATABASE REF
        mDatabase = FirebaseDatabase.getInstance().getReference("restaurant");

        //DECLARE
        imageView_back = findViewById(R.id.imageView_back);
        editText_name = findViewById(R.id.editText_name);
        editText_address = findViewById(R.id.editText_address);
        editText_menu = findViewById(R.id.editText_menu);
        editText_meals = findViewById(R.id.editText_meals);
        button_add = findViewById(R.id.button_add);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill Restaurant Name",Toast.LENGTH_LONG).show();
                }else if(editText_address.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill Restaurant Address",Toast.LENGTH_LONG).show();
                }else if(editText_menu.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill Restaurant Menu",Toast.LENGTH_LONG).show();
                }else if(editText_meals.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill Restaurant Meals",Toast.LENGTH_LONG).show();
                }else {
                    addRestaurant();
                }
            }
        });
    }

    private void addRestaurant(){
        mDatabase.orderByChild("rname").equalTo(editText_name.getText().toString().toUpperCase())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(getApplicationContext(), "Restaurant is exist", Toast.LENGTH_LONG).show();
                        } else {
                            String id = mDatabase.push().getKey();
                            RestaurantClass restaurantClass = new RestaurantClass(
                                    editText_name.getText().toString().toUpperCase(),
                                    editText_address.getText().toString(),
                                    editText_menu.getText().toString(),
                                    editText_meals.getText().toString(),
                                    LoginOwner.ownerEmail);

                            //Saving the pensyasrah
                            mDatabase.child(id).setValue(restaurantClass);

                            //displaying a success toast
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                            Intent next = new Intent(getApplicationContext(), OwnerDashboard.class);
                            startActivity(next);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }
}
