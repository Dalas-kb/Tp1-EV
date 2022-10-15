package hamid.umontpellier.fr;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.CompilationUnit;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class CLI {

	private String path;
	private Parser parser;
	private StatsVisitor statVisitor;
	private GraphVisitor graphVisitor;

	public CLI(String path) {
		
		super();
		this.path = path;
		parser = new Parser();
		statVisitor = new StatsVisitor();
		graphVisitor = new GraphVisitor();
	}

	public void display() {
		//Question 1
		System.out.println("Nombre de classes de l'application : " + statVisitor.getNbClasses());
		//Question 2
		System.out.println("Nombre de lignes de code de l'application : " + Parser.getNbLinesCodes());
		//Question 3
		System.out.println("Nombre total de méthodes de l'application : " + statVisitor.getNbMethods());
		//Question 4
		System.out.println("Nombre total de packages de l'application : " + statVisitor.getNbPackages());
		//Question 5
		System.out.println(
				"Nombre moyen de méthodes par classe : " + (statVisitor.getNbMethods() / statVisitor.getNbClasses()));
		//Question 6
		System.out.println("Nombre moyen de lignes de code par méthode : "
				+ (statVisitor.getNbLocMethods() / statVisitor.getNbMethods()));
		//Question 7
		System.out.println(
				"Nombre moyen d'attributs par classe : " + (statVisitor.getNbAttributs() / statVisitor.getNbClasses()));
		// Question8
		Map<String, List<String>> mapClassesMethods = statVisitor.getMapClassesMethods();
		List<String> tenPercentClassesByGtMethods = getTenPercentClassesBygreaterNb(mapClassesMethods);
		System.out.println("Les 10% des classes qui possèdent le plus grand nombre de méthodes : ");
		displayList(tenPercentClassesByGtMethods);
		//Question 9
		Map<String, List<String>> mapClassesAttributes = statVisitor.getMapClassesAttributes();
		List<String> tenPercentClassesByGtAtt = getTenPercentClassesBygreaterNb(mapClassesAttributes);
		System.out.println("Les 10% des classes qui possèdent le plus grand nombre d'attributs : ");
		displayList(tenPercentClassesByGtAtt);
		// Question 10
		Set<Object> classesOfTwoCategories = getClassesOfTwoCategories(tenPercentClassesByGtAtt,
				tenPercentClassesByGtMethods);
		System.out.println("Les classes qui font partie en même temps des deux catégories précédentes : ");
		displaySet(classesOfTwoCategories);
		//Question 11
		System.out.println("Les classes qui possèdent plus de X méthodes : \n*Veuillez insérer la valeur de X****");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int x = sc.nextInt();
		List<String> classesHavingMoreThanXMeth = getClassesHavingMoreThanXMethods(mapClassesMethods, x);
		displayList(classesHavingMoreThanXMeth);
		///Question 12
		System.out.println("Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par classe) :");
		Map<String, Map<String, Integer>> mapClassesMethodsLoc = statVisitor.getMapClassesMethodsLoc();
		
		Map<String, List<String>> tenPersentMethodsHavingMaxLocByClass = getTenPersentMethodsHavingMaxLocByClass(
				mapClassesMethodsLoc);
		displayMap(tenPersentMethodsHavingMaxLocByClass);
		//Question 13
		System.out
				.println("Le nombre maximal de paramètres par rapport à toutes les méthodes de l'application est de : "
						+ statVisitor.getNbArgsMax());
		System.out.println("");
	}

	public void displayList(List<String> list) {
		if (list.isEmpty()) {
			System.out.println("Aucun élément à afficher");
		} else {
			for (String elt : list) {
				System.out.println(Main.indentationFormat + "" + elt);
			}
		}
	}

	public void displayMap(Map<String, List<String>> map) {
		if (map.isEmpty()) {
			System.out.println("Aucun élément à afficher");
		} else {
			for (String className : map.keySet()) {
				System.out.println(Main.indentationFormat + "Nom de la classe : " + className);
				for (String methodName : map.get(className)) {
					System.out.println(Main.indentationFormat + Main.indentationFormat + methodName);
				}
			}
		}
	}

	public void displayMapMap(Map<String, Map<String, Integer>> map) {
		Map<String, Integer> methodMap;
		if (map.isEmpty()) {
			System.out.println("Pas d'élément à afficher");
		} else {
			for (String className : map.keySet()) {
				System.out.println(Main.indentationFormat + "Nom de la classe : " + className);
				methodMap = map.get(className);
				for (String methodName : methodMap.keySet()) {
					System.out.println(Main.indentationFormat + Main.indentationFormat + "Nom de la méthode : "
							+ methodName + "  nbLine : " + methodMap.get(methodName));

				}
			}
		}
	}

	public void displaySet(Set<Object> classesOfTwoCategories) {
		if (classesOfTwoCategories.isEmpty()) {
			System.out.println("Pas d'élément à afficher");
		} else {
			for (String elt : classesOfTwoCategories) {
				System.out.println(Main.indentationFormat + "" + elt);
			}
		}
	}

	public String Question1() {
		return String.valueOf(statVisitor.getNbClasses());
	}
	public String Question2() {
		return String.valueOf(Parser.getNbLinesCodes());
	}

	public String Question3() {
		return String.valueOf(statVisitor.getNbMethods());
	}

	public String Question4() {
		return String.valueOf(statVisitor.getNbPackages());
	}

	public String Question5() {
		return String.valueOf((statVisitor.getNbMethods() / statVisitor.getNbClasses()));
	}

	public String Question6() {
		return String.valueOf((statVisitor.getNbLocMethods() / statVisitor.getNbMethods()));
	}

	public String Question7() {
		return String.valueOf((statVisitor.getNbAttributs() / statVisitor.getNbClasses()));
	}

	public String Question8() {
		return returnListAsString(getTenPercentClassesBygreaterNb(statVisitor.getMapClassesMethods()));
	}

	public String Question9() {
		return returnListAsString(getTenPercentClassesBygreaterNb(statVisitor.getMapClassesAttributes()));
	}

	public String Question10() {
		return returnSetAsString(
				getClassesOfTwoCategories(getTenPercentClassesBygreaterNb(statVisitor.getMapClassesAttributes()),
						getTenPercentClassesBygreaterNb(statVisitor.getMapClassesMethods())));
	}

	public String Question11(int x) {
		return returnListAsString(getClassesHavingMoreThanXMethods(statVisitor.getMapClassesMethods(), x));
	}

	public String Question12() {
		return returnMapAsString(getTenPersentMethodsHavingMaxLocByClass(statVisitor.getMapClassesMethodsLoc()));
	}

	public String Question13() {
		return String.valueOf(statVisitor.getNbArgsMax());
	}

	

	private List<String> getClassesHavingMoreThanXMethods(Map<String, List<String>> mapClassesMethods, int x) {
		List<String> classesChoosed = new ArrayList<>();
		for (String className : mapClassesMethods.keySet()) {
			if (mapClassesMethods.get(className).size() > x) {
				classesChoosed.add(className);
			}
		}
		return classesChoosed;
	}

	public Set<Object> getClassesOfTwoCategories(List<String> tenPercentClassesByGtAtt,
			List<String> tenPercentClassesByGtMeth) {
		Set<Object> result = tenPercentClassesByGtAtt.stream().distinct().filter(tenPercentClassesByGtMeth::contains)
				.collect(Collectors.toSet());
		return result;
	}

	public List<String> getJavaFiles() {
		File directory = new File(path);
		return parser.getFilesPaths(directory);
	}

	public List<String> getTenPercentClassesBygreaterNb(Map<String, List<String>> map) {
		List<String> classesChoosed = new ArrayList<>();
		SortedMap<Integer, String> tempSortedMap = new TreeMap<>();
		String[] sortedClassesTempArray;
		int nbClasses = map.keySet().size();
		int nbClassesWanted = ((10 * nbClasses) / 100) + 1;
		for (String className : map.keySet()) {
			tempSortedMap.put(map.get(className).size(), className);
		}
		sortedClassesTempArray = tempSortedMap.values().toArray(new String[0]);

		for (int i = sortedClassesTempArray.length - 1; i > (sortedClassesTempArray.length - nbClassesWanted
				- 1); i--) {
			classesChoosed.add(sortedClassesTempArray[i]);
		}
		return classesChoosed;
	}

	private Map<String, List<String>> getTenPersentMethodsHavingMaxLocByClass(
			Map<String, Map<String, Integer>> mapClassesMethodsLoc) {
		Map<String, List<String>> classAndMethodChoosed = new HashMap<>();
		SortedMap<Integer, String> tempSortedMap;
		Map<String, Integer> tempMapMethodLoc;
		String[] sortedMethodLocTempArray;
		List<String> tempListMethodChoosed;
		int tenPersentMethNb = 0;
		for (String ClassName : mapClassesMethodsLoc.keySet()) {
			tempMapMethodLoc = mapClassesMethodsLoc.get(ClassName);
			tenPersentMethNb = (int) ((tempMapMethodLoc.keySet().size() * 0.1) + 1);
			tempSortedMap = new TreeMap<>();
			for (String methodName : tempMapMethodLoc.keySet()) {
				tempSortedMap.put(tempMapMethodLoc.get(methodName), methodName);
			}
			sortedMethodLocTempArray = tempSortedMap.values().toArray(new String[0]);
			if (sortedMethodLocTempArray.length > 0) {
				tempListMethodChoosed = new ArrayList<>();
				for (int i = sortedMethodLocTempArray.length - 1; i > (sortedMethodLocTempArray.length
						- tenPersentMethNb - 1); i--) {
					tempListMethodChoosed.add(sortedMethodLocTempArray[i]);

				}
				classAndMethodChoosed.put(ClassName, tempListMethodChoosed);
			}

		}
		return classAndMethodChoosed;
	}

	public void cli() throws FileNotFoundException, IOException {
		List<String> javaFilesPaths = this.getJavaFiles();
		CompilationUnit ast;

		for (String filePath : javaFilesPaths) {
			ast = parser.getCompilationUnit(filePath);
			statVisitor.setCu(ast);
			ast.accept(statVisitor);
		}
	}

	public void cliGraph() throws FileNotFoundException, IOException {
		List<String> javaFilesPaths = this.getJavaFiles();
		CompilationUnit ast;

		for (String filePath : javaFilesPaths) {
			ast = parser.getCompilationUnit(filePath);
			graphVisitor.setCu(ast);
			ast.accept(graphVisitor);
			graphVisitor.calculateGraph();
		}

	}

	public String returnListAsString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		if (list.isEmpty()) {
			sb.append("Pas d'élément à afficher");
		} else {
			for (String elt : list) {
				sb.append(Main.indentationFormat + "" + elt);
			}
		}
		return sb.toString();
	}

	private String returnMapAsString(Map<String, List<String>> map) {
		StringBuilder sb = new StringBuilder();
		if (map.isEmpty()) {
			sb.append("Pas d'élément à afficher");
		} else {
			for (String className : map.keySet()) {
				sb.append(Main.indentationFormat + "Nom de la classe : " + className);
				for (String methodName : map.get(className)) {
					sb.append(Main.indentationFormat + Main.indentationFormat + "" + methodName);
				}
			}
		}
		return sb.toString();
	}

	public String returnSetAsString(Set<String> set) {
		StringBuilder sb = new StringBuilder();
		if (set.isEmpty()) {
			sb.append("Pas d'élément à afficher");
		} else {
			for (String elt : set) {
				sb.append(Main.indentationFormat + "" + elt);
			}
		}
		return sb.toString();
	}

	public String getGraph() {
		return graphVisitor.getGraph();
	}



	
}