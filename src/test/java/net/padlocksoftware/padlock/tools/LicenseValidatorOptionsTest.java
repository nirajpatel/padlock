package net.padlocksoftware.padlock.tools;

import org.junit.Assert;
import org.junit.Test;

public class LicenseValidatorOptionsTest extends Assert {
    @Test
    public void testNoArgs() throws Exception {
        LicenseValidatorOptions options = new LicenseValidatorOptions(new String[] {});
        assertFalse(options.isValid());
    }

    @Test
    public void testNoKeyFile() throws Exception {
        String[] args = new String[] {
            "-l", "license.lic"
        };
        LicenseValidatorOptions options = new LicenseValidatorOptions(args);
        assertFalse(options.isValid());
    }

    @Test
    public void testNoOutputFile() throws Exception {
        String[] args = new String[] {
            "-k", "somefile.key"
        };
        LicenseValidatorOptions options = new LicenseValidatorOptions(args);
        assertFalse(options.isValid());
    }

    @Test
    public void testValid() throws Exception {
        String[] args = new String[] {
            "-k", "somefile.key",
            "-l", "license.lic"
        };
        LicenseValidatorOptions options = new LicenseValidatorOptions(args);
        assertTrue(options.isValid());
        assertEquals("somefile.key", options.getKeyFile().getPath());
        assertEquals("license.lic", options.getLicenseFile().getPath());
    }
}
