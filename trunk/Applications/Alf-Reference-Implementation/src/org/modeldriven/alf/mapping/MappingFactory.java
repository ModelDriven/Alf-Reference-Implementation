package org.modeldriven.alf.mapping;

import org.modeldriven.alf.syntax.common.SyntaxElement;

public abstract class MappingFactory {
    
    public Mapping getMapping(Object source) {
        Mapping mapping = !(source instanceof SyntaxElement)? null:
            ((SyntaxElement)source).getImpl().getMapping();
        if (mapping == null) {
            mapping = this.instantiateMapping(source);
            mapping.setSource(source);
        }
        return mapping;
    }
    
    public abstract Mapping instantiateMapping(Object source);

}
