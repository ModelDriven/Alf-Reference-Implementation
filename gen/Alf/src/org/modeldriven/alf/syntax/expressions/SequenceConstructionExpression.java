
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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
 * An expression used to construct a sequence of values.
 **/

public class SequenceConstructionExpression extends Expression implements
		ISequenceConstructionExpression {

	private ISequenceElements elements = null;
	private Boolean hasMultiplicity = false;
	private IQualifiedName typeName = null;

	public ISequenceElements getElements() {
		return this.elements;
	}

	public void setElements(ISequenceElements elements) {
		this.elements = elements;
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
		s.append(" hasMultiplicity:");
		s.append(this.getHasMultiplicity());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ISequenceElements elements = this.getElements();
		if (elements != null) {
			elements.print(prefix + " ");
		}
		IQualifiedName typeName = this.getTypeName();
		if (typeName != null) {
			typeName.print(prefix + " ");
		}
	}
} // SequenceConstructionExpression
