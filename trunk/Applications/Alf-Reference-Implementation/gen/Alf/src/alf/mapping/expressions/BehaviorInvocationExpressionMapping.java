
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
		return (BehaviorInvocationExpression) this.getSource();
	} // getBehaviorInvocationExpression

} // BehaviorInvocationExpressionMapping
