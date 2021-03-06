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
import org.modeldriven.alf.syntax.units.impl.ClassDefinitionImpl;

/**
 * The definition of a class, whose members may be properties, operations,
 * signals or signal receptions.
 **/

public class ClassDefinition extends ClassifierDefinition {

	public ClassDefinition() {
		this.impl = new ClassDefinitionImpl(this);
	}

	public ClassDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public ClassDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public ClassDefinitionImpl getImpl() {
		return (ClassDefinitionImpl) this.impl;
	}

	/**
	 * The specialization referents of a class definition must all be classes. A
	 * class definition may not have any referents that are active classes
	 * unless this is an active class definition.
	 **/
	public boolean classDefinitionSpecializationReferent() {
		return this.getImpl().classDefinitionSpecializationReferent();
	}

	/**
	 * If a class definition is not abstract, then no member operations (owned
	 * or inherited) of the class definition may be abstract.
	 **/
	public boolean classDefinitionAbstractMember() {
		return this.getImpl().classDefinitionAbstractMember();
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, a
	 * class definition allows an annotation for any stereotype whose metaclass
	 * is consistent with Class.
	 **/
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Returns true if the given unit definition matches this class definition
	 * considered as a classifier definition and the subunit is for a class
	 * definition.
	 **/
	@Override
    public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * Return true if the given member is either a ClassDefinition or an
	 * imported member whose referent is a ClassDefinition or a Class.
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
		if (!this.classDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"classDefinitionSpecializationReferent", this));
		}
		if (!this.classDefinitionAbstractMember()) {
			violations.add(new ConstraintViolation(
					"classDefinitionAbstractMember", this));
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
} // ClassDefinition
