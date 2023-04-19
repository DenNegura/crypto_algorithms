package org.crypto;

import org.crypto.rsa.RSA;

public class Main {
    public static void main(String[] args) throws Exception {
        RSA rsa = new RSA(45, 100, 1000);
        System.out.println(rsa.report);
    }
}