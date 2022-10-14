package hamid.umontpellier.fr;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Parser{

	private static int nbLinesCode;

	public static int getNbLinesOfCodes() {
		return Parser.nbLinesCode;
	}

	public static void setNbLinesCode(int nbLinesOfCodes) {
		Parser.nbLinesCode = nbLinesOfCodes;
	}

	public CompilationUnit getCompilationUnit(String filePath) throws FileNotFoundException, IOException {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		char[] fileContent = this.getFileContent(filePath).toCharArray();
		parser.setSource(fileContent);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		return cu;
	}

	public String getFileContent(String filePath) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
			Parser.nbLinesCode++;
		}
		br.close();
		return sb.toString();
	}

	public List<String> getFilesPaths(File directory) {

		List<String> filesPaths = new ArrayList<>();

		for (File file : directory.listFiles()) {
			if (!file.isDirectory()) {
				if (this.isJavaFile(file)) {
					filesPaths.add(file.getAbsolutePath());
				}
			} else {
				filesPaths.addAll(getFilesPaths(file));
			}
		}
		return filesPaths;
	}

	private boolean isJavaFile(File file) {

		final String extentionWanted = ".java";
		int extentionIndex = file.getName().length() - 5;
		int endFileIndex = file.getName().length();
		final String fileExtention = file.getName().substring(extentionIndex, endFileIndex);

		return fileExtention.equals(extentionWanted);
	}

}
