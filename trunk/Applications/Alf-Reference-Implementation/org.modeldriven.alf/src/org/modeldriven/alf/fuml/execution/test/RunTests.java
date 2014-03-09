package org.modeldriven.alf.fuml.execution.test;

import java.io.File;

import org.modeldriven.alf.fuml.execution.Alf;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.StereotypeApplication;

public class RunTests {
    
    private static final String TEST_DIR = "../org.modeldriven.alf/tests-x";
    protected static Alf alf;
    
    protected static void runTest(String unitName) {
        System.out.println(unitName + "...");
        try {
            UnitDefinition unit = alf.parse(unitName, false);
            if (unit != null) {
                if (alf.check(unit).isEmpty()) {
                    FumlMapping mapping = alf.map(unit.getDefinition());
                    if (mapping != null) {
                        alf.execute(unit);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected static void runTests() {
        ElementReferenceImpl.clearTemplateBindings();
        StereotypeApplication.clearStereotypeApplications();
        alf.map(RootNamespace.getRootScope());
        alf.setModelDirectory(TEST_DIR);
        
        File directory = new File(TEST_DIR);
        
        for (String fileName: directory.list()) {
            String unitName = fileName.substring(0, fileName.length()-4);
            int i = unitName.indexOf('_');
            if (i > 0) {
                String prefix = unitName.substring(0, i);
                if (prefix.equals("Expressions") || 
                    prefix.equals("Statements") ||
                    prefix.equals("Units")) {
                    runTest(unitName);
                }
            }
        }
        
        System.out.println("All done!");
    }
}