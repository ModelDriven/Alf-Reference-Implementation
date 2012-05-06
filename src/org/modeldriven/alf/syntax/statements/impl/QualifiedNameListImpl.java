
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A group of qualified names.
 **/

public class QualifiedNameListImpl extends SyntaxElementImpl {

	private Collection<QualifiedName> name = new ArrayList<QualifiedName>();

	public QualifiedNameListImpl(QualifiedNameList self) {
		super(self);
	}

	public QualifiedNameList getSelf() {
		return (QualifiedNameList) this.self;
	}

	public Collection<QualifiedName> getName() {
		return this.name;
	}

	public void setName(Collection<QualifiedName> name) {
		this.name = name;
	}

	public void addName(QualifiedName name) {
		this.name.add(name);
	}

	/*
	 * Helper Methods
	 */
	
	public Collection<ElementReference> getNonTemplateClassifierReferents() {
        ArrayList<ElementReference> referents = new ArrayList<ElementReference>();
        for (QualifiedName qualifiedName: this.getSelf().getName()) {
            ElementReference referent = qualifiedName.getImpl().getNonTemplateClassifierReferent();
            if (referent != null) {
                referents.add(referent);
            }
        }
        return referents;
	}

    public void setCurrentScope(NamespaceDefinition currentScope) {
        for (QualifiedName name: this.getSelf().getName()) {
            name.getImpl().setCurrentScope(currentScope);
        }
    }
	
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof QualifiedNameList) {
            QualifiedNameList self = this.getSelf();
            for (QualifiedName name: ((QualifiedNameList)base).getName()) {
                self.addName(name.getImpl().
                        updateForBinding(templateParameters, templateArguments));
            }
        }
    }
    
} // QualifiedNameListImpl
