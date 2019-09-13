/*******************************************************************************
 *  Copyright 2019 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
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
import java.util.function.Supplier;

import org.modeldriven.alf.parser.ParsedElement;

public abstract class SourceProblem implements Comparable<SourceProblem> {
    public static final String DEFAULT_ERROR_MESSAGE_FILE_DIRECTORY = "resources";
    public static final String DEFAULT_ERROR_MESSAGE_FILE_NAME = "error-messages.txt";
    public static final String DEFAULT_ERROR_MESSAGE_FILE_PATH = DEFAULT_ERROR_MESSAGE_FILE_DIRECTORY + "/" + DEFAULT_ERROR_MESSAGE_FILE_NAME;
    protected static String errorMessageFileName = null;
    protected static Map<String, String> errorMessages = new HashMap<String, String>();

    protected String problemKey = null;
    protected ParsedElement violatingElement = null;

    
    public SourceProblem(String problemKey, ParsedElement violatingElement) {
        this.problemKey = problemKey;
        this.violatingElement = violatingElement;
    }

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

    private static String getErrorMessage(String problemKey) {
        String errorMessage = errorMessages.get(problemKey);
        return errorMessage == null? problemKey: 
            errorMessage + " (" + problemKey + ")";
    }


    public String getProblemKey() {
        return this.problemKey == null? "": this.problemKey;
    }

    public String getErrorMessage() {
        return formatErrorMessage(getBeginLine(), getBeginColumn(), getErrorMessage(problemKey));
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
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        SourceProblem violation = (SourceProblem)other;
        String problemName = this.getProblemKey();
        String otherConstraintName = violation.getProblemKey();
        ParsedElement violatingElement = this.getViolatingElement();
        ParsedElement otherViolatingElement = violation.getViolatingElement();
        return problemName.equals(otherConstraintName) &&
               violatingElement == otherViolatingElement;
    }

    @Override
    public int hashCode() {
        return this.getViolatingElement().hashCode();
    }

    @Override
    public int compareTo(SourceProblem other) {
        String problemName = this.getProblemKey();
        ParsedElement element = this.getViolatingElement();
        String fileName = element.getFileName();
        int beginLine = element.getBeginLine();
        int endLine = element.getEndLine();
        int beginColumn = element.getBeginColumn();
        int endColumn = element.getEndColumn();
        
        String otherProblemName = other.getProblemKey();
        element = other.getViolatingElement();
        String otherFileName = element.getFileName();
        int otherBeginLine = element.getBeginLine();
        int otherEndLine = element.getEndLine();
        int otherbeginColumn = element.getBeginColumn();
        int otherEndColumn = element.getEndColumn();
        
        int compare = fileName.compareTo(otherFileName);
        return compare != 0? compare:
               beginLine < otherBeginLine? -1:
               beginLine > otherBeginLine? 1:
               beginColumn < otherbeginColumn? -1:
               beginColumn > otherbeginColumn? 1:
               endLine < otherEndLine? -1:
               endLine > otherEndLine? 1:
               endColumn < otherEndColumn? -1:
               endColumn > otherEndColumn? 1: 
               problemName.compareTo(otherProblemName);
    }

    public static String formatErrorMessage(int line, int column, Supplier<String> message) {
        return "[" + line + ":" + column + "] " + message.get();
    }
    public static String formatErrorMessage(int line, int column, String message) {
        return formatErrorMessage(line, column, () -> message);
    }
}
