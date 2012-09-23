
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
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

import org.modeldriven.uml.Element;
import org.modeldriven.uml.Profile;
import org.modeldriven.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An expression used to obtain the objects in the extent of a class.
 **/

public class ClassExtentExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	private QualifiedName className = null;

	public ClassExtentExpressionImpl(ClassExtentExpression self) {
		super(self);
	}

	public ClassExtentExpression getSelf() {
		return (ClassExtentExpression) this.self;
	}

	public QualifiedName getClassName() {
		return this.className;
	}

	public void setClassName(QualifiedName className) {
		this.className = className;
	}

	/**
	 * The type of a class extent expression is the given class.
	 **/
	public boolean classExtentExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * The multiplicity upper bound of a class expression is *.
	 **/
	public boolean classExtentExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The multiplicity lower bound of a class extent expression is 0.
	 **/
	public boolean classExtentExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * The given type name must resolve to a non-template class.
	 **/
	public boolean classExtentExpressionExtentType() {
		return true;
	}

} // ClassExtentExpressionImpl
