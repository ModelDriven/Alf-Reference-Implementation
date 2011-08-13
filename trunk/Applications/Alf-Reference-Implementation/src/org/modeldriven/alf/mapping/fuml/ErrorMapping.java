package org.modeldriven.alf.mapping.fuml;

import java.util.List;

import org.modeldriven.alf.mapping.MappingError;

import fUML.Syntax.Classes.Kernel.Element;

public class ErrorMapping extends FumlMapping {

    public ErrorMapping(Object source, String errorMessage) {
        this.setSource(source);
        this.setErrorMessage(errorMessage);
    }

    @Override
    public Element getElement() {
        return null;
    }
    
    @Override
    public List<Element> getModelElements() throws MappingError {
        throw new MappingError(this, this.getErrorMessage());
    }

}
