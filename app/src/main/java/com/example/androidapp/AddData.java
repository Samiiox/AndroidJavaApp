package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Controller.DataBaseHandler;
import Model.MachineLearning;
import Utils.Utils;

public class AddData extends AppCompatActivity {
    private EditText mpg, displacement, horsePower, weight, acceleration, origin;
    private Button button;
    private DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        mpg = findViewById(R.id.mpge);
        displacement = findViewById(R.id.displacement);
        horsePower = findViewById(R.id.horsePower);
        weight = findViewById(R.id.weight);
        acceleration = findViewById(R.id.acceleration);
        origin = findViewById(R.id.origine);
        button = findViewById(R.id.addData);

        dataBaseHandler = new DataBaseHandler(this, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer mpgS = Integer.valueOf(mpg.getText().toString());
                Integer displacementS = Integer.valueOf(displacement.getText().toString());
                Integer horsePowerS = Integer.valueOf(horsePower.getText().toString());
                Integer weightS = Integer.valueOf(weight.getText().toString());
                Integer accelerationS = Integer.valueOf(acceleration.getText().toString());
                String originS = origin.getText().toString();

                // Avant l'ajout de données
                Log.d("DataInfo", "Avant l'ajout de données");
                displayAllData();


                dataBaseHandler.addData(new MachineLearning(mpgS, displacementS, horsePowerS, weightS, accelerationS, originS));

                // Après l'ajout de données
                Log.d("DataInfo", "Après l'ajout de données");

                // Affichage de toutes les données
                displayAllData();

                Intent intent = new Intent(AddData.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayAllData() {
        // Affichage de toutes les données
        List<MachineLearning> machineLearningList = dataBaseHandler.getAllData();
        for (MachineLearning ml : machineLearningList) {
            Log.d("DataInfo", "ID: " + ml.getId() +
                    ", MPG: " + ml.getMpg() +
                    ", Displacement: " + ml.getDisplacement() +
                    ", Horse Power: " + ml.getHorsePower() +
                    ", Weight: " + ml.getWeight() +
                    ", Acceleration: " + ml.getAcceleration() +
                    ", Origin: " + ml.getOrigin());
        }
    }
}
