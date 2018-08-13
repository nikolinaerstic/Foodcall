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
import com.example.nikolina.restaurantapp.detail.DesertDetailsActivity;
import com.example.nikolina.restaurantapp.detail.StartDetailsActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StartMealActivity extends AppCompatActivity{

    private ListView listView;
    private List<String> startMeals;
    DatabaseReference databaseReferenceForStartMeals;
    int counter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_list);

        startMeals = new ArrayList<>();


        databaseReferenceForStartMeals = FirebaseDatabase.getInstance().getReference().child("FoodStorage").child("startmeal").child("name");
        databaseReferenceForStartMeals.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data = dataSnapshot.getValue(String.class);
                startMeals.add(data);
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

        databaseReferenceForStartMeals.addListenerForSingleValueEvent(new ValueEventListener() {
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

        ArrayAdapter adapter = new ArrayAdapter<String>(StartMealActivity.this,android.R.layout.simple_list_item_1, startMeals);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(StartMealActivity.this, StartDetailsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }


}
