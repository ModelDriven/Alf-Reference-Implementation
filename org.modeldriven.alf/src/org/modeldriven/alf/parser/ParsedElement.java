/*******************************************************************************
 * Copyright 2011, 2018 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.parser;

public abstract class ParsedElement {
    
    private String fileName = "";
    private int beginLine = 0;
    private int beginColumn = 0;
    private int endLine = 0;
    private int endColumn = 0;

    protected void init(Parser parser) {
        parser.provideInfo(this, true);        
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
    
    public void setBegin(int beginLine, int beginColumn) {
        this.beginLine = beginLine;
        this.beginColumn = beginColumn;
    }
    
    public void setEnd(int endLine, int endColumn) {
        this.endLine = endLine;
        this.endColumn = endColumn;
    }
    
    public void setParserInfo(String fileName, int beginLine, int beginColumn, int endLine, int endColumn) {
        this.fileName = fileName;
        this.setBegin(beginLine, beginColumn);
        this.setEnd(endLine, endColumn);
    }

}
