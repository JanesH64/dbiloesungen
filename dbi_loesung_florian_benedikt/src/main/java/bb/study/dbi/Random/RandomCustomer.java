package bb.study.dbi.Random;

public class RandomCustomer {
    public String name;
    public String city;
    public float discnt;

    private final String[] names = {"Flender GmbH", "d.velop AG", "GuideCom AG", "Takko Fashion GmbH", "items GmbH"};
    private final String[] cities = {"Borken", "Münster", "Bocholt", "Gescher", "Telgte", "Mussum", "Berlin", "Köln", "Düsseldorf", "Raesfeld", "Ahaus", "Gronau", "Velen", "Gemen"};

    public RandomCustomer() {
        this.name = names[(int) (Math.random() * names.length)];
        this.city = cities[(int) (Math.random() * cities.length)];
        this.discnt = (float) (Math.random() * 25);
    }
}
