package org.modeldriven.uml.alf.fuml;


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
		return new InputPin(this.getBase().target);
	}

	public void setTarget(org.modeldriven.alf.uml.InputPin target) {
		this.getBase().setTarget(((InputPin) target).getBase());
	}

}
