package ca.mcgill.ecse223.resto.application;

import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.*;


public class RestoAppApplication {

	private static RestoApp resto;
	private static String filename = "data.txt";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	new AddTablePage().setVisible(true);
            	//new RemoveTablePage().setVisible(true);
            	//new UpdateTablePage().setVisible(true);
            }
        });
        
	}

	public static RestoApp getRestoApp() {
		if (resto == null) {
			// load model
			resto = load();
		}
 		return resto;
	}
	
	public static void save() {
		PersistenceObjectStream.serialize(resto);
	}
	
	public static RestoApp load() {
		PersistenceObjectStream.setFilename(filename);
		resto = (RestoApp) PersistenceObjectStream.deserialize();
		// model cannot be loaded - create empty BTMS
		if (resto == null) {
			resto = new RestoApp();
		}
		else {
			//TODO
			resto.reinitialize();
		}
		return resto;
	}
	
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}
}