
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.structural;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.namespaces.*;
import org.modeldriven.alf.execution.*;

import fUML.Syntax.Classes.Kernel.*;

public class PrimitiveTypeMapping extends ClassifierDefinitionMapping {

	private Environment environment = null;

	public PrimitiveTypeMapping(Environment environment) {
		this.environment = environment;
	} // PrimitiveTypeMapping

	public void mapTo(Classifier classifier) {
		super.mapTo(classifier);
		this.getEnvironment().addBuiltInType((PrimitiveType) classifier);
	} // mapTo

	public Classifier mapClassifier() {
		return new PrimitiveType();
	} // mapClassifier

	public Environment getEnvironment() {
		return this.environment;
	} // getEnvironment

	public PrimitiveType getPrimitiveType() {
		return (PrimitiveType) this.getClassifier();
	} // getPrimitiveType

	public void addMemberTo(Element element, NamedElement namespace) {
		return;
	} // addMemberTo

} // PrimitiveTypeMapping
