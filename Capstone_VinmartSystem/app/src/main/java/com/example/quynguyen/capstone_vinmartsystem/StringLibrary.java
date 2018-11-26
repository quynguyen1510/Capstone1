package com.example.quynguyen.capstone_vinmartsystem;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringLibrary {
    public static String md5(String str) {
        String rs = null;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(str.getBytes());
            BigInteger bigInteger = new BigInteger(1, md.digest());
            rs = bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
