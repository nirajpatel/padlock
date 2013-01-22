package net.padlocksoftware.padlock.tools;

import java.io.File;

import net.padlocksoftware.padlock.TestUtils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class LicenseMakerTest extends Assert {
    @Test
    public void testCreateLicense() throws Exception {
        File keyFile = TestUtils.loadResourceAsFile("/valid.key");
        File tmpFile = File.createTempFile("license", "lic");
        String[] args = new String[] {
            "-k", keyFile.getPath(), "-o", tmpFile.getPath()
        };
        
        tmpFile.delete();
        assertFalse(tmpFile.exists());
        LicenseMaker.main(args);
        
        assertTrue(tmpFile.exists());
        tmpFile.delete();
        
        keyFile.delete();
    }
}
