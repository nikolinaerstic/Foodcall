package com.example.nikolina.restaurantapp.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nikolina.restaurantapp.cart.CartActivity;
import com.example.nikolina.restaurantapp.R;
import com.example.nikolina.restaurantapp.menu.MenuActivity;

public class MainActivity extends Activity {

    Button menuBtn;
    Button cartBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuBtn = findViewById(R.id.menuBtn);
        cartBtn = findViewById(R.id.cartBtn);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCartActivity();
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMenuActivity();
            }
        });
    }

    private void startCartActivity() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    private void startMenuActivity(){
        startActivity(new Intent(this, MenuActivity.class));
    }

}
