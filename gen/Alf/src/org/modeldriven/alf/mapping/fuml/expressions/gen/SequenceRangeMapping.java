
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions.gen;

import org.modeldriven.alf.mapping.fuml.expressions.gen.SequenceElementsMapping;

import org.modeldriven.alf.syntax.expressions.SequenceRange;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class SequenceRangeMapping extends SequenceElementsMapping {

	public SequenceRangeMapping() {
		this.setErrorMessage("SequenceRangeMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public SequenceRange getSequenceRange() {
		return (SequenceRange) this.getSource();
	}

} // SequenceRangeMapping
