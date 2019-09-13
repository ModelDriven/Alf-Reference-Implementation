/*******************************************************************************
 * Copyright (c) 2018, 2019 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.parser;

import java.io.FileNotFoundException;
import java.io.Reader;

public interface ParserFactory {
    
    public Parser createParser(String fileName) throws FileNotFoundException;
    
    public default Parser createParser(String fileName, Reader contents) {
        Parser newParser = createParser(contents);
        newParser.setFileName(fileName);
        return newParser;
    }
    
    public default Parser createParser(Reader contents) {
        throw new UnsupportedOperationException();
    }
    
    public static ParserFactory defaultImplementation() {
        return new ParserFactoryImpl();
    }
}
