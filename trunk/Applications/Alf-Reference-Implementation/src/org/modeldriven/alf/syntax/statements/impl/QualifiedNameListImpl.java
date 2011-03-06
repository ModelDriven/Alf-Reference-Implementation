
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A group of qualified names.
 **/

public class QualifiedNameListImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	private Collection<QualifiedName> name = new ArrayList<QualifiedName>();

	public QualifiedNameListImpl(QualifiedNameList self) {
		super(self);
	}

	public QualifiedNameList getSelf() {
		return (QualifiedNameList) this.self;
	}

	public Collection<QualifiedName> getName() {
		return this.name;
	}

	public void setName(Collection<QualifiedName> name) {
		this.name = name;
	}

	public void addName(QualifiedName name) {
		this.name.add(name);
	}

} // QualifiedNameListImpl
