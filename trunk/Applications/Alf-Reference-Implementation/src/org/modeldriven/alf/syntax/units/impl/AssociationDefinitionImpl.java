
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
 * The definition of an association, whose members must all be properties.
 **/

public class AssociationDefinitionImpl extends ClassifierDefinitionImpl {

	public AssociationDefinitionImpl(AssociationDefinition self) {
		super(self);
	}

	@Override
	public AssociationDefinition getSelf() {
		return (AssociationDefinition) this.self;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The specialization referents of an association definition must all be
	 * associations.
	 **/
	public boolean associationDefinitionSpecializationReferent() {
	    for (ElementReference referent: this.getSelf().getSpecializationReferent()) {
	        if (!referent.getImpl().isAssociation()) {
	            return false;
	        }
	    }
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * Returns true if the given unit definition matches this association
	 * definition considered as a classifier definition and the subunit is for
	 * an association definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return unit.getDefinition() instanceof AssociationDefinition &&
		    super.matchForStub(unit);
	} // matchForStub

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * association definition allows an annotation for any stereotype whose
	 * metaclass is consistent with Association.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    // TODO: Allow stereotypes consistent with associations.
		return super.annotationAllowed(annotation);
	} // annotationAllowed

	/**
	 * Return true if the given member is either an AssociationDefinition or an
	 * imported member whose referent is an AssociationDefinition or an
	 * Association.
	 **/
	public Boolean isSameKindAs(Member member) {
	    return member.getImpl().getReferent().getImpl().isAssociation();
	} // isSameKindAs

} // AssociationDefinitionImpl
