
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class SequenceConstructionExpression extends Expression {

	private SequenceElements elements = null;
	private boolean hasMultiplicity = false;
	private QualifiedName typeName = null;

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	} // setTypeName

	public QualifiedName getTypeName() {
		return this.typeName;
	} // getTypeName

	public void setHasMultiplicity() {
		this.hasMultiplicity = true;
	} // setHasMultiplicity

	public boolean hasMultiplicity() {
		return this.hasMultiplicity;
	} // hasMultiplicity

	public void setElements(SequenceElements elements) {
		this.elements = elements;
	} // setElements

	public SequenceElements getElements() {
		return this.elements;
	} // getElements

	public String toString() {
		QualifiedName typeName = this.getTypeName();
		String s = super.toString();

		if (this.getElements() == null) {
			s = s + " null";
		} else if (typeName != null) {
			s = s + " type:" + typeName + (this.hasMultiplicity() ? "[]" : "");
		}

		return s;
	} // toString

	public void print(String prefix) {
		super.print(prefix);

		if (this.getElements() != null) {
			this.getElements().printChild(prefix);
		}
	} // print

} // SequenceConstructionExpression
