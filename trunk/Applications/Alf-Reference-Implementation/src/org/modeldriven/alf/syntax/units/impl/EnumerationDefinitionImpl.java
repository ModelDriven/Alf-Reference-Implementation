
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * The definition of an enumeration, whose members must all be enumeration
 * literal names.
 **/

public class EnumerationDefinitionImpl extends ClassifierDefinitionImpl {

	public EnumerationDefinitionImpl(EnumerationDefinition self) {
		super(self);
	}

	@Override
	public EnumerationDefinition getSelf() {
		return (EnumerationDefinition) this.self;
	}
	
	/*
	 * Constraints
	 */

    // TODO: Fix constraint name and definition.
    public boolean classDefinitionSpecializationReferent() {
        return enumerationDefinitionSpecializationReferent();
    }
    
	/**
	 * The specialization referents of a enumeration definition must all be enumerations.
	 **/
	public boolean enumerationDefinitionSpecializationReferent() {
        for (ElementReference referent: this.getSelf().getSpecializationReferent()) {
            if (!referent.getImpl().isEnumeration()) {
                return false;
            }
        }
        return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * Returns true if the given unit definition matches this enumeration
	 * definition considered as a classifier definition and the subunit is for
	 * an enumeration definition.
	 **/
	@Override
	public Boolean matchForStub(UnitDefinition unit) {
		return unit.getDefinition() instanceof EnumerationDefinition &&
		        super.matchForStub(unit);
	} // matchForStub

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * enumeration definition allows an annotation for any stereotype whose
	 * metaclass is consistent with Enumeration.
	 **/
	@Override
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    // TODO: Allow stereotypes consistent with enumerations.
		return super.annotationAllowed(annotation);
	} // annotationAllowed

	/**
	 * Return true if the given member is either an EnumerationDefinition or an
	 * imported member whose referent is an EnumerationDefinition or an
	 * Enumeration.
	 **/
	public Boolean isSameKindAs(Member member) {
		return member.getImpl().getReferent().getImpl().isEnumeration();
	} // isSameKindAs

} // EnumerationDefinitionImpl
