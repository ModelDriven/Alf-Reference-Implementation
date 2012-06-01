
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions.gen;

import org.modeldriven.alf.mapping.fuml.expressions.gen.UnaryExpressionMapping;

import org.modeldriven.alf.syntax.expressions.ClassificationExpression;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class ClassificationExpressionMapping extends UnaryExpressionMapping {

	public ClassificationExpressionMapping() {
		this
				.setErrorMessage("ClassificationExpressionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public ClassificationExpression getClassificationExpression() {
		return (ClassificationExpression) this.getSource();
	}

} // ClassificationExpressionMapping
