package it.volta.ts.ulivisamuel.cifrdecifrulivi.biz;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import it.volta.ts.ulivisamuel.cifrdecifrulivi.events.CifrDecifrConsoleListener;

public abstract class BizCifrDecifr 
{
	protected SecretKey                 key;
	protected Cipher                    cifrario;
	protected CifrDecifrConsoleListener consoleListener;
	
	//---------------------------------------------------------------------------------------------
	
	public BizCifrDecifr()
	{
		key             = null;
		cifrario        = null;
		consoleListener = null;
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void setConsoleListener(CifrDecifrConsoleListener consoleListener)
	{
		this.consoleListener = consoleListener;
	}
	
	//---------------------------------------------------------------------------------------------
	
	protected abstract void   generaChiave();
	protected abstract void   inizializzaCifrario();
    public    abstract String cifraTesto(String plainText);
    public    abstract String decifraTesto(String cipherText);
}
