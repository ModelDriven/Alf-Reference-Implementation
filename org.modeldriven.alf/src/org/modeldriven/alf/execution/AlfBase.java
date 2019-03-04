/*******************************************************************************
 * Copyright 2011-2019 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.execution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public abstract class AlfBase {
    
    public static final String ALF_VERSION = "1.1.0f";
    
    protected boolean isVerbose = false;

    public void setIsVerbose(boolean isVerbose) {
        this.isVerbose = isVerbose;
    }
    
    public UnitDefinition resolve(String unitName) {
        if (unitName == null) {
            return null;
        } else {
            String[] names = unitName.replace(".","::").split("::");
            QualifiedName qualifiedName = new QualifiedName();
            for (String name: names) {
                qualifiedName.getImpl().addName(name);
            }
            return RootNamespace.getRootScope().resolveUnit(qualifiedName);
        }
    }
    
    public Collection<ConstraintViolation> check(
            UnitDefinition unit) {
        Collection<ConstraintViolation> violations = null;
        
        if (unit != null) {
            NamespaceDefinition definition = unit.getDefinition();
            if (unit.getImpl().resolveStub()) {
                this.printVerbose("Resolved stub for " + 
                        definition.getImpl().getQualifiedName().getPathName());
            }
    
            RootNamespace root = RootNamespace.getRootScope();
            root.deriveAll();
            violations = root.checkConstraints();
            
            if (!violations.isEmpty()) {
                this.printConstraintViolations(violations);   
            } else {
                this.printVerbose("No constraint violations.");
            }    
        }
        
        return violations;
    }
    
    // NOTE: Presumes that the violations are ordered by file name and then by line
    // under each file name.
    public void printConstraintViolations(Collection<ConstraintViolation> violations) {
        String fileName = null;
        BufferedReader reader = null;
        String line = "";
        int lineNum = 0;
        for (ConstraintViolation violation: violations) {
            String nextFileName = violation.getFileName();
            if (fileName == null || !fileName.equals(nextFileName)) {
                fileName = nextFileName;
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                    reader = null;
                }
                if (fileName != null) {
                    try {
                        reader = Files.newBufferedReader(Paths.get(fileName), Charset.defaultCharset());
                    } catch (IOException e) {
                    }
                }
                line = null;
                lineNum = 0;
                this.println("\n" + (fileName == null? "": fileName) + ":");
            }
            int prevLineNum = lineNum;
            lineNum = violation.getBeginLine();
            line = this.getLine(reader, line, prevLineNum, lineNum);
            this.printConstraintViolation(line, violation);
        }
    }
    
    protected String getLine(BufferedReader reader, String prevLine, int prevLineNum, int thisLineNum) {
        String line = prevLine;
        if (reader != null) {
            try {
                if (prevLineNum < thisLineNum) {
                    for (; prevLineNum < thisLineNum - 1; prevLineNum++) {
                        reader.readLine();
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                line = null;
            }
        }
        return line;
    }
    
    protected void printConstraintViolation(String line, ConstraintViolation violation) {
        this.println("");
        if (line != null) {
            this.println(line);
            StringBuffer marker = new StringBuffer();
            int beginColumn = violation.getBeginColumn();
            int endColumn = violation.getEndColumn();
            for (int n = beginColumn; n > 1; n--) {
                marker.append(" ");
            }
            marker.append("^");
            if (violation.getBeginLine() == violation.getEndLine() && endColumn > beginColumn) {
                for (int n = beginColumn+1; n < endColumn; n++ ) {
                    marker.append("-");
                }
                marker.append("^");
            }
            this.println(marker.toString());
       }
        this.println(violation.getErrorMessage());
    }
    
    protected void printVerbose(String message) {
        if (this.isVerbose) {
            this.println(message);
        }
    }
    
    protected void println(String message) {
        System.out.println(message);
    }
    
}
