
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;

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
	
} // QualifiedNameListImpl
