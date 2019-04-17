package edu.pku;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lei, HUANG (lhuang@pku.edu.cn): 2019-04-17 19:37:38
 * @see https://github.com/starcoinorg/rsa-accumulator/blob/master/src/main/kotlin/org/starcoin/rsa/Util.kt#L35
 */
public class Accumulator {

    private static int RSA_KEY_SIZE           = 3072;
    private static int RSA_PRIME_SIZE         = RSA_KEY_SIZE / 2;
    private static int ACCUMULATED_PRIME_SIZE = 128;

    private       BigInteger                  N;
    private       BigInteger                  A;
    private       BigInteger                  A0;
    final private EnhancedRandom              random;
    final private Map<BigInteger, BigInteger> data;

    public Accumulator() {
        data = new HashMap<>();
        Pair<BigInteger> bigIntegerPair = Util.generateTwoLargeDistinctPrimes(RSA_PRIME_SIZE);
        BigInteger       p              = bigIntegerPair.getFrist();
        BigInteger       q              = bigIntegerPair.getSecond();
        N = p.multiply(q);
        random = new EnhancedRandom();
        A0 = random.nextBigInteger(BigInteger.ZERO, N);
        A = A0;
    }

    /**
     * get size of all accumulated elements
     * @return size of all accumulated elements
     */
    public int size() {
        return data.size();
    }

    public BigInteger getN() {
        return N;
    }

    public BigInteger getNonce(BigInteger x) {
        return data.get(x);
    }

    public BigInteger add(BigInteger x) {
        if (data.containsKey(x)) {
            return A;
        } else {
            Pair<BigInteger> bigIntegerPair = Util.hashToPrime(x, ACCUMULATED_PRIME_SIZE);
            BigInteger       hashPrime      = bigIntegerPair.getFrist();
            BigInteger       nonce          = bigIntegerPair.getSecond();
            A = A.modPow(hashPrime, N);
            data.put(x, nonce);
            return A;
        }
    }

    @SuppressWarnings("all")
    public BigInteger proveMembership(BigInteger x) {
        if (!data.containsKey(x)) {
            return null;
        } else {
            BigInteger product = BigInteger.ONE;
            for (BigInteger k : data.keySet()) {
                if (k.compareTo(x) != 0) {
                    BigInteger v = data.get(k);
                    product = product.multiply(Util.hashToPrime(k, ACCUMULATED_PRIME_SIZE, v).getFrist());
                }
            }
            return A0.modPow(product, N);
        }
    }

    @SuppressWarnings("all")
    public BigInteger delete(BigInteger x) {
        if (!data.containsKey(x)) {
            return A;
        } else {
            data.remove(x);
            BigInteger product = BigInteger.ONE;
            for (BigInteger k : data.keySet()) {
                if (k.compareTo(x) != 0) {
                    BigInteger v = data.get(k);
                    product = product.multiply(Util.hashToPrime(k, ACCUMULATED_PRIME_SIZE, v).getFrist());
                }
            }
            this.A = A0.modPow(product, N);
            return A;
        }
    }

    private static boolean doVerifyMembership(BigInteger A, BigInteger x, BigInteger proof, BigInteger n) {
        return proof.modPow(x, n).compareTo(A) == 0;
    }

    public static boolean verifyMembership(
            BigInteger A,
            BigInteger x,
            BigInteger nonce,
            BigInteger proof,
            BigInteger n
                                          ) {
        return doVerifyMembership(A, Util.hashToPrime(x, ACCUMULATED_PRIME_SIZE, nonce).getFrist(), proof, n);
    }
}
