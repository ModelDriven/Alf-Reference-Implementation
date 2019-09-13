/*******************************************************************************
 * Copyright 2015, 2017 Model Driven Solutions, Inc.
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.fuml.execution.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestRunnerGenerator {
    
    private static final String UNIT_NAME = "_TestRunner";
    private static final String TEST_DIR = "../org.modeldriven.alf/tests-x";
    private static final String FILE_NAME = UNIT_NAME + ".alf";
    
    private ArrayList<String> text = new ArrayList<String>();
    
    public TestRunnerGenerator() {
        this.text.add("active class " + UNIT_NAME + " specializes Test, Test::Tester {");
        this.text.add("    @Create");
        this.text.add("    public _TestRunner(in tester: Tester) {");
        this.text.add("        super.Test(tester);");
        this.text.add("    }");
        this.text.add("} do {");
        this.text.add("    accept(Continue);");
    }
    
    public void addUnit(String unitName) {
        this.text.add("    WriteLine(\"" + unitName + "...\");");
        this.text.add("    new " + unitName + "::run(this);");
        this.text.add("    accept(Continue);");
    }
    
    public ArrayList<String> getText() {
        ArrayList<String> text = new ArrayList<String>(this.text);
        text.add("    this.done(\"All done!\");");
        text.add("}");
        return text;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (String line: this.getText()) {
            buffer.append(line);
            buffer.append("\n");
        }
       return buffer.toString(); 
    }
    
    public static void generate(String testDirectory) {
        TestRunnerGenerator testRunnerGen = new TestRunnerGenerator();
        
        File directory = new File(testDirectory);        
        for (String fileName: directory.list()) {
            String unitName = fileName.substring(0, fileName.length()-4);
            int i = unitName.indexOf('_');
            if (i > 0) {
                String prefix = unitName.substring(0, i);
                if (prefix.equals("Expressions") || 
                    prefix.equals("Statements") ||
                    prefix.equals("Units")) {
                    testRunnerGen.addUnit(unitName);
                }
            }
        }
        
        System.out.println("Generated " + testDirectory + "/" + FILE_NAME);
        try {
            Files.write(Paths.get(testDirectory, FILE_NAME), testRunnerGen.getText(), Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public static void main(String[] args) {
        generate(TEST_DIR);
    }
}