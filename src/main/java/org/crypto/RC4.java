package org.crypto;

public class RC4 {

    private byte[] S;

    private int x;

    private int y;

    StringBuilder report;

    public RC4(String key, String message) {
        this(key);
        report.append("Потоковый шифр RC4.\n Ключ: %s - от 8 до 2048 бит,\nСообщение: %s\n".formatted(key, message));
        report.append("""
                Инициализация RC4 состоит из двух частей:
                    инициализация S-блока;
                    генерация псевдослучайного слова K.
                    
                Для начальной инициализации вектора-перестановки "S" ключём, используется алгоритм ключевого расписания (Key-Scheduling Algorithm):
                Этот алгоритм использует ключ, подаваемый на вход пользователем.
                S заполняется от 0 до 255, далее по формуле вычисляется j = (j + S[i] + key[i % keyLen]) % 256, где начальное значение j = 0, a i = от 0 до 255.
                Перемешиваются значения S по индексу i и j.
                                
                Генератор ключевого потока RC4 переставляет значения, хранящиеся в S.
                В одном цикле RC4 определяется одно n-битное слово K из ключевого потока.
                В дальнейшем ключевое слово будет сложено по модулю два с исходным текстом, которое пользователь хочет зашифровать, и получен зашифрованный текст.
                                
                Pseudo-Random Generation Algorithm:
                      x = (x + 1) % 256
                      y = (y + S[x]) % 256
                      перемещение (x, y)
                                
                      return S[(S[x] + S[y]) % 256];
                
                Шифрование:
                1) Функция генерирует последовательность битов.
                """);
        report.append("Шифрование:\n");
        report.append("1) Функция генерирует последовательность битов.");
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
