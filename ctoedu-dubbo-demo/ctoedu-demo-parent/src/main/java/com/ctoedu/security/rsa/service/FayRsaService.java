package com.ctoedu.security.rsa.service;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class FayRsaService {
	
	public static final String SIGNATURE_ALGORITHM = "SHA512withRSA";
	
	public static final String ALGORITHM = "RSA";
	
	public static final String TEXT = "asfcz";
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        
        String publicKeyS = new String(Base64.encodeBase64(publicKey.getEncoded()));
        String privateKeyS = new String(Base64.encodeBase64(privateKey.getEncoded()));
        
        System.out.println("公钥>"+publicKeyS);
        System.out.println("私钥>"+privateKeyS);

        byte[] encodedText = RSAEncode(publicKey, TEXT.getBytes());
        byte[] signature = sign(privateKey, TEXT.getBytes());
        System.out.println("加密>"+new String(encodedText));
        System.out.println("签名>"+new String(signature));
        
        String decodedText = RSADecode(privateKey, encodedText);
        boolean signatureVerify = verify(publicKey, signature, decodedText.getBytes());
        System.out.println("解密>"+decodedText);
        System.out.println("验签>"+signatureVerify);
	}
	
	public static KeyPair getKeyPair(){
		KeyPairGenerator keyPairGen = null;
		KeyPair keyPair = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
			keyPair = keyPairGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return keyPair;
	}
	
	/**
	 * 签名
	 * @param privateKey
	 * @param text
	 * @return
	 */
	public static byte[] sign(PrivateKey privateKey, byte[] text){
		Signature signature;
		try {
			signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateKey);
			signature.update(text);
			return signature.sign();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 验签
	 * @param publicKey
	 * @param signatureVerify
	 * @param text
	 * @return
	 */
	public static boolean verify(PublicKey publicKey, byte[] signatureVerify, byte[] text){
		try {
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(publicKey);
			signature.update(text);
			return signature.verify(signatureVerify);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 加密
	 * @param key 公钥
	 * @param text
	 * @return
	 */
	public static byte[] RSAEncode(PublicKey key, byte[] text) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(text, 0, 117);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 解密
	 * @param key 私钥
	 * @param encodedText
	 * @return
	 */
	public static String RSADecode(PrivateKey key, byte[] encodedText) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(encodedText));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] decryptByPrivateKey(PrivateKey privateKey, byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        for(int i = 0; inputLen - offSet > 0; offSet = i * 128) {
            byte[] cache;
            if(inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] encryptByPublicKey(PublicKey publicKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 117) {
            byte[] cache;
            if(inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
}