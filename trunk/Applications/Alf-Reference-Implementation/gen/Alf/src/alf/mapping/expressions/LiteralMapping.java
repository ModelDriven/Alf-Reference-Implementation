
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

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;

public abstract class LiteralMapping extends ExpressionMapping {

	private ValueSpecificationAction action = null;

	public void mapTo(ValueSpecificationAction action) {
		super.mapTo(action);

		ValueSpecification value = this.mapValueSpecification();
		action.setName(value.name);
		action.setValue(value);

		OutputPin result = new OutputPin();
		result.setName(action.name + ".result");
		result.setType(value.type);
		result.setLower(1);
		result.setUpper(1);
		action.setResult(result);
	} // mapTo

	public abstract ValueSpecification mapValueSpecification();

	public ActivityNode getResultSource() {
		return this.getAction().result;
	} // getResultSource

	public ValueSpecificationAction getAction() {
		return this.action;
	} // getAction

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getAction());
		return elements;
	} // getModelElements

} // LiteralMapping
