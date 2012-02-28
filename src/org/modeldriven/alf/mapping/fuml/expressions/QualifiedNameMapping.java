
/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.expressions.QualifiedName;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class QualifiedNameMapping extends SyntaxElementMapping {

	public QualifiedNameMapping() {
		this.setErrorMessage("No mapping for QualifiedNameMapping.");
	}

	public List<Element> getModelElements() {
		return new ArrayList<Element>();
	}

	public QualifiedName getQualifiedName() {
		return (QualifiedName) this.getSource();
	}

} // QualifiedNameMapping
