package Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.MachineLearning;

public class Bayes implements Algorithms{

    public List<String> Bayes(List<MachineLearning> machineLearningList, double mpgValue, double displacementValue, double accelerationValue, double weightValue, double horsePowerValue) {

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


            //liste comptenant A et B en plus de calcule des occurence de j , a et e en total
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
            }
        System.out.println(newListUpdated);

            double compteurMpgA = 0;
            double compteurMpgB = 0;
            double compteurDispA = 0;
            double compteurDispB = 0;
            double compteurHorseA = 0;
            double compteurHorseB = 0;
            double compteurWeightA = 0;
            double compteurWeightB = 0;
            double compteurAccA = 0;
            double compteurAccB = 0;

            //japanese
            for (Map<String, String> rowData : newListUpdated) {

                //------------------------------------------section MPG--------------------------------------
                String updatedMpg = rowData.get("mpg");

                // Le nombre total d'occurrences de "mpg" A ou B
                occurrencesJapenese.put(updatedMpg, occurrencesJapenese.getOrDefault(updatedMpg, 0) + 1);

                // Vérifiez si la valeur est "A" et si "japanese" est dans "origin"
                if ("A".equals(updatedMpg) && "japanese".equals(rowData.get("origin"))) {
                    compteurMpgA++;
                    japaneseProba.put("mpg_A", compteurMpgA / occurrencesMap.get("japanese"));
                }

                // Vérifiez si la valeur est "B" et si "japanese" est dans "origin"
                if ("B".equals(updatedMpg) && "japanese".equals(rowData.get("origin"))) {
                    compteurMpgB++;
                    japaneseProba.put("mpg_B", compteurMpgB / occurrencesMap.get("japanese"));
                }


                //------------------------------------------section Displacement--------------------------------------
                String updatedDisp = rowData.get("displacement");
                occurrencesJapenese.put(updatedDisp, occurrencesJapenese.getOrDefault(updatedDisp, 0) + 1);

                if ("A".equals(updatedDisp) && "japanese".equals(rowData.get("origin"))) {
                    compteurDispA++;
                    japaneseProba.put("displacement_A", compteurDispA / occurrencesMap.get("japanese"));
                }

                if ("B".equals(updatedDisp) && "japanese".equals(rowData.get("origin"))) {
                    compteurDispB++;
                    japaneseProba.put("displacement_B", compteurDispB / occurrencesMap.get("japanese"));
                }


                //------------------------------------------section Horse Power--------------------------------------
                String updatedHorse = rowData.get("horsePower");
                occurrencesJapenese.put(updatedHorse, occurrencesJapenese.getOrDefault(updatedHorse, 0) + 1);

                if ("A".equals(updatedHorse) && "japanese".equals(rowData.get("origin"))) {
                    compteurHorseA++;
                    japaneseProba.put("horsePower_A", compteurHorseA / occurrencesMap.get("japanese"));
                }

                if ("B".equals(updatedHorse) && "japanese".equals(rowData.get("origin"))) {
                    compteurHorseB++;
                    japaneseProba.put("horsePower_B", compteurHorseB / occurrencesMap.get("japanese"));
                }


                //------------------------------------------section Weight--------------------------------------
                String updatedWeight = rowData.get("weight");
                occurrencesJapenese.put(updatedWeight, occurrencesJapenese.getOrDefault(updatedWeight, 0) + 1);

                if ("A".equals(updatedWeight) && "japanese".equals(rowData.get("origin"))) {
                    compteurWeightA++;
                    japaneseProba.put("weight_A", compteurWeightA / occurrencesMap.get("japanese"));
                }

                if ("B".equals(updatedWeight) && "japanese".equals(rowData.get("origin"))) {
                    compteurWeightB++;
                    japaneseProba.put("weight_B", compteurWeightB / occurrencesMap.get("japanese"));
                }


                //------------------------------------------section Acceleration--------------------------------------
                String updatedAcc = rowData.get("acceleration");
                occurrencesJapenese.put(updatedAcc, occurrencesJapenese.getOrDefault(updatedAcc, 0) + 1);

                if ("A".equals(updatedAcc) && "japanese".equals(rowData.get("origin"))) {
                    compteurAccA++;
                    japaneseProba.put("acceleration_A", compteurAccA / occurrencesMap.get("japanese"));
                }

                if ("B".equals(updatedAcc) && "japanese".equals(rowData.get("origin"))) {
                    compteurAccB++;
                    japaneseProba.put("acceleration_B", compteurAccB / occurrencesMap.get("japanese"));
                }


            }


