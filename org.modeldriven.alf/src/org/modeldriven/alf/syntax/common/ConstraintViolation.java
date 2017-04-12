/*******************************************************************************
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    protected static Map<String, String> errorMessages = new HashMap<String, String>();

    public static void loadErrorMessageFile() throws IOException {
        loadErrorMessageFile(DEFAULT_ERROR_MESSAGE_FILE_NAME);
    }
    
    public static void loadErrorMessageFile(String path) throws IOException {
        if (path == null) {
            clearErrorMessages();
        } else if (!path.equals(errorMessageFileName)) {
            loadErrorMessageFile(Files.newInputStream(Paths.get(path)));
            errorMessageFileName = path;
        }
    }

    public static void loadErrorMessageFile(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream, Charset.defaultCharset()));
        clearErrorMessages();
        String line;
        do {
            do {
                line = reader.readLine();
            } while (line != null && line.trim().isEmpty());
            if (line != null) {
                String constraintName = line;
                line = reader.readLine();
                if (line != null && !line.trim().isEmpty()) {
                    putErrorMessage(constraintName, line);
                }
                
                // Skip constraint description
                line = reader.readLine();
            }
        } while (line != null);
        reader.close();
    }
    
    public static void clearErrorMessages() {
        errorMessageFileName = null;
        errorMessages = new HashMap<String, String>();
    }
    
    public static void putErrorMessage(String constraintName, String errorMessage) {
        errorMessages.put(constraintName, errorMessage);
    }
    
    public static String getErrorMessage(String constraintName) {
        String errorMessage = errorMessages.get(constraintName);
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
        return "[" + this.getBeginLine() + ":" + this.getBeginColumn() + "] " + 
                getErrorMessage(this.constraintName);
    }
    
    public ParsedElement getViolatingElement() {
        return this.violatingElement;
    }
    
    public String getFileName() {
        return this.getViolatingElement().getFileName();
    }
    
    public int getBeginLine() {
        return this.getViolatingElement().getBeginLine();
    }
    
    public int getBeginColumn() {
        return this.getViolatingElement().getBeginColumn();
    }
    
    public int getEndLine() {
        return this.getViolatingElement().getEndLine();
    }
    
    public int getEndColumn() {
        return this.getViolatingElement().getEndColumn();
    }
    
    @Override
    public String toString() {
        return this.getFileName() + this.getErrorMessage();
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
        int line = element.getBeginLine();
        int column = element.getBeginColumn();
        
        element = other.getViolatingElement();
        String otherFileName = element.getFileName();
        int otherLine = element.getBeginLine();
        int otherColumn = element.getBeginColumn();
        
        int compare = fileName.compareTo(otherFileName);
        return compare != 0? compare:
               line < otherLine? -1:
               line > otherLine? 1:
               column < otherColumn? -1:
               column > otherColumn? 1: 0;
    }

}
