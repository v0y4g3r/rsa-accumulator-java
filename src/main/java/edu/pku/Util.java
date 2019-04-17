package edu.pku;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

import java.math.BigInteger;
import java.util.Random;


public class Util {
    private static final int PRIME_CERTAINTY = 5;

    public static String toHexString(byte[] bytes) {
        return BaseEncoding.base16().lowerCase().encode(bytes);
    }

    public static BigInteger generateLargePrime(int bitLength) {
        Random random = new java.util.Random();
        return BigInteger.probablePrime(bitLength, random);
    }

    public static Pair<BigInteger> generateTwoLargeDistinctPrimes(int bitLength) {
        BigInteger first = generateLargePrime(bitLength);
        while (true) {
            BigInteger second = generateLargePrime(bitLength);
            if (first.compareTo(second) != 0) {
                return new Pair<>(first, second);
            }
        }
    }

    public static Pair<BigInteger> hashToPrime(BigInteger x, int bitLength) {
        return hashToPrime(x, bitLength, BigInteger.ZERO);
    }

    public static Pair<BigInteger> hashToPrime(BigInteger x) {
        return hashToPrime(x, 120, BigInteger.ZERO);
    }

    /**
     * hash x to a prime and get nonce, thus x+nonce will be a large prime with certainty PRIME_CERTAINTY
     *
     * @param x         big int to hash
     * @param bitLength required bit length
     * @param initNonce initial nonce value
     * @return hash
     * @author Lei, HUANG (lhuang@pku.edu.cn): 2019-04-17 22:42:00
     */
    public static Pair<BigInteger> hashToPrime(BigInteger x, int bitLength, BigInteger initNonce) {
        BigInteger nonce = initNonce;
        while (true) {
            BigInteger num = hashToLength(x.add(nonce), bitLength);
            if (num.isProbablePrime(PRIME_CERTAINTY)) {
                return new Pair<>(num, nonce);
            }
            nonce = nonce.add(BigInteger.ONE);
        }
    }

    /**
     * Hash construct
     * ref CL02 H:{0,1}^∗ → {e:e∈{0,1}^λ AND e is prime}
     *
     * @param x         big int to hash
     * @param bitLength target length to hash
     * @return
     * @author Lei, HUANG (lhuang@pku.edu.cn): 2019-04-17 20:58:05
     */
    public static BigInteger hashToLength(BigInteger x, int bitLength) {
        StringBuilder randomHexString = new StringBuilder();
        int           numOfBlocks     = (int) Math.ceil(bitLength / 256.00);

        for (int i = 0; i < numOfBlocks; i++) {
            final BigInteger bigIntI = new BigInteger(Integer.toString(i));
            randomHexString.append(
                    toHexString(Hashing.sha256().hashBytes(
                            (x.add(bigIntI))
                                    .toString(10)
                                    .getBytes()).asBytes()));

        }

        if (bitLength % 256 > 0) {
            randomHexString = new StringBuilder(randomHexString.substring((bitLength % 256) / 4));
        }
        return new BigInteger(randomHexString.toString(), 16);
    }

}
