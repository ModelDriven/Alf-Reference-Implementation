
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

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.CompleteActions.*;

public class ClassExtentExpressionMapping extends ExpressionMapping {

	private ReadExtentAction action = null;

	public void mapTo(ReadExtentAction action) {
		super.mapTo(action);

		Class_ class_ = (Class_) this.getType();
		if (class_ != null) {
			action.setName("ReadExtent(" + class_.name + ")");
			action.setClassifier(class_);

			OutputPin result = new OutputPin();
			result.setName(action.name + ".result");
			result.setType(class_);
			result.setLower(0);
			result.setUpper(-1);
			action.setResult(result);
		}
	} // mapTo

	public ActivityNode getResultSource() {
		return this.getAction().result;
	} // getResultSource

	public ReadExtentAction getAction() {
		if (this.action == null && !this.isError()) {
			this.action = new ReadExtentAction();
			this.mapTo(this.action);
		}

		return this.action;
	} // getAction

	public ClassExtentExpression getClassExtentExpression() {
		return (ClassExtentExpression) this.getSourceNode();
	} // getClassExtentExpression

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();

		ReadExtentAction action = this.getAction();
		if (action != null) {
			elements.add(action);
		}

		return elements;
	} // getModelElements

	public Classifier getType() {
		Member type = this.getClassExtentExpression().getClass(
				this.getContext());

		if (type.isError()) {
			this.setError(((ErrorMember) type).getError());
		} else {
			ClassDefinitionMapping mapping = (ClassDefinitionMapping) this
					.map(type);
			Classifier classifier = mapping.getClassifier();

			if (mapping.isError()) {
				this.setError(mapping.getError());
			} else {
				return classifier;
			}
		}

		return null;
	} // getType

} // ClassExtentExpressionMapping
