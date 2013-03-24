/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms,
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import org.eclipse.uml2.uml.UMLFactory;

public class Realization extends Dependency implements org.modeldriven.alf.uml.Realization {
	
	public Realization() {
		this(UMLFactory.eINSTANCE.createRealization());
	}

	public Realization(org.eclipse.uml2.uml.Realization base) {
		super(base);
	}
	
	public org.eclipse.uml2.uml.Realization getBase() {
		return (org.eclipse.uml2.uml.Realization)this.base;
	}

}
