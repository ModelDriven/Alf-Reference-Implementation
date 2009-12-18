
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class TaggedValueList {

	private ArrayList<TaggedValue> list = new ArrayList<TaggedValue>();

	public void add(TaggedValue taggedValue) {
		this.list.add(taggedValue);
	} // add

	public ArrayList<TaggedValue> getList() {
		return this.list;
	} // getList

	public void print(String prefix) {
		System.out.print(prefix);
		for (TaggedValue taggedValue : this.getList()) {
			System.out.print(taggedValue.toString() + " ");
		}
		System.out.println();
	} // print

} // TaggedValueList
