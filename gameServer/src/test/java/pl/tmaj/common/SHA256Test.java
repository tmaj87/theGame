package pl.tmaj.common;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SHA256Test {

    private static final String DUMMY_STRING = "dummy";
    private static final String HASH_FOR_DUMMY = "1a71f4efd61c5759ce2fde1ac0cdb830128270ee8355727ba698c2487c588a47";

    @Test
    public void shouldGenerateDifferentHashesEachTime() {
        String hash1 = SHA256.of(DUMMY_STRING);
        String hash2 = SHA256.of(DUMMY_STRING);
        assertFalse(hash1.equals(hash2));
    }

    @Test
    public void shouldGenerateSameHashIfSaltProvided() {
        String hash1 = SHA256.of(DUMMY_STRING, DUMMY_STRING);
        String hash2 = SHA256.of(DUMMY_STRING, DUMMY_STRING);
        assertTrue(hash1.equals(hash2));
    }

    @Test
    public void shouldReturnHashForDummy() {
        String hash = SHA256.of(DUMMY_STRING, DUMMY_STRING);
        assertTrue(HASH_FOR_DUMMY.equals(hash));
    }
}
