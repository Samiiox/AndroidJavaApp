package Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.MachineLearning;

public class Knn implements Algorithms{


    public String Knn(List<MachineLearning> machineLearningList, double mpgValue, double displacementValue, double accelerationValue, double weightValue, double horsePowerValue, int kValue) {
        // Créez un tableau pour stocker les données avec la colonne "distance euclidienne"
        List<Map<String, String>> dataWithDistanceList = new ArrayList<>();


        // Parcourez les données et ajoutez-les au tableau avec la distance euclidienne calculée
        for (MachineLearning data : machineLearningList) {
            Map<String, String> rowData = new HashMap<>();

            rowData.put("mpg", String.valueOf(data.getMpg()));
            rowData.put("displacement", String.valueOf(data.getDisplacement()));
            rowData.put("horsePower", String.valueOf(data.getHorsePower()));
            rowData.put("weight", String.valueOf(data.getWeight()));
            rowData.put("acceleration", String.valueOf(data.getAcceleration()));
            rowData.put("origin", String.valueOf(data.getOrigin()));

            String distance = calculateEuclideanDistance(rowData, mpgValue, displacementValue,
                    horsePowerValue, weightValue, accelerationValue);


            rowData.put("distance", String.valueOf(distance));

            // Ajoutez l'objet au tableau
            dataWithDistanceList.add(rowData);

        }


        // Triez la liste en fonction de la colonne "distance"
        Collections.sort(dataWithDistanceList, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> rowData1, Map<String, String> rowData2) {
                // Convertissez les distances en double pour la comparaison
                double distance1 = Double.parseDouble(rowData1.get("distance"));
                double distance2 = Double.parseDouble(rowData2.get("distance"));
                return Double.compare(distance1, distance2);
            }
        });

        // Créez une liste pour stocker les trois valeurs minimales de "origin"
        List<String> minOriginValues = new ArrayList<>();

        // Parcourez les k premiers éléments de la liste triée
        for (int i = 0; i < Math.min(kValue, dataWithDistanceList.size()); i++) {
            Map<String, String> rowData = dataWithDistanceList.get(i);
            // Récupérez la valeur de la colonne "origin" pour chaque élément
            String origin = rowData.get("origin");
            // Ajoutez la valeur à la liste
            minOriginValues.add(origin);
        }
        System.out.println(minOriginValues);

        // Déterminez l'origin le plus fréquent parmi les k valeurs
        String mostFrequentOrigin = findMostFrequentOrigin(minOriginValues);

        return mostFrequentOrigin;
    }

    private String calculateEuclideanDistance(Map<String, String> dataRow, double mpg, double displacement,
                                              double horsePower, double weight,  double acceleration) {
        // Récupérez les valeurs de chaque colonne du tableau
        double rowMpg = Double.parseDouble(dataRow.get("mpg"));
        double rowDisplacement = Double.parseDouble(dataRow.get("displacement"));
        double rowHorsePower = Double.parseDouble(dataRow.get("horsePower"));
        double rowWeight = Double.parseDouble(dataRow.get("weight"));
        double rowAcceleration = Double.parseDouble(dataRow.get("acceleration"));


        // Calculez la distance euclidienne
        double distance = Math.sqrt(
                Math.pow(rowMpg - mpg, 2) +
                        Math.pow(rowDisplacement - displacement, 2) +
                        Math.pow(rowHorsePower - horsePower, 2) +
                        Math.pow(rowWeight - weight, 2) +
                        Math.pow(rowAcceleration - acceleration, 2)
        );

        return String.valueOf(distance);
    }

    private String findMostFrequentOrigin(List<String> originList) {
        Map<String, Integer> originCount = new HashMap<>();

        // Comptez le nombre d'occurrences de chaque origin
        for (String origin : originList) {
            originCount.put(origin, originCount.getOrDefault(origin, 0) + 1);
        }

        // Trouvez l'origin avec le plus grand nombre d'occurrences
        String mostFrequentOrigin = null;
        int maxOccurrences = 0;

        for (Map.Entry<String, Integer> entry : originCount.entrySet()) {
            String origin = entry.getKey();
            int occurrences = entry.getValue();

            if (occurrences > maxOccurrences) {
                maxOccurrences = occurrences;
                mostFrequentOrigin = origin;
            }
        }

        return mostFrequentOrigin;
    }
}

