
/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.FumlMapping;

import org.modeldriven.alf.syntax.expressions.ExtentOrExpression;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class ExtentOrExpressionMapping extends FumlMapping {

	public ExtentOrExpressionMapping() {
		this.setErrorMessage("No mapping for ExtentOrExpressionMapping.");
	}

	public List<Element> getModelElements() {
		return new ArrayList<Element>();
	}

	public ExtentOrExpression getExtentOrExpression() {
		return (ExtentOrExpression) this.getSource();
	}

} // ExtentOrExpressionMapping
