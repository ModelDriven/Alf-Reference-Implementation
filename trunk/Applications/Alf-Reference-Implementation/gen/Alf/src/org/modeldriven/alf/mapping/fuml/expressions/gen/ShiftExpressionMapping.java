
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions.gen;

import org.modeldriven.alf.mapping.fuml.expressions.gen.BinaryExpressionMapping;

import org.modeldriven.alf.syntax.expressions.ShiftExpression;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class ShiftExpressionMapping extends BinaryExpressionMapping {

	public ShiftExpressionMapping() {
		this.setErrorMessage("ShiftExpressionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public ShiftExpression getShiftExpression() {
		return (ShiftExpression) this.getSource();
	}

} // ShiftExpressionMapping
