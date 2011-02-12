
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

import java.util.ArrayList;

/**
 * A syntax element that has documentation comments associated with it.
 **/

public abstract class DocumentedElementImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	public DocumentedElementImpl(DocumentedElement self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.common.DocumentedElement getSelf() {
		return (DocumentedElement) this.self;
	}

} // DocumentedElementImpl
