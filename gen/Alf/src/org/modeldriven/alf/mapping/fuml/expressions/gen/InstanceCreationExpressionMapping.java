
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions.gen;

import org.modeldriven.alf.mapping.fuml.expressions.gen.InvocationExpressionMapping;

import org.modeldriven.alf.syntax.expressions.InstanceCreationExpression;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class InstanceCreationExpressionMapping extends
		InvocationExpressionMapping {

	public InstanceCreationExpressionMapping() {
		this
				.setErrorMessage("InstanceCreationExpressionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public InstanceCreationExpression getInstanceCreationExpression() {
		return (InstanceCreationExpression) this.getSource();
	}

} // InstanceCreationExpressionMapping
