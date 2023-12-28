package com.example.androidapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button mlButton,add,recyclerButton;
    EditText mpg, displacement, acceleration, weight, horsePower;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlButton = findViewById(R.id.buttonMl);
        mpg = findViewById(R.id.editTextNumberSignedMpg);
        displacement = findViewById(R.id.editTextNumberSignedDisp);
        acceleration = findViewById(R.id.editTextNumberSignedAcc);
        weight = findViewById(R.id.editTextNumberSignedWei);
        horsePower = findViewById(R.id.editTextNumberSignedHorse);

        mlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting texte
                String mpgValueStr = mpg.getText().toString();
                double mpgValue = Double.parseDouble(mpgValueStr);

                String displacementValueStr = displacement.getText().toString();
                double displacementValue = Double.parseDouble(displacementValueStr);

                String accelerationValueStr = acceleration.getText().toString();
                double accelerationValue = Double.parseDouble(accelerationValueStr);

                String weightValueStr = weight.getText().toString();
                double weightValue = Double.parseDouble(weightValueStr);

                String horsePowerValueStr = horsePower.getText().toString();
                double horsePowerValue = Double.parseDouble(horsePowerValueStr);

                // Créer une intention pour l'activité SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                // Ajouter les valeurs à l'intention
                intent.putExtra("MPG", mpgValue);
                intent.putExtra("DISPLACEMENT", displacementValue);
                intent.putExtra("ACCELERATION", accelerationValue);
                intent.putExtra("WEIGHT", weightValue);
                intent.putExtra("HORSEPOWER", horsePowerValue);


                startActivity(intent);
            }
        });


        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddData.class);
                startActivity(intent);

            }
        });

        recyclerButton = findViewById(R.id.recyclerButton);
        recyclerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Recycler.class);
                startActivity(intent);

            }
        });




    }
}
