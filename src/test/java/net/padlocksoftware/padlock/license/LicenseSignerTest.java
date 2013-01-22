package net.padlocksoftware.padlock.license;

import java.io.File;
import java.security.KeyPair;
import java.security.interfaces.DSAPrivateKey;

import net.padlocksoftware.padlock.KeyManager;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jason
 */
public class LicenseSignerTest extends Assert {
    /**
     * Test of sign method, of class LicenseSigner.
     */
    @Test
    public void testSign() throws Exception {
        License license = LicenseFactory.createLicense();
        license.addProperty("Name", "Jason Nichols");
        license.addProperty("Email", "jason@padlocksoftware.net");

        KeyPair pair = KeyManager.createKeyPair();

        LicenseSigner signer = LicenseSigner.createLicenseSigner((DSAPrivateKey)pair.getPrivate());
        signer.sign(license);
        
        File testSigned = File.createTempFile("testSigned", "lic");
        LicenseIO.exportLicense(license, testSigned);
        assertTrue(license.isSigned());
        testSigned.delete();
    }

}
