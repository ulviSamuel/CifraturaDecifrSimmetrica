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

import it.volta.ts.ulivisamuel.cifrdecifrulivi.events.CifrDecifrEvent;

public class BizCifrDecifrECB extends BizCifrDecifr 
{
	@Override
	protected void generaChiave()
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
	
	@Override
	protected void inizializzaCifrario()
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
	
	@Override
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
				consoleListener.mostra(new CifrDecifrEvent("\nTesto cifrato: " + cipherTextString));
				return cipherTextString;
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}
		consoleListener.mostraErrore(new CifrDecifrEvent("\nNon è stato possibile cifrare il messaggio!"));
		return null;
	}
	
	//---------------------------------------------------------------------------------------------
	
	@Override
	public String decifraTesto(String cipherText)
	{
	    if (this.key != null && this.cifrario != null) 
	    {
	    	if(cipherText != null)
	    	{
	    		byte[] cipherText64 = Base64.getDecoder().decode(cipherText);
		        try {
		            cifrario.init(Cipher.DECRYPT_MODE, key);
		            byte[] plainText = cifrario.doFinal(cipherText64);
		            String res = new String(plainText);
		            consoleListener.mostra(new CifrDecifrEvent("\nTesto decifrato: " + res));
		            return res;
		        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
		            e.printStackTrace();
		        }
	    	}
	    	consoleListener.mostraErrore(new CifrDecifrEvent("\nNon è stato possibile cifrare il messaggio!"));
			return null;
	    }
	    consoleListener.mostraErrore(new CifrDecifrEvent("\nNon puoi decifrare un messaggio senza prima averne cifrato uno!"));
	    return null;
	}
}
