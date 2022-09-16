import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Basket implements Serializable {
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

//Сериализация - создание файла
    public void saveBin(File file, Basket basket) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(basket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Десериализация - восстановление корзины
    public static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            basket = (Basket) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
