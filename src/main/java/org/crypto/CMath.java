package org.crypto;

import java.util.Random;

public class CMath {

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

    public static long funEuler(long x) {
        long count = 0;
        for(int i = 1; i < x; i++) {
            if(isCoprime(i, x)) {
                count++;
            }
        }
        return count;
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

    public static long primitiveRoot(long x) {
        return 0;
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
