package net.padlocksoftware.padlock.validator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public final class ResourceUtils {
    private ResourceUtils() {
    }
    
    public static File loadResourceAsFile(String path) throws IOException {
        InputStream is = ResourceUtils.class.getResourceAsStream(path);
        File tmpFile = File.createTempFile("tmp", null);
        FileUtils.copyInputStreamToFile(is, tmpFile);
        return tmpFile;
    }
}
