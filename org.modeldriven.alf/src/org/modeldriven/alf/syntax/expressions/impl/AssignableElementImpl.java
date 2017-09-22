/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
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
    
    public boolean isAssignableFrom(ElementReference source, boolean isNullable) {
        return isAssignable(this, source.getImpl(), isNullable);
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
    
    public boolean isMultiplicityConformantWith(AssignableElement source, boolean isNullable) {
        return isMultiplicityConformant(this, source, isNullable);
    }
    
    public boolean isNull() {
        return isNull(this);
    }
    
    public static boolean isAssignable(AssignableElement target, AssignableElement source) {
        return isAssignable(target, source, false);
    }
    
    public static boolean isAssignable(AssignableElement target, AssignableElement source, boolean isNullable) {
        return source == null ||
                isTypeConformant(target, source) && 
                isMultiplicityConformant(target, source, isNullable);
    }
    
    public static boolean isTypeConformant(AssignableElement target, AssignableElement source) {
        ElementReference sourceType = source.getType();
        ElementReference targetType = target.getType();
        int sourceUpper = source.getUpper();
        int targetUpper = target.getUpper();
        
        return 
            // Null conversion
            isNull(source) ||
            
            // Type conformance
            isConformant(targetType, sourceType) ||
            
            // Collection conversion
            isCollectionConformant(targetType, targetUpper, sourceType, sourceUpper);
    }
    
    public static boolean isCollectionConformant(ElementReference targetType, int targetUpper, ElementReference sourceType, int sourceUpper) {
        return sourceType.getImpl().isCollectionClass() && 
                sourceUpper == 1 && (targetUpper == -1 || targetUpper > 1) &&
                isConformant(targetType, sourceType.getImpl().getCollectionSequenceType());
 
    }
    
    public static boolean isConformant(ElementReference targetType, ElementReference sourceType) {
        return sourceType == null || targetType == null ||
                
            // Untyped target is conformant with anything.
            targetType.getImpl().isAny() ||
            
            // Untyped source is only assignable to untyped target.
            !sourceType.getImpl().isAny() && (
            
            // Basic type conformance
            sourceType.getImpl().conformsTo(targetType) ||
            
            // Bit string conversion
            sourceType.getImpl().isInteger() && targetType.getImpl().isBitString() ||
            
            // Real conversion
            sourceType.getImpl().isInteger() && targetType.getImpl().isReal());
    }
    
    public static boolean isMultiplicityConformant(AssignableElement target, AssignableElement source) {
        return isMultiplicityConformant(target, source, false);
    }
    
    public static boolean isMultiplicityConformant(AssignableElement target, AssignableElement source, boolean isNullable) {
        if (target.getType() == null || source.getType() == null) {
            return true;
        } else {
            int targetLower = target.getLower();
            int sourceLower = source.getLower();
            int targetUpper = target.getUpper();
            int sourceUpper = source.getUpper();
            return (isNullable || targetLower == 0 || sourceLower > 0) &&
                   (targetUpper == -1 || targetUpper > 1 || 
                    sourceUpper != -1 && sourceUpper <= targetUpper);
        }
    }
    
    /**
     * Check whether this element is guaranteed to evaluate to a "null"
     * value, e.g., untyped and with multiplicity 0..0.
     */
    public static boolean isNull(AssignableElement element) {
        ElementReference type = element.getType();
        return element.getLower() == 0 && element.getUpper() == 0 && 
               type != null && type.getImpl().isAny();
    }

}
