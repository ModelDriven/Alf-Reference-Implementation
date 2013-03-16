/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class RemoveStructuralFeatureValueAction extends
		WriteStructuralFeatureAction implements
		org.modeldriven.alf.uml.RemoveStructuralFeatureValueAction {
	public RemoveStructuralFeatureValueAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createRemoveStructuralFeatureValueAction());
	}

	public RemoveStructuralFeatureValueAction(
			org.eclipse.uml2.uml.RemoveStructuralFeatureValueAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.RemoveStructuralFeatureValueAction getBase() {
		return (org.eclipse.uml2.uml.RemoveStructuralFeatureValueAction) this.base;
	}

	public boolean getIsRemoveDuplicates() {
		return this.getBase().isRemoveDuplicates();
	}

	public void setIsRemoveDuplicates(boolean isRemoveDuplicates) {
		this.getBase().setIsRemoveDuplicates(isRemoveDuplicates);
	}

	public org.modeldriven.alf.uml.InputPin getRemoveAt() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getRemoveAt());
	}

	public void setRemoveAt(org.modeldriven.alf.uml.InputPin removeAt) {
		this.getBase().setRemoveAt(
				removeAt == null ? null : ((InputPin) removeAt).getBase());
	}

}
