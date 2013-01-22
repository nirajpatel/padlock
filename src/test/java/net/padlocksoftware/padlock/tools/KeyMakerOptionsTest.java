package net.padlocksoftware.padlock.tools;

import org.junit.Test;

import static org.junit.Assert.*;

public class KeyMakerOptionsTest {
    @Test
    public void testNoArgs() throws Exception {
        KeyMakerOptions options = new KeyMakerOptions(new String[] {});
        assertFalse(options.isValid());
    }

    @Test
    public void testKeyFile() throws Exception {
        String[] args = new String[] {
            "-k", "my.key"
        };
        KeyMakerOptions options = new KeyMakerOptions(args);
        assertTrue(options.isValid());
        assertEquals("my.key", options.getKeyFile().getPath());
        assertFalse(options.isJavaFragment());
    }

    @Test
    public void testIsJavaFragment() throws Exception {
        String[] args = new String[] {
            "-k", "my.key",
            "-j"
        };
        KeyMakerOptions options = new KeyMakerOptions(args);
        assertTrue(options.isValid());
        assertEquals("my.key", options.getKeyFile().getPath());
        assertTrue(options.isJavaFragment());
    }
}
