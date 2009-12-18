
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.behavioral;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.execution.Environment;
import org.modeldriven.alf.mapping.namespaces.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior;

public class PrimitiveBehaviorMapping extends NamespaceDefinitionMapping {

	private Environment environment = null;
	private OpaqueBehavior primitiveBehavior = null;

	public PrimitiveBehaviorMapping(Environment environment) {
		this.environment = environment;
	} // PrimitiveBehaviorMapping

	public Environment getEnvironment() {
		return this.environment;
	} // getEnvironment

	public void mapTo(OpaqueBehavior behavior) {
		super.mapTo(behavior);
		this.getEnvironment().addPrimitiveBehavior(behavior);
	} // mapTo

	public void addMemberTo(Element element, NamedElement namespace) {
		if (!(element instanceof Parameter)) {
			this.setError(new ErrorNode(this.getSourceNode(),
					"Member that is not a parameter."));
		} else {
			((OpaqueBehavior) namespace).addOwnedParameter((Parameter) element);
		}

	} // addMemberTo

	public OpaqueBehavior getPrimitiveBehavior() {
		if (this.primitiveBehavior == null) {
			this.primitiveBehavior = new OpaqueBehavior();
			this.mapTo(this.primitiveBehavior);
		}

		return this.primitiveBehavior;
	} // getPrimitiveBehavior

	public ActivityDefinition getActivityDefinition() {
		return (ActivityDefinition) this.getSourceNode();
	} // getActivityDefinition

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getPrimitiveBehavior());
		return elements;
	} // getModelElements

} // PrimitiveBehaviorMapping
