
/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.statements.impl.QualifiedNameListImpl;

/**
 * A group of qualified names.
 **/

public class QualifiedNameList extends SyntaxElement {

	public QualifiedNameList() {
		this.impl = new QualifiedNameListImpl(this);
	}

	public QualifiedNameList(Parser parser) {
		this();
		this.init(parser);
	}

	public QualifiedNameList(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public QualifiedNameListImpl getImpl() {
		return (QualifiedNameListImpl) this.impl;
	}

	public Collection<QualifiedName> getName() {
		return this.getImpl().getName();
	}

	public void setName(Collection<QualifiedName> name) {
		this.getImpl().setName(name);
	}

	public void addName(QualifiedName name) {
		this.getImpl().addName(name);
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getName());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Collection<QualifiedName> name = this.getName();
		if (name != null) {
			for (Object _name : name.toArray()) {
				((QualifiedName) _name).deriveAll();
			}
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		Collection<QualifiedName> name = this.getName();
		if (name != null) {
			for (Object _name : name.toArray()) {
				((QualifiedName) _name).checkConstraints(violations);
			}
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Collection<QualifiedName> name = this.getName();
		if (name != null && name.size() > 0) {
			System.out.println(prefix + " name:");
			for (Object _object : name.toArray()) {
				QualifiedName _name = (QualifiedName) _object;
				if (_name != null) {
					_name.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // QualifiedNameList
