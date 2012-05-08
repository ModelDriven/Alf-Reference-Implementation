
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

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

import org.modeldriven.alf.syntax.units.impl.AssociationDefinitionImpl;

/**
 * The definition of an association, whose members must all be properties.
 **/

public class AssociationDefinition extends ClassifierDefinition {

	public AssociationDefinition() {
		this.impl = new AssociationDefinitionImpl(this);
	}

	public AssociationDefinition(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public AssociationDefinition(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public AssociationDefinitionImpl getImpl() {
		return (AssociationDefinitionImpl) this.impl;
	}

	/**
	 * The specialization referents of an association definition must all be
	 * associations.
	 **/
	public boolean associationDefinitionSpecializationReferent() {
		return this.getImpl().associationDefinitionSpecializationReferent();
	}

	/**
	 * Returns true if the given unit definition matches this association
	 * definition considered as a classifier definition and the subunit is for
	 * an association definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * association definition allows an annotation for any stereotype whose
	 * metaclass is consistent with Association.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either an AssociationDefinition or an
	 * imported member whose referent is an AssociationDefinition or an
	 * Association.
	 **/
	public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.associationDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"associationDefinitionSpecializationReferent", this));
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
	}
} // AssociationDefinition
