package com.esmc.mcnp.core.utils;

import java.security.MessageDigest;

import com.esmc.mcnp.core.constant.CommomConstant;

/**
 * Outil de chiffrement MD5
 */
public class MD5Utils {

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return String.valueOf(CommomConstant.HEX_DIGITS[d1]) + CommomConstant.HEX_DIGITS[d2];
    }

    /**
     * chiffrement MD5
     *
     * @param origin      - la source
     * @param charsetName - Nom du jeu de caractères
     * @return String -
     */
    public static String encode(String origin, String charsetName) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance(CommomConstant.MD5);
            if (charsetName == null || "".equals(charsetName)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
            }
        } catch (Exception exception) {
        }
        return resultString;
    }

    /**
     * chiffrement MD5
     *
     * @param origin - la source
     * @return String -
     */
    public static String encode(String origin) {
        return encode(origin, null);
    }

}
