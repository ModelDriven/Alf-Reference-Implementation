
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

import org.modeldriven.alf.mapping.structural.*;
import org.modeldriven.alf.mapping.behavioral.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

public abstract class InvocationExpressionMapping extends ExpressionMapping {

	private TupleMapping tuple = null;
	private InvocationAction action = null;

	public void mapTo(InvocationAction action) {
		super.mapTo(action);
		this.mapTargetTo(action);

		if (!this.isError()) {
			InvocationExpression expression = this.getInvocationExpression();

			Tuple tuple = expression.getTuple();
			TupleMapping tupleMapping = (TupleMapping) this.map(tuple);
			tupleMapping.mapTo(action, this.getParameters(action), this
					.getContext());
			this.setTuple(tupleMapping);

			if (tupleMapping.isError()) {
				this.setError(tupleMapping.getError());
			}
		}
	} // mapTo

	public abstract InvocationAction mapAction();

	public abstract void mapTargetTo(InvocationAction action);

	public abstract ArrayList<Parameter> getParameters(InvocationAction action);

	public void setTuple(TupleMapping tuple) {
		this.tuple = tuple;
	} // setTuple

	public TupleMapping getTuple() {
		return this.tuple;
	} // getTuple

	public InvocationAction getAction() {
		if (this.action == null && !this.isError()) {
			this.action = this.mapAction();
			this.mapTo(action);
		}

		return this.action;
	} // getAction

	public InvocationExpression getInvocationExpression() {
		return (InvocationExpression) this.getSourceNode();
	} // getInvocationExpression

	public ActivityNode getResultSource() {
		InvocationAction action = this.getAction();
		ArrayList<Parameter> parameters = this.getParameters(action);

		ParameterDirectionKind direction;
		int i = 0;
		int n = 0;
		do {
			direction = parameters.get(i++).direction;
			if (direction == ParameterDirectionKind.out
					|| direction == ParameterDirectionKind.inout) {
				n++;
			}
		} while (i < parameters.size()
				&& direction != ParameterDirectionKind.return_);

		if (direction != ParameterDirectionKind.return_) {
			return null;
		} else {
			return action.output.getValue(n);
		}
	} // getResultSource

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();

		InvocationAction action = this.getAction();
		if (action != null) {
			elements.add(action);

			if (!this.isError()) {
				elements.addAll(this.getTuple().getModelElements());
			}
		}

		return elements;
	} // getModelElements

	public Classifier getType() {
		ActivityNode resultSource = this.getResultSource();

		if (resultSource != null) {
			return (Classifier) ((OutputPin) resultSource).typedElement.type;
		} else {
			return null;
		}
	} // getType

} // InvocationExpressionMapping
