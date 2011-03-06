
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
 * A syntax element synthesized in an abstract syntax tree, along with any
 * additional information determined during static semantic analysis.
 **/

public abstract class SyntaxElementImpl {

	protected SyntaxElement self;

	public SyntaxElementImpl(SyntaxElement self) {
		this.self = self;
	}

	public SyntaxElement getSelf() {
		return (SyntaxElement) this.self;
	}

} // SyntaxElementImpl
