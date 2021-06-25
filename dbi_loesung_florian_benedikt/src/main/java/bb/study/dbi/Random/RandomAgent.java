package bb.study.dbi.Random;

public class RandomAgent {
    public String name;
    public String city;
    public int percent;

    private final String[] given_names = {"Benedikt", "Florian", "Armann", "Mauritz", "Alexander", "Robin", "Arne"};
    private final String[] names = {"Schwering", "Tünte", "Grewal", "Langer", "Eisner", "Vens-Cappell", "Kottenhahn"};
    private final String[] cities = {"Borken", "Münster", "Bocholt", "Gescher", "Telgte", "Mussum", "Berlin", "Köln", "Düsseldorf", "Raesfeld", "Ahaus", "Gronau", "Velen", "Gemen"};

    public RandomAgent() {
        this.name = given_names[(int) (Math.random() * given_names.length)] + " " + names[(int) (Math.random() * names.length)];
        this.city = cities[(int) (Math.random() * cities.length)];
        this.percent = (int) (Math.random() * 25);
    }
}
