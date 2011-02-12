
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A group of qualified names.
 **/

public class QualifiedNameListImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	public QualifiedNameListImpl(QualifiedNameList self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.QualifiedNameList getSelf() {
		return (QualifiedNameList) this.self;
	}

} // QualifiedNameListImpl
