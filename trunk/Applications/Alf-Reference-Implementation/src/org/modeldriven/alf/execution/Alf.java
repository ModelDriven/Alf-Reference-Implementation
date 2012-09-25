/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.execution;

import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public abstract class Alf {
    
    protected static boolean isFileName = false;
    protected static boolean isVerbose = false;
    protected static boolean isParseOnly = false;
    protected static boolean isPrint = false;
    
    public static void setModelDirectory(String modelDirectory) {
        RootNamespace.setModelDirectory(modelDirectory);
    }
    
    public static void setLibraryDirectory(String libraryDirectory) {
        RootNamespace.setLibraryDirectory(libraryDirectory);
    }
    
    public static void setDebugLevel(Level level) {
        Logger logger = Logger.getLogger(fUML.Debug.class);
        logger.setLevel(level);
    }
    
    public static void setIsFileName(boolean isFileName) {
        Alf.isFileName = isFileName;
    }
    
    public static void setIsVerbose(boolean isVerbose) {
        Alf.isVerbose = isVerbose;
        RootNamespace.setIsVerbose(isVerbose);
    }
    
    public static void setIsParseOnly(boolean isParseOnly) {
        Alf.isParseOnly = isParseOnly;
    }
    
    public static void setIsPrint(boolean isPrint) {
        Alf.isPrint = isPrint;
    }

    protected static void printVerbose(String message) {
        if (isVerbose) {
            System.out.println(message);
        }
    }
    
    public static String parseArgs(String[] args) {
        Logger logger = Logger.getLogger(fUML.Debug.class);
        Level level = logger.getLevel();

        int i = 0;
        while (i < args.length) {
            String arg = args[i];
            if (arg.charAt(0) != '-') {
                break;
            }
            String option = arg.substring(1);
            i++;
            if (i < args.length) {
                if (option.equals("v")) {
                    setIsVerbose(true);
                } else if (option.equals("f")) {
                    setIsFileName(true);
                } else if (option.equals("p")) {
                    setIsParseOnly(true);
                } else if (option.equals("P")) {
                    setIsPrint(true);
                } else if (option.matches("[mld]")) {
                    arg = args[i];
                    if (arg.charAt(0) == '-') {
                        return null;
                    }
                    i++;
                    if (option.equals("m")) {
                        setModelDirectory(arg);
                    } else if (option.equals("l")) {
                        setLibraryDirectory(arg);
                    } else if (option.equals("d")) {
                        setDebugLevel(Level.toLevel(arg, level));
                        level = logger.getLevel();
                    }
                } else {
                    return null;
                }
            }
        }
        
        return i == args.length - 1? args[i]: null;
    }
    
    public void executeUnit(String unitName) {
        QualifiedName qualifiedName = new QualifiedName();
        
        if (isFileName) {
            int len = unitName.length();
            if (len > 4 && unitName.substring(len - 4, len).equals(".alf")) {
                unitName = unitName.substring(0, len - 4);
            }
            qualifiedName.getImpl().addName(unitName);
        } else {        
            String[] names = unitName.replace(".","::").split("::");
            for (String name: names) {
                qualifiedName.getImpl().addName(name);
            }
        }

        UnitDefinition unit = RootNamespace.resolveUnit(qualifiedName);
        if (!(unit instanceof MissingUnit)) {
            if (unit.getImpl().resolveStub()) {
                printVerbose("Resolved stub for " + qualifiedName.getPathName());
            }
            
            RootNamespace root = RootNamespace.getRootScope();
            root.deriveAll();
            Collection<ConstraintViolation> violations = root.checkConstraints();
            if (!violations.isEmpty()) {
                System.out.println("Constraint violations:");
                for (ConstraintViolation violation: violations) {
                    System.out.println("  " + violation);
                }
                
            } else {
                printVerbose("No constraint violations.");
            }
            
            if (isPrint) {
                unit.print(true);
            } else if (!isParseOnly && violations.isEmpty()) {
                NamespaceDefinition definition = unit.getDefinition();
                if (definition.getImpl().isTemplate()) { 
                    System.out.println(definition.getName() + " is a template.");
                } else {
                    this.execute(definition);
                }
            }
        }
    }
    
    protected abstract void execute(NamespaceDefinition definition);
    
    public Alf(String[] args) {
        PropertyConfigurator.configure("log4j.properties");

        String unitName = parseArgs(args);
        
        if (unitName != null) {
            this.executeUnit(unitName);
        } else {
            System.out.println("Usage is");
            System.out.println("  alf [options] unit");
            System.out.println("where unit is the qualified name of an Alf unit and");
            System.out.println("allowable options are:");
            System.out.println("  -d OFF|FATAL|ERROR|WARN|INFO|DEBUG|ALL");
            System.out.println("            Set debug logging level (default is as configured)");
            System.out.println("  -f        Treat unit as a file name rather than a qualifed name");
            System.out.println("  -l path   Set library directory path (default is \"Library\")");
            System.out.println("  -m path   Set model directory path (default is \"Models\")");
            System.out.println("  -p        Parse and constraint check only");
            System.out.println("  -P        Parse, constraint check and print abstract syntax tree");
            System.out.println("  -v        Set verbose mode");
        }         
    }
    
}
