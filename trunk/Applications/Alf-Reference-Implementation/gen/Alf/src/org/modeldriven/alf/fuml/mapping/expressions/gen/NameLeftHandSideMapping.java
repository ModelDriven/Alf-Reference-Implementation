
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.expressions.gen;

import org.modeldriven.alf.fuml.mapping.expressions.gen.LeftHandSideMapping;

import org.modeldriven.alf.syntax.expressions.NameLeftHandSide;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class NameLeftHandSideMapping extends LeftHandSideMapping {

	public NameLeftHandSideMapping() {
		this.setErrorMessage("NameLeftHandSideMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public NameLeftHandSide getNameLeftHandSide() {
		return (NameLeftHandSide) this.getSource();
	}

} // NameLeftHandSideMapping
