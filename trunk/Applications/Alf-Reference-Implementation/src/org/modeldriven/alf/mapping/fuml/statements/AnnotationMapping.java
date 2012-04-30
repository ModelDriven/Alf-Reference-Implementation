
/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.statements.Annotation;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class AnnotationMapping extends SyntaxElementMapping {

	public AnnotationMapping() {
		this.setErrorMessage("AnnotationMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		return new ArrayList<Element>();
	}

	public Annotation getAnnotation() {
		return (Annotation) this.getSource();
	}

} // AnnotationMapping
