
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.common.gen;

import org.modeldriven.alf.fuml.mapping.FumlMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class AssignedSourceMapping extends FumlMapping {

	public AssignedSourceMapping() {
		this.setErrorMessage("AssignedSourceMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public AssignedSource getAssignedSource() {
		return (AssignedSource) this.getSource();
	}

} // AssignedSourceMapping
