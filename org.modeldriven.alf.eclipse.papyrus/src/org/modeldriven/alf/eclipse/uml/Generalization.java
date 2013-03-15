/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class Generalization extends Element implements
		org.modeldriven.alf.uml.Generalization {
	public Generalization() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createGeneralization());
	}

	public Generalization(org.eclipse.uml2.uml.Generalization base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Generalization getBase() {
		return (org.eclipse.uml2.uml.Generalization) this.base;
	}

	public boolean getIsSubstitutable() {
		return this.getBase().isSubstitutable();
	}

	public void setIsSubstitutable(boolean isSubstitutable) {
		this.getBase().setIsSubstitutable(isSubstitutable);
	}

	public org.modeldriven.alf.uml.Classifier getSpecific() {
		return (org.modeldriven.alf.uml.Classifier) wrap(this.getBase()
				.getSpecific());
	}

	public org.modeldriven.alf.uml.Classifier getGeneral() {
		return (org.modeldriven.alf.uml.Classifier) wrap(this.getBase()
				.getGeneral());
	}

	public void setGeneral(org.modeldriven.alf.uml.Classifier general) {
		this.getBase().setGeneral(
				general == null ? null : ((Classifier) general).getBase());
	}

}
