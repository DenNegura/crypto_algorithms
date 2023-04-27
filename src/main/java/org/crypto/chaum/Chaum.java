package org.crypto.chaum;
public class Chaum {
    public StringBuilder getReport() {
        return report;
    }

    private final StringBuilder report;

    public Chaum() {
        report = new StringBuilder("Протокол подписи Chaum, также известный как протокол анонимной подписи,\n" +
                "позволяет подписывать сообщения анонимно, не раскрывая личности подписавшего, \n" +
                "и одновременно обеспечивает невозможность подделки подписи.\n" +
                "\n" +
                "Протокол состоит из трех фаз:\n" +
                "\n" +
                "  1  Фаза генерации ключей\n" +
                "\n" +
                "На этой фазе участник создает пару ключей: открытый и закрытый. Закрытый ключ известен только ему,\n" +
                "а открытый ключ распространяется среди остальных участников. \n" +
                "Ключи генерируются с помощью криптографических алгоритмов, например, алгоритма генерации ключей Диффи-Хеллмана.\n" +
                "\n" +
                "  2  Фаза подписи сообщения\n" +
                "\n" +
                "Пусть имеется сообщение m.\n" +
                "\n" +
                "    Выбирается случайное число r из поля p, где p - большое простое число.\n" +
                "    Вычисляется y = r^2 mod p и отправляется для подписи.\n" +
                "    Вычисляется z = r * (m - sx) mod p, где s - закрытый ключ, x - открытый ключ.\n" +
                "    Отправляется z вместе с сообщением m.\n" +
                "\n" +
                "  3  Фаза проверки подписи\n" +
                "\n" +
                "Пусть имеется сообщение m и подпись z.\n" +
                "\n" +
                "    Вычисляется y = z^2 * x^s mod p.\n" +
                "    Если y = r * (m - sx) mod p, то подпись действительна.");
        report.append("import random\n" +
                "\n" +
                "def generate_keys():\n" +
                "    # Шаг 1: генерируем параметры p, q, g, lam\n" +
                "    p = bigPrime()\n" +
                "    q = bigPrime()\n" +
                "    g = pow(2, (p-1)//q, p)\n" +
                "    lam = (p-1) // q\n" +
                "\n" +
                "    # Шаг 2: генерируем закрытый ключ x и соответствующий ему открытый ключ y\n" +
                "    x = randint(1, q-1)\n" +
                "    y = pow(g, x, p)\n" +
                "\n" +
                "    return (p, q, g, lam, x), y\n" +
                "\n" +
                "def sign(msg, keys):\n" +
                "    p, q, g, lam, x = keys\n" +
                "\n" +
                "    # Шаг 3: вычисляем хэш сообщения\n" +
                "    h = hash(msg)\n" +
                "\n" +
                "    while True:\n" +
                "        # Шаг 4: генерируем случайное число k, такое что 1 < k < q и НОД(k, q) = 1\n" +
                "        k = randint(2, q-1)\n" +
                "        if gcd(k, q) == 1:\n" +
                "            break\n" +
                "\n" +
                "    # Шаг 5: вычисляем подпись s и t\n" +
                "    r = pow(g, k, p) % q\n" +
                "    s = (modinv(k, q) * (h + x*r)) % q\n" +
                "\n" +
                "    return (r, s)\n" +
                "\n" +
                "def verify(msg, signature, pubkey):\n" +
                "    p, q, g, lam, x = pubkey\n" +
                "    r, s = signature\n" +
                "\n" +
                "    # Шаг 3: вычисляем хэш сообщения\n" +
                "    h = hash(msg)\n" +
                "\n" +
                "    # Шаг 6: проверяем подпись\n" +
                "    w = modinv(s, q)\n" +
                "    u1 = (h*w) % q\n" +
                "    u2 = (r*w) % q\n" +
                "    v = ((pow(g, u1, p) * pow(pow(g, x, p), u2, p)) % p) % q\n" +
                "\n" +
                "    if v == r:\n" +
                "        return True\n" +
                "    else:\n" +
                "        return False\n" +
                "\n" +
                "def hash(msg):\n" +
                "    return int.from_bytes(msg.encode(), byteorder='big')\n" +
                "\n" +
                "def gcd(a, b):\n" +
                "    while b != 0:\n" +
                "        a, b = b, a % b\n" +
                "    return a\n" +
                "\n" +
                "def egcd(a, b):\n" +
                "    if a == 0:\n" +
                "        return (b, 0, 1)\n" +
                "    else:\n" +
                "        g, y, x = egcd(b % a, a)\n" +
                "        return (g, x - (b // a) * y, y)\n" +
                "\n" +
                "def modinv(a, m):\n" +
                "    g, x, y = egcd(a, m)\n" +
                "    if g != 1:\n" +
                "        raise ValueError('Обратный элемент не существует')\n" +
                "    else:\n" +
                "        return x % m\n");
    }
}
