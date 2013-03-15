/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class PackageableElement extends NamedElement implements
		org.modeldriven.alf.uml.PackageableElement {

	public PackageableElement(org.eclipse.uml2.uml.PackageableElement base) {
		super(base);
	}

	public org.eclipse.uml2.uml.PackageableElement getBase() {
		return (org.eclipse.uml2.uml.PackageableElement) this.base;
	}

}
