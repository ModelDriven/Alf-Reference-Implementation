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
import org.modeldriven.alf.syntax.units.impl.AssociationDefinitionImpl;

/**
 * The definition of an association, whose members must all be properties.
 **/

public class AssociationDefinition extends ClassifierDefinition {

	public AssociationDefinition() {
		this.impl = new AssociationDefinitionImpl(this);
	}

	public AssociationDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public AssociationDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	@Override
    public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * association definition allows an annotation for any stereotype whose
	 * metaclass is consistent with Association.
	 **/
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either an AssociationDefinition or an
	 * imported member whose referent is an AssociationDefinition or an
	 * Association.
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
		if (!this.associationDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"associationDefinitionSpecializationReferent", this));
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
} // AssociationDefinition
