
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

import java.util.ArrayList;

/**
 * A group of qualified names.
 **/

public class QualifiedNameList extends SyntaxElement implements
		IQualifiedNameList {

	private ArrayList<IQualifiedName> name = new ArrayList<IQualifiedName>();

	public ArrayList<IQualifiedName> getName() {
		return this.name;
	}

	public void setName(ArrayList<IQualifiedName> name) {
		this.name = name;
	}

	public void addName(IQualifiedName name) {
		this.name.add(name);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<IQualifiedName> name = this.getName();
		if (name != null) {
			for (IQualifiedName item : this.getName()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
	}
} // QualifiedNameList
