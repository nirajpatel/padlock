package net.padlocksoftware.padlock.tools;

import java.io.File;

import net.padlocksoftware.padlock.TestUtils;

import org.junit.Test;

import static org.junit.Assert.*;

public class LicenseValidatorTest {
    @Test
    public void testValid() throws Exception {
        File keyFile = TestUtils.loadResourceAsFile("/valid.key");
        File licFile = TestUtils.loadResourceAsFile("/valid.lic");
        String[] args = new String[] {
            "-k", keyFile.getPath(), "-o", licFile.getPath(),
        };
        
        LicenseValidator.main(args);
    }

}
