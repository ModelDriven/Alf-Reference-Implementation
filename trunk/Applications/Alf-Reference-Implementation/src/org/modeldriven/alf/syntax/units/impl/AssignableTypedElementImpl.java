package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.impl.AssignableElementImpl;

public class AssignableTypedElementImpl extends AssignableElementImpl {
    
    private TypedElementDefinitionImpl typedElement = null;

    public AssignableTypedElementImpl(TypedElementDefinitionImpl typedElement) {
        super(typedElement.getSelf());
        this.typedElement  = typedElement;
    }

    @Override
    public ElementReference getType() {
        return this.typedElement.getType();
    }

    @Override
    public Integer getLower() {
        return this.typedElement.getLower();
    }

    @Override
    public Integer getUpper() {
        return this.typedElement.getUpper();
    }

}
