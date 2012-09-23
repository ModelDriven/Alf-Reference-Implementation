/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.uml.Parameter;

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
