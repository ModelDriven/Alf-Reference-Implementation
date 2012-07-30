
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * The definition of a data type, whose members must all be properties.
 **/

public class DataTypeDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.ClassifierDefinitionImpl {

	public DataTypeDefinitionImpl(DataTypeDefinition self) {
		super(self);
	}

	public DataTypeDefinition getSelf() {
		return (DataTypeDefinition) this.self;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * If a data type is primitive, then it may not have any owned members.
	 **/
	public boolean dataTypeDefinitionPrimitive() {
	    DataTypeDefinition self = this.getSelf();
		return !self.getIsPrimitive() || this.getSubunitOwnedMembers().size() == 0;
	}

	/**
	 * The specialization referents of a data type definition must all be data
	 * types.
	 **/
	public boolean dataTypeDefinitionSpecializationReferent() {
	    for (ElementReference referent: this.getSelf().getSpecializationReferent()) {
	        if (!referent.getImpl().isDataType()) {
	            return false;
	        }
	    }
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * Returns true if the given unit definition matches this data type
	 * definition considered as a classifier definition and the subunit is for a
	 * data type definition.
	 **/
	@Override
	public Boolean matchForStub(UnitDefinition unit) {
		return unit.getDefinition() instanceof DataTypeDefinition &&
		        super.matchForStub(unit);
	} // matchForStub

	/**
	 * In addition to the annotations allowed for classifiers in general, a data
	 * type definition allows @primitive annotations plus any stereotype whose
	 * metaclass is consistent with DataType.
	 **/
	@Override
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    // TODO: Allow stereotypes consistent with data types.
		return annotation.getStereotypeName().getImpl().equals("primitive") ||
		        super.annotationAllowed(annotation);
	} // annotationAllowed

	/**
	 * Return true if the given member is either a DataTypeDefinition or an
	 * imported member whose referent is a DataTypeDefinition or a DataType.
	 **/
	public Boolean isSameKindAs(Member member) {
		return member.getImpl().getReferent().getImpl().isDataType();
	} // isSameKindAs

} // DataTypeDefinitionImpl
