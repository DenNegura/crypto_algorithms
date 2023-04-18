package org.crypto;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        byte[] key = "key".getBytes();
        RC4 rc4 = new RC4(key);
        byte[] testB = "test".getBytes();
        byte[] result = rc4.encode(testB, testB.length);

        byte[] decryptedB = rc4.decode(result, result.length);

        String decryptedStr = Arrays.toString(decryptedB);
    }
}