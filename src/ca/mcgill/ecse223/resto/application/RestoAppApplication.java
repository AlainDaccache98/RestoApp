package ca.mcgill.ecse223.resto.application;

import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.view.*;


public class RestoAppApplication {

private static RestoApp resto;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	new RestoAppPage().setVisible(true);
            }
        });
        
	}

	public static RestoApp getRestoApp() {
		if (resto == null) {
			// load model
			// TODO
			// for now, we are just creating an empty 
			resto = new RestoApp();
		}
 		return resto;
	}
}
