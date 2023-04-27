package org.crypto.dss;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class DSS {
    private final String message;
    private  List<BigInteger> publicKey;
    private List<BigInteger> privateKey;
    public StringBuilder report = new StringBuilder();


    public DSS(String message, String hashAlgorithm) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        this.message = message;
        byte[] hash;
        try {
            MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
            hash = digest.digest(message.getBytes());
        } catch (NoSuchAlgorithmException e) {
            report.append("Алгоритм хеширования не найден/n");
            return;
        }
        String hexString = "";
        for (byte b : hash) {
            hexString += String.format("%02x", b);
        }
        System.out.println(hexString);
        List<Key> keys = getPrivateKeys();
    }
    private List<Key> getPrivateKeys() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        SecureRandom random = SecureRandom.getInstanceStrong();
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
        keyGen.initialize(ecSpec, random);
        KeyPair pair = keyGen.generateKeyPair();

        PrivateKey privateKey = pair.getPrivate();
        System.out.println(Arrays.toString(privateKey.getEncoded()));
        PublicKey publicKey = pair.getPublic();
        System.out.println(publicKey.toString());
        return asList(privateKey,publicKey);
    }
}
