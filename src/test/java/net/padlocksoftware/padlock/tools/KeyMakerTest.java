package net.padlocksoftware.padlock.tools;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.*;

public class KeyMakerTest {
    @Test
    public void testMakeKey() throws Exception {
        File tmpFile = File.createTempFile("key", ".key");
        
        String[] args = new String[] {
            "-k", tmpFile.getPath()
        };
        
        KeyMaker.main(args);
        
        tmpFile.delete();
    }
}
