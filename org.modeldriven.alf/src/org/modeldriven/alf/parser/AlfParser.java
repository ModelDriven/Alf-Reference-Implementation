/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.parser;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.fuml.units.RootNamespaceImpl;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class AlfParser {
    public static final String version = "v1.0";

    public static final char checkOption = 'c';
    public static final char checkAllOption = 'C';
    public static final char printOption = 'p';
    public static final char printDerivedOption = 'P';

    private static boolean checkConstraints = false;
    private static boolean checkAllConstraints = false;
    private static boolean print = false;
    private static boolean printDerived = false;

    private static boolean optionsAreLegal = true;
    private static UnitDefinition unit = null;
    private static Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();

    public static boolean optionsAreLegal() {
        return optionsAreLegal;
    }

    public static UnitDefinition getUnit() {
        return unit;
    }

    public static Collection<ConstraintViolation> getViolations() {
        return violations;
    }

    public static boolean constraintsCheck() {
        return getViolations().isEmpty();
    }

    public static boolean parseOptions(String options) {

        checkConstraints = false;
        print = false;
        printDerived = false;

        if (options.length() == 0) {
            optionsAreLegal = true;
            return true;
        }

        for (int i = 0; i < options.length(); i++) {
            switch (options.charAt(i)) {
            case checkOption:
                checkConstraints = true;
                break;
            case checkAllOption:
                checkAllConstraints = true;
                break;
            case printOption:
                print = true;
                break;
            case printDerivedOption:
                printDerived = true;
                break;
            default:
                optionsAreLegal = false;
                return false;
            }
        }

        optionsAreLegal = true;
        return true;
    }

    public static UnitDefinition parse(String filePath) {
        unit = null;

        try {
            new RootNamespaceImpl().setIsVerbose(true);
            RootNamespace root = RootNamespace.getRootScope();
            unit = ((RootNamespaceImpl) root.getImpl()).getModelScopeImpl().
                    resolveModelFile(filePath);

            if (unit != null) {
                if (checkConstraints || checkAllConstraints) {
                    if (unit.getImpl().resolveStub()) {
                        System.out.println("Resolved stub for "
                                + unit.getDefinition().getImpl().getQualifiedName().getPathName());
                    } else {
                        unit.getImpl().addImplicitImports();
                    }
                    if (checkAllConstraints) {
                        root.deriveAll();
                        violations = root.checkConstraints();
                    } else {
                        violations = unit.checkConstraints();
                    }
                    if (constraintsCheck()) {
                        System.out.println("No constraint violations.");
                    } else {
                        System.out.println("Constraint violations:");
                        for (ConstraintViolation violation : violations) {
                            System.out.println("  " + violation);
                        }
                    }
                }

                if (print || printDerived) {
                    printElement(printDerived);
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (Throwable e) {
            System.out.println("Failed.");
            e.printStackTrace();
        }

        return unit;

    }

    public static void printElement(boolean includeDerived) {
        SyntaxElement element = getUnit();
        if (element != null) {
            element.print(includeDerived);
        }
    }

    public static void printFromRoot() {
        RootNamespace.getRootScope().print(true);
    }

    public static void main(String args[]) {
        System.out.println("Alf " + version + " Parser");

        String filePath = null;
        String options = "";

        if (args.length > 0) {
            if (args[0].charAt(0) == '-') {
                options = args[0].substring(1);
                if (args.length > 1) {
                    filePath = args[1];
                }
            } else {
                filePath = args[0];
            }
        }

        if (args.length > 0 && args.length <= 2 && parseOptions(options)) {
            parse(filePath);
        } else {
            System.out.println("Usage is");
            System.out.println("      alfp [-options] inputfile");
            System.out.println("Options:");
            System.out.println("  p   Print abstract syntax tree");
            System.out.println("  P   Print with derived properties");
            System.out.println("  c   Perform static semantic checking)");
            System.out.println("  C   Perform static semantic checking from root");
        }
    }

}
