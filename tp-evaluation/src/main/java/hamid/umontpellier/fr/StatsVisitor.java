package hamid.umontpellier.fr;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class StatsVisitor extends ASTVisitor {

	private int nbClasses;
	private int nbMethods;
	private int nbPackages;
	private List<String> packagesNames = new ArrayList<>();
	private int nbLocMethods;
	private CompilationUnit cu;
	private int nbAttributs;
	private Map<String, List<String>> mapClassesMethods = new HashMap<>();
	private String tempClassName;
	private Map<String, List<String>> mapClassesAttributes = new HashMap<>();
	private Map<String, Map<String, Integer>> mapClassesMethodsLoc = new HashMap<>();
	private int nbInstrMeth;
	private int nbArgsMax;

	public StatsVisitor() {
		super();
		nbClasses = 0;
		nbMethods = 0;
		nbPackages = 0;
		nbLocMethods = 0;
		nbAttributs = 0;
		nbInstrMeth = 0;
		setNbArgsMax(0);
	}

	public StatsVisitor(boolean visitDocTags) {
		super(visitDocTags);
	}

	public void addFieldsForClass(String className, FieldDeclaration[] fields) {
		for (FieldDeclaration field : fields) {
			mapClassesAttributes.get(className).add(field.toString());
		}
	}

	@Override
	public void endVisit(MethodDeclaration md) {
		Map<String, Integer> mapMethodsLoc = mapClassesMethodsLoc.get(tempClassName);
		mapMethodsLoc.put(md.getName().toString(), nbInstrMeth);

	}

	public CompilationUnit getCu() {
		return cu;
	}

	public Map<String, List<String>> getMapClassesAttributes() {
		return mapClassesAttributes;
	}

	public Map<String, List<String>> getMapClassesMethods() {
		return mapClassesMethods;
	}

	// pour le calculer du nombre de ligne de codes de toutes les méthodes

	public Map<String, Map<String, Integer>> getMapClassesMethodsLoc() {
		return mapClassesMethodsLoc;
	}

	public int getNbArgsMax() {
		return nbArgsMax;
	}

	public int getNbAttributs() {
		return nbAttributs;
	}

	// Getters and Setters
	public int getNbClasses() {
		return nbClasses;
	}

	public int getNbLocMethods() {
		return nbLocMethods;
	}

	public int getNbMethods() {
		return nbMethods;
	}

	public int getNbPackages() {
		return nbPackages;
	}

	public void setCu(CompilationUnit cu) {
		this.cu = cu;
	}

	public void setMapClassesAttributes(Map<String, List<String>> mapClassesAttributes) {
		this.mapClassesAttributes = mapClassesAttributes;
	}

	public void setMapClassesMethods(Map<String, List<String>> mapClassesMethods) {
		this.mapClassesMethods = mapClassesMethods;
	}

	public void setMapClassesMethodsLoc(Map<String, Map<String, Integer>> mapClassesMethodsLoc) {
		this.mapClassesMethodsLoc = mapClassesMethodsLoc;
	}

	public void setNbArgsMax(int nbArgsMax) {
		this.nbArgsMax = nbArgsMax;
	}

	public void setNbAttributs(int nbAttributs) {
		this.nbAttributs = nbAttributs;
	}

	public void setNbClasses(int nbClasses) {
		this.nbClasses = nbClasses;
	}

	public void setNbLocMethods(int nbLocMethods) {
		this.nbLocMethods = nbLocMethods;
	}

	public void setNbMethods(int nbMethods) {
		this.nbMethods = nbMethods;
	}

	public void setNbPackages(int nbPackages) {
		this.nbPackages = nbPackages;
	}

	@Override
	public boolean visit(AssertStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(Block node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(BreakStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(ConstructorInvocation node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(ContinueStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(DoStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(EmptyStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(ExpressionStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(ForStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(IfStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(LabeledStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(MethodDeclaration methodNode) {
		nbMethods++;
		mapClassesMethods.get(tempClassName).add(methodNode.getName().toString());
		nbInstrMeth = 0;
		Map<String, Integer> mapMethodsLoc = mapClassesMethodsLoc.get(tempClassName);
		mapMethodsLoc.put(methodNode.getName().toString(), nbInstrMeth);
		// Pour la question 13
		if (nbArgsMax < methodNode.parameters().size()) {
			nbArgsMax = methodNode.parameters().size();
		}
		return true;
	}

	@Override
	public boolean visit(PackageDeclaration packageNode) {
		if (!packagesNames.contains(packageNode.getName().toString())) {
			nbPackages++;
			packagesNames.add(packageNode.getName().toString());
		}
		return true;
	}

	@Override
	public boolean visit(ReturnStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(SuperConstructorInvocation node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(SwitchCase node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(SwitchStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(SynchronizedStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(ThrowStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(TryStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration typeNode) {
		tempClassName = typeNode.getName().toString();
		nbAttributs += typeNode.getFields().length;
		mapClassesAttributes.put(tempClassName, new ArrayList<String>());
		addFieldsForClass(tempClassName, typeNode.getFields());
		mapClassesMethods.put(tempClassName, new ArrayList<String>());
		nbClasses++;
		mapClassesMethodsLoc.put(tempClassName, new HashMap<String, Integer>());
		return true;
	}

	@Override
	public boolean visit(TypeDeclarationStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

	@Override
	public boolean visit(WhileStatement node) {
		nbLocMethods++;
		nbInstrMeth++;
		return true;
	}

}
