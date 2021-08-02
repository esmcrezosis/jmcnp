package com.esmc.mcnp.commons.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esmc.mcnp.core.constant.CommomConstant;

/**
 * Outil de chiffrement MD5
 */
public class MD5Utils {
	
	private static final Logger log = LoggerFactory.getLogger(MD5Utils.class);
	
	private static byte[] md5(String s) {
		MessageDigest algorithm;
		try {
			algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(s.getBytes("UTF-8"));
			byte[] messageDigest = algorithm.digest();
			return messageDigest;
		} catch (Exception e) {
			log.error("MD5 Error...", e);
		}
		return null;
	}

	private static final String toHex(byte hash[]) {
		if (hash == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i;

		for (i = 0; i < hash.length; i++) {
			if ((hash[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(hash[i] & 0xff, 16));
		}
		return buf.toString();
	}

	public static String hash(String s) {
		try {
			return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
		} catch (Exception e) {
			log.error("not supported charset...{}", e);
			return s;
		}
	}

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
