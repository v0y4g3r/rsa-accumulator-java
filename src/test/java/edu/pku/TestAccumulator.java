package edu.pku;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;


public class TestAccumulator {
    @Test
    public void testAccumulator() {
        for (int i = 0; i < 100; i++) {
            BigInteger  mem      = new EnhancedRandom().nextBigInteger();
            Accumulator accu1    = new Accumulator();
            BigInteger  a1       = accu1.add(mem);
            BigInteger  witness1 = accu1.proveMembership(mem);
            BigInteger  nonce1   = accu1.getNonce(mem);
            BigInteger  n1       = accu1.getN();
            try {
                Assert.assertTrue(Accumulator.verifyMembership(a1, mem, nonce1, witness1, n1));
            } catch (AssertionError e) {
                System.err.print("False negative:" + "mem:   " + mem.toString() + ", " + "accu1: " + accu1.toString() + ", " + "a1:    " + a1.toString() + ", " + "witness1:  " + witness1.toString() + ", " + "nonce1:    " + nonce1.toString() + ", " + "n1:    " + n1.toString() + ", ");
            }

            final BigInteger nonce2 = nonce1.add(BigInteger.ONE);
            try {
                Assert.assertFalse(Accumulator.verifyMembership(a1, mem, nonce2, witness1, n1));
            } catch (AssertionError e) {
                System.out.println("False positive:" + "mem:   " + mem.toString() + ", " + "accu1: " + accu1.toString() + ", " + "a1:    " + a1.toString() + ", " + "witness1:  " + witness1.toString() + ", " + "nonce2:    " + nonce2.toString() + ", " + "n1:    " + n1.toString() + ", ");
            }
        }
    }
}
