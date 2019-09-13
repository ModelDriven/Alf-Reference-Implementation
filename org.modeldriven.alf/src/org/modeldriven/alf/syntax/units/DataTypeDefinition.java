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
		this.init(parser);
	}

	public DataTypeDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	@Override
    public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, a data
	 * type definition allows @primitive annotations plus any stereotype whose
	 * metaclass is consistent with DataType.
	 **/
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either a DataTypeDefinition or an
	 * imported member whose referent is a DataTypeDefinition or a DataType.
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
		if (!this.dataTypeDefinitionPrimitive()) {
			violations.add(new ConstraintViolation(
					"dataTypeDefinitionPrimitive", this));
		}
		if (!this.dataTypeDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"dataTypeDefinitionSpecializationReferent", this));
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
} // DataTypeDefinition
