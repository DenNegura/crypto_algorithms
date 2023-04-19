package org.crypto;

import org.crypto.rc4.RC4;
import org.crypto.rsa.RSA;

public class Main {
    public static void main(String[] args) throws Exception {
        RSA rsa = new RSA(45,10,1000);
        System.out.println(rsa.report);
    }
}