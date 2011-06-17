package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.units.RootNamespace;

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
        if (source == null) {
            return false;
        }

        // Null conversion
        if (source.isNull()) {
            return true;
        } 

        ElementReference sourceType = source.getType();
        ElementReference targetType = this.getType();

        int targetUpper = this.getUpper();
        int sourceUpper = source.getUpper();
        boolean upperBoundsConform = targetUpper == -1 || targetUpper > 1 || 
                    sourceUpper != -1 && sourceUpper <= targetUpper;
        
        // Conformance
        if (targetType == null) {
            return upperBoundsConform;
        }
        if (sourceType == null) {
            return false; // Untyped source is only assignable to untyped target.
        }
        if (sourceType.getImpl().conformsTo(targetType)) {
            return upperBoundsConform;
        }
        
        // Bit string conversion
        if (sourceType.getImpl().conformsTo(RootNamespace.getIntegerType()) &&
                targetType.getImpl().isBitString()) {
            return upperBoundsConform;
        }
        
        // Collection conversion
        if (sourceType.getImpl().isCollectionClass() && source.getUpper() == 1) {
            final ElementReference collectionType = 
                sourceType.getImpl().getCollectionArgument();
             AssignableElementImpl effectiveSource = new AssignableElementImpl(null) {
                public String toString(boolean includeDerived) {
                    return "AssignableElementImpl";
                }
                public ElementReference getType() {
                    return collectionType;
                }
                public Integer getLower() {
                    return 0;
                }
                public Integer getUpper() {
                    return -1;
                }
            };
            return this.isAssignableFrom(effectiveSource);
        }
        
        return false;
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
