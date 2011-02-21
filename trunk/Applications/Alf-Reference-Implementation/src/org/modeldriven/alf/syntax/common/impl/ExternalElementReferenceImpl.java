
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;
import org.omg.uml.Element;

import java.util.ArrayList;

/**
 * A direct reference to a UML model element.
 **/

public class ExternalElementReferenceImpl extends
		org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl {

	public ExternalElementReferenceImpl(ExternalElementReference self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.common.ExternalElementReference getSelf() {
		return (ExternalElementReference) this.self;
	}

    @Override
    public SyntaxElement getAlf() {
        return null;
    }

    @Override
    public Element getUml() {
        return this.getSelf().getElement();
    }

} // ExternalElementReferenceImpl
