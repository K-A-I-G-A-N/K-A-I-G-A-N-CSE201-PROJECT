import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.Frame;
public class GuestTest {
    @Test
    void testGuestAccessDoesNotThrow() {
        assertDoesNotThrow(() -> {
            SwingUtilities.invokeAndWait(Guest::guestAccess);
        });
    }
    @Test
    void testGuestFrameIsCreated() {
        SwingUtilities.invokeLater(Guest::guestAccess);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {}
        Frame[] frames = Frame.getFrames();
        assertTrue(frames.length > 0);
    }
}
