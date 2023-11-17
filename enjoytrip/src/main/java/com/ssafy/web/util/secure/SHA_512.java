package com.ssafy.web.util.secure;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;

public class SHA_512
{
  public static String getSalt()
  {
    String salt = "";
    try {
      SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
      byte[] bytes = new byte[16];
      random.nextBytes(bytes);
      salt = new String(Base64.getEncoder().encode(bytes));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return salt;
  }

  public static String SHA512(String plaintext, String salt)
  {
    String hash = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      md.update((plaintext + salt).getBytes());
      hash = String.format("%0128x", new Object[] { new BigInteger(1, md.digest()) });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return hash;
  }

  public static String DSHA512(String plaintext, String salt)
  {
    return SHA512(SHA512(plaintext, salt), salt);
  }
}