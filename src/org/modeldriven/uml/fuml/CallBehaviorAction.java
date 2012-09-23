package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Behavior;
import org.modeldriven.uml.fuml.CallAction;

public class CallBehaviorAction extends CallAction implements
		org.modeldriven.alf.uml.CallBehaviorAction {
	public CallBehaviorAction() {
		this(new fUML.Syntax.Actions.BasicActions.CallBehaviorAction());
	}

	public CallBehaviorAction(
			fUML.Syntax.Actions.BasicActions.CallBehaviorAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.CallBehaviorAction getBase() {
		return (fUML.Syntax.Actions.BasicActions.CallBehaviorAction) this.base;
	}

	public org.modeldriven.alf.uml.Behavior getBehavior() {
		return new Behavior(this.getBase().behavior);
	}

	public void setBehavior(org.modeldriven.alf.uml.Behavior behavior) {
		this.getBase().setBehavior(((Behavior) behavior).getBase());
	}

}
