
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.expressions;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import alf.mapping.structural.*;
import alf.mapping.behavioral.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

public class BehaviorInvocationExpressionMapping extends ExpressionMapping {

	private CallBehaviorAction action = null;
	private TupleMapping tuple = null;

	public void mapTo(CallBehaviorAction action) {
		super.mapTo(action);

		BehaviorInvocationExpression expression = this
				.getBehaviorInvocationExpression();
		Member member = expression.getBehavior(this.getContext());

		if (member.isError()) {
			this.setError(((ErrorMember) member).getError());
		} else {
			ActivityDefinitionMapping mapping = (ActivityDefinitionMapping) this
					.map((ActivityDefinition) member);
			Activity activity = mapping.getActivity();

			if (mapping.isError()) {
				this.setError(mapping.getError());
			} else {
				action.setBehavior(activity);

				Tuple tuple = expression.getTuple();
				TupleMapping tupleMapping = (TupleMapping) this.map(tuple);
				tupleMapping.setContext(this.getContext());
				tupleMapping.mapTo(action);
				this.setTuple(tupleMapping);

				if (tupleMapping.isError()) {
					this.setError(tupleMapping.getError());
				}
			}
		}

	} // mapTo

	public void setTuple(TupleMapping tuple) {
		this.tuple = tuple;
	} // setTuple

	public TupleMapping getTuple() {
		return this.tuple;
	} // getTuple

	public ActivityNode getResultSource() {
		CallBehaviorAction action = this.getAction();
		Behavior behavior = action.behavior;
		ParameterList parameters = behavior.ownedParameter;

		int i = 0;
		int n = 0;
		ParameterDirectionKind direction = parameters.get(0).direction;
		while (i < parameters.size()
				&& direction != ParameterDirectionKind.return_) {
			i++;
			if (direction == ParameterDirectionKind.out
					|| direction == ParameterDirectionKind.inout) {
				n++;
			}
		}

		if (i == parameters.size()) {
			return null;
		} else {
			return action.output.getValue(n);
		}
	} // getResultSource

	public CallBehaviorAction getAction() {
		if (this.action == null) {
			this.action = new CallBehaviorAction();
			this.mapTo(action);
		}

		return this.action;
	} // getAction

	public BehaviorInvocationExpression getBehaviorInvocationExpression() {
		return (BehaviorInvocationExpression) this.getSource();
	} // getBehaviorInvocationExpression

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getAction());

		if (!this.isError()) {
			elements.addAll(this.getTuple().getModelElements());
		}

		return elements;
	} // getModelElements

} // BehaviorInvocationExpressionMapping
