/*******************************************************************************
 * Copyright 2014, 2015 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

/**
 * @author seidewitz
 *
 */
public class Port extends Property {

	public Port() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createPort());
	}

	public Port(org.eclipse.uml2.uml.Port base) {
		super(base);
	}
	
	public org.eclipse.uml2.uml.Port getBase() {
		return (org.eclipse.uml2.uml.Port) this.base;
	}

}
