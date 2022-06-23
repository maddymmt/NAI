import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {


    static List<String[]> loadFile(String filename) throws IOException {
        List<String[]> dataset = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            dataset.add(line.split(","));
        }
        reader.close();
        return dataset;
    }

    static List<Double> fillWeights(int dimension) {
        List<Double> weight = new LinkedList<>();
        for (int i = 0; i < dimension; i++) {
            weight.add(Math.random());
        }
        return weight;
    }

    static Integer wartoscWyjsciowa(List<Double> vector, List<Double> weight, double odchylenie) {
        double net = 0;
        for (int i = 0; i < vector.size(); i++) {
            net += weight.get(i) * vector.get(i);
        }
        net = net - odchylenie;
        if (net >= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    static void regulaDelta(List<Double> vector, List<Double> weight, double alfa, double netExpected, double net) {
        for (int i = 0; i < vector.size(); i++) {
            double newDouble = weight.get(i) + alfa * (netExpected - net) * vector.get(i);
            weight.set(i, newDouble);
        }
        odchylenie = odchylenie - alfa * (netExpected - net);
    }

    static Double odchylenie = Math.random();
//    static Double odchylenie = 0.0;

    public static void main(String[] args) throws IOException {

        List<String[]> trainSet = loadFile(args[1]);
        List<String[]> testSet = loadFile(args[2]);
        List<Double> weight = fillWeights(trainSet.get(0).length - 1);
        double alfa = Double.parseDouble(args[0]);
        double E = 0;
        int count = 0;

        //dla TRAIN SET
        while (count <= 350) {
            int correct = 0;
            double roznica = 0;
            for (int i = 0; i < trainSet.size(); i++) {
                List<Double> vector = new LinkedList<>();
                int netExpected;
                for (int j = 0; j < trainSet.get(i).length - 1; j++) {
                    vector.add(Double.parseDouble(trainSet.get(i)[j]));
                }
                if (trainSet.get(i)[trainSet.get(i).length - 1].equals("Iris-virginica")) {
                    netExpected = 1;
                } else {
                    netExpected = 0;
                }
                int net = wartoscWyjsciowa(vector, weight, odchylenie);
                roznica += Math.pow(netExpected - net, 2);
//                System.out.println("Wagi: " + weight +" Wektor: "+ Arrays.toString(trainSet.get(i)) + " Output: " + net);
                if (net == netExpected) {
                    correct++;
                    continue;
                }
                regulaDelta(vector, weight, alfa, netExpected, net);
            }
            E = 1 - (1.0 / trainSet.size()) * roznica;
            count++;
        }

        System.out.println("Dokladnosc dla TRAINT SET: " + E);

        //dla TEST SET

        int correct = 0;
        for (int i = 0; i < testSet.size(); i++) {
            List<Double> vector = new LinkedList<>();
            for (int j = 0; j < testSet.get(i).length - 1; j++) {
                vector.add(Double.parseDouble(testSet.get(i)[j]));
            }
            int net = wartoscWyjsciowa(vector, weight, odchylenie);
            System.out.println(i + " " + Arrays.toString(testSet.get(i)) + (net == 1 ? " Iris-virginica" : " Iris-versicolor"));
            if (net == 1 && testSet.get(i)[testSet.get(i).length - 1].equals("Iris-virginica") || net == 0 && testSet.get(i)[testSet.get(i).length - 1].equals("Iris-versicolor")) {
                correct++;
            }
        }
        System.out.println(correct);


        //dla USER INPUT
//        System.out.println("\n==================================");
//        System.out.println("Podaj wektor do klasyfikacji");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        String input = "";
//
//        while ((input = reader.readLine()) != null) {
//            List<Double> vector = Arrays.stream(input.split(",")).map(Double::parseDouble).toList();
//            int output = wartoscWyjsciowa(vector, weight,odchylenie);
//            System.out.println(output == 1 ? "Iris-virginica" : "Iris-versicolor");
//            System.out.println("Podaj wektor do klasyfikacji");
//        }
//        reader.close();
    }
}
