package org.modeldriven.alf.eclipse.papyrus.execution.test;

import java.io.File;

import org.modeldriven.alf.eclipse.papyrus.execution.Fuml;

public class ExecuteTests {
	
	private static final String UML_DIR = "../org.modeldriven.alf.eclipse/UML";
	private static final String LIBRARY_DIR = UML_DIR + "/Libraries";
	
	protected static Fuml fuml = new Fuml();
	
	public static void executeTest(String fileName) {
		System.out.println(fileName + "...");
		try {
			fuml.execute(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static void executeTests() {
    	fuml.setUmlDirectory(UML_DIR);
    	fuml.setUmlLibraryDirectory(LIBRARY_DIR);
    	
        File directory = new File(UML_DIR);        
        for (String fileName: directory.list()) {
            String unitName = fileName.substring(0, fileName.length()-4);
            int i = unitName.indexOf('_');
            if (i > 0) {
                String prefix = unitName.substring(0, i);
                if (prefix.equals("Expressions") || 
                    prefix.equals("Statements") ||
                    prefix.equals("Units")) {
                    executeTest(unitName);
                }
            }
        }
        
        System.out.println("All done!");
    }
		
	public static void main(String[] args) {
		executeTests();
	}

}
