package org.crypto;


import java.util.Random;



public class ElGamal {

    public void generateKeys(int numFrom, int numTo) {
        int p = new Random().nextInt(numTo - numFrom) + numFrom;

    }
}
