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

public class Extension extends Association implements org.modeldriven.alf.uml.Extension {

	public Extension() {
		this(UMLFactory.eINSTANCE.createExtension());
	}

	public Extension(org.eclipse.uml2.uml.Extension base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.Extension getBase() {
		return (org.eclipse.uml2.uml.Extension)this.base;
	}

}
