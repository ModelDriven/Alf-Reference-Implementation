/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
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
