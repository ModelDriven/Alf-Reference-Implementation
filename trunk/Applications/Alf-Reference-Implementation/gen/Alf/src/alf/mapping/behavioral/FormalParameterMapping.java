
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.behavioral;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import alf.mapping.namespaces.*;
import alf.mapping.structural.*;

import fUML.Syntax.Classes.Kernel.*;

public class FormalParameterMapping extends MemberMapping {

	private Parameter parameter = null;

	public void mapTo(Parameter parameter) {
		super.mapTo(parameter);

		FormalParameter formal = this.getFormalParameter();
		Member type = formal.getType();

		if (type.isError()) {
			this.setError(((ErrorMember) type).getError());
		} else {
			ClassifierDefinitionMapping mapping = (ClassifierDefinitionMapping) this
					.map((ClassifierDefinition) type);
			parameter.setType(mapping.getClassifier());

			String direction = formal.getDirection();
			if (direction.equals("in")) {
				parameter.setDirection(ParameterDirectionKind.in);
			} else if (direction.equals("out")) {
				parameter.setDirection(ParameterDirectionKind.out);
			} else if (direction.equals("inout")) {
				parameter.setDirection(ParameterDirectionKind.inout);
			} else if (direction.equals("return")) {
				parameter.setDirection(ParameterDirectionKind.return_);
			}

			TypedElementDeclaration declaration = formal.getDeclaration();
			parameter.setLower(declaration.getLower());
			parameter.setUpper(declaration.getUpper());
			parameter.setIsOrdered(declaration.getOrdering());
			parameter.setIsUnique(!declaration.getNonuniqueness());
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
		return (FormalParameter) this.getSource();
	} // getFormalParameter

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getParameter());
		return elements;
	} // getModelElements

} // FormalParameterMapping
