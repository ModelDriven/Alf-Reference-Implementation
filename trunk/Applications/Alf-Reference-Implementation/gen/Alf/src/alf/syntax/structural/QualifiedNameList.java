
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.structural;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class QualifiedNameList extends Node {

	private ArrayList<QualifiedName> list = new ArrayList<QualifiedName>();

	public void add(QualifiedName qualifiedName) {
		this.list.add(qualifiedName);
	} // add

	public ArrayList<QualifiedName> getList() {
		return this.list;
	} // getList

	public void print(String prefix) {
		super.print(prefix);

		for (QualifiedName name : this.getList()) {
			name.printChild(prefix);
		}
	} // print

} // QualifiedNameList
