
/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.statements;

import org.modeldriven.alf.fuml.mapping.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.statements.QualifiedNameList;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class QualifiedNameListMapping extends SyntaxElementMapping {

	public QualifiedNameListMapping() {
		this.setErrorMessage("No mapping for QualifiedNameList.");
	}

	public List<Element> getModelElements() {
		return new ArrayList<Element>();
	}

	public QualifiedNameList getQualifiedNameList() {
		return (QualifiedNameList) this.getSource();
	}

} // QualifiedNameListMapping
