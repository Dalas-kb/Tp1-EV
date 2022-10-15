package hamid.umontpellier.fr;


import java.util.ArrayList;

public class Method {

	String nameMethod;
	ArrayList<String> appels = new ArrayList<>();
	ArrayList<String> entrées = new ArrayList<>();

	public Method(String name, ArrayList<String> appels) {
		super();
		this.nameMethod = name;
		this.appels = appels;
	}

	public void addIfNotContained(Method method) {
		if (method.getAppels().contains(this.getMethod())) {
			this.getEntrées().add(method.getMethod());
		}
	}

	public ArrayList<String> getAppels() {
		return appels;
	}

	public ArrayList<String> getEntrées() {
		return entrées;
	}

	public String getMethod() {
		return nameMethod;
	}

	public void setAppels(ArrayList<String> appels) {
		this.appels = appels;
	}

	public void setEntrées(ArrayList<String> entrées) {
		this.entrées = entrées;
	}

	public void setName(String name) {
		this.nameMethod = name;
	}

	public String getMethodWithCallsLinks() {
		StringBuilder res = new StringBuilder("");
		for (String string : appels) {
			res.append(nameMethod).append("->").append(string).append(" ");
		}
		return res.toString();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Methode : ").append(this.getMethod()).append("\nMethode(s) sortie : ")
				.append(this.getAppels().toString()).append("\nMethode(s) entree : ")
				.append(this.getEntrées().toString()).append("\n");
		return result.toString();
	}

}
