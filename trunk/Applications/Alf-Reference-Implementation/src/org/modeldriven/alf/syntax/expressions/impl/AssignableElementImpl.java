/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.Expression;

public abstract class AssignableElementImpl extends SyntaxElementImpl {

    public AssignableElementImpl(SyntaxElement self) {
        super(self);
    }
    
    public abstract ElementReference getType();
    public abstract Integer getLower();
    public abstract Integer getUpper();
    
    public boolean isAssignableFrom(Expression source) {
        return this.isAssignableFrom(source.getImpl());
    }
    
    public boolean isAssignableFrom(AssignableElementImpl source) {
        return source == null ||
            this.isTypeConformantWith(source) && 
            this.isMultiplicityConformantWith(source);
    }
    
    public boolean isTypeConformantWith(AssignableElementImpl source) {
        ElementReference sourceType = source.getType();
        int sourceUpper = source.getUpper();
        int targetUpper = this.getUpper();
        
        return
            // Null conversion
            source.isNull() ||
            
            // Type conformance
            this.isTypeConformantWith(sourceType) ||
            
            // Collection conversion
            sourceType != null && sourceType.getImpl().isCollectionClass() && 
            sourceUpper == 1 && (targetUpper == -1 || targetUpper > 1) &&
            this.isTypeConformantWith(sourceType.getImpl().getCollectionArgument());
    }
    
    public boolean isTypeConformantWith(ElementReference sourceType) {
        ElementReference targetType = this.getType();
        return
            // Untyped target is conformant with anything.
            targetType == null ||
            
            // Untyped source is only assignable to untyped target.
            sourceType != null && (
            
            // Basic type conformance
            sourceType.getImpl().conformsTo(targetType) ||
            
            // Bit string conversion
            sourceType.getImpl().isInteger() && targetType.getImpl().isBitString());
    }
    
    public boolean isMultiplicityConformantWith(AssignableElementImpl source) {
        int targetUpper = this.getUpper();
        int sourceUpper = source.getUpper();
        return targetUpper == -1 || targetUpper > 1 || 
               sourceUpper != -1 && sourceUpper <= targetUpper;
    }
    
    /**
     * Check whether this element is guaranteed to evaluate to a "null"
     * value, e.g., untyped and with multiplicity 0..0.
     */
    public boolean isNull() {
        return this.getLower() == 0 && this.getUpper() == 0 && 
               this.getType() == null ;
    }

}
