
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * An identified modification to the behavior of an annotated statement.
 **/

public class AnnotationImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	public AnnotationImpl(Annotation self) {
		super(self);
	}

	public Annotation getSelf() {
		return (Annotation) this.self;
	}

} // AnnotationImpl
