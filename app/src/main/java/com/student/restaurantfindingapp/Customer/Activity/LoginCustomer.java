package com.student.restaurantfindingapp.Customer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.restaurantfindingapp.MainActivity;
import com.student.restaurantfindingapp.R;

public class LoginCustomer extends AppCompatActivity {

    //DECLARE
    ImageView imageView_back;
    EditText editText_email,editText_password;
    Button button_login,button_register;
    TextView textView_skip;
    private DatabaseReference mDatabase;
    public static int customerLogin = 0;
    public static String emailCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);

        //FIREBASE DATABASE REF
        mDatabase = FirebaseDatabase.getInstance().getReference("customer");

        //DECLARE
        imageView_back = findViewById(R.id.imageView_back);
        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);
        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);
        textView_skip = findViewById(R.id.textView_skip);

        //BACK CLICK
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerLogin = 0;
                Intent next = new Intent(getApplicationContext(), CustomerDashboard.class);
                startActivity(next);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_email.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill in email",Toast.LENGTH_LONG).show();
                }else if (editText_password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill in password",Toast.LENGTH_LONG).show();
                }else {
                    login();
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),RegisterCustomer.class);
                startActivity(next);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent next = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(next);
    }

    private void login(){
        mDatabase.orderByChild("email").equalTo(editText_email.getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot customer: dataSnapshot.getChildren()) {
                                if(editText_password.getText().toString().equals(customer.child("password").getValue().toString())){
                                    customerLogin = 1;
                                    emailCustomer = editText_email.getText().toString();
                                    Intent next = new Intent(getApplicationContext(), CustomerDashboard.class);
                                    startActivity(next);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Wrong password !!!", Toast.LENGTH_LONG).show();
                                }
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid email !!!", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }
}
