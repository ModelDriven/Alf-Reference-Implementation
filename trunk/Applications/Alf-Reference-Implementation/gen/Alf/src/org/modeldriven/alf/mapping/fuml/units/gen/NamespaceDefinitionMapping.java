
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.units.gen;

import org.modeldriven.alf.mapping.fuml.units.gen.MemberMapping;

import org.modeldriven.alf.syntax.units.NamespaceDefinition;

public abstract class NamespaceDefinitionMapping extends MemberMapping {

	public NamespaceDefinition getNamespaceDefinition() {
		return (NamespaceDefinition) this.getSource();
	}

} // NamespaceDefinitionMapping
