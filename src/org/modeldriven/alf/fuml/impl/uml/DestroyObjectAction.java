/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class DestroyObjectAction extends Action implements
		org.modeldriven.alf.uml.DestroyObjectAction {
	public DestroyObjectAction() {
		this(new fUML.Syntax.Actions.IntermediateActions.DestroyObjectAction());
	}

	public DestroyObjectAction(
			fUML.Syntax.Actions.IntermediateActions.DestroyObjectAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.DestroyObjectAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.DestroyObjectAction) this.base;
	}

	public boolean getIsDestroyLinks() {
		return this.getBase().isDestroyLinks;
	}

	public void setIsDestroyLinks(boolean isDestroyLinks) {
		this.getBase().setIsDestroyLinks(isDestroyLinks);
	}

	public boolean getIsDestroyOwnedObjects() {
		return this.getBase().isDestroyOwnedObjects;
	}

	public void setIsDestroyOwnedObjects(boolean isDestroyOwnedObjects) {
		this.getBase().setIsDestroyOwnedObjects(isDestroyOwnedObjects);
	}

	public org.modeldriven.alf.uml.InputPin getTarget() {
		return (InputPin)this.wrap(this.getBase().target);
	}

	public void setTarget(org.modeldriven.alf.uml.InputPin target) {
		this.getBase().setTarget(target==null? null: ((InputPin) target).getBase());
	}

}
