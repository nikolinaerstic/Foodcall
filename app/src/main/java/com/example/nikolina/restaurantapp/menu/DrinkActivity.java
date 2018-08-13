package com.example.nikolina.restaurantapp.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nikolina.restaurantapp.R;
import com.example.nikolina.restaurantapp.detail.DrinkDetailsActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DrinkActivity extends AppCompatActivity{

    private ListView listView;
    private List<String> drinks;
    DatabaseReference databaseReferenceForDrinks;
    int counter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_list);

        drinks = new ArrayList<>();


        databaseReferenceForDrinks = FirebaseDatabase.getInstance().getReference().child("FoodStorage").child("drinks").child("name");
        databaseReferenceForDrinks.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data = dataSnapshot.getValue(String.class);
                drinks.add(data);
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


        databaseReferenceForDrinks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadItemsForListview();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadItemsForListview() {
        listView = findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter<String>(DrinkActivity.this,android.R.layout.simple_list_item_1, drinks);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(DrinkActivity.this, DrinkDetailsActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
        }

}
