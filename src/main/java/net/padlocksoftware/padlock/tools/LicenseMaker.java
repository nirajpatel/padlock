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

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.interfaces.DSAPrivateKey;
import java.util.Map;

import net.padlocksoftware.padlock.KeyManager;
import net.padlocksoftware.padlock.license.License;
import net.padlocksoftware.padlock.license.LicenseFactory;
import net.padlocksoftware.padlock.license.LicenseIO;
import net.padlocksoftware.padlock.license.LicenseSigner;

import org.kohsuke.args4j.CmdLineException;

/**
 * @author Jason Nichols (jason@padlocksoftware.net)
 */
public class LicenseMaker {
    private final KeyPair keyPair;
    private final License license;

    public LicenseMaker(final LicenseMakerOptions options) throws IOException {
        this.keyPair = KeyManager.importKeyPair(options.getKeyFile());
        this.license = LicenseFactory.createLicense();

        if (options.getStartDate() != null) {
            license.setStartDate(options.getStartDate());
        }

        if (options.getExpirationDate() != null) {
            license.setExpirationDate(options.getExpirationDate());
        }

        if (options.getExpirationFloatInMs() != null) {
            license.setFloatingExpirationPeriod(options.getExpirationFloatInMs());
        }

        for (Map.Entry<String, String> entry : options.getProperties().entrySet()) {
            license.addProperty(entry.getKey(), entry.getValue());
        }

        for (String address : options.getAddresses()) {
            license.addHardwareAddress(address);
        }

        LicenseSigner signer = LicenseSigner.createLicenseSigner((DSAPrivateKey) keyPair.getPrivate());
        signer.sign(license);
    }

    public void writeLicence(OutputStream licenseStream) throws IOException {
        LicenseIO.exportLicense(license, licenseStream);
        licenseStream.close();
    }

    public static void main(String[] args) throws CmdLineException, IOException {
        LicenseMakerOptions options = new LicenseMakerOptions(args);
        if (options.isValid()) {
            LicenseMaker maker = new LicenseMaker(options);
            
            if (options.isStandardOut()) {
                ByteArrayOutputStream licenseStream = new ByteArrayOutputStream();
                maker.writeLicence(licenseStream);
                System.out.println(licenseStream.toString());
            } else {
                maker.writeLicence(new FileOutputStream(options.getLicenseFile()));
            }
        } else {
            System.err.println("Error: " + options.getErrorMessage());
            System.err.println(options.getUsage());
        }
    }
}
