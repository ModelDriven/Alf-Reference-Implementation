/*******************************************************************************
 * Copyright 2011, 2016 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.common;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.syntax.common.ElementReference;

public abstract class ElementReferenceMapping extends FumlMapping {
    
    public abstract FumlMapping getMapping();
    
	public ElementReference getElementReference() {
		return (ElementReference) this.getSource();
	}
	
} // ElementReferenceMapping
