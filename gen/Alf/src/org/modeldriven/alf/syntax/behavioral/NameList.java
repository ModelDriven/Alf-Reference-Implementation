
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.behavioral;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public class NameList extends SyntaxNode {

	private ArrayList<String> list = new ArrayList<String>();

	public void add(String name) {
		this.list.add(name);
	} // add

	public ArrayList<String> getList() {
		return this.list;
	} // getList

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());

		for (String name : this.getList()) {
			s.append(" ");
			s.append(name);
		}

		return s.toString();
	} // toString

} // NameList
