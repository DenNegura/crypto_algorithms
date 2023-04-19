package org.crypto.rc4;

public class RC4 {

    private byte[] S;

    private int x;

    private int y;

    private final StringBuilder report;

    public RC4(String key, String message) {
        this(key);
        report.append("Потоковый шифр RC4.\n    Ключ: \"%s\" - от 8 до 2048 бит,\n    Сообщение: \"%s\"\n\n".formatted(key, message));
        report.append("""
                Инициализация RC4 состоит из двух частей:
                    инициализация S-блока;
                    генерация псевдослучайного слова K.
                   
                Для инициализации блока S применяется алгоритм:
                Key-Scheduling Algorithm:
                    for i from 0 to 255:
                        S[i] = i
                    j = 0
                    for i from 0 to 255:
                        j = (j + S[i] + key[i % key.length]) % 256
                        поменять местами S[i] и S[j]
                                
                Генератор ключевого потока RC4 переставляет значения, хранящиеся в S.
                В одном цикле RC4 определяется одно n-битное слово Ki из ключевого потока:
                Pseudo-Random Generation Algorithm:
                      x = (x + 1) % 256
                      y = (y + S[x]) % 256
                      перемещение S[x] и S[y]
                      Возвращаемое значение S[(S[x] + S[y]) % 256]
                                
                Шифрование:
                Затем последовательность K битов посредством операции xor объединяется с открытым текстом m.
                В результате получается шифрограмма c:
                """);

        StringBuilder encodingMessage = new StringBuilder();
        for (byte b : encodeString(message)) {
            encodingMessage.append((char) b);
        }
        report.append("Зашифрованное сообщение: ").append(encodingMessage);
        init(key.getBytes());
        report.append("\n\n").append("""
                Расшифровывание:
                    1. Повторно создаётся (регенерируется) поток битов ключа Ki
                    2. Поток битов ключа складывается с шифрограммой c операцией «xor».
                        На выходе получается исходный текст m:
                """);
        String decodingMessage = decode(encodingMessage.toString().getBytes());
        report.append("Расшифрованное сообщение: ").append(decodingMessage);
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
            byte item = keyItem();
            cipher[m] = (byte) (data[m] ^ item);
            report.append("C[%d] = K[%d] ^ m[%d] : %d = %d ^ %d\n".formatted(m, m, m, cipher[m], data[m], item));
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

    public String getReport() {
        return report.toString();
    }
}
