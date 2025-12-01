
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.swing.*;
import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.*;

public class AppBaseTest {

    /******************************
     * SECTION 1 — GUEST TESTS
     ******************************/
	
// Test 1: Ensures that calling Guest.guestAccess() does NOT throw any exceptions.
    @Test
    void testGuestAccessDoesNotThrow() {
        assertDoesNotThrow(() -> {
            SwingUtilities.invokeAndWait(Guest::guestAccess);
        });
    }
    
// Test 2: Confirms that a JFrame is actually created when guestAccess() is executed.
    @Test
    void testGuestFrameIsCreated() {
        SwingUtilities.invokeLater(Guest::guestAccess);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {}

        Frame[] frames = Frame.getFrames();
        assertTrue(frames.length > 0);
    }


    /******************************
     * SECTION 2 — FILTER TESTS
     ******************************/
    private FilterGUI gui;

    @BeforeAll
    static void createFakeCSV() throws Exception {
        FileWriter fw = new FileWriter("App Database.csv");
        fw.write("Name,Company,Platforms,Link,Price,Genre,Description\n");
        fw.close();
    }

    @BeforeEach
    void setUpFilter() {
        gui = new FilterGUI(); // <-- FilterGUI now created AFTER fake CSV exists

        gui.setAllAppsForTest(java.util.Arrays.asList(
            new FilterGUI.App(new String[]{"Spotify", "Spotify Inc", "", "", "", "Music", ""}),
            new FilterGUI.App(new String[]{"Instagram", "Meta", "", "", "", "Social", ""}),
            new FilterGUI.App(new String[]{"YouTube", "Google", "", "", "", "Video", ""}),
            new FilterGUI.App(new String[]{"Apple Music", "Apple", "", "", "", "Music", ""})
        ));
    }

    
// Test 3: Checks that filtering by "Music" returns only the two Music apps.
    @Test
    void testFilterMusicCategory() {
        java.util.List<FilterGUI.App> result = gui.filterAppsByCategory("Music");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(a -> a.name.equals("Spotify")));
        assertTrue(result.stream().anyMatch(a -> a.name.equals("Apple Music")));
    }
    
// Test 4: Verifies that filtering by "Social" returns exactly one result: Instagram.
    @Test
    void testFilterSocialCategory() {
        java.util.List<FilterGUI.App> result = gui.filterAppsByCategory("Social");

        assertEquals(1, result.size());
        assertEquals("Instagram", result.get(0).name);
    }
    
// Test 5: Ensures that an empty category returns all apps (no filtering applied).
    @Test
    void testFilterEmptyCategoryReturnsAllApps() {
        java.util.List<FilterGUI.App> result = gui.filterAppsByCategory("");

        assertEquals(4, result.size());
    }
    
// Test 6: Confirms that selecting a category with no matches returns an empty list.
    @Test
    void testFilterInvalidCategory() {
        java.util.List<FilterGUI.App> result = gui.filterAppsByCategory("Games");

        assertEquals(0, result.size());
    }


    /******************************
     * SECTION 3 — SEARCHBAR TESTS
     ******************************/
    private File tempDatabase;

    @BeforeEach
    void setUpCSV() throws Exception {
        tempDatabase = new File("App Database.csv");
        FileWriter writer = new FileWriter(tempDatabase);

        writer.write("Name,Company,Platforms,Link,Price,Genre,Description\n");
        writer.write("WhatsApp,Meta,,link,,Social,Messaging app\n");
        writer.write("Instagram,Meta,,link,,Social,Photo sharing\n");
        writer.write("Spotify,Spotify AB,,link,,Music,Music streaming\n");

        writer.close();
    }

    void cleanUpCSV() {
        tempDatabase.delete();
    }
    private String invokeFindApp(SearchBar sb, String query) throws Exception {
        Method method = SearchBar.class.getDeclaredMethod("findAppInDatabase", String.class);
        method.setAccessible(true);
        return (String) method.invoke(sb, query);
    }
// Test 7: Ensures that searching for “whatsapp” correctly returns “WhatsApp”.
    @Test
    void testAppFoundInDatabase() throws Exception {
        SearchBar sb = new SearchBar(new JFrame(), true);
        String result = invokeFindApp(sb, "whatsapp");
        assertEquals("WhatsApp", result);
    }
// Test 8: Ensures that searching for “spotify” returns the Spotify entry.
    @Test
    void testAnotherAppFound() throws Exception {
        SearchBar sb = new SearchBar(new JFrame(), true);
        String result = invokeFindApp(sb, "spotify");
        assertEquals("Spotify", result);
    }
 // Test 9: Confirms that searching for an app not in the CSV returns null.
    @Test
    void testAppNotFound() throws Exception {
        SearchBar sb = new SearchBar(new JFrame(), true);
        String result = invokeFindApp(sb, "nonexistentapp");
        assertNull(result);
    }
}
