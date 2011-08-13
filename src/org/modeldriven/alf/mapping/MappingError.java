package org.modeldriven.alf.mapping;

public class MappingError extends Exception {
    
    private static final long serialVersionUID = 7023532129809394745L;
    
    private Mapping mapping = null;
    
    public MappingError(Mapping mapping, String message) {
        super(message);
        this.mapping = mapping;
    }
    
    public Mapping getMapping() {
        return this.mapping;
    }

}
