import java.util.LinkedList;
import java.util.List;

public class Perceptron {
    private List<Double> weights;
    private double odchylenie;
    private double alfa;
    private String name;


    public Perceptron(String name, double alfa) {
        this.name = name;
        odchylenie = Math.random();
        this.alfa = alfa;
        weights = fillWeights();
    }



    static List<Double> fillWeights() {
        List<Double> weight = new LinkedList<>();
        for (int i = 0; i < 26; i++) {
            weight.add(Math.random());
        }
        return weight;
    }

    public Integer wartoscWyjsciowa(List<Double> vector, List<Double> weight, double odchylenie) {
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
    }

    public void train(String nameGiven, List<List<Double>> vectorList) {
        if (weights.size() == 0) {
            fillWeights();
        }
        for (int j = 0; j < vectorList.size(); j++) { //dla kazdego z zestawu treningowego
            List<Double> vector = vectorList.get(j);
            int netExpected;
            if (name.equals(nameGiven)) {
                netExpected = 1;
            } else {
                netExpected = 0;
            }
            int net = (wartoscWyjsciowa(vector, weights, odchylenie));
            if (net == netExpected) {
                continue;
            }
            regulaDelta(vector, weights, alfa, netExpected, net);
            odchylenie = odchylenie - alfa * (netExpected - net);
        }
    }

    public void test(String nameGiven, List<List<Double>> vectorList) {
        int correct = 0;
        for (int j = 0; j < vectorList.size(); j++) { //dla kazdego z zestawu treningowego
            List<Double> vector = vectorList.get(j);
            int net = wartoscWyjsciowa(vector, weights, odchylenie);
            int netExpected;
            if (name.equals(nameGiven)) {
                netExpected = 1;
            } else {
                netExpected = 0;
            }
            if (net == netExpected) {
                correct++;
            }
        }
    }

    public String getName() {
        return name;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public double getOdchylenie() {
        return odchylenie;
    }
}
