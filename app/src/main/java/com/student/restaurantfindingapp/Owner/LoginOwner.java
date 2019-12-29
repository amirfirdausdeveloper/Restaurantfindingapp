package com.student.restaurantfindingapp.Owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.student.restaurantfindingapp.MainActivity;
import com.student.restaurantfindingapp.R;

public class LoginOwner extends AppCompatActivity {

    //DECLARE
    ImageView imageView_back;
    EditText editText_email,editText_password;
    Button button_login,button_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_owner);

        //DECLARE
        imageView_back = findViewById(R.id.imageView_back);
        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);
        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);

        //BACK CLICK
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent next = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(next);
    }
}
