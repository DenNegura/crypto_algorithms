package org.crypto.rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.crypto.math.CMath.isCoprime;
import static org.crypto.math.CMath.modularMultiplicativeInverse;
import static org.crypto.math.CMath.modulo;
import static org.crypto.math.CMath.nextPrime;
import static org.crypto.math.CMath.randomPrime;
import static org.crypto.math.CMath.randomRange;

public class RSA {
    private final Long message;
    private final List<BigInteger> publicKey;
    private final List<BigInteger> privateKey;
    public StringBuilder report = new StringBuilder();

    public RSA(long message, Integer minValueForGeneration, Integer maxValueForGeneration) throws Exception {
        this.message = message;
        this.report.append("Сообщение для ширования: ").append(this.message).append("\n");
        Map<String, List<BigInteger>> keys = generateKeys(minValueForGeneration, maxValueForGeneration);
        this.publicKey = keys.get("public");
        this.privateKey = keys.get("private");
        this.report.append("Пара (e,n) составляет публичный ключ\nПубличный ключ: ").append(this.publicKey).append("\n");
        this.report.append("Пара (d,n) составляет приватный ключ\nПриватный ключ: ").append(this.privateKey).append("\n");
        Long encryptedMessage = encryptMessage();
        this.report.append("Зашифрованное сообщение: c = ").append(encryptedMessage).append("\n");
        Long decryptedMessage = decryptMessage(encryptedMessage);
        this.report.append("Разшифрованниое сообщение: m' = ").append(decryptedMessage).append("\n");
        if (decryptedMessage.equals(message)) {
            this.report.append("Испходное сообщение m = ").append(message).append(" равно расшифрованному m' = ")
                    .append(decryptedMessage).append("\n");
        }
        report.append("\n");
        Long signedMessage = signMessage();
        this.report.append("Подпись сообщения: s = ").append(signedMessage).append("\n");
        Boolean signCheck = checkSign(signedMessage);
        if (signCheck) {
            this.report.append("Подпись верна!!!");
        } else {
            this.report.append("Подпись не верна!!!");
        }
    }

    private Boolean checkSign(Long signedMessage) {
        this.report.append("""
                Подпись провереятся по формуле: m' = s ^ e mod n
                Где m - сообщение, m' - прообраз сообщения из подписи, {m,s} - пара сообщение и подпись, (e,n) - публичный ключ
                Если m' = m то подпись верна
                """);
        long modulo = modulo(signedMessage, this.publicKey.get(0).longValue(), this.publicKey.get(1).longValue());
        this.report.append("Значение сообщения с подписью: ").append(signedMessage)
                .append(" равна: ").append(modulo).append("\n");
        return this.message.equals(modulo);
    }

    private Long signMessage() {
        this.report.append("""
                Сообщение подписывается с помощью секретного ключа по формуле: m ^ e mod n
                Где m - сообщение, (d,n) - приватный ключ
                """);
        return modulo(this.message, this.privateKey.get(0).longValue(), this.privateKey.get(1).longValue());
    }

    private Long decryptMessage(long encryptedMessage) {
        this.report.append("""
                Сообщение разшифровывается с помощью сеансового ключа симметричным алгоритмом: c ^ d mod n
                Где c - сообщение для разшифровывания, (d,n) - приватный ключ
                """);
        return modulo(encryptedMessage, this.privateKey.get(0).longValue(), this.privateKey.get(1).longValue());
    }

    private Map<String, List<BigInteger>> generateKeys(Integer minValueForGeneration, Integer maxValueForGeneration) throws Exception {
        Map<String, List<BigInteger>> result = new HashMap<>();
        long p = randomPrime(minValueForGeneration, maxValueForGeneration);
        long q = randomPrime(minValueForGeneration, maxValueForGeneration);
        this.report.append("Выбираются два случайных простых числа p = ").append(p).append(" и q = ")
                .append(q).append("\n");
        BigInteger n = BigInteger.valueOf(p * q);
        this.report.append("Вычисляется выражение n = p * q = ").append(n).append("\n");
        BigInteger eulerFunction = BigInteger.valueOf((p - 1) * (q - 1));
        this.report.append("Вычисляется значение вункции Эйлера от числа n f(n): (p - 1) * (q - 1) = ")
                .append(eulerFunction).append("\n");
        BigInteger openExponent = getOpenExponent(eulerFunction);
        this.report.append("Выбирается целое числсло e: 1 < e <  f(n) взаимнопростое со значением f(n) = ")
                .append(openExponent).append("\n");
        long d = modularMultiplicativeInverse(openExponent.longValue(), eulerFunction.longValue());
        this.report.append("Вычисляется мультипликативное число d к числу e по модулю f(n) = ")
                .append(d).append("\n");
        List<BigInteger> list = List.of(openExponent, n, BigInteger.valueOf(d), n);
        result.put("public", list.subList(0, 2));
        result.put("private", list.subList(2, 4));
        return result;
    }


    private BigInteger getOpenExponent(BigInteger eulerFunction) {
        List<BigInteger> primeArray = new ArrayList<>();
        int count = 0;
        BigInteger prime = nextPrime(4);
        while (count < 20) {
            prime = nextPrime(prime.longValue());
            if (prime.compareTo(eulerFunction) > 0) {
                break;
            } else if (isCoprime(prime.longValue(), eulerFunction.longValue())) {
                primeArray.add(prime);
                count++;
            }
        }
        return primeArray.get((int) randomRange(10, primeArray.size() - 1));
    }

    private Long encryptMessage() {
        this.report.append("""
                Сообщение шифруется с помощью сеансового ключа симметричным алгоритмом: m ^ e mod n
                Где m - сообщение для шифрования, (e,n) - публичный ключ
                """);
        return modulo(this.message, this.publicKey.get(0).longValue(), this.publicKey.get(1).longValue());
    }
}
