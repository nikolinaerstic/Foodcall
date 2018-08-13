package com.example.nikolina.restaurantapp.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.nikolina.restaurantapp.R;
import com.example.nikolina.restaurantapp.menu.DesertMealActivity;
import com.example.nikolina.restaurantapp.menu.DrinkActivity;
import com.example.nikolina.restaurantapp.menu.MainMealActivity;
import com.example.nikolina.restaurantapp.menu.StartMealActivity;

public class MenuActivity extends AppCompatActivity{

    ImageView startMealIv;
    ImageView mainMealIv;
    ImageView desertIv;
    ImageView drinkIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startMealIv = findViewById(R.id.start_meal_iv);
        mainMealIv = findViewById(R.id.main_meal_iv);
        desertIv = findViewById(R.id.desert_iv);
        drinkIv = findViewById(R.id.drink_iv);

        startMealIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStartMealActivity();
            }
        });

        mainMealIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainMealActivity();
            }
        });

        desertIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDesertMealActivity();
            }
        });

        drinkIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDrinkMealActivity();
            }
        });

    }

    private void startDrinkMealActivity() {
        startActivity(new Intent(this, DrinkActivity.class));
    }

    private void startDesertMealActivity() {
        startActivity(new Intent(this, DesertMealActivity.class));
    }

    private void startMainMealActivity() {
        startActivity(new Intent(this, MainMealActivity.class));
    }

    private void startStartMealActivity() {
        startActivity(new Intent(this, StartMealActivity.class));
    }

}
