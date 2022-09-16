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

    public void addToCart(int productNum, int amount) {
        this.amount[productNum - 1] += amount;
    }

    public int[] getAmount() {
        return amount;
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
