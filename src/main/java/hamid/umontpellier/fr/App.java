package hamid.umontpellier.fr;

import java.io.FileNotFoundException;
import java.io.IOException;



/**
 * Hello world!
 *
 */
public class App {
	static String path;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if(args.length>0) {
			App.path = args[0];
			
		}else {
			System.out.println("Veuillez insérer le chemin de dossier à analyser en argument");
		}
		
	}



}
