/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.common;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.Token;

public abstract class ParsedElement {
    
    private String fileName = "";
    private int beginLine = 0;
    private int beginColumn = 0;
    private int endLine = 0;
    private int endColumn = 0;

    protected void init(Parser parser) {
        Token token = parser.getToken(0);
        if (token.next != null) {
            token = token.next;
        }
        this.setParserInfo(parser.getFileName(), 
                token.beginLine, token.beginColumn,
                token.endLine, token.endColumn);        
    }
    
    protected void init(ParsedElement element) {
        this.setParserInfo(element.getFileName(), 
                element.getBeginLine(), element.getBeginColumn(),
                element.getEndLine(), element.getEndColumn());
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getBeginLine() {
        return this.beginLine;
    }

    public int getBeginColumn() {
        return this.beginColumn;
    }
    
    public int getEndLine() {
        return this.endLine;
    }

    public int getEndColumn() {
        return this.endColumn;
    }
    
    public void setParserInfo(String fileName, int beginLine, int beginColumn, int endLine, int endColumn) {
        this.fileName = fileName;
        this.beginLine = beginLine;
        this.beginColumn = beginColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

}
