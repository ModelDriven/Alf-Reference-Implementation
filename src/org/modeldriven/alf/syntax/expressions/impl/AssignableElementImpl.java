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
        // TODO Implement full assignability condition.
        if (source == null) {
            return false;
        } else {
            ElementReference sourceType = source.getType();
            ElementReference targetType = this.getType();
            return sourceType == null && targetType == null ||
                    sourceType.getImpl().conformsTo(targetType);
        }
    }



}
