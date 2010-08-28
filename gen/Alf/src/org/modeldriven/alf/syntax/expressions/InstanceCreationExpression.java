
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
 * An expression used to create a new instance of a class or data type.
 **/

public class InstanceCreationExpression extends InvocationExpression {

	private boolean isConstructorless = false; // DERIVED
	private boolean isObjectCreation = false; // DERIVED
	private QualifiedName constructor = null;

	public boolean getIsConstructorless() {
		return this.isConstructorless;
	}

	public void setIsConstructorless(boolean isConstructorless) {
		this.isConstructorless = isConstructorless;
	}

	public boolean getIsObjectCreation() {
		return this.isObjectCreation;
	}

	public void setIsObjectCreation(boolean isObjectCreation) {
		this.isObjectCreation = isObjectCreation;
	}

	public QualifiedName getConstructor() {
		return this.constructor;
	}

	public void setConstructor(QualifiedName constructor) {
		this.constructor = constructor;
	}

	public ArrayList<ElementReference> parameterElements() {
		/*
		 * Returns the parameters of a constructor operation or the attributes
		 * of a data type, or an empty set for a constructorless instance
		 * creation.
		 */
		return new ArrayList<ElementReference>(); // STUB
	} // parameterElements

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.constructor != null) {
			this.constructor.print(prefix + " ");
		}
	}
} // InstanceCreationExpression
