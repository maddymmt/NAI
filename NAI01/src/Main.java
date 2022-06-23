import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    static List<String[]> loadFile(String filename) throws IOException {

        List<String[]> dataset = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = "";

        while ((line = reader.readLine()) != null) {
            dataset.add(line.split(","));
        }
        reader.close();
        return dataset;
    }

    static double euclideanDistance(String[] trainRow, String[] testRow) {
        double distance = 0;
        for (int i = 0; i < trainRow.length - 1; i++) {
            distance += Math.pow((Double.parseDouble(trainRow[i]) - Double.parseDouble(testRow[i])), 2);
        }
        return Math.sqrt(distance);
    }

    static List<String[]> getNeighbors(List<String[]> trainSet, String[] testRow, int numberNeighbors) {
        Map<String[], Double> distances = new HashMap<>();
        for (int i = 0; i < trainSet.size(); i++) {
            double distance = euclideanDistance(trainSet.get(i), testRow);
            distances.put(trainSet.get(i), distance);
        }

        List<Map.Entry<String[], Double>> entries = new LinkedList<>(distances.entrySet());
        entries.sort(Map.Entry.comparingByValue());

        List<String[]> nearestNeighbors = new LinkedList<>();
        entries.stream().limit(numberNeighbors).forEach(e -> nearestNeighbors.add(e.getKey()));

        return nearestNeighbors;
    }

    static String PredictClassification(List<String[]> trainSet, String[] testRow, int numberNeighbors) {
        List<String[]> nearestNeighbors = getNeighbors(trainSet, testRow, numberNeighbors);
        int frequency = 0;
        Map<String, Integer> outputValues = new HashMap<>();

        for (int i = 0; i < nearestNeighbors.size(); i++) {
            String nazwaKwiatka = nearestNeighbors.get(i)[testRow.length - 1];
            if (!outputValues.containsKey(nazwaKwiatka)) {
                outputValues.put(nazwaKwiatka, 1);
            } else {
                outputValues.put(nazwaKwiatka, ++frequency);
            }
        }

        List<Map.Entry<String, Integer>> outputValuesSorted = new LinkedList<>(outputValues.entrySet());
        outputValuesSorted.sort(Map.Entry.comparingByValue());
        outputValuesSorted = outputValuesSorted.stream().limit(1).collect(Collectors.toList());

        return String.valueOf(outputValuesSorted.get(0).getKey());
    }

    public static void main(String[] args) throws IOException {

        List<String[]> trainSet = loadFile(args[1]);
        List<String[]> testSet = loadFile(args[2]);
        int numberNeighbors = Integer.parseInt(args[0]);
        double score = 0;
        for (int i = 0; i < testSet.size(); i++) {
            String[] row = testSet.get(i);
            String prediction = PredictClassification(trainSet, testSet.get(i), numberNeighbors);
            System.out.println(Arrays.toString(testSet.get(i)) + ", Prediction: " + PredictClassification(trainSet, testSet.get(i), numberNeighbors));

            if (row[row.length - 1].equals(prediction)){
                score++;
            }
        }
        score = (score/ testSet.size())*100;
        System.out.println("Accuracy: " + score);
        System.out.println("==================================");
        System.out.println("Podaj wektor do klasyfikacji");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String wektor = "";
        while ((wektor = reader.readLine()) != null) {
            String[] wektorRow = wektor.split(",");
            System.out.println(Arrays.toString(wektorRow) + ", Prediction: " + PredictClassification(trainSet, wektorRow, numberNeighbors));
            System.out.println("Podaj wektor do klasyfikacji");
        }
        reader.close();




    }

}
