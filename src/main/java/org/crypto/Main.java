package org.crypto;

public class Main {
    public static void main(String[] args) throws Exception {
        byte[] key = "key".getBytes();
        RC4 rc4 = new RC4(key);
        byte[] testB = "vectorus".getBytes();
        byte[] result = rc4.encode(testB, testB.length);

        rc4 = new RC4(key);
        byte[] decryptedB = rc4.decode(result, result.length);
        char[] chars = new char[decryptedB.length];
        int j = 0;
        for (byte b:decryptedB) {
            chars[j] = (char) b;
            j++;
        }
        String decryptedStr = new String(chars);
        System.out.println(decryptedStr);
    }
}