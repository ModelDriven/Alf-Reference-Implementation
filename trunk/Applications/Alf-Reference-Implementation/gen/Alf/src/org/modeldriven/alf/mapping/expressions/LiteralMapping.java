
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

import org.modeldriven.alf.mapping.*;
import org.modeldriven.alf.mapping.structural.*;

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
		ValueSpecificationAction action = this.getAction();
		if (action == null) {
			return null;
		} else {
			return action.result;
		}

	} // getResultSource

	public ValueSpecificationAction getAction() {
		if (this.action == null && !this.isError()) {
			this.action = new ValueSpecificationAction();
			this.mapTo(action);
		}

		return this.action;
	} // getAction

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();

		ValueSpecificationAction action = this.getAction();
		if (action != null) {
			elements.add(action);
		}

		return elements;
	} // getModelElements

	public Classifier getType() {
		QualifiedName qualifiedName = new QualifiedName();
		// qualifiedName.setIsAbsolute();
		qualifiedName.addName("UML");
		qualifiedName.addName("AuxiliaryConstructs");
		qualifiedName.addName("PrimitiveTypes");
		qualifiedName.addName(this.getTypeName());

		ArrayList<Member> members = qualifiedName.resolve(this.getContext());

		if (members.size() == 1 && !members.get(0).isError()) {
			MappingNode mapping = this.map(members.get(0));
			if (mapping instanceof PrimitiveTypeMapping) {
				return ((PrimitiveTypeMapping) mapping).getPrimitiveType();
			}
		}

		return null;
	} // getType

	public abstract String getTypeName();
} // LiteralMapping
