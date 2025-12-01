import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

public class FilterTest {
    private FilterGUI gui;
    void setUp() {
        gui = new FilterGUI();

        gui.setAllAppsForTest(Arrays.asList(
        	    new FilterGUI.App(new String[]{"Spotify", "Spotify Inc", "", "", "", "Music", ""}),
        	    new FilterGUI.App(new String[]{"Instagram", "Meta", "", "", "", "Social", ""}),
        	    new FilterGUI.App(new String[]{"YouTube", "Google", "", "", "", "Video", ""}),
        	    new FilterGUI.App(new String[]{"Apple Music", "Apple", "", "", "", "Music", ""})
        	));
    }
    
    @Test
    void testFilterMusicCategory() {
        List<FilterGUI.App> result = gui.filterAppsByCategory("Music");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(a -> a.name.equals("Spotify")));
        assertTrue(result.stream().anyMatch(a -> a.name.equals("Apple Music")));
    }

    @Test
    void testFilterSocialCategory() {
        List<FilterGUI.App> result = gui.filterAppsByCategory("Social");

        assertEquals(1, result.size());
        assertEquals("Instagram", result.get(0).name);
    }

    @Test
    void testFilterEmptyCategoryReturnsAllApps() {
        List<FilterGUI.App> result = gui.filterAppsByCategory("");

        assertEquals(4, result.size());
    }

    @Test
    void testFilterInvalidCategory() {
        List<FilterGUI.App> result = gui.filterAppsByCategory("Games");

        assertEquals(0, result.size());
    }
}
