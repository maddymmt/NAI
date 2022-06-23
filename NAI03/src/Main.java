import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class Main {

    static Map<String, List<String>> loadFile(String filename) {
        Map<String, List<String>> mapList = new HashMap<>();

        try {
            Path path = Paths.get(filename);
            Files.walk(path).filter(Files::isRegularFile).forEach(filePath -> {
                        try {
                            String key = filePath.getParent().getFileName().toString();
                            if (!mapList.containsKey(filePath.getParent().getFileName().toString())) {
                                mapList.put(key, Collections.singletonList(Files.readString(filePath, Charset.defaultCharset())));
                            } else {
                                List<String> list = mapList.get(key);
                                list.add(Files.readString(filePath, Charset.defaultCharset()));
                                mapList.put(key, list);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapList;
    }

    static List<String> fillAlphabet() {
        List<String> alphabet = new LinkedList<>();
        for (int i = 0; i < 26; i++) {
            alphabet.add(Character.toString((char) (i + 97)));
        }
        return alphabet;
    }

    static void normalizeVector(List<Double> vector) {
        double length = 0;
        for (double d : vector) {
            length += d * d;
        }
        length = Math.sqrt(length);
        for (int i = 0; i < vector.size(); i++) {
            vector.set(i, vector.get(i) / length);
        }
    }

    static Map<String, List<List<Double>>> countChars(Map<String, List<String>> textMap) {
        Map<String, List<List<Double>>> map = new HashMap<>();
        //angielski, i np 10 roznych list double bo to rozne wektory
        List<String> alphabet = fillAlphabet();
        for (String key : textMap.keySet()) {
            for (String text : textMap.get(key)) {
                List<Double> vector = new LinkedList<>();
                text = text.toLowerCase().chars().filter(c -> c >= 97 && c <= 122).toString();
                for (String letter : alphabet) {
                    vector.add((double) text.toLowerCase().chars().filter(c -> c == letter.charAt(0)).count());
                }
                normalizeVector(vector);
                if (!map.containsKey(key)) {
                    map.put(key, Collections.singletonList(vector));
                } else {
                    List<List<Double>> list = map.get(key);
                    list.add(vector);
                    map.put(key, list);
                }
            }
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
        Map<String, List<String>> trainSet = loadFile("src/lang");
        Map<String, List<String>> testSet = loadFile("src/lang.test");

        Map<String, List<List<Double>>> vectorMap = countChars(trainSet);
        Map<String, List<List<Double>>> testVectorMap = countChars(testSet);

        double alfa = Double.parseDouble(args[0]);
        int count = 0;

        Perceptron English = new Perceptron("English",alfa);
        Perceptron Polish = new Perceptron("Polish",alfa);
        Perceptron German = new Perceptron("German",alfa);

        List<Perceptron> perceptrons = new LinkedList<>();
        perceptrons.add(English);
        perceptrons.add(Polish);
        perceptrons.add(German);

        while (count <= 10000) {
            for (int i = 0; i < perceptrons.size(); i++) {//dla kazdego z 3 językow
                Perceptron perceptron = perceptrons.get(i);
                for (Map.Entry<String, List<List<Double>>> entry : vectorMap.entrySet()) {//dla kazdego z 10 wektorow
                    perceptron.train(entry.getKey(),entry.getValue() );
                }
            }
            count++;
        }


        for (int i = 0; i < perceptrons.size(); i++) {//dla kazdego z 3 językow
            Perceptron perceptron = perceptrons.get(i);
            for (Map.Entry<String, List<List<Double>>> entry : testVectorMap.entrySet()) {//dla kazdego z 10 wektorow
                perceptron.test(entry.getKey(),entry.getValue() );
            }
        }


//        dla USER INPUT
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
