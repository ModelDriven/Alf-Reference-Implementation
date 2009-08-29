
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

public class AlternativeConstructorInvocation extends Statement {

	private String constructor = "";
	private Tuple tuple = null;

	public AlternativeConstructorInvocation(String constructor, Tuple tuple,
			String documentation) {
		this.constructor = constructor;
		this.tuple = tuple;
		this.addDocumentation(documentation);
	} // AlternativeConstructorInvocation

	public String getConstructor() {
		return this.constructor;
	} // getConstructor

	public Tuple getTuple() {
		return this.tuple;
	} // getTuple

	public String toString() {
		return super.toString() + " constructor:" + this.getConstructor();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getTuple().printChild(prefix);
	} // print

} // AlternativeConstructorInvocation