            //initialisation des compteurs
            compteurMpgA = compteurMpgB = compteurDispA = compteurDispB = compteurHorseA = compteurHorseB = compteurWeightA = compteurWeightB = compteurAccA = compteurAccB = 0;


            //american
            for (Map<String, String> rowData : newListUpdated) {

                //------------------------------------------section MPG--------------------------------------
                String updatedMpg = rowData.get("mpg");

                // Le nombre total d'occurrences de "mpg" A ou B
                occurrencesAmerican.put(updatedMpg, occurrencesAmerican.getOrDefault(updatedMpg, 0) + 1);

                // Vérifiez si la valeur est "A" et si "japanese" est dans "origin"
                if ("A".equals(updatedMpg) && "american".equals(rowData.get("origin"))) {
                    compteurMpgA++;
                    americanProba.put("mpg_A", compteurMpgA / occurrencesMap.get("american"));
                }

                // Vérifiez si la valeur est "B" et si "japanese" est dans "origin"
                if ("B".equals(updatedMpg) && "american".equals(rowData.get("origin"))) {
                    compteurMpgB++;
                    americanProba.put("mpg_B", compteurMpgB / occurrencesMap.get("american"));
                } else americanProba.put("mpg_B", 0.0);


                //------------------------------------------section Displacement--------------------------------------
                String updatedDisp = rowData.get("displacement");
                occurrencesAmerican.put(updatedDisp, occurrencesAmerican.getOrDefault(updatedDisp, 0) + 1);

                if ("A".equals(updatedDisp) && "american".equals(rowData.get("origin"))) {
                    compteurDispA++;
                    americanProba.put("displacement_A", compteurDispA / occurrencesMap.get("american"));
                }

                if ("B".equals(updatedDisp) && "american".equals(rowData.get("origin"))) {
                    compteurDispB++;
                    americanProba.put("displacement_B", compteurDispB / occurrencesMap.get("american"));
                } else americanProba.put("displacement_B", 0.0);


                //------------------------------------------section Horse Power--------------------------------------
                String updatedHorse = rowData.get("horsePower");
                occurrencesAmerican.put(updatedHorse, occurrencesAmerican.getOrDefault(updatedHorse, 0) + 1);

                if ("A".equals(updatedHorse) && "american".equals(rowData.get("origin"))) {
                    compteurHorseA++;
                    americanProba.put("horsePower_A", compteurHorseA / occurrencesMap.get("american"));
                }

                if ("B".equals(updatedHorse) && "american".equals(rowData.get("origin"))) {
                    compteurHorseB++;
                    americanProba.put("horsePower_B", compteurHorseB / occurrencesMap.get("american"));
                } else americanProba.put("horsePower_B", 0.0);


                //------------------------------------------section Weight--------------------------------------
                String updatedWeight = rowData.get("weight");
                occurrencesAmerican.put(updatedWeight, occurrencesAmerican.getOrDefault(updatedWeight, 0) + 1);

                if ("A".equals(updatedWeight) && "american".equals(rowData.get("origin"))) {
                    compteurWeightA++;
                    americanProba.put("weight_A", compteurWeightA / occurrencesMap.get("american"));
                }

                if ("B".equals(updatedWeight) && "american".equals(rowData.get("origin"))) {
                    compteurWeightB++;
                    americanProba.put("weight_B", compteurWeightB / occurrencesMap.get("american"));
                } else americanProba.put("weight_B", 0.0);


                //------------------------------------------section Acceleration--------------------------------------
                String updatedAcc = rowData.get("acceleration");
                occurrencesAmerican.put(updatedAcc, occurrencesAmerican.getOrDefault(updatedAcc, 0) + 1);

                if ("A".equals(updatedAcc) && "american".equals(rowData.get("origin"))) {
                    compteurAccA++;
                    americanProba.put("acceleration_A", compteurAccA / occurrencesMap.get("american"));
                } else americanProba.put("acceleration_A", 0.0);

                if ("B".equals(updatedAcc) && "american".equals(rowData.get("origin"))) {
                    compteurAccB++;
                    americanProba.put("acceleration_B", compteurAccB / occurrencesMap.get("american"));
                }


            }


