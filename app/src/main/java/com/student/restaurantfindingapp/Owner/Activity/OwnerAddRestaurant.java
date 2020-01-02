package com.student.restaurantfindingapp.Owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.student.restaurantfindingapp.Owner.Class.OwnerClass;
import com.student.restaurantfindingapp.Owner.Class.RestaurantClass;
import com.student.restaurantfindingapp.R;
import com.student.restaurantfindingapp.StandardProgressDialog;

import java.io.ByteArrayOutputStream;

public class OwnerAddRestaurant extends AppCompatActivity {

    ImageView imageView_back;
    EditText editText_name,editText_address,editText_menu,editText_meals;
    Button button_add;
    private DatabaseReference mDatabase;
    ImageView imageView_gambar,imageView_gambar2;
    Bitmap selected_image,selected_image2;
    String url,url2;
    boolean statusImage = false;
    boolean statusImage2 = false;
    private StorageReference mStorageRef;
    StandardProgressDialog standardProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_restaurant);

        //FIREBASE DATABASE REF
        mDatabase = FirebaseDatabase.getInstance().getReference("restaurant");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());

        //DECLARE
        imageView_back = findViewById(R.id.imageView_back);
        editText_name = findViewById(R.id.editText_name);
        editText_address = findViewById(R.id.editText_address);
        editText_menu = findViewById(R.id.editText_menu);
        editText_meals = findViewById(R.id.editText_meals);
        button_add = findViewById(R.id.button_add);
        imageView_gambar = findViewById(R.id.imageView_gambar);
        imageView_gambar2 = findViewById(R.id.imageView_gambar_2);

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
                }else if(statusImage == false || statusImage2 == false){
                    Toast.makeText(getApplicationContext(),"Insert image fist",Toast.LENGTH_LONG).show();
                } else {
                    standardProgressDialog.show();
                    insertImageFirst();
                }
            }
        });

        imageView_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });

        imageView_gambar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2);
            }
        });
    }

    private void addRestaurant(){
        standardProgressDialog.show();
        mDatabase.orderByChild("rname").equalTo(editText_name.getText().toString().toUpperCase())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            standardProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Restaurant is exist", Toast.LENGTH_LONG).show();
                        } else {
                            standardProgressDialog.dismiss();
                            String id = mDatabase.push().getKey();
                            RestaurantClass restaurantClass = new RestaurantClass(
                                    editText_name.getText().toString().toUpperCase(),
                                    editText_address.getText().toString(),
                                    editText_menu.getText().toString(),
                                    editText_meals.getText().toString(),
                                    LoginOwner.ownerEmail,
                                    url,url2);

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
                        standardProgressDialog.dismiss();

                    }
                });
    }

    //CAMERA ACTIVITY
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                selected_image = Bitmap.createScaledBitmap(thumbnail, 750, 1000, true);
                imageView_gambar.setImageBitmap(selected_image);
                statusImage = true;

            }
            if (requestCode == 2) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                selected_image2 = Bitmap.createScaledBitmap(thumbnail, 750, 1000, true);
                imageView_gambar2.setImageBitmap(selected_image2);
                statusImage2 = true;

            }
        }else {

        }

    }

    //INSERT IMAGE IN FIREBASE
    private void insertImageFirst(){
        Uri file = getImageUri(getApplicationContext(),selected_image);
        StorageReference riversRef = mStorageRef.child("imageGambar/satu/"+LoginOwner.ownerEmail+".jpg");

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                url = o.toString();
                                standardProgressDialog.dismiss();
                                insertImageSecond();
                            }
                        });

//
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void insertImageSecond(){
        standardProgressDialog.show();
        Uri file = getImageUri(getApplicationContext(),selected_image);
        StorageReference riversRef = mStorageRef.child("imageGambar/dua/"+LoginOwner.ownerEmail+".jpg");

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                url2 = o.toString();
                                standardProgressDialog.dismiss();
                                addRestaurant();
                            }
                        });

//
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "saman", null);
        return Uri.parse(path);
    }
}


