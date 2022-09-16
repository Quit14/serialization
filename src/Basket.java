import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Basket {
    private String[] names;
    private double[] prices;
    private int[] amount;

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
// запись корзины в файл
    protected void saveTxt(File file) {
        try (PrintWriter saveTxt = new PrintWriter(file)) {
            for (int i = 0; i < names.length; i++) {
                saveTxt.print(names[i] + " " + prices[i]+ " " + amount[i] + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//воссоздание корзины из файла
    public static Basket loadFromTxtFile(File textFile) {
        try {
            BufferedReader buff = new BufferedReader(new FileReader(textFile));
            String s;
            List<String> names = new ArrayList<>();
            List<Double> prices = new ArrayList<>();
            List<Integer> amount = new ArrayList<>();
            while ((s = buff.readLine()) != null) {
                String[] parts = s.split(" ");
                names.add(parts[0]);
                prices.add(Double.parseDouble(parts[1]));
                amount.add(Integer.parseInt(parts[2]));
            }
            String[] name = names.toArray(new String[names.size()]);
            double[] price = prices.stream().mapToDouble(d -> d).toArray();
            int[] amounts = amount.stream().mapToInt(i -> i).toArray();
            buff.close();

            return new Basket(name, price, amounts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
