/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public abstract class WriteStructuralFeatureAction extends StructuralFeatureAction
		implements org.modeldriven.alf.uml.WriteStructuralFeatureAction {

	public WriteStructuralFeatureAction(
			fUML.Syntax.Actions.IntermediateActions.WriteStructuralFeatureAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.WriteStructuralFeatureAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.WriteStructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getValue() {
		return (InputPin)this.wrap(this.getBase().value);
	}

	public void setValue(org.modeldriven.alf.uml.InputPin value) {
		this.getBase().setValue(value==null? null: ((InputPin) value).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return (OutputPin)this.wrap(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(result==null? null: ((OutputPin) result).getBase());
	}

}
