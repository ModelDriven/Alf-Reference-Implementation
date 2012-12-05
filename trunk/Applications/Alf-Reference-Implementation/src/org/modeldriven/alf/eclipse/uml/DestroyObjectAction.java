package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

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
