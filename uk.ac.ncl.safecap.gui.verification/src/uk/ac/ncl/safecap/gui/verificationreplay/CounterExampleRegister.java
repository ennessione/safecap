package uk.ac.ncl.safecap.gui.verificationreplay;

import java.util.HashMap;

import safecap.Project;

public class CounterExampleRegister {
	private static HashMap<Project, CounterExample> register;

	static {
		register = new HashMap<>(20);
	}

	public static void addCounterExample(Project project, CounterExample ce) {
		register.put(project, ce);
	}

	public static CounterExample getCounterExample(Project project) {
		return register.get(project);
	}
}
