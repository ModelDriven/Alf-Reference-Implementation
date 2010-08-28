
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
 * An expression used to construct a sequence of values.
 **/

public class SequenceConstructionExpression extends Expression {

	private SequenceElements elements = null;
	private boolean hasMultiplicity = false;
	private QualifiedName typeName = null;

	public SequenceElements getElements() {
		return this.elements;
	}

	public void setElements(SequenceElements elements) {
		this.elements = elements;
	}

	public boolean getHasMultiplicity() {
		return this.hasMultiplicity;
	}

	public void setHasMultiplicity(boolean hasMultiplicity) {
		this.hasMultiplicity = hasMultiplicity;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" hasMultiplicity:");
		s.append(this.hasMultiplicity);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.elements != null) {
			this.elements.print(prefix + " ");
		}
		if (this.typeName != null) {
			this.typeName.print(prefix + " ");
		}
	}
} // SequenceConstructionExpression
