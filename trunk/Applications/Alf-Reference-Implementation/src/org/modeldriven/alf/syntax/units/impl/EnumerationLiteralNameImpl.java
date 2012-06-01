
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.units.*;

/**
 * The definition of an enumeration literal, as a member of an enumeration
 * definition.
 **/

public class EnumerationLiteralNameImpl extends MemberImpl {

	public EnumerationLiteralNameImpl(EnumerationLiteralName self) {
		super(self);
	}

	@Override
	public EnumerationLiteralName getSelf() {
		return (EnumerationLiteralName) this.self;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * Returns false. (Enumeration literal name cannot have annotations.)
	 **/
    @Override
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false;
	} // annotationAllowed
	
	/*
	 * Helper Methods
	 */

	/**
	 * Return true if the given member is an EnumerationLiteralName.
	 **/
    @Override
	public Boolean isSameKindAs(Member member) {
		return member instanceof EnumerationLiteralName;
	} // isSameKindAs

} // EnumerationLiteralNameImpl
