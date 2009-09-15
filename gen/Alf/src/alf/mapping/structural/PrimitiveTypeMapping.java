
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.structural;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import alf.mapping.namespaces.*;
import alf.execution.*;

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
