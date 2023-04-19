package org.crypto;

import org.crypto.elgamal.ElGamal;
import org.crypto.math.CMath;
import org.crypto.rc4.RC4;
import org.crypto.rsa.RSA;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println(randomString(6));

    }

    public static String randomString(long length) {
        StringBuilder string = new StringBuilder();
        for(int i = 0; i < length; i++) {
            string.append((char) CMath.randomRange(97, 122));
        }
        return string.toString();
    }
}