/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class StructuralFeatureAction extends Action implements
		org.modeldriven.alf.uml.StructuralFeatureAction {

	public StructuralFeatureAction(
			org.eclipse.uml2.uml.StructuralFeatureAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.StructuralFeatureAction getBase() {
		return (org.eclipse.uml2.uml.StructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.StructuralFeature getStructuralFeature() {
		return (org.modeldriven.alf.uml.StructuralFeature) wrap(this.getBase()
				.getStructuralFeature());
	}

	public void setStructuralFeature(
			org.modeldriven.alf.uml.StructuralFeature structuralFeature) {
		this.getBase().setStructuralFeature(
				structuralFeature == null ? null
						: ((StructuralFeature) structuralFeature).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getObject());
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(
				object == null ? null : ((InputPin) object).getBase());
	}

}
