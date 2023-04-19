package org.crypto.rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.crypto.CMath.isCoprime;
import static org.crypto.CMath.modularMultiplicativeInverse;
import static org.crypto.CMath.modulo;
import static org.crypto.CMath.nextPrime;
import static org.crypto.CMath.randomPrime;
import static org.crypto.CMath.randomRange;

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
        this.report.append("Публичный ключ: ").append(this.publicKey).append("\n");
        this.report.append("Приватный ключ: ").append(this.privateKey).append("\n");
        Long encryptedMessage = encryptMessage();
        this.report.append("Зашифрованное сообщение: ").append(encryptedMessage).append("\n");
        Long decryptedMessage = decryptMessage(encryptedMessage);
        this.report.append("Разшифрованниое сообщение: ").append(decryptedMessage).append("\n");
        Long signedMessage = signMessage();
        this.report.append("Подпись сообщения: ").append(signedMessage).append("\n");
        Boolean signCheck = checkSign(signedMessage);
        if (signCheck){
            this.report.append("Подпись верна!!!");
        }else {
            this.report.append("Подпись не верна!!!");
        }
    }

    private Boolean checkSign(Long signedMessage){
        long modulo = modulo(signedMessage, this.publicKey.get(0).longValue(), this.publicKey.get(1).longValue());
        this.report.append("Значение сообщения с подписью: ").append(signedMessage)
                .append(" равна: ").append(modulo).append("\n");
        return this.message.equals(modulo);
    }
    private Long signMessage() {
        return modulo(this.message,this.privateKey.get(0).longValue(),this.privateKey.get(1).longValue());
    }

    private Long decryptMessage(long encryptedMessage) {
        return modulo(encryptedMessage,this.privateKey.get(0).longValue(),this.privateKey.get(1).longValue());
    }

    private Map<String, List<BigInteger>> generateKeys(Integer minValueForGeneration, Integer maxValueForGeneration) throws Exception {
        Map<String, List<BigInteger>> result = new HashMap<>();
        long p = randomPrime(minValueForGeneration, maxValueForGeneration);
        long q = randomPrime(minValueForGeneration, maxValueForGeneration);
        BigInteger n = BigInteger.valueOf(p * q);
        BigInteger eulerFunction = BigInteger.valueOf((p - 1) * (q - 1));
        BigInteger openExponent = getOpenExponent(eulerFunction);
        long d = modularMultiplicativeInverse(openExponent.longValue(),eulerFunction.longValue());
        List<BigInteger> list = List.of(openExponent, n, BigInteger.valueOf(d), n);
        result.put("public",list.subList(0,2));
        result.put("private",list.subList(2,4));
        return result;
    }



    private BigInteger getOpenExponent(BigInteger eulerFunction) {
        List<BigInteger> primeArray = new ArrayList<>();
        int count = 0;
        BigInteger prime = nextPrime(4);;
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

    private Long encryptMessage(){
        return modulo(this.message,this.publicKey.get(0).longValue(),this.publicKey.get(1).longValue());
    }


    public List<BigInteger> getPublicKey() {
        return publicKey;
    }
}
