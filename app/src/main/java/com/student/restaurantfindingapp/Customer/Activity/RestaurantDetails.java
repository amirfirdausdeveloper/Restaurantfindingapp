package com.student.restaurantfindingapp.Customer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.restaurantfindingapp.Customer.Adapter.RestaurantListAdapter;
import com.student.restaurantfindingapp.Customer.Class.CustomerClass;
import com.student.restaurantfindingapp.Customer.Class.RestaurantClass;
import com.student.restaurantfindingapp.MainActivity;
import com.student.restaurantfindingapp.R;

import java.util.HashMap;

public class RestaurantDetails extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    private ViewFlipper myViewFlipper;
    private float initialXPoint;
    String rname,raddress,rmenu,rmeals,ownerid,gambar1,gambar2;
    private DatabaseReference mDatabaseSaman,mDatabaseRating,mDatabaseComment,mDatabaseReview;
    SliderLayout mDemoSlider;
    RatingBar rating;
    TextView textView_rname,textView_rowner,textView_rmeals,textView_rmenu;
    ImageView imageView_back;
    EditText editText_comment;
    Button button_submit;
    String totalReviewRate = "0.0";
    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        //SET FIREBASE
        mDatabaseSaman = FirebaseDatabase.getInstance().getReference("restaurant");
        mDatabaseRating = FirebaseDatabase.getInstance().getReference("rating");
        mDatabaseComment = FirebaseDatabase.getInstance().getReference("comment");
        mDatabaseReview = FirebaseDatabase.getInstance().getReference("review");

        //GET INTENT
        rname = getIntent().getStringExtra("rname");
        raddress = getIntent().getStringExtra("raddress");
        rmenu = getIntent().getStringExtra("rmenu");
        rmeals = getIntent().getStringExtra("rmeals");
        ownerid = getIntent().getStringExtra("ownerid");
        gambar1 = getIntent().getStringExtra("gambar1");
        gambar2 = getIntent().getStringExtra("gambar2");
        mobile = getIntent().getStringExtra("mobile");

        mDemoSlider = findViewById(R.id.slider);
        rating = findViewById(R.id.rating);

        textView_rname = findViewById(R.id.textView_rname);
        textView_rowner = findViewById(R.id.textView_rowner);
        textView_rmeals = findViewById(R.id.textView_rmeals);
        textView_rmenu = findViewById(R.id.textView_rmenu);

        textView_rname.setText(rname);
        textView_rowner.setText(mobile);
        textView_rmeals.setText(rmeals);
        textView_rmenu.setText(rmenu);

        ImageView imageView_back = findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editText_comment = findViewById(R.id.editText_comment);
        button_submit = findViewById(R.id.button_submit);

        declareSLide();

       //ONCLICK BAR
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
//                rateFunction(String.valueOf(rating));
                totalReviewRate = String.valueOf(rating);
            }
        });

        //SUBMIT COMMENT
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_comment.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please insert comment before submit",Toast.LENGTH_LONG).show();
                }else {
                    functionComment();
                }
            }
        });
    }

    //DECLARE SLIDE
    private void declareSLide(){
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("gambar1", gambar1);
        url_maps.put("gambar2", gambar2);

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(RestaurantDetails.this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    //RATE FUNCTION
    private void rateFunction(final String star){
        mDatabaseRating.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String id = mDatabaseRating.push().getKey();
                        mDatabaseRating.child(id).setValue(id);
                        mDatabaseRating.child(id).child("ownerid").setValue(ownerid);
                        mDatabaseRating.child(id).child("rname").setValue(rname);
                        mDatabaseRating.child(id).child("customerid").setValue(LoginCustomer.emailCustomer);
                        mDatabaseRating.child(id).child("rating").setValue(star);

                        Toast.makeText(getApplicationContext(),"Rate success",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    //functionComment
    private void functionComment(){
        mDatabaseReview.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String id = mDatabaseReview.push().getKey();
                mDatabaseReview.child(id).setValue(id);
                mDatabaseReview.child(id).child("ownerid").setValue(ownerid);
                mDatabaseReview.child(id).child("rname").setValue(rname);
                mDatabaseReview.child(id).child("customerid").setValue(LoginCustomer.emailCustomer);
                mDatabaseReview.child(id).child("comment").setValue(editText_comment.getText().toString());
                mDatabaseReview.child(id).child("rate").setValue(totalReviewRate);
                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
