package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.omg.uml.Parameter;

public class ExternalParameter extends FormalParameter {
    
    public ExternalParameter(Parameter parameter) {
        super();
        
        String direction = parameter.getDirection().toString();
        if (direction.equals("return_")) {
            direction = "return";
        }
        this.setDirection(direction);
        
        ExternalElementReference typeReference = new ExternalElementReference();
        typeReference.setElement(parameter.getType());
        this.setType(typeReference);
        
        this.setName(parameter.getName());
        this.setLower(parameter.getLower());
        this.setUpper(parameter.getUpper());
        this.setIsOrdered(parameter.getIsOrdered());
        this.setIsNonunique(!parameter.getIsUnique());
    }

}
