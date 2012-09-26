/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;


public class StructuralFeatureAction extends Action implements
		org.modeldriven.alf.uml.StructuralFeatureAction {

	public StructuralFeatureAction(
			fUML.Syntax.Actions.IntermediateActions.StructuralFeatureAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.StructuralFeatureAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.StructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.StructuralFeature getStructuralFeature() {
		return (StructuralFeature)this.wrap(this.getBase().structuralFeature);
	}

	public void setStructuralFeature(
			org.modeldriven.alf.uml.StructuralFeature structuralFeature) {
		this.getBase().setStructuralFeature(
				((StructuralFeature) structuralFeature).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return (InputPin)this.wrap(this.getBase().object);
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(object==null? null: ((InputPin) object).getBase());
	}

}
