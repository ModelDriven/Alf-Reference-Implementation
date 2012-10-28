
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * An expression used to construct a sequence of values.
 **/

public class SequenceConstructionExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	private SequenceElements elements = null;
	private Boolean hasMultiplicity = false;
	private QualifiedName typeName = null;

	public SequenceConstructionExpressionImpl(
			SequenceConstructionExpression self) {
		super(self);
	}

	public SequenceConstructionExpression getSelf() {
		return (SequenceConstructionExpression) this.self;
	}

	public SequenceElements getElements() {
		return this.elements;
	}

	public void setElements(SequenceElements elements) {
		this.elements = elements;
	}

	public Boolean getHasMultiplicity() {
		return this.hasMultiplicity;
	}

	public void setHasMultiplicity(Boolean hasMultiplicity) {
		this.hasMultiplicity = hasMultiplicity;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	/**
	 * If the type name of a sequence construction expression is not empty, then
	 * the type of the expression is the classifier to which the type name
	 * resolves.
	 **/
	public boolean sequenceConstructionExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * If a sequence construction expression has multiplicity, then its
	 * multiplicity upper bound is given by its elements, if this is not empty,
	 * and zero otherwise. If a sequence construction expression does not have
	 * multiplicity, then its multiplicity upper bound is one.
	 **/
	public boolean sequenceConstructionExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * If a sequence construction expression has multiplicity, then its
	 * multiplicity lower bound is given by its elements, if this is not empty,
	 * and zero otherwise. If a sequence construction expression does not have
	 * multiplicity, then its multiplicity lower bound is one.
	 **/
	public boolean sequenceConstructionExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * If the type name of a sequence construction expression is not empty, then
	 * it must resolve to a non-template classifier. If the expression does not
	 * have multiplicity, then the type name must not be empty and the
	 * classifier to which it resolves must be the instantiation of a collection
	 * class.
	 **/
	public boolean sequenceConstructionExpressionType() {
		return true;
	}

	/**
	 * If the elements of a sequence construction expression are given by a
	 * sequence range, then the expression must have type Integer. If the
	 * elements are given by a sequence element list, and the sequence
	 * construction expression has a non-empty type, then each expression in the
	 * list must have a type that either conforms to the type of the sequence
	 * construction expression or is convertible to it by bit string conversion.
	 **/
	public boolean sequenceConstructionExpressionElementType() {
		return true;
	}

	/**
	 * If the elements of a sequence construction expression are given by a
	 * sequence expression list, then the assignments before the first
	 * expression in the list are the same as the assignments before the
	 * sequence construction expression, and the assignments before each
	 * subsequent expression are the assignments after the previous expression.
	 * If the elements are given by a sequence range, the assignments before
	 * both expressions in the range are the same as the assignments before the
	 * sequence construction expression.
	 **/
	public boolean sequenceConstructionExpressionAssignmentsBefore() {
		return true;
	}

	/**
	 * If the elements of the sequence construction expression are given by a
	 * sequence expression list, then return the assignments after the last
	 * expression in the list (if the list is empty, then return the assignments
	 * before the sequence construction expression). If the elements are given
	 * by a sequence range, then return the union of the assignments after each
	 * of the expressions in the range.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // SequenceConstructionExpressionImpl
