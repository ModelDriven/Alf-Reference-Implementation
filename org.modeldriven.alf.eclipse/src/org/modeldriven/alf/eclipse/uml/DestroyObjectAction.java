/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class DestroyObjectAction extends Action implements
		org.modeldriven.alf.uml.DestroyObjectAction {
	public DestroyObjectAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createDestroyObjectAction());
	}

	public DestroyObjectAction(org.eclipse.uml2.uml.DestroyObjectAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.DestroyObjectAction getBase() {
		return (org.eclipse.uml2.uml.DestroyObjectAction) this.base;
	}

	public boolean getIsDestroyLinks() {
		return this.getBase().isDestroyLinks();
	}

	public void setIsDestroyLinks(boolean isDestroyLinks) {
		this.getBase().setIsDestroyLinks(isDestroyLinks);
	}

	public boolean getIsDestroyOwnedObjects() {
		return this.getBase().isDestroyOwnedObjects();
	}

	public void setIsDestroyOwnedObjects(boolean isDestroyOwnedObjects) {
		this.getBase().setIsDestroyOwnedObjects(isDestroyOwnedObjects);
	}

	public org.modeldriven.alf.uml.InputPin getTarget() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getTarget());
	}

	public void setTarget(org.modeldriven.alf.uml.InputPin target) {
		this.getBase().setTarget(
				target == null ? null : ((InputPin) target).getBase());
	}

}
