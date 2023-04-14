package org.crypto;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
       ElGamal elGamal = new ElGamal(10000, 1000000);
       System.out.println(elGamal.getOpenKey());
//        System.out.println(CMath.isPrime(2));
//        System.out.println(CMath.randomPrime(1000, 100000));
//        System.out.println(CMath.modulo(98765, 1024, 123557));
        // System.out.println(CMath.funEuler(36));
        // System.out.println(CMath.comparToModulo(26, 11, 5));
        // System.out.println(CMath.firstPrimitiveRoot(23));
    }
}