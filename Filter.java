import java.util.*;
import java.util.stream.Collectors;

public class Filter {
    private String criteria;

    public Filter(String criteria) {
        this.criteria = criteria;
    }

    public List<App> applyFilter(List<App> apps, String platform, boolean freeOnly) {
        System.out.println("\nApplying Filter → Platform: " + platform + ", Free Only: " + freeOnly);
        System.out.println("Checking " + apps.size() + " apps...");

        List<App> results = apps.stream()
                .filter(a -> platform.isEmpty() || Arrays.stream(a.getPlatforms())
                        .anyMatch(p -> p.trim().equalsIgnoreCase(platform)))
                .filter(a -> !freeOnly || a.getPrice() == 0.0)
                .collect(Collectors.toList());

        System.out.println("Filtered results: " + results.size() + " apps match.\n");
        return results;
    }
}
