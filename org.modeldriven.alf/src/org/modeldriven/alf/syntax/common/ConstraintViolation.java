/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConstraintViolation implements Comparable<ConstraintViolation> {
    
    public static final String DEFAULT_ERROR_MESSAGE_FILE_DIRECTORY = "resources";
    public static final String DEFAULT_ERROR_MESSAGE_FILE_NAME = "error-messages.txt";
    public static final String DEFAULT_ERROR_MESSAGE_FILE_PATH = 
            DEFAULT_ERROR_MESSAGE_FILE_DIRECTORY + "/" + DEFAULT_ERROR_MESSAGE_FILE_NAME;
    
    protected static String errorMessageFileName = null;
    protected static Map<String, String> errorMessages = null;

    public static void loadErrorMessageFile(String path) throws IOException {
        if (path == null) {
            errorMessages = null;
        } else if (errorMessages == null || path != errorMessageFileName) {            
            BufferedReader reader = 
                    Files.newBufferedReader(Paths.get(path), Charset.defaultCharset());
            errorMessages = new HashMap<String, String>();
            String line;
            do {
                do {
                    line = reader.readLine();
                } while (line != null && line.equals(""));
                if (line != null) {
                    String constraintName = line;
                    line = reader.readLine();
                    if (line != null && !line.equals("")) {
                        errorMessages.put(constraintName, line);
                    }
                }
            } while (line != null);
            errorMessageFileName = path;
            reader.close();
        }
    }
    
    public static void loadErrorMessageFile() throws IOException {
        loadErrorMessageFile(DEFAULT_ERROR_MESSAGE_FILE_NAME);
    }
    
    public String getErrorMessage(String constraintName) {
        String errorMessage = errorMessages == null? null: 
            errorMessages.get(constraintName);
        return errorMessage == null? constraintName: 
            errorMessage + " (" + constraintName + ")";
    }
    
    private String constraintName = null;
    private ParsedElement violatingElement = null;
    
    public ConstraintViolation(String constraintName, ParsedElement violatingElement) {
        this.constraintName = constraintName;
        this.violatingElement = violatingElement;
    }
    
    public String getConstraintName() {
        return this.constraintName;
    }
    
    public String getErrorMessage() {
        return getErrorMessage(this.constraintName);
    }
    
    public ParsedElement getViolatingElement() {
        return this.violatingElement;
    }
    
    public String getFileName() {
        return this.getViolatingElement().getFileName();
    }
    
    public int getLine() {
        return this.getViolatingElement().getLine();
    }
    
    public int getColumn() {
        return this.getViolatingElement().getColumn();
    }
    
    @Override
    public String toString() {
        return this.getErrorMessage() + " in " + this.getFileName() + 
            " at line " + this.getLine() + ", column " + this.getColumn();
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ConstraintViolation)) {
            return false;
        } else {
            ConstraintViolation violation = (ConstraintViolation)other;
            String constraintName = this.getConstraintName();
            String otherConstraintName = violation.getConstraintName();
            ParsedElement violatingElement = this.getViolatingElement();
            ParsedElement otherViolatingElement = violation.getViolatingElement();
            return (constraintName == null && otherConstraintName == null ||
                    constraintName != null && constraintName.equals(otherConstraintName)) &&
                   violatingElement == otherViolatingElement;
        }
    }
    
    @Override
    public int hashCode() {
        return this.getViolatingElement().hashCode();
    }

    @Override
    public int compareTo(ConstraintViolation other) {
        ParsedElement element = this.getViolatingElement();
        String fileName = element.getFileName();
        int line = element.getLine();
        int column = element.getColumn();
        
        element = other.getViolatingElement();
        String otherFileName = element.getFileName();
        int otherLine = element.getLine();
        int otherColumn = element.getColumn();
        
        int compare = fileName.compareTo(otherFileName);
        return compare != 0? compare:
               line < otherLine? -1:
               line > otherLine? 1:
               column < otherColumn? -1:
               column > otherColumn? 1: 0;
    }

}
