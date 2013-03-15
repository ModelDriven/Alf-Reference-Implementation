/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class ClearAssociationAction extends Action implements
		org.modeldriven.alf.uml.ClearAssociationAction {
	public ClearAssociationAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createClearAssociationAction());
	}

	public ClearAssociationAction(
			org.eclipse.uml2.uml.ClearAssociationAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ClearAssociationAction getBase() {
		return (org.eclipse.uml2.uml.ClearAssociationAction) this.base;
	}

	public org.modeldriven.alf.uml.Association getAssociation() {
		return (org.modeldriven.alf.uml.Association) wrap(this.getBase()
				.getAssociation());
	}

	public void setAssociation(org.modeldriven.alf.uml.Association association) {
		this.getBase().setAssociation(
				association == null ? null : ((Association) association)
						.getBase());
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
