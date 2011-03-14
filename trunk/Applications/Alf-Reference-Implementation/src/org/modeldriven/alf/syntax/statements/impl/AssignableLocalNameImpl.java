package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.impl.AssignableElementImpl;

public class AssignableLocalNameImpl extends AssignableElementImpl {
    
    private LocalNameDeclarationStatementImpl localNameDeclaration = null;

    public AssignableLocalNameImpl(LocalNameDeclarationStatementImpl localNameDeclaration) {
        super(localNameDeclaration.getSelf());
        this.localNameDeclaration  = localNameDeclaration;
    }

    @Override
    public ElementReference getType() {
        return this.localNameDeclaration.getType();
    }

    @Override
    public Integer getLower() {
        return 0;
    }

    @Override
    public Integer getUpper() {
        return this.localNameDeclaration.getHasMultiplicity()? -1: 1;
    }

}
