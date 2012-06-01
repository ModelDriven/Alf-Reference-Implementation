
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

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
