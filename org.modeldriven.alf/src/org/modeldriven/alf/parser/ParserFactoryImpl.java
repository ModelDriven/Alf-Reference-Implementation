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

public class ParserFactoryImpl implements ParserFactory {

    @Override
    public Parser createParser(String fileName) throws FileNotFoundException {
        ParserImpl parserImpl = new ParserImpl(fileName);
        parserImpl.installCustomTokenManager(parserImpl::collectLexicalError);
        return parserImpl;
    }
    
    @Override
    public Parser createParser(String fileName, Reader contents) {
        ParserImpl newParser = new ParserImpl(contents);
        newParser.setFileName(fileName);
        newParser.installCustomTokenManager(newParser::collectLexicalError);
        return newParser;
    }

    @Override
    public Parser createParser(Reader contents){
        ParserImpl parserImpl = new ParserImpl(contents);
        parserImpl.installCustomTokenManager(parserImpl::collectLexicalError);
        return parserImpl;
    }
    
}
