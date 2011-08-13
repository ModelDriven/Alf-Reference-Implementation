
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.SequenceElementsMapping;

import org.modeldriven.alf.syntax.expressions.SequenceExpressionList;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class SequenceExpressionListMapping extends SequenceElementsMapping {

	public SequenceExpressionListMapping() {
		this
				.setErrorMessage("SequenceExpressionListMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public SequenceExpressionList getSequenceExpressionList() {
		return (SequenceExpressionList) this.getSource();
	}

} // SequenceExpressionListMapping
