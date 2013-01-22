package net.padlocksoftware.padlock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.padlocksoftware.padlock.tools.DateOptionHandler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public final class TestUtils {
    private TestUtils() {
    }
    
    public static File loadResourceAsFile(String path) throws IOException {
        InputStream is = TestUtils.class.getResourceAsStream(path);
        File tmpFile = File.createTempFile("tmp", null);
        FileUtils.copyInputStreamToFile(is, tmpFile);
        return tmpFile;
    }
    
    public static String toString(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateOptionHandler.DATE_FORMAT);
        return dateFormat.format(date);
    }
    
    public static Long toDateLong(String date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateOptionHandler.DATE_FORMAT);
        return dateFormat.parse(date).getTime();
    }
}
