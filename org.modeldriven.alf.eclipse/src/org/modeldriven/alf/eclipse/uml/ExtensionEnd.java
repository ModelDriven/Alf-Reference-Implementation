/*******************************************************************************
 * Copyright 2019 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.uml;

import org.eclipse.uml2.uml.UMLFactory;

public class ExtensionEnd extends Property implements org.modeldriven.alf.uml.ExtensionEnd {

	public ExtensionEnd() {
		this(UMLFactory.eINSTANCE.createExtensionEnd());
	}

	public ExtensionEnd(org.eclipse.uml2.uml.ExtensionEnd base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.ExtensionEnd getBase() {
		return (org.eclipse.uml2.uml.ExtensionEnd)this.base;
	}

}
