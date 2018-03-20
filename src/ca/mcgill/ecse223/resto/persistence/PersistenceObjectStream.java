package ca.mcgill.ecse223.resto.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;

public class PersistenceObjectStream {

	private static String filename = "data.txt";

	public static void serialize(Object object) {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			//System.out.println("before writing the object");
			//System.out.println(RestoAppApplication.getRestoApp().getTables());
			
			out.writeObject(object);
			System.out.println(RestoAppApplication.getRestoApp().getTables());
			System.out.println(RestoAppApplication.getRestoApp().getTable(0).numberOfSeats());
			//System.out.println("object written in file");
			out.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not save data to file '" + filename + "'.");
		}

	}

	public static Object deserialize() {
		Object o = null;
		ObjectInputStream in;
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			in = new ObjectInputStream(fileIn);
			o = in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			o = null;
		}
		return o;
	}
	
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}

}