            //initialisation des compteurs
            compteurMpgA = compteurMpgB = compteurDispA = compteurDispB = compteurHorseA = compteurHorseB = compteurWeightA = compteurWeightB = compteurAccA = compteurAccB = 0;


            //european
            for (Map<String, String> rowData : newListUpdated) {

                //------------------------------------------section MPG--------------------------------------
                String updatedMpg = rowData.get("mpg");

                // Le nombre total d'occurrences de "mpg" A ou B
                occurrencesEuropean.put(updatedMpg, occurrencesEuropean.getOrDefault(updatedMpg, 0) + 1);

                // Vérifiez si la valeur est "A" et si "japanese" est dans "origin"
                if ("A".equals(updatedMpg) && "european".equals(rowData.get("origin"))) {
                    compteurMpgA++;
                    europeanProba.put("mpg_A", compteurMpgA / occurrencesMap.get("european"));
                }

                // Vérifiez si la valeur est "B" et si "japanese" est dans "origin"
                if ("B".equals(updatedMpg) && "european".equals(rowData.get("origin"))) {
                    compteurMpgB++;
                    europeanProba.put("mpg_B", compteurMpgB / occurrencesMap.get("european"));
                }


                //------------------------------------------section Displacement--------------------------------------
                String updatedDisp = rowData.get("displacement");
                occurrencesEuropean.put(updatedDisp, occurrencesEuropean.getOrDefault(updatedDisp, 0) + 1);

                if ("A".equals(updatedDisp) && "european".equals(rowData.get("origin"))) {
                    compteurDispA++;
                    europeanProba.put("displacement_A", compteurDispA / occurrencesMap.get("european"));
                }

                if ("B".equals(updatedDisp) && "european".equals(rowData.get("origin"))) {
                    compteurDispB++;
                    europeanProba.put("displacement_B", compteurDispB / occurrencesMap.get("european"));
                }


                //------------------------------------------section Horse Power--------------------------------------
                String updatedHorse = rowData.get("horsePower");
                occurrencesEuropean.put(updatedHorse, occurrencesEuropean.getOrDefault(updatedHorse, 0) + 1);

                if ("A".equals(updatedHorse) && "european".equals(rowData.get("origin"))) {
                    compteurHorseA++;
                    europeanProba.put("horsePower_A", compteurHorseA / occurrencesMap.get("european"));
                }

                if ("B".equals(updatedHorse) && "european".equals(rowData.get("origin"))) {
                    compteurHorseB++;
                    europeanProba.put("horsePower_B", compteurHorseB / occurrencesMap.get("european"));
                }


                //------------------------------------------section Weight--------------------------------------
                String updatedWeight = rowData.get("weight");
                occurrencesEuropean.put(updatedWeight, occurrencesEuropean.getOrDefault(updatedWeight, 0) + 1);

                if ("A".equals(updatedWeight) && "european".equals(rowData.get("origin"))) {
                    compteurWeightA++;
                    europeanProba.put("weight_A", compteurWeightA / occurrencesMap.get("european"));
                }

                if ("B".equals(updatedWeight) && "european".equals(rowData.get("origin"))) {
                    compteurWeightB++;
                    europeanProba.put("weight_B", compteurWeightB / occurrencesMap.get("european"));
                } else europeanProba.put("weight_B", 0.0);


                //------------------------------------------section Acceleration--------------------------------------
                String updatedAcc = rowData.get("acceleration");
                occurrencesEuropean.put(updatedAcc, occurrencesEuropean.getOrDefault(updatedAcc, 0) + 1);

                if ("A".equals(updatedAcc) && "european".equals(rowData.get("origin"))) {
                    compteurAccA++;
                    europeanProba.put("acceleration_A", compteurAccA / occurrencesMap.get("european"));
                }

                if ("B".equals(updatedAcc) && "european".equals(rowData.get("origin"))) {
                    compteurAccB++;
                    europeanProba.put("acceleration_B", compteurAccB / occurrencesMap.get("european"));
                }


            }

