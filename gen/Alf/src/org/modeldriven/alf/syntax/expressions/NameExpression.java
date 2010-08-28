
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An expression that comprises a name reference.
 **/

public class NameExpression extends Expression {

	private ElementReference enumerationLiteral = null; // DERIVED
	private AssignedSource assignment = null; // DERIVED
	private PropertyAccessExpression propertyAccess = null; // DERIVED
	private QualifiedName name = null;

	public ElementReference getEnumerationLiteral() {
		return this.enumerationLiteral;
	}

	public void setEnumerationLiteral(ElementReference enumerationLiteral) {
		this.enumerationLiteral = enumerationLiteral;
	}

	public AssignedSource getAssignment() {
		return this.assignment;
	}

	public void setAssignment(AssignedSource assignment) {
		this.assignment = assignment;
	}

	public PropertyAccessExpression getPropertyAccess() {
		return this.propertyAccess;
	}

	public void setPropertyAccess(PropertyAccessExpression propertyAccess) {
		this.propertyAccess = propertyAccess;
	}

	public QualifiedName getName() {
		return this.name;
	}

	public void setName(QualifiedName name) {
		this.name = name;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.name != null) {
			this.name.print(prefix + " ");
		}
	}
} // NameExpression
