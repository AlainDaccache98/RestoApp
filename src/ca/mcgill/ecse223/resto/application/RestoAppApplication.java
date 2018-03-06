package ca.mcgill.ecse223.resto.application;

import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.UpdateTable;


public class RestoAppApplication {

	private static RestoApp resto;
	private static String filename = "data.btms";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	new UpdateTable().setVisible(true);
            }
        });
        
	}

	public static RestoApp getRestoApp() {
		if (resto == null) {
			// load model
			resto = load();
			// TODO
			// for now, we are just creating an empty 
			resto = new RestoApp();
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
