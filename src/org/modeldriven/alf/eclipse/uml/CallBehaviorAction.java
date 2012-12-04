package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class CallBehaviorAction extends CallAction implements
		org.modeldriven.alf.uml.CallBehaviorAction {
	public CallBehaviorAction() {
		this(UMLFactory.eINSTANCE.createCallBehaviorAction());
	}

	public CallBehaviorAction(
			fUML.Syntax.Actions.BasicActions.CallBehaviorAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.CallBehaviorAction getBase() {
		return (org.eclipse.uml2.uml.CallBehaviorAction) this.base;
	}

	public org.modeldriven.alf.uml.Behavior getBehavior() {
		return new Behavior(this.getBase().getBehavior());
	}

	public void setBehavior(org.modeldriven.alf.uml.Behavior behavior) {
		this.getBase().setBehavior(
				behavior == null ? null : ((Behavior) behavior).getBase());
	}

}
