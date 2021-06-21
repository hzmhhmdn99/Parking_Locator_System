package com.example.parkinglocator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnSubmit, btnReset;
    EditText plateNumber;
    boolean isAllFieldsChecked = false;

    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final Context context = this;

        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        btnReset = (Button) findViewById(R.id.buttonReset);
        plateNumber = (EditText) findViewById(R.id.editPlateNumber);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();

                String plateNum = plateNumber.getText().toString();

                if(!plateNum.isEmpty()){
                    readData(plateNum);
                }else {
                    Toast.makeText(MainActivity.this,"PLease Enter Plate Number",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plateNumber.setText("");
            }
        });


    }

    private void readData(String plateNum) {
        dbReference = FirebaseDatabase.getInstance().getReference("Images");
        dbReference.child(plateNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        DataSnapshot dataSnapshot = task.getResult();
                        String PlateNum = String.valueOf(dataSnapshot.child("plateNumber").getValue());
                        Toast.makeText(MainActivity.this, "Successfully Read", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, RightPlateNumber.class);
                        intent.putExtra("plateNumber", plateNum);
                        startActivity(intent);

                    } else {

                        Toast.makeText(MainActivity.this, "Plate doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(MainActivity.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean CheckAllFields() {
        if (plateNumber.length() == 0) {
            plateNumber.setError("This field is required");
            return false;
        }
        return true;
    }
}