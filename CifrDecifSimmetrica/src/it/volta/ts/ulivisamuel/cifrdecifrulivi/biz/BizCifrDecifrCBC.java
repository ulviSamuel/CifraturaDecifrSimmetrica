package it.volta.ts.ulivisamuel.cifrdecifrulivi.biz;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import it.volta.ts.ulivisamuel.cifrdecifrulivi.events.CifrDecifrEvent;

public class BizCifrDecifrCBC extends BizCifrDecifr 
{
	private IvParameterSpec ivparams;
	
	//---------------------------------------------------------------------------------------------
	
	public BizCifrDecifrCBC()
	{
		ivparams = null;
	}
	
	//---------------------------------------------------------------------------------------------
	
	@Override
	protected void generaChiave()
	{
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
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
			this.cifrario = Cipher.getInstance("DES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void generaIV()
	{
		byte[] randomBytes = new byte[cifrario.getBlockSize()];
		SecureRandom random = new SecureRandom();
		random.nextBytes(randomBytes);
		ivparams = new IvParameterSpec(randomBytes);
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
			if(this.ivparams == null)
				 generaIV();
			try {
				cifrario.init(Cipher.ENCRYPT_MODE, key, ivparams);
			} catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
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
	    if (this.key != null && this.cifrario != null && this.ivparams != null) 
	    {
	    	if(cipherText != null)
	    	{
	    		byte[] cipherText64 = Base64.getDecoder().decode(cipherText);
		        try {
		        	cifrario.init(Cipher.DECRYPT_MODE, key, ivparams);
		            byte[] plainText = cifrario.doFinal(cipherText64);
		            String res = new String(plainText);
		            consoleListener.mostra(new CifrDecifrEvent("\nTesto decifrato: " + res));
		            return res;
		        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
		            e.printStackTrace();
		        }
	    	}
	    	consoleListener.mostraErrore(new CifrDecifrEvent("\nNon è stato possibile cifrare il messaggio!"));
			return null;
	    }
	    consoleListener.mostraErrore(new CifrDecifrEvent("\nNon puoi decifrare un messaggio senza prima averne cifrato uno con metodo CBC!"));
	    return null;
	}
}
