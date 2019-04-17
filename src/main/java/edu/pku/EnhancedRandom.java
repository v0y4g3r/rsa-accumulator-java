/**
 * Enhanced {@link java.util.Random}
 *
 * @author Lei, HUANG (lhuang@pku.edu.cn): 2019-04-17 19:34:28
 */
package edu.pku;

import java.math.BigInteger;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class EnhancedRandom {
    final private Random random;

    public EnhancedRandom() {
        this.random = new Random();
    }

    public EnhancedRandom(Random random) {
        this.random = random;
    }

    public BigInteger nextBigInteger() {
        return nextBigInteger(BigInteger.valueOf(2).pow(256));
    }

    /**
     * @param until upper bound
     * @return randomly generated big integer between 0 and until
     * @author Lei, HUANG (lhuang@pku.edu.cn): 2019-04-17 19:36:58
     */
    public BigInteger nextBigInteger(BigInteger until) {
        return nextBigInteger(BigInteger.ZERO, until);
    }

    /**
     * @param from  lower bound
     * @param until upper bound
     * @return randomly generated big integer between from and until
     * @author Lei, HUANG (lhuang@pku.edu.cn): 2019-04-17 19:36:43
     */
    public BigInteger nextBigInteger(BigInteger from, BigInteger until) {
        if (from.compareTo(until) >= 0) {
            throw new IllegalArgumentException("until must be greater than from");
        }
        BigInteger randomNumber;
        int        bitLength;
        if (from.bitLength() == until.bitLength()) {
            int fromBitLength = from.bitLength();
            bitLength = fromBitLength;
        } else {
            int fromBitLength  = from.bitLength();
            int untilBitLength = until.bitLength();
            bitLength = this.nextInt(fromBitLength, untilBitLength);
        }

        do {
            randomNumber = new BigInteger(bitLength, random);
        } while (randomNumber.compareTo(from) < 0 || randomNumber.compareTo(until) >= 0);

        return randomNumber;
    }

    /**
     * @param from  lower bound
     * @param until upper bound
     * @return randomly generated integer between from and until
     */
    public int nextInt(int from, int until) {
        return this.random.nextInt(until - from) + from;
    }

    public void setSeed(long seed)                                                                    {random.setSeed(seed);}

    public void nextBytes(byte[] bytes)                                                               {random.nextBytes(bytes);}

    public int nextInt()                                                                              {return random.nextInt();}

    public int nextInt(int bound)                                                                     {return random.nextInt(bound);}

    public long nextLong()                                                                            {return random.nextLong();}

    public boolean nextBoolean()                                                                      {return random.nextBoolean();}

    public float nextFloat()                                                                          {return random.nextFloat();}

    public double nextDouble()                                                                        {return random.nextDouble();}

    public double nextGaussian()                                                                      {return random.nextGaussian();}

    public IntStream ints(long streamSize)                                                            {return random.ints(streamSize);}

    public IntStream ints()                                                                           {return random.ints();}

    public IntStream ints(long streamSize, int randomNumberOrigin, int randomNumberBound)             {return random.ints(streamSize, randomNumberOrigin, randomNumberBound);}

    public IntStream ints(int randomNumberOrigin, int randomNumberBound)                              {return random.ints(randomNumberOrigin, randomNumberBound);}

    public LongStream longs(long streamSize)                                                          {return random.longs(streamSize);}

    public LongStream longs()                                                                         {return random.longs();}

    public LongStream longs(long streamSize, long randomNumberOrigin, long randomNumberBound)         {return random.longs(streamSize, randomNumberOrigin, randomNumberBound);}

    public LongStream longs(long randomNumberOrigin, long randomNumberBound)                          {return random.longs(randomNumberOrigin, randomNumberBound);}

    public DoubleStream doubles(long streamSize)                                                      {return random.doubles(streamSize);}

    public DoubleStream doubles()                                                                     {return random.doubles();}

    public DoubleStream doubles(long streamSize, double randomNumberOrigin, double randomNumberBound) {return random.doubles(streamSize, randomNumberOrigin, randomNumberBound);}

    public DoubleStream doubles(double randomNumberOrigin, double randomNumberBound)                  {return random.doubles(randomNumberOrigin, randomNumberBound);}
}
