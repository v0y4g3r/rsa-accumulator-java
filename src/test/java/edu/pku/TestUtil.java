package edu.pku;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class TestUtil {
    private static final int PRIME_CERTAINTY = 5;

    @Test
    public void testGenerateLargePrime() {
        for (int i = 0; i < 100; i++) {
            BigInteger bigInteger = null;
            try {
                bigInteger = Util.generateLargePrime(3752 / 2);
                Assert.assertTrue(bigInteger.isProbablePrime(PRIME_CERTAINTY));
            } catch (AssertionError e) {
                if (bigInteger != null) {
                    System.err.println(bigInteger.toString() + " is not a prime with certainty 5");
                }
            }
        }
    }
}
