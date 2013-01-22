package net.padlocksoftware.padlock.tools;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.kohsuke.args4j.CmdLineException;

import org.junit.Assert;
import org.junit.Test;

public class LicenseMakerOptionsTest extends Assert {
    @Test(expected = CmdLineException.class)
    public void testNoArgs() throws Exception {
        new LicenseMakerOptions(new String[] {});
    }

    @Test(expected = CmdLineException.class)
    public void testNoKeyFile() throws Exception {
        String[] args = new String[] {
            "-o", "newfile.lic"
        };
        new LicenseMakerOptions(args);
    }

    @Test(expected = CmdLineException.class)
    public void testNoOutputFile() throws Exception {
        String[] args = new String[] {
            "-k", "somefile.key",
        };
        new LicenseMakerOptions(args);
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

    @Test(expected = CmdLineException.class)
    public void testStartDateStartTimeInMsChecked() throws Exception {
        String[] args = new String[] {
            "-k", "somefile.key", "-O", "-s", "23123", "-S", "2013/12/12"
        };

        new LicenseMakerOptions(args);
    }

    @Test(expected = CmdLineException.class)
    public void testExpireDateExpireTimeInMsChecked() throws Exception {
        String[] args = new String[] {
            "-k", "somefile.key", "-O", "-e", "23123", "-E", "2013/12/12"
        };

        new LicenseMakerOptions(args);
    }

    @Test
    public void testDates() throws Exception {
        String[] args = new String[] {
            "-k", "somefile.key", "-o", "newfile.lic", "-S", "2012/12/22", "-E", "2013/12/30"
        };

        LicenseMakerOptions options = new LicenseMakerOptions(args);
        assertEquals("2012/12/22", toString(options.getStartDate()));
        assertEquals("2013/12/30", toString(options.getExpirationDate()));
    }

    @Test
    public void testSimpleArgs() throws Exception {
        Properties fileProps = new Properties();
        fileProps.setProperty("hello", "world");
        fileProps.setProperty("first", "First: ${first}");
        File tmpProps = savePropertiesFile(fileProps);
        
        String[] args = new String[] {
            "-k", "somefile.key", "-o", "newfile.lic", "-s", toDateLong("2012/12/22").toString(), "-e", toDateLong("2013/11/23").toString(), "-x",
            "12312", "-p", "first=jason", "second=mathew", "last=pell", "-h", "192.168.0.5", "192.168.0.6",
            "-P", tmpProps.getAbsoluteFile().toString()
        };

        LicenseMakerOptions options = new LicenseMakerOptions(args);
        assertEquals("somefile.key", options.getKeyFile().getPath());
        assertEquals("newfile.lic", options.getLicenseFile().getPath());
        assertEquals("2012/12/22", toString(options.getStartDate()));
        assertEquals("2013/11/23", toString(options.getExpirationDate()));
        
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
    
    private String toString(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateOptionHandler.DATE_FORMAT);
        return dateFormat.format(date);
    }
    
    private Long toDateLong(String date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateOptionHandler.DATE_FORMAT);
        return dateFormat.parse(date).getTime();
    }
}
