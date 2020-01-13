package com.student.restaurantfindingapp.Admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.student.restaurantfindingapp.Owner.Activity.LoginOwner;
import com.student.restaurantfindingapp.R;

public class DashboardAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        Button button_view = findViewById(R.id.button_view);
        button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),ViewRestaurantAdmin.class);
                startActivity(next);
            }
        });

        Button button_myreview = findViewById(R.id.button_myreview);
        button_myreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),ReviewAdmin.class);
                startActivity(next);
            }
        });

        Button button_logout = findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), LoginOwner.class);
                startActivity(next);
                LoginOwner.ownerEmail = "";
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
