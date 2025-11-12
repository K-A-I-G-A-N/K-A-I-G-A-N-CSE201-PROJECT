public class App {
    private String name;
    private String date;
    private String version;
    private String company;
    private String[] platforms;
    private String link;
    private double price;

    public App(String name, String date, String version, String company, String[] platforms, String link, double price) {
        this.name = name;
        this.date = date;
        this.version = version;
        this.company = company;
        this.platforms = platforms;
        this.link = link;
        this.price = price;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getVersion() { return version; }
    public String getCompany() { return company; }
    public String[] getPlatforms() { return platforms; }
    public String getLink() { return link; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " by " + company + " (" + String.join(", ", platforms) + ") - $" + price;
    }
}
