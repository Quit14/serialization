import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Basket {
    private String[] names;
    private double[] prices;
    private int[] amount;

    public Basket() { //objectmapper всегда нужен дефолтный конструктор
    }

    public Basket(String[] names, double[] prices) {
        this.names = names;
        this.prices = prices;
        this.amount = new int[names.length];
    }

    public Basket(String[] names, double[] prices, int[] amount) {
        this.names = names;
        this.prices = prices;
        this.amount = amount;
    }

    public void addToCart(int productNum, int amount) {
        this.amount[productNum - 1] += amount;
    }

    public int[] getAmount() {

        return amount;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public double[] getPrices() {
        return prices;
    }

    public void setPrices(double[] prices) {
        this.prices = prices;
    }

    // запись корзины в json-файл
    public void saveJson(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, new Basket(this.names, this.prices, this.amount));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //воссоздание корзины из файла
    public static Basket loadFromJsonFile(File jsonFile) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        Basket basket = mapper.readValue(jsonFile, Basket.class);
        return basket;
    }


    public void printCart() {
        IntStream
                .range(0, names.length)
                .filter(i -> amount[i] > 0)
                .forEach(i -> System.out.println((i + 1) + ". "
                        + names[i] + " - " + amount[i]
                        + " шт., " + prices[i] * amount[i] + " руб."));

        Map<Double, Integer> total = IntStream.range(0, prices.length).boxed()
                .collect(Collectors.toMap(price -> prices[price], cnt -> amount[cnt]));
        double totalSum = total.entrySet().stream().
                filter(cnt -> cnt.getValue() > 0)
                .mapToDouble(i -> i.getValue() * i.getKey()).sum();

        int totalAmount = total.values()
                .stream()
                .filter(amount -> amount > 0).mapToInt(a -> a)
                .sum();
        System.out.println("Итого: " + totalAmount + " позиций на общую сумму "
                + totalSum + " рублей");
    }
}