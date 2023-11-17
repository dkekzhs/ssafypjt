package com.ssafy.web.util.secure;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RSAKeyManager
{
  private static HashMap<String, KeyPair> keyMap;
  private static RSAKeyManager instance = null;

  public static RSAKeyManager getInstnace()
  {
    if (instance == null) {
      instance = new RSAKeyManager();
      keyMap = new HashMap();
    }

    return instance;
  }

  public PublicKey createKeyPair(String ip)
  {
    if ((ip == null) || (!isValidIP(ip))) {
      return null;
    }
    if (keyMap.get(ip) == null) {
      KeyPair keyPair = RSA_2048.createKey();
      keyMap.put(ip, keyPair);
      return keyPair.getPublic();
    }
    return ((KeyPair)keyMap.get(ip)).getPublic();
  }

  public PrivateKey getPrivateKey(String ip)
  {
    if ((ip == null) || (!isValidIP(ip))) {
      return null;
    }
    if (keyMap.get(ip) == null) {
      return null;
    }
    return ((KeyPair)keyMap.get(ip)).getPrivate();
  }

  public PublicKey getPublicKey(String ip)
  {
    if ((ip == null) || (!isValidIP(ip))) {
      return null;
    }
    if (keyMap.get(ip) == null) {
      return null;
    }
    return ((KeyPair)keyMap.get(ip)).getPublic();
  }

  public RSAPublicKeySpec getPublicKeySpecFromKeyManager(String ip)
  {
    if ((ip == null) || (!isValidIP(ip))) {
      return null;
    }
    if (keyMap.get(ip) == null) {
      return null;
    }

    return getPublicKeySpec(((KeyPair)keyMap.get(ip)).getPublic());
  }

  private static RSAPublicKeySpec getPublicKeySpec(PublicKey pk) {
    if (pk == null)
      return null;
    try
    {
      return (RSAPublicKeySpec)KeyFactory.getInstance("RSA").getKeySpec(pk, RSAPublicKeySpec.class);
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
    }return null;
  }

  public String getPublicKeyModulus(String ip)
  {
    RSAPublicKey pk = (RSAPublicKey)getInstnace().getPublicKey(ip);
    if (pk == null) {
      return null;
    }
    return pk.getModulus().toString(16);
  }

  public String getPublicKeyExponent(String ip)
  {
    RSAPublicKey pk = (RSAPublicKey)getInstnace().getPublicKey(ip);
    if (pk == null)
      return null;
    return pk.getPublicExponent().toString(16);
  }

  public boolean isValidIP(String ip)
  {
    String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(ip);

    return true;
  }
}