import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.io.*;
import java.lang.reflect.Method;

public class SearchBarTest {

    private File tempDatabase;

    @BeforeEach
    void setUp() throws Exception {
        tempDatabase = new File("App Database.csv");
        FileWriter writer = new FileWriter(tempDatabase);
        writer.write("Name,Company,Platforms,Link,Price,Genre,Description\n");
        writer.write("WhatsApp,Meta,,link,,Social,Messaging app\n");
        writer.write("Instagram,Meta,,link,,Social,Photo sharing\n");
        writer.write("Spotify,Spotify AB,,link,,Music,Music streaming\n");
        writer.close();
    }
    @AfterEach
    void cleanUp() {
        tempDatabase.delete();
    }

    private String invokeFindApp(SearchBar sb, String query) throws Exception {
        Method method = SearchBar.class.getDeclaredMethod("findAppInDatabase", String.class);
        method.setAccessible(true);
        return (String) method.invoke(sb, query);
    }

    @Test
    void testAppFoundInDatabase() throws Exception {
        SearchBar sb = new SearchBar(new JFrame(), true);
        String result = invokeFindApp(sb, "whatsapp");
        assertEquals("WhatsApp", result);
    }

    @Test
    void testAnotherAppFound() throws Exception {
        SearchBar sb = new SearchBar(new JFrame(), true);
        String result = invokeFindApp(sb, "spotify");
        assertEquals("Spotify", result);
    }

    @Test
    void testAppNotFound() throws Exception {
        SearchBar sb = new SearchBar(new JFrame(), true);
        String result = invokeFindApp(sb, "nonexistentapp");
        assertNull(result);
    }
}
