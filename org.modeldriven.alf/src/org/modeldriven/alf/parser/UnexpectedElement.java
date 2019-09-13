/*******************************************************************************
 * Copyright 2019 Model Driven Solutions, Inc.
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.parser;

/**
 * Provides a contextual element to provide source information for parsing errors, 
 * which otherwise wouldn't have one.
 */
public class UnexpectedElement extends ParsedElement {
    public UnexpectedElement(Parser parser) {
        this.init(parser);
    }
    
    public UnexpectedElement(String fileName, int line, int column) {
        this.setParserInfo(fileName, line, column, line, column);
    }
    
    @Override
    protected boolean getInfoFromNextToken() {
        return true;
    }
}
