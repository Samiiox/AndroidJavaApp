package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import Controller.DataBaseHandler;
import Model.MachineLearning;
import Utils.Utils;

public class EditData extends AppCompatActivity {

    private MachineLearning selectedMachineLearning;
    private EditText editTextMpgE, editTextDispE, editTextHorsPE, editTextWeiE, editTextAccE, editTextOrE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        // Retrieve the selected MachineLearning object from the intent
        Intent intent = getIntent();
        if (intent.hasExtra("machineLearning")) {
            selectedMachineLearning = intent.getParcelableExtra("machineLearning");

            // Find the EditText views in the layout
            editTextMpgE = findViewById(R.id.editTextMpgE);
            editTextDispE = findViewById(R.id.editTextDispE);
            editTextHorsPE = findViewById(R.id.editTextHorsPE);
            editTextWeiE = findViewById(R.id.editTextWeiE);
            editTextAccE = findViewById(R.id.editTextAccE);
            editTextOrE = findViewById(R.id.editTextOrE);
            Button saveButton = findViewById(R.id.button2E);

            // Set the text values based on the selectedMachineLearning object
            editTextMpgE.setText(String.valueOf(selectedMachineLearning.getMpg()));
            editTextDispE.setText(String.valueOf(selectedMachineLearning.getDisplacement()));
            editTextHorsPE.setText(String.valueOf(selectedMachineLearning.getHorsePower()));
            editTextWeiE.setText(String.valueOf(selectedMachineLearning.getWeight()));
            editTextAccE.setText(String.valueOf(selectedMachineLearning.getAcceleration()));
            editTextOrE.setText(selectedMachineLearning.getOrigin());

            // Find the saveButton and add a click listener
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Update the selectedMachineLearning object with the new values
                    selectedMachineLearning.setMpg(Integer.parseInt(editTextMpgE.getText().toString()));
                    selectedMachineLearning.setDisplacement(Integer.parseInt(editTextDispE.getText().toString()));
                    selectedMachineLearning.setHorsePower(Integer.parseInt(editTextHorsPE.getText().toString()));
                    selectedMachineLearning.setWeight(Integer.parseInt(editTextWeiE.getText().toString()));
                    selectedMachineLearning.setAcceleration(Integer.parseInt(editTextAccE.getText().toString()));
                    selectedMachineLearning.setOrigin(editTextOrE.getText().toString());

                    // Update the data in the database
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(EditData.this, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
                    dataBaseHandler.updateData(selectedMachineLearning.getId(), selectedMachineLearning);

                    // Finish the activity to go back to the previous one
                    finish();
                }
            });
        } else {
            // Handle the case where the intent doesn't have the "machineLearning" extra
            // You might want to show an error message or handle it as needed
            finish(); // Finish the activity in this case
        }
    }
}