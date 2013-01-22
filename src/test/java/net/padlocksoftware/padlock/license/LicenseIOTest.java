package net.padlocksoftware.padlock.license;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jason
 */
public class LicenseIOTest extends Assert {
    @Test
    public void testLicenseIO() throws Exception {
        License license = LicenseFactory.createLicense();
        license.addProperty("name", "Jason Nichols");
        license.addProperty("email", "jason@padlocksoftware.net");
        license.setFloatingExpirationPeriod(200000L);

        File file = File.createTempFile("test", "lic");
        
        LicenseIO.exportLicense(license, file);

        License license2 = LicenseIO.importLicense(file);

        assertTrue(license.equals(license2));

        file.delete();
    }
}
