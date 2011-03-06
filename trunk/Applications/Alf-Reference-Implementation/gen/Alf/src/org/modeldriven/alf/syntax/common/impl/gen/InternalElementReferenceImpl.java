
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A direct reference to a UML model element.
 **/

public class InternalElementReferenceImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.ElementReferenceImpl {

	private SyntaxElement element = null;

	public InternalElementReferenceImpl(InternalElementReference self) {
		super(self);
	}

	public InternalElementReference getSelf() {
		return (InternalElementReference) this.self;
	}

	public SyntaxElement getElement() {
		return this.element;
	}

	public void setElement(SyntaxElement element) {
		this.element = element;
	}

} // InternalElementReferenceImpl
