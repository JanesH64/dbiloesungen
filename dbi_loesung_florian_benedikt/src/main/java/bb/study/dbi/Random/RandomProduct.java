package bb.study.dbi.Random;

public class RandomProduct {
    public String name;
    public String city;
    public int quantity;
    public float price;

    private final String[] products = {"Bratwurst", "Staubsauger"};
    private final String[] cities = {"Borken", "Münster", "Bocholt", "Gescher", "Telgte", "Mussum", "Berlin", "Köln", "Düsseldorf", "Raesfeld", "Ahaus", "Gronau", "Velen", "Gemen"};

    public RandomProduct() {
        this.name = products[(int) (Math.random() * products.length)];
        this.city = cities[(int) (Math.random() * cities.length)];
        this.quantity = (int) (Math.random() * 1000);
        this.price = (float) (Math.round((Math.random() * 100) * 100.0) / 100.0);
    }
}
