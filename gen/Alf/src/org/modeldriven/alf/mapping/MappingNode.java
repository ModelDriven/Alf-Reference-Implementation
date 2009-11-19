
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

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
		error.print();
	} // setError

	public ErrorNode getError() {
		return this.error;
	} // getError

	public SyntaxNode getSyntaxNode() {
		return (SyntaxNode) this.getSourceNode();
	} // getSyntaxNode

	public abstract ArrayList<Element> getModelElements();

	public MappingNode map(SyntaxNode syntaxNode) {
		return this.factory.getMapping(syntaxNode);
	} // map

	public void mapTo(Element element) {
		/*
		 * System.out.print(this);
		 * 
		 * if (element == null) { System.out.println(""); } else {
		 * System.out.println(" to: " + element.getClass().getName()); }
		 */
	} // mapTo

	public String toString() {
		return super.toString() + " source: " + this.getSourceNode();
	} // toString

	public void print(String prefix) {
		ArrayList<Element> elements = this.getModelElements();

		System.out.println("Mapping complete.");
		super.print(prefix);
		if (this.isError()) {
			this.getError().printChild(prefix);
		}
		for (Element element : elements) {
			System.out.println(element.getClass().getName());
		}
	} // print

	public boolean isError() {
		return this.getError() != null;
	} // isError

} // MappingNode
