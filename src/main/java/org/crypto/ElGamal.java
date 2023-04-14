package org.crypto;

import java.util.List;
import java.util.ArrayList;



public class ElGamal {

    private List<Long> openKey;

    private Long privateKey;

    private StringBuilder report;

    ElGamal(int numFrom, int numTo) throws Exception {
        generateKeys(numFrom, numTo);
    }

    private void generateKeys(int numFrom, int numTo) throws Exception {
        long p = CMath.randomPrime(numFrom, numTo);
        long g = CMath.firstPrimitiveRoot(p);
        long x = CMath.randomRange(1, p - 2);
        long y = CMath.modulo(g, x, p);
        openKey = List.of(p, g, y);
        privateKey = x;
    } 

    public List<Long> getOpenKey() {
        return openKey;
    }

    public Long getPrivateKey() {
        return privateKey;
    }
}
