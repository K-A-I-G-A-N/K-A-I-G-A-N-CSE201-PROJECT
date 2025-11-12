import java.io.*;
import java.util.*;

public class AppLoader {

    public static List<App> loadAppsFromCSV(String filename) {
        List<App> apps = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                // Skip header line
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // Split by commas not inside quotes
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (parts.length < 8) {
                    System.out.println("Skipping line (invalid columns): " + line);
                    continue;
                }

                String name = parts[0].trim();
                String date = parts[1].trim();
                String version = parts[2].trim();
                String company = parts[3].trim();

                // Split platforms by comma and optional space
                String[] platforms = parts[4].trim().split(",\\s*");

                String link = parts[6].trim();
                double price = 0.0;

                try {
                    // Remove $ if present
                    price = Double.parseDouble(parts[7].replace("$", "").trim());
                } catch (Exception e) {
                    System.out.println("Could not parse price for: " + name);
                }

                App app = new App(name, date, version, company, platforms, link, price);
                apps.add(app);
                System.out.println("Loaded: " + name + " | Platforms: " + Arrays.toString(platforms));
            }

            System.out.println("✅ Loaded " + apps.size() + " apps from CSV file.");
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }

        return apps;
    }
}
