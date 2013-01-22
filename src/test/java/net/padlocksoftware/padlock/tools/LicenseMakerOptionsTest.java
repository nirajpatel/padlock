package net.padlocksoftware.padlock.tools;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.padlocksoftware.padlock.TestUtils;

import org.kohsuke.args4j.CmdLineException;

import org.junit.Assert;
import org.junit.Test;

public class LicenseMakerOptionsTest extends Assert {
    @Test
    public void testNoArgs() throws Exception {
        LicenseMakerOptions options = new LicenseMakerOptions(new String[] {});
        assertFalse(options.isValid());
    }

    @Test
    public void testNoKeyFile() throws Exception {
        String[] args = new String[] {
            "-o", "newfile.lic"
        };
        LicenseMakerOptions options = new LicenseMakerOptions(args);
        assertFalse(options.isValid());
    }

    @Test
    public void testNoOutputFile() throws Exception {
        String[] args = new String[] {
            "-k", "somefile.key",
        };
        LicenseMakerOptions options = new LicenseMakerOptions(args);
        assertFalse(options.isValid());
    }

    @Test
    public void testNoOutputFileButStandardOutIsSpecified() throws Exception {
        String[] args = new String[] {
            "-k", "somefile.key", "-O"
        };
        LicenseMakerOptions options = new LicenseMakerOptions(args);
        assertTrue(options.isStandardOut());
        assertNotNull(options.getProperties());
        assertNotNull(options.getAddresses());
    }

    @Test
    public void testStartDateStartTimeInMsChecked() throws Exception {
        String[] args = new String[] {
            "-k", "somefile.key", "-O", "-s", "23123", "-S", "2013/12/12"
        };

        LicenseMakerOptions options = new LicenseMakerOptions(args);
        assertFalse(options.isValid());
    }

    @Test
    public void testExpireDateExpireTimeInMsChecked() throws Exception {
        String[] args = new String[] {
            "-k", "somefile.key", "-O", "-e", "23123", "-E", "2013/12/12"
        };

        LicenseMakerOptions options = new LicenseMakerOptions(args);
        assertFalse(options.isValid());
    }

    @Test
    public void testDates() throws Exception {
        String[] args = new String[] {
            "-k", "somefile.key", "-o", "newfile.lic", "-S", "2012/12/22", "-E", "2013/12/30"
        };

        LicenseMakerOptions options = new LicenseMakerOptions(args);
        assertEquals("2012/12/22", TestUtils.toString(options.getStartDate()));
        assertEquals("2013/12/30", TestUtils.toString(options.getExpirationDate()));
    }

    @Test
    public void testSimpleArgs() throws Exception {
        Properties fileProps = new Properties();
        fileProps.setProperty("hello", "world");
        fileProps.setProperty("first", "First: ${first}");
        File tmpProps = savePropertiesFile(fileProps);
        
        String[] args = new String[] {
            "-k", "somefile.key", "-o", "newfile.lic", "-s", TestUtils.toDateLong("2012/12/22").toString(), "-e", TestUtils.toDateLong("2013/11/23").toString(), "-x",
            "12312", "-p", "first=jason", "second=mathew", "last=pell", "-h", "192.168.0.5", "192.168.0.6",
            "-P", tmpProps.getAbsoluteFile().toString()
        };

        LicenseMakerOptions options = new LicenseMakerOptions(args);
        assertEquals("somefile.key", options.getKeyFile().getPath());
        assertEquals("newfile.lic", options.getLicenseFile().getPath());
        assertEquals("2012/12/22", TestUtils.toString(options.getStartDate()));
        assertEquals("2013/11/23", TestUtils.toString(options.getExpirationDate()));
        
        assertEquals("12312", options.getExpirationFloatInMs().toString());

        Map<String, String> properties = options.getProperties();
        assertEquals("First: jason", properties.get("first"));
        assertEquals("mathew", properties.get("second"));
        assertEquals("pell", properties.get("last"));
        assertEquals("world", properties.get("hello"));
        
        Set<String> macs = options.getAddresses();
        assertTrue(macs.contains("192.168.0.5"));
        assertTrue(macs.contains("192.168.0.6"));
        
        tmpProps.delete();
    }

    private File savePropertiesFile(Properties p) throws Exception {
        File tmpFile = File.createTempFile("props", ".properties");
        FileWriter writer = new FileWriter(tmpFile);
        p.store(writer, null);
        writer.close();
        return tmpFile;
    }
}
