
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A statement that declares the type of a local name and assigns it an initial
 * value.
 **/

public class LocalNameDeclarationStatement extends Statement implements
		ILocalNameDeclarationStatement {

	private String name = "";
	private IExpression expression = null;
	private Boolean hasMultiplicity = false;
	private IQualifiedName typeName = null;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IExpression getExpression() {
		return this.expression;
	}

	public void setExpression(IExpression expression) {
		this.expression = expression;
	}

	public Boolean getHasMultiplicity() {
		return this.hasMultiplicity;
	}

	public void setHasMultiplicity(Boolean hasMultiplicity) {
		this.hasMultiplicity = hasMultiplicity;
	}

	public IQualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(IQualifiedName typeName) {
		this.typeName = typeName;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.getName());
		s.append(" hasMultiplicity:");
		s.append(this.getHasMultiplicity());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression expression = this.getExpression();
		if (expression != null) {
			expression.print(prefix + " ");
		}
		IQualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.print(prefix + " ");
		}
	}
} // LocalNameDeclarationStatement
