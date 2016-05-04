/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.AssignableElement;

public abstract class AssignableElementImpl extends SyntaxElementImpl 
    implements AssignableElement {

    public AssignableElementImpl(SyntaxElement self) {
        super(self);
    }
    
    public boolean isAssignableFrom(ElementReference source) {
        return this.isAssignableFrom(source.getImpl());
    }
    
    public boolean isAssignableFrom(Expression source) {
        return this.isAssignableFrom(source.getImpl());
    }
    
    public boolean isAssignableFrom(AssignableElement source) {
        return isAssignable(this, source);
    }
    
    public boolean isTypeConformantWith(AssignableElement source) {
        return isTypeConformant(this, source);
    }
    
    public boolean isMultiplicityConformantWith(AssignableElement source) {
        return isMultiplicityConformant(this, source);
    }
    
    public boolean isNull() {
        return isNull(this);
    }
    
    public static boolean isAssignable(AssignableElement target, AssignableElement source) {
        return source == null ||
                isTypeConformant(target, source) && 
                isMultiplicityConformant(target, source);
    }
    
    public static boolean isTypeConformant(AssignableElement target, AssignableElement source) {
        ElementReference sourceType = source.getType();
        ElementReference targetType = target.getType();
        int sourceUpper = source.getUpper();
        int targetLower = target.getLower();
        int targetUpper = target.getUpper();
        
        return
            // Null conversion
            isNull(source) && targetLower == 0 ||
            
            // Type conformance
            isConformant(targetType, sourceType) ||
            
            // Collection conversion
            sourceType != null && sourceType.getImpl().isCollectionClass() && 
            sourceUpper == 1 && (targetUpper == -1 || targetUpper > 1) &&
            isConformant(targetType, sourceType.getImpl().getCollectionArgument());
    }
    
    public static boolean isConformant(ElementReference targetType, ElementReference sourceType) {
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
    
    public static boolean isMultiplicityConformant(AssignableElement target, AssignableElement source) {
        int targetUpper = target.getUpper();
        int sourceUpper = source.getUpper();
        return targetUpper == -1 || targetUpper > 1 || 
               sourceUpper != -1 && sourceUpper <= targetUpper;
    }
    
    /**
     * Check whether this element is guaranteed to evaluate to a "null"
     * value, e.g., untyped and with multiplicity 0..0.
     */
    public static boolean isNull(AssignableElement element) {
        return element.getLower() == 0 && element.getUpper() == 0 && 
               element.getType() == null ;
    }

}