    //                    System.out.println(europeanProba);


            //Map qui contient les valeurs de user avec A et B
            Map<String, String> userValueUpdated = new HashMap<>();
            userValueUpdated.put("mpg", mpgValue >= mpgMoyen ? "A" : "B");
            userValueUpdated.put("displacement", displacementValue >= displacementMoyen ? "A" : "B");
            userValueUpdated.put("horsePower", horsePowerValue >= horsePowerMoyen ? "A" : "B");
            userValueUpdated.put("weight", weightValue >= weightMoyen ? "A" : "B");
            userValueUpdated.put("acceleration", accelerationValue >= accelerationMoyen ? "A" : "B");


    //                    System.out.println(userValueUpdated);


            //Calcule de probabilité
            Map<String, Double> calculeProba = new HashMap<>();

            for (String attribute : userValueUpdated.keySet()) {
                // Construct the key for each region's probability based on the attribute and user's updated value
                String japaneseKey = attribute + "_" + userValueUpdated.get(attribute);
                String americanKey = attribute + "_" + userValueUpdated.get(attribute);
                String europeanKey = attribute + "_" + userValueUpdated.get(attribute);

                // Retrieve the probabilities from each region's map based on the keys
                Double japaneseProb = japaneseProba.get(japaneseKey);
                Double americanProb = americanProba.get(americanKey);
                Double europeanProb = europeanProba.get(europeanKey);

                // Store the result in calculeProba
                calculeProba.put(attribute + "_japaneseProba", japaneseProb);
                calculeProba.put(attribute + "_americanProba", americanProb);
                calculeProba.put(attribute + "_europeanProba", europeanProb);
            }

    // Calculate the final product of probabilities for each region
            Double finalJapaneseProba = calculeProba.get("mpg_japaneseProba") * calculeProba.get("displacement_japaneseProba")
                    * calculeProba.get("horsePower_japaneseProba") * calculeProba.get("weight_japaneseProba")
                    * calculeProba.get("acceleration_japaneseProba");

            Double finalAmericanProba = calculeProba.get("mpg_americanProba") * calculeProba.get("displacement_americanProba")
                    * calculeProba.get("horsePower_americanProba") * calculeProba.get("weight_americanProba")
                    * calculeProba.get("acceleration_americanProba");
    //
            Double finalEuropeanProba = calculeProba.get("mpg_europeanProba") * calculeProba.get("displacement_europeanProba")
                    * calculeProba.get("horsePower_europeanProba") * calculeProba.get("weight_europeanProba")
                    * calculeProba.get("acceleration_europeanProba");

            calculeProba.put("finalJapaneseProba", finalJapaneseProba);
            calculeProba.put("finalAmericanProba", finalAmericanProba);
            calculeProba.put("finalEuropeanProba", finalEuropeanProba);

            System.out.println(calculeProba.get("finalJapaneseProba"));
            System.out.println(calculeProba.get("finalAmericanProba"));
            System.out.println(calculeProba.get("finalEuropeanProba"));

            // Convert the values to a list of strings
            List<String> resultList = Arrays.asList(
                    String.valueOf(calculeProba.get("finalJapaneseProba")),
                    String.valueOf(calculeProba.get("finalAmericanProba")),
                    String.valueOf(calculeProba.get("finalEuropeanProba"))
            );

        return resultList;



    }

}

