/*
 * Copyright (c) 2009-2012 Jason Nichols

 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights to 
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
 * of the Software, and to permit persons to whom the Software is furnished to do 
 * so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */

package net.padlocksoftware.padlock.tools;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import net.padlocksoftware.padlock.KeyManager;
import net.padlocksoftware.padlock.license.ImportException;
import net.padlocksoftware.padlock.license.License;
import net.padlocksoftware.padlock.license.LicenseIO;
import net.padlocksoftware.padlock.license.LicenseState;
import net.padlocksoftware.padlock.license.TestResult;
import net.padlocksoftware.padlock.validator.Validator;
import net.padlocksoftware.padlock.validator.ValidatorException;
import org.apache.commons.codec.binary.Hex;

/**
 * @author Jason Nichols (jason@padlocksoftware.net)
 */
public class LicenseValidator {
    private final License license;
    private final KeyPair pair;

    public LicenseValidator(LicenseValidatorOptions options) throws IOException {
        license = LicenseIO.importLicense(options.getLicenseFile());
        pair = KeyManager.importKeyPair(options.getKeyFile()); 
    }
    
    public String validate() {
        Date currentDate = new Date();

        Validator v = new Validator(license, new String(Hex.encodeHex(pair.getPublic().getEncoded())));
        v.setIgnoreFloatTime(true);

        LicenseState state;
        try {
            state = v.validate();
        } catch (ValidatorException e) {
            state = e.getLicenseState();
        }

        StringBuilder builder = new StringBuilder();
        
        // Show test status
        builder.append("\nValidation Test Results:\n");
        builder.append("========================\n\n");
        for (TestResult result : state.getTests()) {
            builder.append("\t" + result.getTest().getName() + "\t\t\t"
                               + (result.passed() ? "Passed" : "Failed") + "\n");
        }

        builder.append("\nLicense state: " + (state.isValid() ? "Valid" : "Invalid" + "\n"));

        //
        // Cycle through any dates
        //
        Date d = license.getCreationDate();
        builder.append("\nCreation date: \t\t" + d + "\n");

        d = license.getStartDate();
        builder.append("Start date: \t\t" + d + "\n");

        d = license.getExpirationDate();
        builder.append("Expiration date: \t" + d + "\n");

        Long floatPeroid = license.getFloatingExpirationPeriod();
        if (floatPeroid != null) {
            long seconds = floatPeroid / 1000L;
            builder.append("\nExpire after first run: " + seconds + " seconds\n");

        }

        if (floatPeroid != null || license.getExpirationDate() != null) {
            long remaining = v.getTimeRemaining(currentDate) / 1000L;
            builder.append("\nTime remaining: " + remaining + " seconds\n");
        }

        //
        // License properties
        //
        builder.append("\nLicense Properties\n");
        Properties p = license.getProperties();
        if (p.size() == 0) {
            builder.append("None\n");
        }

        for (final Enumeration propNames = p.propertyNames(); propNames.hasMoreElements();) {
            final String key = (String)propNames.nextElement();
            builder.append("Property: " + key + " = " + p.getProperty(key) + "\n");
        }

        //
        // Hardware locking
        //
        for (String address : license.getHardwareAddresses()) {
            builder.append("\nHardware lock: " + address + "\n");
        }
        builder.append("\n");
        
        return builder.toString();
    }

    /**
     * @param args the command line arguments
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        LicenseValidatorOptions options = new LicenseValidatorOptions(args);
        if (options.isValid()) {
            LicenseValidator validator = new LicenseValidator(options);
            System.out.println(validator.validate());
        } else {
            System.err.println(options.getErrorMessage());
            System.err.println(options.getUsage());
        }
    }
}
