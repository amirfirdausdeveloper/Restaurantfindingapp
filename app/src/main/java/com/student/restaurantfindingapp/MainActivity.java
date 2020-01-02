package com.student.restaurantfindingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.student.restaurantfindingapp.Admin.LoginAdmin;
import com.student.restaurantfindingapp.Customer.Activity.LoginCustomer;
import com.student.restaurantfindingapp.Owner.Activity.LoginOwner;

public class MainActivity extends AppCompatActivity {

    //DECLARE
    Button button_customer,button_owner,button_admin;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WAKE_LOCK,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);

        //SET
        button_customer = findViewById(R.id.button_customer);
        button_owner = findViewById(R.id.button_owner);
        button_admin = findViewById(R.id.button_admin);

        //BUTTON ONCLICK
        button_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), LoginCustomer.class);
                startActivity(next);
            }
        });

        button_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), LoginOwner.class);
                startActivity(next);
            }
        });

        button_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), LoginAdmin.class);
                startActivity(next);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())  moveTaskToBack(true);
        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}
