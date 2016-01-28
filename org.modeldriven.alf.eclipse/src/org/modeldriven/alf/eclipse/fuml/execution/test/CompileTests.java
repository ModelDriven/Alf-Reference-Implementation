/*******************************************************************************
 * Copyright 2015-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.eclipse.fuml.execution.test;

import java.io.File;

import org.modeldriven.alf.eclipse.fuml.execution.AlfCompiler;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.uml.StereotypeApplication;

public class CompileTests {
	
	private static final String LIBRARY_DIR = "../org.modeldriven.alf.eclipse/Libraries";
	private static final String TEST_DIR = "../org.modeldriven.alf/tests-x";
	
	private static String[] args = {"-l", LIBRARY_DIR, "-m", TEST_DIR, ""};
	
	public static void compileTest(String unitName) {
		System.out.println(unitName + "...");
		
        ElementReferenceImpl.clearTemplateBindings();
        StereotypeApplication.clearStereotypeApplications();
        
        try {
        	args[args.length-1] = unitName;
        	new AlfCompiler(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
    public static void compileTests() {
        File directory = new File(TEST_DIR);        
        for (String fileName: directory.list()) {
        	int l = fileName.length();
        	if (fileName.startsWith("Expressions_") || fileName.startsWith("Statements_") || 
        			fileName.startsWith("Units_") || fileName.startsWith("Interactive_")) {
	            String unitName = fileName.substring(0, l-4);
	            compileTest(unitName);
        	}
        }
        
        compileTest("_RunTests");
        
        System.out.println("All done!");
    }
		
	public static void main(String[] args) {
		compileTests();
	}

}
