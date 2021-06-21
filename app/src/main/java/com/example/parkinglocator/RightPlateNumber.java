package com.example.parkinglocator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class RightPlateNumber extends AppCompatActivity {

    Button btnYes, btnNo;
    ImageView imageCar;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_plate_number);

        String plateNum = getIntent().getStringExtra("plateNumber");

        readData(plateNum);
        addListenerOnButton(plateNum);



    }

    private void readData(String plateNum) {
        imageCar = (ImageView) findViewById(R.id.imageCar);
        dbReference = FirebaseDatabase.getInstance().getReference("Images");
        dbReference.child(plateNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();

                String imageURL = String.valueOf(dataSnapshot.child("imageURL").getValue());
                Picasso.get().load(imageURL).into(imageCar);
            }
        });
    }

    private void addListenerOnButton(String plateNum) {

        btnYes = (Button) findViewById(R.id.buttonYes);
        btnNo = (Button) findViewById(R.id.buttonNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RightPlateNumber.this, RightCar.class);
                intent.putExtra("plateNumber", plateNum);
                startActivity(intent);
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Toast.makeText(RightPlateNumber.this,"Re-enter Your Plate Number",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RightPlateNumber.this, MainActivity.class);

                startActivity(intent);

            }

        });
    }
}