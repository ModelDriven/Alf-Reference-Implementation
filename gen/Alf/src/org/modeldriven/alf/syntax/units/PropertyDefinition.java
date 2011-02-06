
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A typed element definition for a property (attribute or association end).
 **/

public class PropertyDefinition extends TypedElementDefinition implements
		IPropertyDefinition {

	private Boolean isComposite = false;
	private IExpression initializer = null;

	public Boolean getIsComposite() {
		return this.isComposite;
	}

	public void setIsComposite(Boolean isComposite) {
		this.isComposite = isComposite;
	}

	public IExpression getInitializer() {
		return this.initializer;
	}

	public void setInitializer(IExpression initializer) {
		this.initializer = initializer;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isComposite:");
		s.append(this.getIsComposite());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression initializer = this.getInitializer();
		if (initializer != null) {
			initializer.print(prefix + " ");
		}
	}
} // PropertyDefinition
