
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.statements.impl.QualifiedNameListImpl;

/**
 * A group of qualified names.
 **/

public class QualifiedNameList extends SyntaxElement {

	private ArrayList<QualifiedName> name = new ArrayList<QualifiedName>();

	public QualifiedNameList() {
		this.impl = new QualifiedNameListImpl(this);
	}

	public QualifiedNameListImpl getImpl() {
		return (QualifiedNameListImpl) this.impl;
	}

	public ArrayList<QualifiedName> getName() {
		return this.name;
	}

	public void setName(ArrayList<QualifiedName> name) {
		this.name = name;
	}

	public void addName(QualifiedName name) {
		this.name.add(name);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<QualifiedName> name = this.getName();
		if (name != null) {
			if (name.size() > 0) {
				System.out.println(prefix + " name:");
			}
			for (QualifiedName _name : (ArrayList<QualifiedName>) name.clone()) {
				if (_name != null) {
					_name.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // QualifiedNameList
