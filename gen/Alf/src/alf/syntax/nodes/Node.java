
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.nodes;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public abstract class Node {

	public String toString() {
		String className = this.getClass().getName();
		int i = className.lastIndexOf(".");
		return className.substring(i + 1);
	} // toString

	public String toString(String prefix) {
		return prefix + this.toString();
	} // toString

	public void printChild(String prefix) {
		if (prefix == null || prefix.length() == 0) {
			this.print(" ");
		} else {
			this.print(prefix.charAt(0) + prefix);
		}
	} // printChild

	public void print(String prefix) {
		System.out.println(this.toString(prefix));
	} // print

	public void print() {
		this.print("");
	} // print

} // Node
