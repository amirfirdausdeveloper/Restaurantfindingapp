package com.student.restaurantfindingapp.Owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.student.restaurantfindingapp.Customer.Activity.LoginCustomer;
import com.student.restaurantfindingapp.R;

public class OwnerManageAccount extends AppCompatActivity {

    ImageView imageView_back;
    private DatabaseReference mDatabase;
    EditText editText_name,editText_password;
    TextView textView_email;
    Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_manage_account);

        //DATABASE
        mDatabase = FirebaseDatabase.getInstance().getReference("owner");

        //DECLARE
        imageView_back = findViewById(R.id.imageView_back);
        textView_email = findViewById(R.id.textView_email);
        editText_name = findViewById(R.id.editText_name);
        editText_password = findViewById(R.id.editText_password);
        button_save = findViewById(R.id.button_save);

        //BACK FUNCTION
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //SAVE BUTTON
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_LONG).show();
                }else if(editText_name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Enter name",Toast.LENGTH_LONG).show();
                }else {
                    updateDetails();
                }
            }
        });

        getInfoOwner();
    }

    //GET OWNER INFO
    private void getInfoOwner(){
        mDatabase.orderByChild("email").equalTo(LoginOwner.ownerEmail)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot pelajar : dataSnapshot.getChildren()) {
                                textView_email.setText(pelajar.child("email").getValue().toString());
                                editText_name.setText(pelajar.child("name").getValue().toString());
                                editText_password.setText(pelajar.child("password").getValue().toString());
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    //UPDATE DETAILS
    private void updateDetails(){
        mDatabase.orderByChild("email").equalTo(LoginOwner.ownerEmail)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot customer : dataSnapshot.getChildren()) {
                                customer.getRef().child("name").setValue(editText_name.getText().toString());
                                customer.getRef().child("password").setValue(editText_password.getText().toString());

                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }
}
