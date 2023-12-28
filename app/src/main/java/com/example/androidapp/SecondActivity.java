package com.example.androidapp;

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

    private TextView textView;
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
        textView = findViewById(R.id.textView);
        radioGroup = findViewById(R.id.radioGroup);
        k = findViewById(R.id.editTextK);
        buttonCalcule = findViewById(R.id.buttonCalcule);



        // Set up the RadioGroup's OnCheckedChangeListener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                radioButton = findViewById(checkedId);

                if (checkedId == R.id.radioButtonKnn) {
                    k.setVisibility(View.VISIBLE);
                    textView.setText("Selected: KNN");
                } else if (checkedId == R.id.radioButtonDT) {
                    k.setVisibility(View.INVISIBLE);
                    textView.setText("Selected: Decision Tree");
                } else if (checkedId == R.id.radioButtonB) {
                    k.setVisibility(View.INVISIBLE);
                    textView.setText("Selected: Bayes Network");
                }
            }
        });



        dataBaseHandler = new DataBaseHandler(this, "machine_learning", null, 1);
        buttonCalcule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Récupérez les données depuis la base de données
                List<MachineLearning> machineLearningList = dataBaseHandler.getAllData();


                if(textView.getText() == "Selected: KNN"){
                    int kValue = Integer.parseInt(k.getText().toString());
                    knnHelper = new Knn();
                    String res = knnHelper.Knn( machineLearningList,  mpgValue,  displacementValue,  accelerationValue,  weightValue,  horsePowerValue,  kValue);
                    Log.d("res", res);
                } else if (textView.getText() == "Selected: Bayes Network") {
                    bayesHelper = new Bayes();
                    List<String> res = bayesHelper.Bayes( machineLearningList,  mpgValue,  displacementValue,  accelerationValue,  weightValue,  horsePowerValue);
                    System.out.println(res);
                } else if (textView.getText() == "Selected: Decision Tree") {
                    // Créez un tableauAmeliorer
                    List<Map<String, String>> newList = new ArrayList<>();

                    for (MachineLearning data : machineLearningList) {
                        Map<String, String> rowData = new HashMap<>();

                        rowData.put("mpg", String.valueOf(data.getMpg()));
                        rowData.put("displacement", String.valueOf(data.getDisplacement()));
                        rowData.put("horsePower", String.valueOf(data.getHorsePower()));
                        rowData.put("weight", String.valueOf(data.getWeight()));
                        rowData.put("acceleration", String.valueOf(data.getAcceleration()));
                        rowData.put("origin", String.valueOf(data.getOrigin()));

                        newList.add(rowData);

                    }


                    double size = newList.size();
                    double mpgMoyen = 0;
                    double displacementMoyen = 0;
                    double accelerationMoyen = 0;
                    double weightMoyen = 0;
                    double horsePowerMoyen = 0;


                    for (Map<String, String> rowData : newList) {
                        // calculer la somme de la colonne
                        mpgMoyen += Integer.parseInt(rowData.get("mpg"));
                        displacementMoyen += Integer.parseInt(rowData.get("displacement"));
                        accelerationMoyen += Integer.parseInt(rowData.get("acceleration"));
                        weightMoyen += Integer.parseInt(rowData.get("weight"));
                        horsePowerMoyen += Integer.parseInt(rowData.get("horsePower"));

                    }
                    //calculer la moyenne
                    mpgMoyen /= size;
                    displacementMoyen /= size;
                    accelerationMoyen /= size;
                    weightMoyen /= size;
                    horsePowerMoyen /= size;


                    //liste qui vas stocker le tableau qui contient A et B
                    List<Map<String, String>> newListUpdated = new ArrayList<>();

                    //Map qui vas contenir le nombre d'occurence de Origin
                    Map<String, Integer> occurrencesMap = new HashMap<>();

                    Map<String, Double> japaneseProba = new HashMap<>();
                    Map<String, Double> americanProba = new HashMap<>();
                    Map<String, Double> europeanProba = new HashMap<>();


                    Map<String, Integer> occurrencesJapenese = new HashMap<>();
                    Map<String, Integer> occurrencesAmerican = new HashMap<>();
                    Map<String, Integer> occurrencesEuropean = new HashMap<>();


                    //liste comptenant A et B en plus de calcule des occurence
                    List<String> attributes = Arrays.asList("mpg", "displacement", "horsePower", "weight", "acceleration");
                    List<String> types = Arrays.asList("japanese", "american", "european");
                    List<String> values = Arrays.asList("A", "B");

                    // Generate attribute names
//                    for (String attribute : attributes) {
//                        for (String type : types) {
//                            for (String value : values) {
//                                String attributeName = "count_" + type + "_" + value + "_" + attribute;
//                            }
//                        }
//                    }










                    Double countJA = 0.0;
                    Double countJB = 0.0;
                    Double countAA = 0.0;
                    Double countAB = 0.0;
                    Double countEA = 0.0;
                    Double countEB = 0.0;
                    Double totalAMpg = 0.0;
                    Double totalBMpg = 0.0;
                    Double entropieA = 0.0;
                    Double entropieB = 0.0;
                    Map<String , Double> mpgProba = new HashMap<>();
                    Map<String , Double> entropie = new HashMap<>();
                    for (Map<String, String> rowData : newList) {
                        // Mettez à jour la valeur de "mpg" en fonction de la moyenne
                        rowData.put("mpg", (Integer.parseInt(rowData.get("mpg")) >= mpgMoyen) ? "A" : "B");
                        rowData.put("displacement", (Integer.parseInt(rowData.get("displacement")) >= displacementMoyen) ? "A" : "B");
                        rowData.put("horsePower", (Integer.parseInt(rowData.get("horsePower")) >= horsePowerMoyen) ? "A" : "B");
                        rowData.put("weight", (Integer.parseInt(rowData.get("weight")) >= weightMoyen) ? "A" : "B");
                        rowData.put("acceleration", (Integer.parseInt(rowData.get("acceleration")) >= accelerationMoyen) ? "A" : "B");
                        // Ajoutez la ligne mise à jour à la nouvelle liste
                        newListUpdated.add(rowData);

                        String origin = rowData.get("origin");
                        occurrencesMap.put(origin, occurrencesMap.getOrDefault(origin, 0) + 1);

                        // Check the mpg
                        String updatedOrigin = rowData.get("origin");
                        if ("A".equals(rowData.get("mpg"))) {
                            if ("japanese".equals(updatedOrigin)) {
                                countJA++;
                            } else if("american".equals(updatedOrigin)) {
                                countAA++;
                            } else if ("european".equals(updatedOrigin)) {
                                countEA++;
                            }
                        }else if("B".equals(rowData.get("mpg"))){
                            if ("japanese".equals(updatedOrigin)) {
                                countJB++;
                            } else if("american".equals(updatedOrigin)) {
                                countAB++;
                            } else if ("european".equals(updatedOrigin)) {
                                countEB++;
                            }
                        }

                        if ("A".equals(rowData.get("mpg"))) {
                            totalAMpg++;
                        } else if ("B".equals(rowData.get("mpg"))) {
                            totalBMpg++;
                        }


                    }


                    entropieA = -(countJA/totalAMpg)*logBase2(countJA , totalAMpg)
                            -(countAA/totalAMpg)*logBase2(countAA , totalAMpg)
                            -(countEA/totalAMpg)*logBase2(countEA , totalAMpg);
                    entropieB = -(countJB/totalBMpg)*logBase2(countJB , totalBMpg)
                            -(countAB/totalBMpg)*logBase2(countAB , totalBMpg)
                            -(countEB/totalBMpg)*logBase2(countEB , totalBMpg);

                    mpgProba.put("JA",countJA);
                    mpgProba.put("JB",countJB);
                    mpgProba.put("AA",countAA);
                    mpgProba.put("AB",countAB);
                    mpgProba.put("EA",countEA);
                    mpgProba.put("EB",countJA);
                    mpgProba.put("entropieA",entropieA);
                    mpgProba.put("entropieB",entropieB);

                    entropie.put("mpg" , ((countEA+countAA+countJA)/(totalAMpg+totalBMpg))*entropieA
                    + ((countEB+countAB+countJB)/(totalAMpg+totalBMpg))*entropieB);

                    System.out.println(mpgProba.get("entropieA"));
                    System.out.println(mpgProba.get("entropieB"));
                    System.out.println(entropie);


                     countJA = 0.0;
                     countJB = 0.0;
                     countAA = 0.0;
                     countAB = 0.0;
                     countEA = 0.0;
                     countEB = 0.0;
                     totalAMpg = 0.0;
                     totalBMpg = 0.0;
                     entropieA = 0.0;
                     entropieB = 0.0;


                    for (Map<String, String> rowData : newList) {
                        String origin = rowData.get("origin");

                        // Check the displacement
                        String updatedOrigin = rowData.get("origin");
                        if ("A".equals(rowData.get("displacement"))) {
                            if ("japanese".equals(updatedOrigin)) {
                                countJA++;
                            } else if("american".equals(updatedOrigin)) {
                                countAA++;
                            } else if ("european".equals(updatedOrigin)) {
                                countEA++;
                            }
                        }else if("B".equals(rowData.get("displacement"))){
                            if ("japanese".equals(updatedOrigin)) {
                                countJB++;
                            } else if("american".equals(updatedOrigin)) {
                                countAB++;
                            } else if ("european".equals(updatedOrigin)) {
                                countEB++;
                            }
                        }

                        if ("A".equals(rowData.get("displacement"))) {
                            totalAMpg++;
                        } else if ("B".equals(rowData.get("displacement"))) {
                            totalBMpg++;
                        }



                    }

                    entropieA = -(countJA/totalAMpg)*logBase2(countJA , totalAMpg)
                            -(countAA/totalAMpg)*logBase2(countAA , totalAMpg)
                            -(countEA/totalAMpg)*logBase2(countEA , totalAMpg);
                    entropieB = -(countJB/totalBMpg)*logBase2(countJB , totalBMpg)
                            -(countAB/totalBMpg)*logBase2(countAB , totalBMpg)
                            -(countEB/totalBMpg)*logBase2(countEB , totalBMpg);

                    mpgProba.put("JA",countJA);
                    mpgProba.put("JB",countJB);
                    mpgProba.put("AA",countAA);
                    mpgProba.put("AB",countAB);
                    mpgProba.put("EA",countEA);
                    mpgProba.put("EB",countJA);
                    mpgProba.put("entropieA",entropieA);
                    mpgProba.put("entropieB",entropieB);

                    entropie.put("mpg" , ((countEA+countAA+countJA)/(totalAMpg+totalBMpg))*entropieA
                            + ((countEB+countAB+countJB)/(totalAMpg+totalBMpg))*entropieB);

                    System.out.println(mpgProba.get("entropieA"));
                    System.out.println(mpgProba.get("entropieB"));
                    System.out.println(entropie);







                    countJA = 0.0;
                    countJB = 0.0;
                    countAA = 0.0;
                    countAB = 0.0;
                    countEA = 0.0;
                    countEB = 0.0;
                    totalAMpg = 0.0;
                    totalBMpg = 0.0;
                    entropieA = 0.0;
                    entropieB = 0.0;


                    for (Map<String, String> rowData : newList) {
                        String origin = rowData.get("origin");

                        // Check the displacement
                        String updatedOrigin = rowData.get("origin");
                        if ("A".equals(rowData.get("horsePower"))) {
                            if ("japanese".equals(updatedOrigin)) {
                                countJA++;
                            } else if("american".equals(updatedOrigin)) {
                                countAA++;
                            } else if ("european".equals(updatedOrigin)) {
                                countEA++;
                            }
                        }else if("B".equals(rowData.get("horsePower"))){
                            if ("japanese".equals(updatedOrigin)) {
                                countJB++;
                            } else if("american".equals(updatedOrigin)) {
                                countAB++;
                            } else if ("european".equals(updatedOrigin)) {
                                countEB++;
                            }
                        }

                        if ("A".equals(rowData.get("horsePower"))) {
                            totalAMpg++;
                        } else if ("B".equals(rowData.get("horsePower"))) {
                            totalBMpg++;
                        }



                    }

                    entropieA = -(countJA/totalAMpg)*logBase2(countJA , totalAMpg)
                            -(countAA/totalAMpg)*logBase2(countAA , totalAMpg)
                            -(countEA/totalAMpg)*logBase2(countEA , totalAMpg);
                    entropieB = -(countJB/totalBMpg)*logBase2(countJB , totalBMpg)
                            -(countAB/totalBMpg)*logBase2(countAB , totalBMpg)
                            -(countEB/totalBMpg)*logBase2(countEB , totalBMpg);

                    mpgProba.put("JA",countJA);
                    mpgProba.put("JB",countJB);
                    mpgProba.put("AA",countAA);
                    mpgProba.put("AB",countAB);
                    mpgProba.put("EA",countEA);
                    mpgProba.put("EB",countJA);
                    mpgProba.put("entropieA",entropieA);
                    mpgProba.put("entropieB",entropieB);

                    entropie.put("mpg" , ((countEA+countAA+countJA)/(totalAMpg+totalBMpg))*entropieA
                            + ((countEB+countAB+countJB)/(totalAMpg+totalBMpg))*entropieB);

                    System.out.println(mpgProba.get("entropieA"));
                    System.out.println(mpgProba.get("entropieB"));
                    System.out.println(entropie);









                    }



            }
        });
    }
public Double logBase2(double numerateur , double denominateur ){
        if(numerateur == 0) return 0.0;
        return Math.log10(numerateur / denominateur) / Math.log10(2);
}

}
