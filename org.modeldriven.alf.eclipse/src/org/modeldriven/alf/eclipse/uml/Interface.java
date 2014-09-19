/*******************************************************************************
 * Copyright 2014 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class Interface extends Classifier {

	public Interface() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createInterface());
	}

	public Interface(org.eclipse.uml2.uml.Interface base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Interface getBase() {
		return (org.eclipse.uml2.uml.Interface) this.base;
	}

}
