
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

	public QualifiedNameList copy() {
		QualifiedNameList copy = new QualifiedNameList();

		for (QualifiedName name : this.getList()) {
			copy.add(name);
		}

		return copy;
	} // copy

	public boolean equals(QualifiedNameList other, NamespaceDefinition context) {
		if (other == null) {
			return false;
		} else {
			ArrayList<QualifiedName> list = (ArrayList<QualifiedName>) this
					.getList().clone();
			ArrayList<QualifiedName> otherList = other.getList();

			Boolean found;
			for (QualifiedName otherName : otherList) {
				found = false;

				Member otherClassifier = otherName.getClassifier(context);
				if (otherClassifier.isError()) {
					return false;
				}

				for (QualifiedName name : list) {

					Member classifier = name.getClassifier(context);
					if (classifier.isError()) {
						return false;
					}

					if (classifier == otherClassifier) {
						list.remove(name);
						found = true;
						break;
					}
				}

				if (!found) {
					return false;
				}
			}

			return true;
		}
	} // equals

} // QualifiedNameList
