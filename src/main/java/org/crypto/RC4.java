package org.crypto;

public class RC4 {

    private byte[] S;

    private int x;

    private int y;

    StringBuilder report;

    public RC4(String key, String message) {
        this(key);
        byte[] encodingMessage = encodeString(message);
        init(key.getBytes());
        String decodingMessage = decode(encodingMessage);
        System.out.println(decodingMessage);
    }

    public RC4(String key) {
        this(key.getBytes());
    }

    public RC4(byte[] key) {
        report = new StringBuilder();
        init(key);
    }

    private void init(byte[] key) {
        S = new byte[256];
        x = 0;
        y = 0;
        int keyLen = key.length;

        for (int i = 0; i < 128; i++) {
            S[i] = (byte) i;
        }

        int j = 0;
        for (int i = 0; i < 256; i++) {
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
        x = (x + 1) % 256;
        y = (y + S[x]) % 256;

        swap(S, x, y);

        return S[(S[x] + S[y]) % 256];
    }

    public byte[] encodeString(String message) {
        return encodeByte(message.getBytes());
    }

    public byte[] encodeByte(byte[] message) {
        byte[] data = message.clone();

        byte[] cipher = new byte[data.length];

        for (int m = 0; m < data.length; m++) {
            cipher[m] = (byte) (data[m] ^ keyItem());
        }

        return cipher;
    }

    public String decode(byte[] message) {
        StringBuilder messageEncode = new StringBuilder();
        for (byte b : decodeToByte(message)) {
            messageEncode.append((char) b);
        }
        return messageEncode.toString();
    }

    public byte[] decodeToByte(byte[] message) {
        return encodeByte(message);
    }
}
