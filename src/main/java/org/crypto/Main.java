package org.crypto;

import org.crypto.chaum.Chaum;
import org.crypto.dss.DSS;
import org.crypto.rsa.RSA;

public class Main {
    public static void main(String[] args) throws Exception {
        Chaum chaum = new Chaum();
        System.out.println(chaum.getReport());
    }


}