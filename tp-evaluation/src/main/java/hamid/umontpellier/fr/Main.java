package hamid.umontpellier.fr;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


/**
 * Hello world!
 *
 */
public class Main {
	static String path;
	public final static String indentationFormat = "\t|\t-\t";

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if(args.length>0) {
			Main.path = args[0];
			Main.Menu();
		}else {
			System.out.println("Veuillez insérer le chemin de dossier à analyser en argument");
		}	
		
	}


	public static void Menu() throws FileNotFoundException, IOException {
		CLI cli;
		
		cli = new CLI(Main.path);
		cli.cli();
		cli.getGraph();
		while (true) {
			System.out.println(
					"******** Tp1 Analyse Statique*******");
			System.out.println(
					"==================================================================");
					System.out.println(
					"Pour obtenir les informations des classes du projet (infos de la partie 2 du TP) tapez 1");
			
			System.out.println("Pour quitter tapez 0");
		}
	}

}
