
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.NamedExpressionMapping;

import org.modeldriven.alf.syntax.expressions.OutputNamedExpression;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class OutputNamedExpressionMapping extends NamedExpressionMapping {

	public OutputNamedExpressionMapping() {
		this.setErrorMessage("No mapping for OutputNamedExpressionMapping.");
	}

	public List<Element> getModelElements() {
		return new ArrayList<Element>();
	}

	public OutputNamedExpression getOutputNamedExpression() {
		return (OutputNamedExpression) this.getSource();
	}

} // OutputNamedExpressionMapping
