package com.student.restaurantfindingapp.Admin.Activity;

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
import com.student.restaurantfindingapp.Customer.Activity.LoginCustomer;
import com.student.restaurantfindingapp.Customer.Class.CustomerClass;
import com.student.restaurantfindingapp.R;

public class RegisterAdmin extends AppCompatActivity {

    ImageView imageView_back;
    EditText editText_email,editText_nama,editText_password;
    Button button_register;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        //FIREBASE DATABASE REF
        mDatabase = FirebaseDatabase.getInstance().getReference("admin");

        //DECLARE
        imageView_back = findViewById(R.id.imageView_back);
        editText_email = findViewById(R.id.editText_email);
        editText_nama = findViewById(R.id.editText_nama);
        editText_password = findViewById(R.id.editText_password);
        button_register = findViewById(R.id.button_register);

        //BACK
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_email.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill in email",Toast.LENGTH_LONG).show();
                }else if(editText_nama.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill in Name",Toast.LENGTH_LONG).show();
                }else if(editText_password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill in password",Toast.LENGTH_LONG).show();
                }else {
                    register();
                }
            }
        });
    }

    private void register(){
        mDatabase.orderByChild("email").equalTo(editText_email.getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(getApplicationContext(), "Email is exist", Toast.LENGTH_LONG).show();
                        } else {
                            String id = mDatabase.push().getKey();
                            CustomerClass customerClass = new CustomerClass(
                                    editText_email.getText().toString(),
                                    editText_nama.getText().toString(),
                                    editText_password.getText().toString());

                            //Saving the pensyasrah
                            mDatabase.child(id).setValue(customerClass);

                            //setting edittext to blank again
                            editText_email.setText("");
                            editText_nama.setText("");
                            editText_password.setText("");

                            //displaying a success toast
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                            Intent next = new Intent(getApplicationContext(), LoginAdmin.class);
                            startActivity(next);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

    }
}
