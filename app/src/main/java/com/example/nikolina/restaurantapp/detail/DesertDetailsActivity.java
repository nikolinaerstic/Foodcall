package com.example.nikolina.restaurantapp.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikolina.restaurantapp.R;
import com.example.nikolina.restaurantapp.database.DatabaseHandler;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DesertDetailsActivity extends AppCompatActivity {

    TextView nameText;
    TextView descriptionText;
    TextView priceText;
    Button addToCartBtn;
    ImageView image;
    private AdView adView;

    List<String> name;
    List<String> description;
    List<Integer> price;
    List<String> images;

    int counter = 0;

    DatabaseReference databaseReferenceForName;
    DatabaseReference databaseReferenceForDesc;
    DatabaseReference databaseReferenceForPrice;
    DatabaseReference databaseReferenceForImages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        MobileAds.initialize(this, "ca-app-pub-8605568693117140~4983529291");

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        name = new ArrayList<>();
        description = new ArrayList<>();
        price = new ArrayList<>();
        images = new ArrayList<>();

        nameText = findViewById(R.id.name);
        descriptionText = findViewById(R.id.description);
        priceText = findViewById(R.id.price);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        image = findViewById(R.id.image);

        databaseReferenceForName = FirebaseDatabase.getInstance().getReference().child("FoodStorage").child("desert").child("name");
        databaseReferenceForDesc = FirebaseDatabase.getInstance().getReference().child("FoodStorage").child("desert").child("description");
        databaseReferenceForPrice = FirebaseDatabase.getInstance().getReference().child("FoodStorage").child("desert").child("price");
        databaseReferenceForImages = FirebaseDatabase.getInstance().getReference().child("FoodStorage").child("desert").child("image");

        databaseReferenceForName.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data = dataSnapshot.getValue(String.class);
                name.add(data);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceForDesc.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data = dataSnapshot.getValue(String.class);
                description.add(data);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceForPrice.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data = dataSnapshot.getValue(String.class);
                price.add(Integer.valueOf(data));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReferenceForImages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data = dataSnapshot.getValue(String.class);
                images.add(data);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReferenceForImages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadFoodData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void loadFoodData() {
        final Intent intent = getIntent();

        if (intent.hasExtra("position")){
            int position = intent.getIntExtra("position", -1);
            nameText.setText(name.get(position));
            descriptionText.setText(description.get(position));
            priceText.setText(String.valueOf(price.get(position)) + " kn");
            Picasso.get().load(images.get(position)).into(image);
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler databaseHandler = new DatabaseHandler(DesertDetailsActivity.this);

                if (intent.hasExtra("position")){
                    int position = intent.getIntExtra("position", -1);
                    databaseHandler.saveToCart(name.get(position), price.get(position));
                    finish();
                }

            }
        });
    }

}
