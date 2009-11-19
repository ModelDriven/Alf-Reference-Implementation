
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Activities.CompleteStructuredActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

public class PositionalTupleMapping extends TupleMapping {

	public void mapTo(InvocationAction action, ArrayList<Parameter> parameters,
			NamespaceDefinition context) {
		PositionalTuple tuple = this.getPositionalTuple();
		ArrayList<Expression> expressions = tuple.getExpressions();

		if (expressions.size() > parameters.size()) {
			this.setError(new ErrorNode(tuple, "Too many arguments."));
		} else {
			StructuredActivityNode node = new StructuredActivityNode();
			node.setName("PositionalTuple@"
					+ Integer.toHexString(tuple.hashCode()));
			this.addModelElement(node);

			ControlFlow control = new ControlFlow();
			control.setSource(node);
			control.setTarget(action);
			this.addModelElement(control);

			for (int i = 0; i < parameters.size(); i++) {
				Parameter parameter = parameters.get(i);

				if (parameter.direction == ParameterDirectionKind.in
						|| parameter.direction == ParameterDirectionKind.inout) {
					ExpressionMapping mapping = (ExpressionMapping) this
							.map(expressions.get(i));
					mapping.setContext(context);
					ArrayList<Element> elements = mapping.getModelElements();

					if (mapping.isError()) {
						this.setError(mapping.getError());
						return;
					}

					for (Element element : elements) {
						if (element instanceof ActivityNode) {
							node.addNode((ActivityNode) element);
						} else if (element instanceof ControlFlow) {
							node.addEdge((ActivityEdge) element);
						} else {
							ActivityEdge edge = (ActivityEdge) element;
							if (edge.source.activity != null
									|| edge.target.activity != null) {
								this.addModelElement(edge);
							} else {
								node.addEdge(edge);
							}
						}
					}

					InputPin pin = new InputPin();
					pin.setName(action.name + ".argument(" + parameter.name
							+ ")");
					pin.setLower(parameter.multiplicityElement.lower);
					pin
							.setUpper(parameter.multiplicityElement.upper.naturalValue);
					pin.setType(parameter.type);
					action.addArgument(pin);

					ActivityNode resultSource = mapping.getResultSource();

					ObjectFlow flow = new ObjectFlow();
					flow.setSource(resultSource);
					flow.setTarget(pin);
					this.addModelElement(flow);
				}

				if (parameter.direction == ParameterDirectionKind.out
						|| parameter.direction == ParameterDirectionKind.inout) {
					OutputPin pin = new OutputPin();
					pin
							.setName(action.name + ".result(" + parameter.name
									+ ")");
					pin.setLower(parameter.multiplicityElement.lower);
					pin
							.setUpper(parameter.multiplicityElement.upper.naturalValue);
					pin.setType(parameter.type);
					((CallAction) action).addResult(pin);

					// NOTE: Output pins are not currently being connected.
				}
			}
		}
	} // mapTo

	public PositionalTuple getPositionalTuple() {
		return (PositionalTuple) this.getSourceNode();
	} // getPositionalTuple

} // PositionalTupleMapping
