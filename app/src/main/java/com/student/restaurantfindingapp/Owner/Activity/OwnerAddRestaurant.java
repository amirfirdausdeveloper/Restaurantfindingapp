package com.student.restaurantfindingapp.Owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;
import com.student.restaurantfindingapp.Owner.Class.OwnerClass;
import com.student.restaurantfindingapp.Owner.Class.RestaurantClass;
import com.student.restaurantfindingapp.R;
import com.student.restaurantfindingapp.StandardProgressDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OwnerAddRestaurant extends AppCompatActivity {

    ImageView imageView_back;
    EditText editText_name,editText_address,editText_menu,editText_mobile;
    Spinner editText_meals;
    Button button_add;
    private DatabaseReference mDatabase;
    ImageView imageView_gambar,imageView_gambar2;
    Bitmap selected_image,selected_image2;
    String url,url2;
    boolean statusImage = false;
    boolean statusImage2 = false;
    private StorageReference mStorageRef;
    StandardProgressDialog standardProgressDialog;
    boolean statusEditGambar1 = false;
    boolean statusEditGambar2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_restaurant);

        if (android.os.Build.VERSION.SDK_INT >= 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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
        editText_mobile = findViewById(R.id.editText_mobile);
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

                if(getIntent().hasExtra("status")){
                    if(statusEditGambar1 == false || statusEditGambar2 == false){
                        editRestaurant();
                    }else {
                        standardProgressDialog.show();
                        insertImageFirst();
                    }
                }else {
                    if(editText_name.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Fill Restaurant Name",Toast.LENGTH_LONG).show();
                    }else if(editText_address.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Fill Restaurant Address",Toast.LENGTH_LONG).show();
                    }else if(editText_menu.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Fill Restaurant Menu",Toast.LENGTH_LONG).show();
                    }else if(editText_meals.getSelectedItem().toString().equals("Choose Meals")){
                        Toast.makeText(getApplicationContext(),"Fill Restaurant Meals",Toast.LENGTH_LONG).show();
                    }else if(editText_mobile.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Fill mobile no",Toast.LENGTH_LONG).show();
                    }else if(statusImage == false || statusImage2 == false){
                        Toast.makeText(getApplicationContext(),"Insert image fist",Toast.LENGTH_LONG).show();
                    } else {
                        standardProgressDialog.show();
                        insertImageFirst();

                    }
                }

            }
        });

        imageView_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(OwnerAddRestaurant.this)
                        .setCancelable(true)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Please Select")
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 1);
                            }
                        }).setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 11);
                    }
                }).show();

            }
        });

        imageView_gambar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(OwnerAddRestaurant.this)
                        .setCancelable(true)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Please Select")
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 2);
                            }
                        }).setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 22);
                    }
                }).show();
            }
        });


        //KALAU EDIT MASUK SINI
        if(getIntent().hasExtra("status")){
            if(getIntent().getStringExtra("status").equals("edit")){
                TextView textView2 = findViewById(R.id.textView2);
                textView2.setText("Edit Restaurant");
                button_add.setText("Edit Restaurant");

                editText_name.setText(getIntent().getStringExtra("rname"));
                editText_name.setEnabled(false);
                editText_address.setText(getIntent().getStringExtra("raddress"));
                editText_menu.setText(getIntent().getStringExtra("rmenu"));
                editText_meals.setSelection(getIndex(editText_meals,getIntent().getStringExtra("rmeals")));
                editText_mobile.setText(getIntent().getStringExtra("mobile"));

                URL url = null;
                URL url2 = null;
                try {
                    url = new URL(getIntent().getStringExtra("gambar1"));
                    Bitmap myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    imageView_gambar.setImageBitmap(myBitmap);

                    url2 = new URL(getIntent().getStringExtra("gambar2"));
                    Bitmap myBitmaps = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
                    imageView_gambar2.setImageBitmap(myBitmaps);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public Bitmap getbmpfromURL(String surl){
        try {
            URL url = new URL(surl);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setDoInput(true);
            urlcon.connect();
            InputStream in = urlcon.getInputStream();
            Bitmap mIcon = BitmapFactory.decodeStream(in);
            return  mIcon;
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    private void editRestaurant(){
        mDatabase.orderByChild("rname").equalTo(editText_name.getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot restaurant : dataSnapshot.getChildren()) {
                                restaurant.getRef().child("gambar1").setValue(getIntent().getStringExtra("gambar1"));
                                restaurant.getRef().child("gambar2").setValue(getIntent().getStringExtra("gambar2"));
                                restaurant.getRef().child("ownerid").setValue(LoginOwner.ownerEmail);
                                restaurant.getRef().child("raddress").setValue(editText_address.getText().toString());
                                restaurant.getRef().child("rmeals").setValue(editText_meals.getSelectedItem().toString());
                                restaurant.getRef().child("rmenu").setValue(editText_menu.getText().toString());
                                restaurant.getRef().child("mobile").setValue(editText_mobile.getText().toString());

                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Log.d("Exist","Exists");
                        }
//                        if (dataSnapshot.exists()) {
//                            Log.d("Exist","Exist");
//                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        standardProgressDialog.dismiss();

                    }
                });
    }

    private void editRestaurants(){
        mDatabase.orderByChild("rname").equalTo(editText_name.getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot restaurant : dataSnapshot.getChildren()) {
                                restaurant.getRef().child("gambar1").setValue(url);
                                restaurant.getRef().child("gambar2").setValue(url2);
                                restaurant.getRef().child("ownerid").setValue(LoginOwner.ownerEmail);
                                restaurant.getRef().child("raddress").setValue(editText_address.getText().toString());
                                restaurant.getRef().child("rmeals").setValue(editText_meals.getSelectedItem().toString());
                                restaurant.getRef().child("rmenu").setValue(editText_menu.getText().toString());
                                restaurant.getRef().child("mobile").setValue(editText_mobile.getText().toString());

                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Log.d("Exist","Exists");
                        }
//                        if (dataSnapshot.exists()) {
//                            Log.d("Exist","Exist");
//                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        standardProgressDialog.dismiss();

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
                                    editText_meals.getSelectedItem().toString(),
                                    LoginOwner.ownerEmail,
                                    url,url2,
                                    editText_mobile.getText().toString());

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
                statusEditGambar1 = true;

            }
            if (requestCode == 11) {
                Uri selectedImage = data.getData();
                Bitmap thumbnail = null;
                try {
                    thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_image = Bitmap.createScaledBitmap(thumbnail, 750, 1000, true);
                imageView_gambar.setImageBitmap(selected_image);
                statusImage = true;
                statusEditGambar1 = true;

            }
            if (requestCode == 22) {
                Uri selectedImage = data.getData();
                Bitmap thumbnail = null;
                try {
                    thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_image2 = Bitmap.createScaledBitmap(thumbnail, 750, 1000, true);
                imageView_gambar2.setImageBitmap(selected_image2);
                statusImage2 = true;
                statusEditGambar2 = true;

            }
            if (requestCode == 2) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                selected_image2 = Bitmap.createScaledBitmap(thumbnail, 750, 1000, true);
                imageView_gambar2.setImageBitmap(selected_image2);
                statusImage2 = true;
                statusEditGambar2 = true;

            }
        }else {
            if(getIntent().hasExtra("status")){
                if(requestCode == 1){
                    statusEditGambar1 = false;
                }
                if(requestCode == 2){
                    statusEditGambar2 = false;
                }
            }
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
        Uri file = getImageUri(getApplicationContext(),selected_image2);
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
                                if (getIntent().hasExtra("status")){
                                    editRestaurants();
                                }else {
                                    addRestaurant();
                                }

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


