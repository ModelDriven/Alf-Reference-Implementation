/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
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
