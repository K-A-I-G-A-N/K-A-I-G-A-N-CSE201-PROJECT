import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class SearchBarTest {

    @Test
    public void testAppSearchFound() {
        // Typically, SearchBar would have access to the list/database of app names.
        // Let's assume searchApps returns a list of matching names.

        SearchBar sb = new SearchBar(null, false);
        List<String> results = sb.searchApps("WhatsApp");
        assertTrue(results.contains("WhatsApp"));
    }
}

    