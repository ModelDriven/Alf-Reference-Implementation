/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class ClearStructuralFeatureAction extends StructuralFeatureAction
		implements org.modeldriven.alf.uml.ClearStructuralFeatureAction {
	public ClearStructuralFeatureAction() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.ClearStructuralFeatureAction());
	}

	public ClearStructuralFeatureAction(
			fUML.Syntax.Actions.IntermediateActions.ClearStructuralFeatureAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.ClearStructuralFeatureAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.ClearStructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return (OutputPin)wrap(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(result==null? null: ((OutputPin) result).getBase());
	}

}
