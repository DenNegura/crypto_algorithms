package org.crypto;

import org.crypto.rsa.RSA;

import java.util.Arrays;
import java.util.BitSet;

public class Main {
    public static void main(String[] args) throws Exception {
//        RSA rsa = new RSA(45, 100, 1000);
//        System.out.println(rsa.report);
        String str = "str";
        System.out.println(Arrays.toString(str.getBytes()));
        BitSet r = BitSet.valueOf(str.getBytes());
        System.out.println(r + " " + r.size());
        for(int i = 0; i < r.size(); i++) {
            System.out.print(r.get(i) ? "1" : "0");
        }
    }
}