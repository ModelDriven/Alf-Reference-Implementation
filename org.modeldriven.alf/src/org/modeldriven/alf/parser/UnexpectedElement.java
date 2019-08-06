package org.modeldriven.alf.parser;

public class UnexpectedElement extends ParsedElement {
    public UnexpectedElement(Parser parser) {
        this.init(parser);
    }
    
    @Override
    protected boolean getInfoFromNextToken() {
        return false;
    }
}
