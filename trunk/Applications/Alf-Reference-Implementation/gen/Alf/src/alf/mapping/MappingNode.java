
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Comment;

public abstract class MappingNode extends Node {

	private MappingFactory factory = null;
	private ErrorNode error = null;

	public void setFactory(MappingFactory factory) {
		this.factory = factory;
	} // setFactory

	public MappingFactory getFactory() {
		return this.factory;
	} // getFactory

	public void setError(ErrorNode error) {
		this.error = error;
	} // setError

	public ErrorNode getError() {
		return this.error;
	} // getError

	public SyntaxNode getSyntaxNode() {
		return (SyntaxNode) this.getSource();
	} // getSyntaxNode

	public abstract ArrayList<Element> getModelElements();

	public MappingNode map(SyntaxNode syntaxNode) {
		return this.factory.getMapping(syntaxNode);
	} // map

	public void mapTo(Element element) {
		System.out.print("Mapping " + this);

		if (element == null) {
			System.out.println("");
		} else {
			System.out.println(" to: " + element.getClass().getName());
		}

	} // mapTo

	public String toString() {
		return super.toString() + " source: " + this.getSource();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		ErrorNode error = this.getError();
		if (error != null) {
			error.printChild(prefix);
		}
		ArrayList<Element> elements = this.getModelElements();
		for (Element element : elements) {
			System.out.println(element.getClass().getName());
		}
	} // print

	public boolean isError() {
		return this.getError() != null;
	} // isError

} // MappingNode
