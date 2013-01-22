/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.padlocksoftware.padlock.key;

import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import net.padlocksoftware.padlock.KeyManager;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jason
 */
public class KeyManagerTest extends Assert {
    @Test
    public void testKeyManager() throws Exception {
        KeyPair pair = KeyManager.createKeyPair();
        File file = File.createTempFile("test", "key");

        KeyManager.exportKeyPair(pair, file);
        KeyPair pair2 = KeyManager.importKeyPair(file);

        PublicKey public1 = pair.getPublic();
        PublicKey public2 = pair2.getPublic();
        assertEquals(public1, public2);

        PrivateKey private1 = pair.getPrivate();
        PrivateKey private2 = pair2.getPrivate();
        assertEquals(private1, private2);
        file.delete();
    }
}
