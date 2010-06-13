
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.structural;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.namespaces.*;

import fUML.Syntax.Classes.Kernel.*;

public class FormalParameterMapping extends TypedElementDefinitionMapping {

	private Parameter parameter = null;

	public void mapTo(Parameter parameter) {
		super.mapTo(parameter, parameter.multiplicityElement);

		if (!this.isError()) {
			String direction = this.getFormalParameter().getDirection();
			if (direction.equals("in")) {
				parameter.setDirection(ParameterDirectionKind.in);
			} else if (direction.equals("out")) {
				parameter.setDirection(ParameterDirectionKind.out);
			} else if (direction.equals("inout")) {
				parameter.setDirection(ParameterDirectionKind.inout);
			} else if (direction.equals("return")) {
				parameter.setDirection(ParameterDirectionKind.return_);
			}
		}
	} // mapTo

	public Parameter getParameter() {
		if (this.parameter == null) {
			this.parameter = new Parameter();
			this.mapTo(this.parameter);
		}

		return this.parameter;
	} // getParameter

	public FormalParameter getFormalParameter() {
		return (FormalParameter) this.getSourceNode();
	} // getFormalParameter

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getParameter());
		return elements;
	} // getModelElements

} // FormalParameterMapping
