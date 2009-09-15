
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
import alf.mapping.behavioral.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.Activities.IntermediateActivities.*;

public class OperationDefinitionMapping extends NamespaceDefinitionMapping {

	private Operation operation = null;

	public void mapTo(Operation operation) {
		super.mapTo(operation);

		if (!this.isError()) {

			OperationDefinition definition = this.getOperationDefinition();

			if (definition.isAbstract()) {
				operation.setIsAbstract(true);
			} else {

				BlockMapping bodyMapping = (BlockMapping) this.map(definition
						.getBody());
				bodyMapping.setContext(definition);
				ArrayList<Element> elements = bodyMapping.getModelElements();

				if (bodyMapping.isError()) {
					this.setError(bodyMapping.getError());
				} else {
					Activity activity = new Activity();
					operation.addMethod(activity);

					ParameterList parameters = operation.ownedParameter;
					for (int i = 0; i < parameters.size(); i++) {
						Parameter parameter = parameters.get(i);
						Parameter copy = new Parameter();
						copy.setName(parameter.name);
						copy.setDirection(parameter.direction);
						copy.setLower(parameter.multiplicityElement.lower);
						copy
								.setUpper(parameter.multiplicityElement.upper.naturalValue);
						copy.setType(parameter.type);
						copy
								.setIsOrdered(parameter.multiplicityElement.isOrdered);
						copy
								.setIsUnique(parameter.multiplicityElement.isUnique);
						activity.addOwnedParameter(copy);
					}

					for (Element element : elements) {
						if (element instanceof ActivityNode) {
							activity.addNode((ActivityNode) element);
						} else if (element instanceof ActivityEdge) {
							activity.addEdge((ActivityEdge) element);
						} else {
							this.setError(new ErrorNode(definition.getBody(),
									"Element not an activity node."));
						}
					}
				}
			}
		}
	} // mapTo

	public void addMemberTo(Element element, NamedElement namespace) {
		if (!(element instanceof Parameter)) {
			this.setError(new ErrorNode(this.getSourceNode(),
					"Member that is not a parameter."));
		} else {
			// Note: An operation is a namespace in full UML, but not in fUML,
			// so the "namespace"
			// parameter actually has the type "NamedElement".
			((Operation) namespace).addOwnedParameter((Parameter) element);
		}

	} // addMemberTo

	public Operation getOperation() {
		if (this.operation == null) {
			this.operation = new Operation();
			this.mapTo(operation);
		}

		return this.operation;
	} // getOperation

	public OperationDefinition getOperationDefinition() {
		return (OperationDefinition) this.getSourceNode();
	} // getOperationDefinition

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getOperation());
		return elements;
	} // getModelElements

} // OperationDefinitionMapping
