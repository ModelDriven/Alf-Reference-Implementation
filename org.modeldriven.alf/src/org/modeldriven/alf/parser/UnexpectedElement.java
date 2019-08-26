package org.modeldriven.alf.parser;

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
