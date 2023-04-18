package org.crypto;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.List;


public class ElGamal {

    private List<Long> openKey;

    private Long privateKey;

    private StringBuilder report;

    ElGamal() {
        report = new StringBuilder("Схема Эль-Гамаля.\n");
    }

    ElGamal(int numFrom, int numTo) throws Exception {
        report = new StringBuilder("Схема Эль-Гамаля.\n");
        generateKeys(numFrom, numTo);
        long message = CMath.randomRange(numFrom, openKey.get(0));
        List<Long> codingMessage = coding(message);
        decoding(codingMessage);
    }

    public void generateKeys(int numFrom, int numTo) throws Exception {
        report.append("\nГенерация ключей:\n");
        long p = CMath.randomPrime(numFrom, numTo);
        report.append("1) Генерируется случайное простое число p = %d, от %d до %d.\n".formatted(p, numFrom, numTo));
        long g = CMath.firstPrimitiveRoot(p);
        report.append("2) Выбирается целое число g — первообразный корень p (%d). g = %d\n".formatted(p, g));
        long x = CMath.randomRange(1, p - 2);
        report.append("3) Выбирается случайное целое число x такое, что (1 < x < p − 1 (%d)). x = %d\n"
                .formatted(p - 1, x));
        long y = CMath.modulo(g, x, p);
        report.append("4) Вычисляется y = g ^ x mod p, y = %d ^ %d mod %d = %d\n".formatted(g, x, p, y));
        openKey = List.of(p, g, y);
        report.append("5) Открытым ключом является (y, g, p) = (%d, %d, %d), закрытым ключом — число x (%d).\n"
                .formatted(y, g, p, x));
        privateKey = x;
    }

    public List<Long> coding(long message) throws Exception {
        return coding(message, openKey);
    }

    public List<Long> coding(long message, List<Long> openKey) throws Exception {
        report.append("\nШифрование:\nСообщение m должно быть меньше числа p. m = %d < %d\n"
                .formatted(message, openKey.get(0)));
        BigInteger p = BigInteger.valueOf(openKey.get(0));
        BigInteger g = BigInteger.valueOf(openKey.get(1));
        BigInteger y = BigInteger.valueOf(openKey.get(2));
        if (message < 1 || message > p.intValue()) {
            throw new Exception("Сообщение (%d) больше чем открытый ключ (%d)".formatted(message, p));
        }
        long k = CMath.randomRange(1, p.intValue() - 2);
        report.append(("1) Выбирается сессионный ключ — случайное целое число, " +
                "взаимно простое с (p − 1), k k такое, что 1 < k < p − 1 (%d). k = %d\n")
                .formatted(p.intValue() - 1, k));
        long a = CMath.modulo(g.longValue(), k, p.longValue());
        long b = y.pow((int) k).multiply(BigInteger.valueOf(message)).remainder(p).longValue();
        report.append("2) Вычисляются числа a = g ^ k mod p (%d ^ %d mod %d) = %d\n".formatted(g, k, p, a))
                .append("\tи b = y ^ k * M mod p (%d ^ %d * %d mod %d) = %d\n".formatted(y, k, message, p, b))
                .append("3) Пара чисел (a, b) (%d, %d) является шифротекстом.\n".formatted(a, b));
        return List.of(a, b);
    }

    public long decoding(List<Long> message) {
        return decoding(message, openKey, privateKey);
    }

    public long decoding(List<Long> message, List<Long> openKey, long privateKey) {
        report.append("\nРасшифрование:\n");
        BigInteger a = new BigInteger(String.valueOf(message.get(0)));
        BigInteger b = new BigInteger(String.valueOf(message.get(1)));
        BigInteger p = new BigInteger(String.valueOf(openKey.get(0)));
        BigInteger c = p.subtract(new BigInteger(String.valueOf(1 + privateKey)));
        BigInteger d = a.pow(c.intValue());
        long decodingMessage = b.multiply(d).remainder(p).longValue();
        report.append(("Расшифрование по формуле: m = " +
                "b * (a ^ x) ^ -1 mod p = %d * (%d ^ %d) ^ -1 mod %d = %d - исходное сообщение.")
                .formatted(b.longValue(), a.longValue(), privateKey, p.longValue(), decodingMessage));
        return decodingMessage;
    }

    public List<Long> getOpenKey() {
        return openKey;
    }

    public Long getPrivateKey() {
        return privateKey;
    }

    public StringBuilder getReport() {
        return report;
    }
}
