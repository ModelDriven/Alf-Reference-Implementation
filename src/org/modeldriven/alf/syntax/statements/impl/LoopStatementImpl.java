package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.statements.Statement;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

public class LoopStatementImpl extends StatementImpl {
    
    private NamespaceDefinition currentScope = null;

    public LoopStatementImpl(Statement self) {
        super(self);
    }
    
    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        this.currentScope = currentScope;
    }
    
    protected boolean isParameter(String name) {
        QualifiedName qualifiedName = new QualifiedName().getImpl().addName(name);
        qualifiedName.getImpl().setCurrentScope(this.currentScope);
        ElementReference parameter = qualifiedName.getImpl().getParameterReferent();
        return parameter != null && 
                parameter.getImpl().isInNamespace(this.currentScope);
    }

}
