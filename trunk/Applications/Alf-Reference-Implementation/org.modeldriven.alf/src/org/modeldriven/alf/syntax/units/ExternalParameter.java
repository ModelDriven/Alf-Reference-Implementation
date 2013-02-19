/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.uml.Parameter;
import org.modeldriven.alf.uml.Type;

public class ExternalParameter extends FormalParameter {
    
    private Parameter parameter;
    
    public ExternalParameter(Parameter parameter) {
        super();
        this.parameter = parameter;
        
        this.setDirection(parameter.getDirection());
        
        Type type = parameter.getType();
        if (type != null) {
            this.setType(ElementReferenceImpl.makeElementReference(type));
        }
        
        this.setName(parameter.getName());
        this.setLower(parameter.getLower());
        this.setUpper(parameter.getUpper());
        this.setIsOrdered(parameter.getIsOrdered());
        this.setIsNonunique(!parameter.getIsUnique());
    }
    
    public boolean equals(Object other) {
        return other instanceof ExternalParameter && 
                ((ExternalParameter)other).parameter.equals(this.parameter);
    }

}
