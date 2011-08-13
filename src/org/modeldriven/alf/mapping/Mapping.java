package org.modeldriven.alf.mapping;

import org.modeldriven.alf.syntax.common.SyntaxElement;

public abstract class Mapping {
    
    private MappingFactory factory = null;    
    private String errorMessage = null;
    private Object source = null;
    
    public MappingFactory getFactory() {
        return factory;
    }
    
    public void setFactory(MappingFactory factory) {
        this.factory = factory;
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public Object getSource() {
        return source;
    }
    
    public void setSource(Object source) {
        this.source = source;
        if (source instanceof SyntaxElement) {
            ((SyntaxElement)source).getImpl().setMapping(this);
        }
    }
    
    public Mapping map(Object source) {
        return this.getFactory().getMapping(source);
    }
    
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public void print() {
        this.print("");
    }
    
    public void print(String prefix) {
        System.out.println(prefix + this.toString());
        System.out.println(prefix + " source: " + this.getSource());
        String errorMessage = this.getErrorMessage();
        if (errorMessage != null) {
            System.out.println(prefix + " error: " + errorMessage);
        }
    }

    public void printChild(String prefix) {
        assert prefix != null;
        this.print(prefix + "  ");
    }
}
