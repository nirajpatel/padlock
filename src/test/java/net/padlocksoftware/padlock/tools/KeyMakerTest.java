package net.padlocksoftware.padlock.tools;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.*;

public class KeyMakerTest {
    @Test
    public void testMakeKey() throws Exception {
        File tmpFile = File.createTempFile("key", ".key");
        tmpFile.delete();
        
        String[] args = new String[] {
            "-k", tmpFile.getPath(), "-j"
        };
        
        KeyMaker.main(args);

        // second time will not write out key as already exists
        KeyMaker.main(args);
        
        tmpFile.delete();
    }
}
