package org.crypto;

import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;



public class ElGamal {

    private List<Long> openKey;

    private Long privateKey;

    private StringBuilder report;

    ElGamal(int numFrom, int numTo) throws Exception {
        generateKeys(numFrom, numTo);
    }

    public void generateKeys(int numFrom, int numTo) throws Exception {
        long p = CMath.randomPrime(numFrom, numTo);
        long g = CMath.firstPrimitiveRoot(p);
        long x = CMath.randomRange(1, p - 2);
        long y = CMath.modulo(g, x, p);
        openKey = List.of(p, g, y);
        privateKey = x;
    }

    public List<Long> coding(long message) throws Exception {
            return coding(message, openKey);
    }

    public List<Long> coding(long message, List<Long> openKey) throws Exception {
        BigInteger p = BigInteger.valueOf(openKey.get(0));
        BigInteger g = BigInteger.valueOf(openKey.get(1));
        BigInteger y = BigInteger.valueOf(openKey.get(2));
        if(message < 1 || message > p.intValue()) {
            throw new Exception("Сообщение (%d) больше чем открытый ключ (%d)".formatted(message, p));
        }
        long k = CMath.randomRange(1, p.intValue() - 2);
        long a = CMath.modulo(g.longValue(), k, p.longValue());
        long b = y.pow((int) k).multiply(BigInteger.valueOf(message)).remainder(p).longValue();
        return List.of(a, b);
    }

    public long decoding(List<Long> message) {
        return decoding(message, openKey, privateKey);
    }

    public long decoding(List<Long> message, List<Long> openKey, long privateKey) {
        BigInteger a = new BigInteger(String.valueOf(message.get(0)));
        BigInteger b = new BigInteger(String.valueOf(message.get(1)));
        BigInteger p = new BigInteger(String.valueOf(openKey.get(0)));
        BigInteger c = p.subtract(new BigInteger(String.valueOf(1 + privateKey)));
        a = a.pow(c.intValue());
        return b.multiply(a).remainder(p).longValue();
    }

    public List<Long> getOpenKey() {
        return openKey;
    }

    public Long getPrivateKey() {
        return privateKey;
    }
}
