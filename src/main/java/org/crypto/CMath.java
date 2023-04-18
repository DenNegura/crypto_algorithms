package org.crypto;

import java.math.BigInteger;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class CMath {

    public static BigInteger toBigInt(int num) {
        return BigInteger.valueOf(num);
    }

    public static BigInteger toBigInt(long num) {
        return BigInteger.valueOf(num);
    }

    public static boolean isPrime(long x) {
        long num = (long) Math.sqrt(x);
        if(x == 1) {
            return false;
        }

        if(x == 2) {
            return true;
        }

        for(int i = 2; i <= num; i++) {
            if(x % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static long randomPrime(long from, long to) throws Exception {
        if(from > to) {
            throw new Exception("Верхняя граница (%d) меньше нижней (%d)".formatted(to, from));
        }
        long randNum = randomRange(from, to);
        boolean isUp = randNum > to - from;

        if(isUp) {
            for(long i = randNum; i <= to; i++) {
                if(isPrime(i)) {
                   return i;
                }
            }
            for(long i = randNum; i >= from; i--) {
                if(isPrime(i)) {
                    return i;
                }
            }
        }
        else {
            for(long i = randNum; i >= from; i--) {
                if(isPrime(i)) {
                    return i;
                }
            }
            for(long i = randNum; i <= to; i++) {
                if(isPrime(i)) {
                    return i;
                }
            }
        }
        throw new Exception("Нет простого числа между [%d, %d]".formatted(from, to));
    }

    public static boolean isCoprime(long a, long b) {
        long num = Math.min(a, b);
        for(int i = 2; i <= num; i++) {
            if(a % i == 0 && b % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean compareToModulo(long a, long b, long mod) {
        long modA = (a % mod + mod) % mod;
        long modB = (b % mod + mod) % mod;
        return modA == modB;
    }

    public static long firstPrimitiveRoot(long x) {
        if (!isPrime(x)) {
            return -1;
        }
        long phi = x - 1;
        List<Long> factors = factorize(phi);
        for (long g = 2; g <= x; g++) {
            boolean isPrimitiveRoot = true;
            for (long factor : factors) {
                if (modulo(g, (long) (phi / factor), x) == 1) {
                    isPrimitiveRoot = false;
                    break;
                }
            }
            if (isPrimitiveRoot) {
                return g;
            }
        }
        return -1;
    }

    public static List<Long> factorize(long n) {
        List<Long> factors = new ArrayList<>();
        for (long i = 2; i <= n / i; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }

    public static long randomRange(long from, long to) {
        return new Random().nextLong(to - from) + from;
    }

    public static long modulo(long a, long b, long m) {
        if (m == 1) {
            return 0;
        }
        long result = 1;
        a = a % m;
        while (b > 0) {
            if ((b & 1) == 1) {
                result = (result * a) % m;
            }
            b = b >> 1;
            a = (a * a) % m;
        }
        return result;
    }
}
