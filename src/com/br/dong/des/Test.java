package com.br.dong.des;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

public class Test {

	public static void main(String args[]) {
		String textToCrypting = "information_jjh_jjxy_jjzs";
		
		// Object of this class provides the functionality for
        // encryption and decryption.
        Cipher cipher;
        try {
            // parameter "DES" specifies type of cipher we want to create
            // through the factory method. It includes algorithm, mode and
            // padding. You can define only algorithm and in that case default
            // values will be used for mode and padding.
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException ex) {
            // requested cryptographic algorithm is not available
        	ex.printStackTrace();
            return;
        } catch (NoSuchPaddingException ex) {
            // requested padding mechanism is not available in the environment.
        	ex.printStackTrace();
            return;
        }
        // The length of keyString is 8. It's important characteristic
        // for encryption
        String keyString = "12345678";
        byte[] keyData = keyString.getBytes();
        // key object specifies a secret key
        // it uses to construct the secret key for our cipher object from a byte
        // array
        SecretKeySpec key = new SecretKeySpec(keyData, 0, keyData.length, "DES");
        try {
            // initializing with setting up the type of operation it's to be
            // used for. In this case - for encryption.
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException ex) {
            // given key object is inappropriate for initializing this cipher
        	ex.printStackTrace();
            return;
        }
        int cypheredBytes = 0;
        byte[] inputBytes = textToCrypting.getBytes();
        byte[] outputBytes = new byte[100];
        try {
            // doFinal method encrypts data to outputBytes array.
            // for unknown or big counts of data update method more recommended
            // counts of crypted bytes saved inside cypheredBytes variable
            cypheredBytes = cipher.doFinal(inputBytes, 0, inputBytes.length,
                    outputBytes, 0);
        } catch (IllegalStateException ex) {
        	ex.printStackTrace();
            return;

        } catch (ShortBufferException ex) {
        	ex.printStackTrace();
            return;
        } catch (IllegalBlockSizeException ex) {
        	ex.printStackTrace();
            return;
        } catch (BadPaddingException ex) {
        	ex.printStackTrace();
            return;
        }
        String str;
		try {
			str = new String(outputBytes, 0, cypheredBytes, "iso-8859-1");
			inputBytes = str.getBytes("iso-8859-1");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException ex) {
            return;
        }
        try {
            // decrypts data
            cypheredBytes = cipher.doFinal(inputBytes, 0, inputBytes.length,
                    outputBytes, 0);
        } catch (IllegalStateException ex) {
        	ex.printStackTrace();
            return;
        } catch (ShortBufferException ex) {
        	ex.printStackTrace();
            return;
        } catch (IllegalBlockSizeException ex) {
        	ex.printStackTrace();
            return;
        } catch (BadPaddingException ex) {
        	ex.printStackTrace();
            return;
        }
        str = new String(outputBytes, 0, cypheredBytes);
        
        System.out.println(str);

	}

}

