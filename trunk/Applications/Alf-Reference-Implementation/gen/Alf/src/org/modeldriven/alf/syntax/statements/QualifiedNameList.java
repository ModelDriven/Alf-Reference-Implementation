
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.QualifiedNameListImpl;

/**
 * A group of qualified names.
 **/

public class QualifiedNameList extends SyntaxElement {

	public QualifiedNameList() {
		this.impl = new QualifiedNameListImpl(this);
	}

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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		for (Object _name : this.getName().toArray()) {
			((QualifiedName) _name).checkConstraints(violations);
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Collection<QualifiedName> name = this.getName();
		if (name != null) {
			if (name.size() > 0) {
				System.out.println(prefix + " name:");
			}
			for (Object _object : name.toArray()) {
				QualifiedName _name = (QualifiedName) _object;
				if (_name != null) {
					_name.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // QualifiedNameList
