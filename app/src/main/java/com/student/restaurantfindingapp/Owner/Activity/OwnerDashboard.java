package com.student.restaurantfindingapp.Owner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.student.restaurantfindingapp.R;

public class OwnerDashboard extends AppCompatActivity {

    TextView textView_email;
    Button button_manage_acc,button_add,button_view,button_myreview,button_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);

        //DECLARE
        textView_email = findViewById(R.id.textView_email);
        button_manage_acc = findViewById(R.id.button_manage_acc);
        button_add = findViewById(R.id.button_add);
        button_view = findViewById(R.id.button_view);
        button_myreview = findViewById(R.id.button_myreview);
        button_logout = findViewById(R.id.button_logout);

        textView_email.setText("WELCOME "+LoginOwner.ownerEmail);

        //ONCLICK
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),LoginOwner.class);
                startActivity(next);
                LoginOwner.ownerEmail = "";
            }
        });

        //ADD RESTAURANT
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),OwnerAddRestaurant.class);
                startActivity(next);
            }
        });

        //MANAGE ACCOUNT
        button_manage_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),OwnerManageAccount.class);
                startActivity(next);
            }
        });

        //VIEW RESTAURANT
        button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),OwnerViewRestaurant.class);
                startActivity(next);
            }
        });

        //view review
        button_myreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),OwnerReview.class);
                startActivity(next);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
