package com.example.nikolina.restaurantapp.cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikolina.restaurantapp.R;
import com.example.nikolina.restaurantapp.database.Cart;
import com.example.nikolina.restaurantapp.database.DatabaseHandler;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView listView;
    private Button clearBtn;
    private Button orderBtn;
    private TextView priceText;
    private List<String> name;
    private List<Integer> price;
    private DatabaseHandler databaseHandler;
    private DatabaseReference databaseReference;
    private ArrayAdapter adapter;
    private EditText address;
    private double lat;
    private double lng;
    private static final int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        listView = findViewById(R.id.listView);
        clearBtn = findViewById(R.id.clearBtn);
        orderBtn = findViewById(R.id.orderBtn);
        priceText = findViewById(R.id.price);


        price = new ArrayList<>();
        name = new ArrayList<>();

        databaseHandler = new DatabaseHandler(this);
        Cursor cursor = databaseHandler.getAllMeals();


        if (cursor.moveToFirst()){
            do{
                String n = cursor.getString(1);
                int p = cursor.getInt(2);
                name.add(n);
                price.add(p);

            }while(cursor.moveToNext());
        }

        cursor.close();


        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, name);
        listView.setAdapter(adapter);

        int sum = 0;

        for (int i = 0; i < price.size(); i++) {
            sum += price.get(i);
        }

        priceText.setText(String.valueOf(sum) + " kn");

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.clearCartItems();
                name.clear();
                price.clear();
                priceText.setText("0" + " kn");
                adapter.notifyDataSetChanged();
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (priceText.getText().toString().equals("0")){
                    Toast.makeText(CartActivity.this, "Košarica je prazna.", Toast.LENGTH_SHORT).show();
                }else {
                    String orders = "";
                    int prices = 0;

                    for (int i = 0; i < price.size(); i++) {
                        prices += price.get(i);
                    }

                    for (int i = 0; i < name.size(); i++) {
                        orders += name.get(i) + ", ";
                    }

                    openOrderDialog(orders, String.valueOf(prices));
                }
            }
        });

    }

    private void openOrderDialog(final String order, final String prices) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        View view = getLayoutInflater().inflate(R.layout.order_dialog, null);

        final EditText nameText = view.findViewById(R.id.name);
        final EditText surname = view.findViewById(R.id.surname);
        address = view.findViewById(R.id.address);
        final EditText phone = view.findViewById(R.id.phone);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(CartActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Poništi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Naruči", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (nameText.getText().toString().equals("") || surname.getText().toString().equals("") ||
                        address.getText().toString().equals("") || phone.getText().toString().equals("")){

                    Toast.makeText(CartActivity.this, "Popunite sva polja.", Toast.LENGTH_SHORT).show();
                }else {
                    Cart cart = new Cart(nameText.getText().toString(), surname.getText().toString(),
                            address.getText().toString(), phone.getText().toString(), order, prices + " kn", lat, lng);
                    databaseReference.child("orders").setValue(cart);
                    Toast.makeText(CartActivity.this,  "Uspješno ste naručili proizvode. Dobar tek!", Toast.LENGTH_SHORT).show();

                    databaseHandler.clearCartItems();
                    name.clear();
                    price.clear();
                    priceText.setText("0" + " kn");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                address.setText(place.getName());
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;
            }
        }
    }
}
