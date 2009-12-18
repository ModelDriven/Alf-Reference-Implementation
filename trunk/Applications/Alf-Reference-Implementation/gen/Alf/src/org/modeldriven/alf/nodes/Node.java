
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.nodes;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public abstract class Node {

	private Node targetNode = null;
	private Node sourceNode = null;

	public void setTargetNode(Node target) {
		this.targetNode = target;
	} // setTargetNode

	public Node getTargetNode() {
		return this.targetNode;
	} // getTargetNode

	public void setSourceNode(Node source) {
		this.sourceNode = source;
		source.setTargetNode(this);
	} // setSourceNode

	public Node getSourceNode() {
		return this.sourceNode;
	} // getSourceNode

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
