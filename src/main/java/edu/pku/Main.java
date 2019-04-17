package edu.pku;

import java.math.BigInteger;

public class Main {
    public static void main(String... args) {
//        testEnhancedRandomNextInt();
        testEnhancedRandom();
        testEnhancedRandomNextInt();
        testMain();
    }

    private static void testHashToPrime() {
        for (int i = 0; i < 10000; i++) {
            BigInteger x         = Util.generateLargePrime(128);
            BigInteger initNonce = Util.generateLargePrime(128);

            int bitlen = 128;

            TwoValue<BigInteger> res1 = UtilkKt.hashToPrime(x, bitlen, initNonce);
            Pair<BigInteger>     res2 = Util.hashToPrime(x, bitlen, initNonce);

            if (!(res1.getFirst().compareTo(res2.getFrist()) == 0 && res1.getSecond().compareTo(res2.getSecond()) == 0)) {
                throw new RuntimeException("failed");
            }
        }
    }

    private static void testHashToLength() {
        for (int i = 0; i < 100; i++) {
            final BigInteger bigInteger = Util.generateLargePrime(1536);
            final BigInteger k          = UtilkKt.hashToLength(bigInteger, 1536);
            final BigInteger j          = Util.hashToLength(bigInteger, 1536);
            if (k.compareTo(j) != 0) {
                System.out.println("hashToLength failed");
            }
        }
    }

    private static void testGenerateLargePrime() {
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                System.out.println("" + i / 10 + "0%");
            }
            final BigInteger bigInteger = Util.generateLargePrime(3072 / 2);
            if (!bigInteger.isProbablePrime(100)) {
                System.out.println(bigInteger.toString() + " is not a prime!");
            }
        }
    }

    private static void testEnhancedRandom() {
        for (int i = 0; i < 100; i++) {
            final EnhancedRandom enhancedRandom = new EnhancedRandom();

            final BigInteger from           = enhancedRandom.nextBigInteger(BigInteger.ONE, new BigInteger("100000000000000000000"));
            final BigInteger to             = enhancedRandom.nextBigInteger(from, new BigInteger("100000000000000000000"));
            final BigInteger nextBigInteger = enhancedRandom.nextBigInteger(from, to);
            if (nextBigInteger.compareTo(from) < 0 || nextBigInteger.compareTo(to) > 0) {
                throw new RuntimeException("nextBigInteger failed");
            }
        }
    }


    private static void testEnhancedRandomNextInt() {
        for (int i = 0; i < 1000000; i++) {
            final EnhancedRandom enhancedRandom = new EnhancedRandom();
            final int            i1             = enhancedRandom.nextInt(100, 1000);
            if (i1 < 100 || i1 >= 1000) {
                throw new RuntimeException("testEnhancedRandomNextInt failed");
            }
        }
    }

    private static void testMain() {
        BigInteger  mem      = new BigInteger("10000000000000000000");
        Accumulator accu1    = new Accumulator();
        BigInteger  a1       = accu1.add(mem);
        BigInteger  witness1 = accu1.proveMembership(mem);
        BigInteger  nonce1   = accu1.getNonce(mem);
        BigInteger  n1       = accu1.getN();
        final BigInteger nonce2 = nonce1.add(BigInteger.ONE);
        System.out.println(Accumulator.verifyMembership(a1, mem, nonce2, witness1, n1));
    }
}

