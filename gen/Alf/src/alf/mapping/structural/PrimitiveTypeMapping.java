
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.structural;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import alf.mapping.namespaces.*;
import alf.execution.*;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.PrimitiveType;

public class PrimitiveTypeMapping extends MemberMapping {

	private PrimitiveType primitiveType = null;
	private Environment environment = null;

	public PrimitiveTypeMapping(Environment environment) {
		this.environment = environment;
	} // PrimitiveTypeMapping

	public Environment getEnvironment() {
		return this.environment;
	} // getEnvironment

	public PrimitiveType getPrimitiveType() {
		if (this.primitiveType == null) {
			this.primitiveType = new PrimitiveType();
			this.mapTo(this.primitiveType);
			this.getEnvironment().addBuiltInType(this.primitiveType);
		}

		return this.primitiveType;
	} // getPrimitiveType

	public DataTypeDefinition getDataTypeDefinition() {
		return (DataTypeDefinition) this.getSourceNode();
	} // getDataTypeDefinition

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getPrimitiveType());
		return elements;
	} // getModelElements

} // PrimitiveTypeMapping
