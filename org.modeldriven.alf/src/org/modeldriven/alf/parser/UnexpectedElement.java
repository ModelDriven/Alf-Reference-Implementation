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
        return false;
    }
}
