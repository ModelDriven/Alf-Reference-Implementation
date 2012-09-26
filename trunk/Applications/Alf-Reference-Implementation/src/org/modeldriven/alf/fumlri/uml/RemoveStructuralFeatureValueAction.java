/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;


public class RemoveStructuralFeatureValueAction extends
		WriteStructuralFeatureAction implements
		org.modeldriven.alf.uml.RemoveStructuralFeatureValueAction {
	public RemoveStructuralFeatureValueAction() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.RemoveStructuralFeatureValueAction());
	}

	public RemoveStructuralFeatureValueAction(
			fUML.Syntax.Actions.IntermediateActions.RemoveStructuralFeatureValueAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.RemoveStructuralFeatureValueAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.RemoveStructuralFeatureValueAction) this.base;
	}

	public boolean getIsRemoveDuplicates() {
		return this.getBase().isRemoveDuplicates;
	}

	public void setIsRemoveDuplicates(boolean isRemoveDuplicates) {
		this.getBase().setIsRemoveDuplicates(isRemoveDuplicates);
	}

	public org.modeldriven.alf.uml.InputPin getRemoveAt() {
		return (InputPin)this.wrap(this.getBase().removeAt);
	}

	public void setRemoveAt(org.modeldriven.alf.uml.InputPin removeAt) {
		this.getBase().setRemoveAt(removeAt==null? null: ((InputPin) removeAt).getBase());
	}

}
