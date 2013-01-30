/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class ReadStructuralFeatureAction extends StructuralFeatureAction
		implements org.modeldriven.alf.uml.ReadStructuralFeatureAction {
	public ReadStructuralFeatureAction() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.ReadStructuralFeatureAction());
	}

	public ReadStructuralFeatureAction(
			fUML.Syntax.Actions.IntermediateActions.ReadStructuralFeatureAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.ReadStructuralFeatureAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.ReadStructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return (OutputPin)this.wrap(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(result==null? null: ((OutputPin) result).getBase());
	}

}
