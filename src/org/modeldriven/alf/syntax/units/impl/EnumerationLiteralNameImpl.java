
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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
