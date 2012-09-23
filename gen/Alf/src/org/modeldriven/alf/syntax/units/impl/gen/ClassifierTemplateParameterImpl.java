
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

/**
 * The definition of a classifier template parameter, which acts as a classifier
 * within the definition of the template.
 **/

public class ClassifierTemplateParameterImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.ClassifierDefinitionImpl {

	public ClassifierTemplateParameterImpl(ClassifierTemplateParameter self) {
		super(self);
	}

	public ClassifierTemplateParameter getSelf() {
		return (ClassifierTemplateParameter) this.self;
	}

	/**
	 * Annotations are not allowed on classifier template parameters.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false; // STUB
	} // annotationAllowed

	/**
	 * Returns false. (Classifier template parameters cannot be stubs.)
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return false; // STUB
	} // matchForStub

	/**
	 * Return true if the given member is a classifier template parameter.
	 **/
	public Boolean isSameKindAs(Member member) {
		return false; // STUB
	} // isSameKindAs

} // ClassifierTemplateParameterImpl
