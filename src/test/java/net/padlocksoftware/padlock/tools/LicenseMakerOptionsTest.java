package net.padlocksoftware.padlock.tools;

import org.kohsuke.args4j.CmdLineException;

import org.junit.Assert;
import org.junit.Test;

public class LicenseMakerOptionsTest extends Assert {
    @Test(expected=CmdLineException.class)
    public void testNoArgs() throws Exception {
        new LicenseMakerOptions(new String[]{});
    }
    
    @Test(expected=CmdLineException.class)
    public void testNoKeyFile() throws Exception {
        String[] args = new String[] {
          "-o",
          "newfile.lic"
        };
       new LicenseMakerOptions(args);
    }
    
    @Test(expected=CmdLineException.class)
    public void testNoOutputFile() throws Exception {
        String[] args = new String[] {
          "-k",
          "somefile.key",
        };
       new LicenseMakerOptions(args);
    }
    
    @Test
    public void testNoOutputFileButStandardOutIsSpecified() throws Exception {
        String[] args = new String[] {
          "-k",
          "somefile.key",
          "-O"
        };
       new LicenseMakerOptions(args);
    }
    
    @Test(expected=CmdLineException.class)
    public void testStartDateStartTimeInMsChecked() throws Exception {
        String[] args = new String[] {
              "-k",
              "somefile.key",
              "-O",
              "-s",
              "23123",
              "-S",
              "2013/12/12"
            };
        
           new LicenseMakerOptions(args);
    }
    
    @Test(expected=CmdLineException.class)
    public void testExpireDateExpireTimeInMsChecked() throws Exception {
        String[] args = new String[] {
              "-k",
              "somefile.key",
              "-O",
              "-e",
              "23123",
              "-E",
              "2013/12/12"
            };
        
           new LicenseMakerOptions(args);
    }
    
    @Test
    public void testSimpleArgs() throws Exception {
        String[] args = new String[] {
          "-k",
          "somefile.key",
          "-o",
          "newfile.lic"
        };
        
        LicenseMakerOptions options = new LicenseMakerOptions(args);
        assertEquals("somefile.key", options.getKeyFile().getPath());
        assertEquals("newfile.lic", options.getOutputFile().getPath());
        
    }
}
