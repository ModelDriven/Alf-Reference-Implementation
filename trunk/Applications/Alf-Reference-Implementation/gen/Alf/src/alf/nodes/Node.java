
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.nodes;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public abstract class Node {

	private Node target = null;
	private Node source = null;

	public void setTarget(Node target) {
		this.target = target;
	} // setTarget

	public Node getTarget() {
		return this.target;
	} // getTarget

	public void setSource(Node source) {
		this.source = source;
	} // setSource

	public Node getSource() {
		return this.source;
	} // getSource

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

	public boolean isError() {
		return false;
	} // isError

	public String processName(String name) {
		if (name.charAt(0) == '\'') {
			return this.replaceEscapes(name.substring(1, name.length() - 1));
		} else {
			return name;
		}
	} // processName

	public String replaceEscapes(String original) {
		String s = new String(original);

		int i = s.indexOf("\\");

		while (i > -1 && i < s.length() - 1) {

			char escape = s.charAt(i + 1);
			String replacement;

			if (escape == 'b') {
				replacement = "\b";
			} else if (escape == 'f') {
				replacement = "\f";
			} else if (escape == 'n') {
				replacement = "\n";
			} else if (escape == 't') {
				replacement = "\t";
			} else {
				replacement = Character.toString(escape);
			}

			s = s.substring(0, i) + replacement
					+ s.substring(i + 1, s.length());
			i = s.indexOf("\\", i + 1);

		}

		return s;
	} // replaceEscapes

} // Node
