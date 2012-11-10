
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.Parser;
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

import org.modeldriven.alf.syntax.units.impl.DataTypeDefinitionImpl;

/**
 * The definition of a data type, whose members must all be properties.
 **/

public class DataTypeDefinition extends ClassifierDefinition {

	public DataTypeDefinition() {
		this.impl = new DataTypeDefinitionImpl(this);
	}

	public DataTypeDefinition(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public DataTypeDefinition(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public DataTypeDefinitionImpl getImpl() {
		return (DataTypeDefinitionImpl) this.impl;
	}

	/**
	 * If a data type is primitive, then it may not have any owned members.
	 **/
	public boolean dataTypeDefinitionPrimitive() {
		return this.getImpl().dataTypeDefinitionPrimitive();
	}

	/**
	 * The specialization referents of a data type definition must all be data
	 * types.
	 **/
	public boolean dataTypeDefinitionSpecializationReferent() {
		return this.getImpl().dataTypeDefinitionSpecializationReferent();
	}

	/**
	 * Returns true if the given unit definition matches this data type
	 * definition considered as a classifier definition and the subunit is for a
	 * data type definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, a data
	 * type definition allows @primitive annotations plus any stereotype whose
	 * metaclass is consistent with DataType.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either a DataTypeDefinition or an
	 * imported member whose referent is a DataTypeDefinition or a DataType.
	 **/
	public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.dataTypeDefinitionPrimitive()) {
			violations.add(new ConstraintViolation(
					"dataTypeDefinitionPrimitive", this));
		}
		if (!this.dataTypeDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"dataTypeDefinitionSpecializationReferent", this));
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
} // DataTypeDefinition
