
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A tuple in which the arguments are matched to parameters by name.
 **/

public class NamedTupleImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.TupleImpl {

	private List<NamedExpression> namedExpression = new ArrayList<NamedExpression>();

	public NamedTupleImpl(NamedTuple self) {
		super(self);
	}

	public NamedTuple getSelf() {
		return (NamedTuple) this.self;
	}

	public List<NamedExpression> getNamedExpression() {
		return this.namedExpression;
	}

	public void setNamedExpression(List<NamedExpression> namedExpression) {
		this.namedExpression = namedExpression;
	}

	public void addNamedExpression(NamedExpression namedExpression) {
		this.namedExpression.add(namedExpression);
	}

} // NamedTupleImpl
