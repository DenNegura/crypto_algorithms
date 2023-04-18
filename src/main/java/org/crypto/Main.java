package org.crypto;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
       ElGamal elGamal = new ElGamal(1000, 10000);
       System.out.println(elGamal.getOpenKey());
        List<Long> codingMessage = elGamal.coding(1446);
        System.out.println(codingMessage);
       System.out.println(elGamal.decoding(codingMessage));
//        System.out.println(CMath.isPrime(2));
//        System.out.println(CMath.randomPrime(1000, 100000));
//        System.out.println(CMath.modulo(98765, 1024, 123557));
        // System.out.println(CMath.funEuler(36));
        // System.out.println(CMath.comparToModulo(26, 11, 5));
        // System.out.println(CMath.firstPrimitiveRoot(23));
    }
}