
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

public class NameExpressionMapping extends ExpressionMapping {

	private ActivityNode activityNode = null;

	public Member getFormalParameter() {
		return ((NameExpression) this.getSourceNode()).getParameter(this
				.getContext());
	} // getFormalParameter

	public ActivityNode getResultSource() {
		if (this.activityNode == null && !this.isError()) {
			this.mapTo(null);

			Member member = this.getFormalParameter();

			if (member == null) {
				this.setError(new ErrorNode(this.getSourceNode(),
						"Local names not supported."));
			} else if (member.isError()) {
				this.setError(((ErrorMember) member).getError());
			} else {
				FormalParameterMapping parameterMapping = (FormalParameterMapping) this
						.map(member);
				Parameter parameter = parameterMapping.getParameter();

				if (parameterMapping.isError()) {
					this.setError(parameterMapping.getError());
				} else {
					NamespaceDefinition context = this.getContext();
					if (!(context instanceof ActivityDefinition)) {
						this
								.setError(new ErrorNode(this.getSourceNode(),
										"Cannot reference parameter outside of an activity."));
					} else {
						ActivityDefinitionMapping activityMapping = (ActivityDefinitionMapping) this
								.map(context);
						ActivityParameterNode parameterNode = activityMapping
								.getParameterNode(parameter);

						if (activityMapping.isError()) {
							this.setError(activityMapping.getError());
						} else if (parameterNode == null) {
							this.setError(new ErrorNode(this.getSourceNode(),
									"Activity does not contain parameter."));
						} else if (parameterNode.outgoing.size() == 0) {
							this
									.setError(new ErrorNode(this
											.getSourceNode(),
											"Reference to output parameter not supported."));
						} else {
							this.activityNode = parameterNode.outgoing
									.getValue(0).target;
						}
					}
				}
			}
		}

		return this.activityNode;
	} // getResultSource

	public ArrayList<Element> getModelElements() {
		this.getResultSource();
		return new ArrayList<Element>();
	} // getModelElements

	public Classifier getType() {
		ActivityNode resultSource = this.getResultSource();

		if (resultSource != null
				&& resultSource instanceof ActivityParameterNode) {
			return (Classifier) ((ActivityParameterNode) resultSource).parameter.type;
		} else {
			return null;
		}
	} // getType

} // NameExpressionMapping
