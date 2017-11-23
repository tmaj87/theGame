package pl.tmaj.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.tmaj.common.SHA256.ofString;

class SHA256Test {

    private static final String DUMMY_STRING = "dummy";

    @Test
    void shouldGenerateDifferentHashesEachTime() {
        String hash1 = ofString(DUMMY_STRING);
        String hash2 = ofString(DUMMY_STRING);
        assertFalse(hash1.equals(hash2));
    }

    @Test
    void shouldGenerateSameHashIfSaltProvided() {
        String hash1 = ofString(DUMMY_STRING, DUMMY_STRING);
        String hash2 = ofString(DUMMY_STRING, DUMMY_STRING);
        assertTrue(hash1.equals(hash2));
    }
}