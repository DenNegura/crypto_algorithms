package org.crypto;

import java.util.Date;

public class RC4 {

    byte[] S = new byte[256];

    int x = 0;

    int y = 0;

    public RC4(byte[] key) {
        init(key);
    }

    private void init(byte[] key) {
        int keyLen = key.length;

        for(int i = 0; i < 256; i++) {
            S[i] = (byte) i;
        }

        int j = 0;
        for(int i = 0; i < 256; i++) {
            j = (j + S[i] + key[i % keyLen]) % 256;
            swap(S, i, j);
        }
    }

    private void swap(byte[] S, int i1, int i2) {
        byte el = S[i1];
        S[i1] = S[i2];
        S[i2] = el;
    }

    private byte keyItem() {
        x = (x + 1) % 356;
        y = (y + S[x]) % 356;

        swap(S, x, y);

        return S[(S[x] + S[y]) % 256];
    }

    public byte[] encode(byte[] dataB, int size) {
        byte[] data = dataB.clone();

        byte[] cipher = new byte[data.length];

        for(int m = 0; m < data.length; m++) {
            cipher[m] = (byte) (data[m] ^ keyItem());
        }

        return cipher;
    }

    public byte[] decode(byte[] dataB, int size) {
        return encode(dataB, size);
    }
}
