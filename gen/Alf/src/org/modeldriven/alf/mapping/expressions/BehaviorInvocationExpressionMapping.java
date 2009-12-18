
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

public class BehaviorInvocationExpressionMapping extends
		InvocationExpressionMapping {

	public void mapTargetTo(InvocationAction action) {
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
				action.setName("CallBehavior(" + activity.qualifiedName + ")");
				((CallBehaviorAction) action).setBehavior(activity);
			}
		}

	} // mapTargetTo

	public ArrayList<Parameter> getParameters(InvocationAction action) {
		ParameterList ownedParameters = ((CallBehaviorAction) action).behavior.ownedParameter;
		ArrayList<Parameter> parameters = new ArrayList<Parameter>();

		for (int i = 0; i < ownedParameters.size(); i++) {
			parameters.add(ownedParameters.getValue(i));
		}

		return parameters;
	} // getParameters

	public InvocationAction mapAction() {
		return new CallBehaviorAction();
	} // mapAction

	public BehaviorInvocationExpression getBehaviorInvocationExpression() {
		return (BehaviorInvocationExpression) this.getSourceNode();
	} // getBehaviorInvocationExpression

} // BehaviorInvocationExpressionMapping
