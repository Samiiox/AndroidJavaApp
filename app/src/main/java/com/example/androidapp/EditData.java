package com.example.androidapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import Controller.DataBaseHandler;
import Model.MachineLearning;
import Utils.Utils;

public class EditData extends AppCompatActivity {
    private EditText mpg, displacement, horsePower, weight, acceleration, origin;
    private Button button;
    private DataBaseHandler dataBaseHandler;
    private MachineLearning machineLearning;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        dataBaseHandler = new DataBaseHandler(getApplicationContext(), Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);


        mpg = findViewById(R.id.editTextMpgE);
        displacement = findViewById(R.id.editTextDispE);
        horsePower = findViewById(R.id.editTextHorsPE);
        weight = findViewById(R.id.editTextWeiE);
        acceleration = findViewById(R.id.editTextAccE);
        origin = findViewById(R.id.editTextOrE);
        button = findViewById(R.id.button2E);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("position");
            machineLearning = dataBaseHandler.getData(position);

            // Afficher les données existantes dans les champs d'édition
            mpg.setText(String.valueOf(machineLearning.getMpg()));
            displacement.setText(String.valueOf(machineLearning.getDisplacement()));
            horsePower.setText(String.valueOf(machineLearning.getHorsePower()));
            weight.setText(String.valueOf(machineLearning.getWeight()));
            acceleration.setText(String.valueOf(machineLearning.getAcceleration()));
            origin.setText(machineLearning.getOrigin());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mettre à jour les données avec les nouvelles valeurs
                machineLearning.setMpg(Integer.parseInt(mpg.getText().toString()));
                machineLearning.setDisplacement(Integer.parseInt(displacement.getText().toString()));
                machineLearning.setHorsePower(Integer.parseInt(horsePower.getText().toString()));
                machineLearning.setWeight(Integer.parseInt(weight.getText().toString()));
                machineLearning.setAcceleration(Integer.parseInt(acceleration.getText().toString()));
                machineLearning.setOrigin(origin.getText().toString());
                // Mettre à jour les autres champs de la machineLearning si nécessaire

                // Appeler la méthode d'update dans la base de données
                dataBaseHandler.updateData(machineLearning.getId(), machineLearning);

                // Terminer l'activité après la mise à jour
                finish();
            }
        });
    }
}
