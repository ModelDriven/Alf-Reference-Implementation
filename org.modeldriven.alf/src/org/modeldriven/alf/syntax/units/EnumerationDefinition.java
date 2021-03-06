/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.EnumerationDefinitionImpl;

/**
 * The definition of an enumeration, whose members must all be enumeration
 * literal names.
 **/

public class EnumerationDefinition extends ClassifierDefinition {

	public EnumerationDefinition() {
		this.impl = new EnumerationDefinitionImpl(this);
	}

	public EnumerationDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public EnumerationDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public EnumerationDefinitionImpl getImpl() {
		return (EnumerationDefinitionImpl) this.impl;
	}

	/**
	 * The specialization referents of a class definition must all be classes. A
	 * class definition may not have any referents that are active classes
	 * unless this is an active class definition.
	 **/
	public boolean enumerationDefinitionSpecializationReferent() {
		return this.getImpl().enumerationDefinitionSpecializationReferent();
	}

	/**
	 * Returns true if the given unit definition matches this enumeration
	 * definition considered as a classifier definition and the subunit is for
	 * an enumeration definition.
	 **/
	@Override
    public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * enumeration definition allows an annotation for any stereotype whose
	 * metaclass is consistent with Enumeration.
	 **/
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either an EnumerationDefinition or an
	 * imported member whose referent is an EnumerationDefinition or an
	 * Enumeration.
	 **/
	@Override
    public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.enumerationDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"enumerationDefinitionSpecializationReferent", this));
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
	}
} // EnumerationDefinition
