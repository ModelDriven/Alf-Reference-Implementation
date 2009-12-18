
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.structural.*;
import org.modeldriven.alf.mapping.behavioral.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

public class FeatureInvocationExpressionMapping extends
		InvocationExpressionMapping {

	private ArrayList<Element> modelElements = new ArrayList<Element>();

	public void mapTargetTo(InvocationAction action) {
		FeatureReference target = this.getFeatureInvocationExpression()
				.getTarget();

		FeatureReferenceMapping mapping = (FeatureReferenceMapping) this
				.map(target);
		ArrayList<Feature> features = mapping.getFeatures();

		if (mapping.isError()) {
			this.setError(mapping.getError());
		} else {
			ArrayList<Operation> operations = new ArrayList<Operation>();
			for (Feature feature : features) {
				if (feature instanceof Operation) {
					operations.add((Operation) feature);
				}
			}

			if (operations.size() == 0) {
				this.setError(new ErrorNode(target,
						"Only operation calls are supported."));
			} else if (operations.size() > 1) {
				this.setError(new ErrorNode(target,
						"Overloading not supported."));
			} else {
				Operation operation = operations.get(0);
				action
						.setName("CallOperation(" + operation.qualifiedName
								+ ")");
				((CallOperationAction) action).setOperation(operation);
				ArrayList<Element> elements = mapping.getExpressionElements();
				if (mapping.isError()) {
					this.setError(this.getError());
				} else {
					InputPin targetPin = new InputPin();
					targetPin.setName(action.name + ".target");
					targetPin.setLower(1);
					targetPin.setUpper(1);
					((CallOperationAction) action).setTarget(targetPin);

					ObjectFlow flow = new ObjectFlow();
					flow.setSource(mapping.getExpressionMapping()
							.getResultSource());
					flow.setTarget(targetPin);
					elements.add(flow);

					this.setModelElements(elements);
				}
			}
		}
	} // mapTargetTo

	public ArrayList<Parameter> getParameters(InvocationAction action) {
		ParameterList ownedParameters = ((CallOperationAction) action).operation.ownedParameter;
		ArrayList<Parameter> parameters = new ArrayList<Parameter>();

		for (int i = 0; i < ownedParameters.size(); i++) {
			parameters.add(ownedParameters.getValue(i));
		}

		return parameters;
	} // getParameters

	public InvocationAction mapAction() {
		return new CallOperationAction();
	} // mapAction

	public FeatureInvocationExpression getFeatureInvocationExpression() {
		return (FeatureInvocationExpression) this.getSourceNode();
	} // getFeatureInvocationExpression

	public void setModelElements(ArrayList<Element> elements) {
		this.modelElements = elements;
	} // setModelElements

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = (ArrayList<Element>) super
				.getModelElements().clone();
		elements.addAll(this.modelElements);
		return elements;
	} // getModelElements

} // FeatureInvocationExpressionMapping
