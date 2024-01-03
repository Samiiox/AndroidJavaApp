package com.example.androidapp;

import static Service.EntropyCalculator.calculateAndPrintEntropy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Controller.DataBaseHandler;
import Model.MachineLearning;
import Service.Bayes;
import Service.Knn;

public class SecondActivity extends AppCompatActivity {

    public String checkedRadioButtonText = "";

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText k;
    private DataBaseHandler dataBaseHandler;
    private Button buttonCalcule;
    private Knn knnHelper;
    private Bayes bayesHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        //recuperation des valeurs entrer par l'utilisateur depuis MainActivity
        Intent intent = getIntent();
        double mpgValue = intent.getDoubleExtra("MPG", 0.0);
        double displacementValue = intent.getDoubleExtra("DISPLACEMENT", 0.0);
        double accelerationValue = intent.getDoubleExtra("ACCELERATION", 0.0);
        double weightValue = intent.getDoubleExtra("WEIGHT", 0.0);
        double horsePowerValue = intent.getDoubleExtra("HORSEPOWER", 0.0);


        // Initialize views
//        textView = findViewById(R.id.textView);
        radioGroup = findViewById(R.id.radioGroupAlgo);
        TextInputLayout kEditTextLayout = findViewById(R.id.kInputLayout);
        k = findViewById(R.id.kInput);
        buttonCalcule = findViewById(R.id.submit_algo);
        //by defaul the kEditTextLayout should be invisible
        kEditTextLayout.setVisibility(View.GONE);


        // Set up the RadioGroup's OnCheckedChangeListener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = findViewById(checkedId);
                checkedRadioButtonText = checkedRadioButton.getText().toString();
                if (checkedId == R.id.radioButtonKnn) {
                    kEditTextLayout.setVisibility(View.VISIBLE);
                } else if(checkedId==R.id.radioButtonDT){
                    kEditTextLayout.setVisibility(View.GONE);
                    k.setText("");
                }else if(checkedId==R.id.radioButtonBayes){
                    kEditTextLayout.setVisibility(View.GONE);
                    k.setText("");
                }
            }
        });



        dataBaseHandler = new DataBaseHandler(this, "machine_learning", null, 1);
        buttonCalcule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG",checkedRadioButtonText);


                // Récupérez les données depuis la base de données
                List<MachineLearning> machineLearningList = dataBaseHandler.getAllData();

                if(checkedRadioButtonText.equals("KNN")){
                    int kValue = Integer.parseInt(k.getText().toString());
                    Log.e("K", String.valueOf(kValue));
                    knnHelper = new Knn();
                    String res = knnHelper.Knn( machineLearningList,  mpgValue,  displacementValue,  accelerationValue,  weightValue,  horsePowerValue,  kValue);
                    Log.d("res", res);
                } else if (checkedRadioButtonText.equals("BAYES NETWORK")) {
                    bayesHelper = new Bayes();
                    List<String> res = bayesHelper.Bayes( machineLearningList,  mpgValue,  displacementValue,  accelerationValue,  weightValue,  horsePowerValue);
                    System.out.println(res);
                } else if (checkedRadioButtonText.equals("DECISION TREE")) {
                    // Créez un tableau Ameliorer
                    Map<String, Double> entrop = calculateAndPrintEntropy(machineLearningList);
                }
            }
        });
    }
public Double logBase2(double numerateur , double denominateur ){
        if(numerateur == 0) return 0.0;
        return Math.log10(numerateur / denominateur) / Math.log10(2);
}

}
