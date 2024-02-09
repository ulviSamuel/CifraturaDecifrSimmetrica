package it.volta.ts.ulivisamuel.cifrdecifrulivi.biz;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class BizCifrDecifrECB
{
	private SecretKey key;
	private Cipher    cifrario;
	
	//---------------------------------------------------------------------------------------------
	
	public BizCifrDecifrECB()
	{
		key      = null;
		cifrario = null;
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void generaChiave()
	{
		try {
		    KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		    keyGenerator.init(56);
			this.key = keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
		    e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void inizializzaCifrario()
	{
		try {
			this.cifrario = Cipher.getInstance("DES/ECB/PKCS5Padding"); //l'algoritmo da lei indicato non era compatibile
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	public String cifraTesto(String plainText)
	{
		if(plainText != null)
		{
			if(this.key == null)
				generaChiave();
			if(this.cifrario == null)
				inizializzaCifrario();
			try {
				cifrario.init(Cipher.ENCRYPT_MODE, key);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
			try {
				byte[] cipherText       = cifrario.doFinal(plainText.getBytes());
				String cipherTextString = Base64.getEncoder().encodeToString(cipherText);
				return cipherTextString;
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//---------------------------------------------------------------------------------------------
	
	public String decifraTesto(String cipherText)
	{
		if(this.key != null && this.cifrario != null && cipherText != null)
		{
			byte[] cipherText64 = Base64.getDecoder().decode(cipherText);
			try {
				cifrario.init(Cipher.DECRYPT_MODE, key);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
			try {
				byte[] plainText    = cifrario.doFinal(cipherText64);
				String plainTextStr = Base64.getEncoder().encodeToString(plainText);
				return plainTextStr;
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
