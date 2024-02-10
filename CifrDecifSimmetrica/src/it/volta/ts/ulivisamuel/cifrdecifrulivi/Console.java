package it.volta.ts.ulivisamuel.cifrdecifrulivi;

import java.util.Scanner;

import it.volta.ts.ulivisamuel.cifrdecifrulivi.biz.BizCifrDecifrECB;
import it.volta.ts.ulivisamuel.cifrdecifrulivi.events.CifrDecifrConsoleListener;
import it.volta.ts.ulivisamuel.cifrdecifrulivi.util.Util;

public class Console 
{
	private Scanner          scanner;
	private BizCifrDecifrECB bizCifrDecifrECB;
	
	//---------------------------------------------------------------------------------------------
	
	public Console()
	{
		bizCifrDecifrECB = new BizCifrDecifrECB();
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void esegui()
	{
		scanner = new Scanner(System.in);
		bizCifrDecifrECB.setConsoleListener(new CifrDecifrConsoleListener());
		menu();
		scanner.close();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void menu()
	{
		String  mess     = "\nMenu\n   1.Cifra un messaggio"
				         + "\n   2.Decifra un messaggio\n   0.Esci";
		boolean continua = true;
		while(continua)
		{
			int scelta = Util.leggiInt(scanner, mess, 0, 2, true, 0);
			switch(scelta)
			{
			case 0:
				continua = false;
				break;
			case 1:
				cifraMess();
				break;
			case 2:
				decifraMess();
				break;
			}
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void cifraMess()
	{
		String  mess     = "\nMenu\n   1.Cifra un messaggio con metodo ECB"
		         + "\n   2.Cifra un messaggio con metodo CBC\n   0.Esci";
		int scelta = Util.leggiInt(scanner, mess, 0, 2, true, 0);
		switch(scelta)
		{
		case 0:
			break;
		case 1:
			cifraMessECB();
			break;
		case 2:
			cifraMessCBC();
			break;
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void cifraMessECB()
	{
		String mess2 = "\nInserisci il messaggio da cifrare.\nOppure premi invio per "
			     + "annullare l'operazione.";
		String messNoDecr = Util.leggiString(scanner, mess2, false, null);
		if(messNoDecr != null)
			bizCifrDecifrECB.cifraTesto(messNoDecr);
	}

	//---------------------------------------------------------------------------------------------
	
	private void cifraMessCBC()
	{
		String mess2 = "\nInserisci il messaggio da cifrare.\nOppure premi invio per "
			     + "annullare l'operazione.";
		String messNoDecr = Util.leggiString(scanner, mess2, false, null);
		if(messNoDecr != null)
		{
			
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void decifraMess()
	{
		String  mess     = "\nMenu\n   1.Decifra un messaggio con metodo ECB"
		         + "\n   2.Decifra un messaggio con metodo CBC\n   0.Esci";
		int scelta = Util.leggiInt(scanner, mess, 0, 2, true, 0);
		switch(scelta)
		{
		case 0:
			break;
		case 1:
			decifraMessECB();
			break;
		case 2:
			decifraMessCBC();
			break;
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void decifraMessECB()
	{
		String mess2 = "\nInserisci il messaggio da decifrare.\nOppure premi invio per "
			     + "annullare l'operazione.";
		String messNoDecr = Util.leggiString(scanner, mess2, false, null);
		if(messNoDecr != null)
			bizCifrDecifrECB.decifraTesto(messNoDecr);
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void decifraMessCBC()
	{
		String mess2 = "\nInserisci il messaggio da decifrare.\nOppure premi invio per "
			     + "annullare l'operazione.";
		String messNoDecr = Util.leggiString(scanner, mess2, false, null);
		if(messNoDecr != null)
		{
			
		}
	}
}
