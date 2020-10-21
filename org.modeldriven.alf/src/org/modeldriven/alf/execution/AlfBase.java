/*******************************************************************************
 * Copyright 2011-2020 Model Driven Solutions, Inc.
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

import org.modeldriven.alf.syntax.common.SourceProblem;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public abstract class AlfBase {
    
    public static final String ALF_VERSION = "1.1.0j";
    
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
    
    public void check(UnitDefinition unit, Collection<SourceProblem> problems) {
        if (unit != null) {
            NamespaceDefinition definition = unit.getDefinition();
            if (unit.getImpl().resolveStub()) {
                this.printVerbose("Resolved stub for " + 
                        definition.getImpl().getQualifiedName().getPathName());
            }
    
            RootNamespace root = RootNamespace.getRootScope();
            root.deriveAll();           
            problems.addAll(root.checkConstraints());
        }
    }
    
    // NOTE: Presumes that the problems are ordered by file name and then by line
    // under each file name.
    public void printSourceProblems(Collection<? extends SourceProblem> problems) {
        String fileName = null;
        BufferedReader reader = null;
        String line = "";
        int lineNum = 0;
        for (SourceProblem problem: problems) {
            String nextFileName = problem.getFileName();
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
            lineNum = problem.getBeginLine();
            line = this.getLine(reader, line, prevLineNum, lineNum);
            this.printSourceProblem(line, problem);
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
    
    protected void printSourceProblem(String line, SourceProblem problem) {
        this.println("");
        if (line != null) {
            this.println(line);
            StringBuffer marker = new StringBuffer();
            int beginColumn = problem.getBeginColumn();
            int endColumn = problem.getEndColumn();
            for (int n = beginColumn; n > 1; n--) {
                marker.append(" ");
            }
            marker.append("^");
            if (problem.getBeginLine() == problem.getEndLine() && endColumn > beginColumn) {
                for (int n = beginColumn+1; n < endColumn; n++ ) {
                    marker.append("-");
                }
                marker.append("^");
            }
            this.println(marker.toString());
       }
        this.println(problem.getErrorMessage());
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
