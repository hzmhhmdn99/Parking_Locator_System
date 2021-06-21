package com.example.parkinglocator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class RightCar extends AppCompatActivity {

    Button btnHome;
    TextView txtLocation;
    ImageView imageCar2;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_car);
        String plateNum = getIntent().getStringExtra("plateNumber");
        readData(plateNum);
        addListenerOnButton();
    }

    private void readData(String plateNum) {
        imageCar2 = (ImageView) findViewById(R.id.imageCar2);
        txtLocation = (TextView) findViewById(R.id.textLocation);
        dbReference = FirebaseDatabase.getInstance().getReference("Images");
        dbReference.child(plateNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();

                String imageURI = String.valueOf(dataSnapshot.child("imageURL").getValue());
                String plateLocation = String.valueOf(dataSnapshot.child("plateLocation").getValue());

                txtLocation.setText(plateLocation);
                Picasso.get().load(imageURI).into(imageCar2);


            }
        });
    }

    private void addListenerOnButton() {
        btnHome = (Button) findViewById(R.id.buttonHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RightCar.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}