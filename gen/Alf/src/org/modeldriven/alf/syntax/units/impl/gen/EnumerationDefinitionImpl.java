
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
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

/**
 * The definition of an enumeration, whose members must all be enumeration
 * literal names.
 **/

public class EnumerationDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.ClassifierDefinitionImpl {

	public EnumerationDefinitionImpl(EnumerationDefinition self) {
		super(self);
	}

	public EnumerationDefinition getSelf() {
		return (EnumerationDefinition) this.self;
	}

	/**
	 * The specialization referents of a class definition must all be classes. A
	 * class definition may not have any referents that are active classes
	 * unless this is an active class definition.
	 **/
	public boolean enumerationDefinitionSpecializationReferent() {
		return true;
	}

	/**
	 * Returns true if the given unit definition matches this enumeration
	 * definition considered as a classifier definition and the subunit is for
	 * an enumeration definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return false; // STUB
	} // matchForStub

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * enumeration definition allows an annotation for any stereotype whose
	 * metaclass is consistent with Enumeration.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false; // STUB
	} // annotationAllowed

	/**
	 * Return true if the given member is either an EnumerationDefinition or an
	 * imported member whose referent is an EnumerationDefinition or an
	 * Enumeration.
	 **/
	public Boolean isSameKindAs(Member member) {
		return false; // STUB
	} // isSameKindAs

} // EnumerationDefinitionImpl
