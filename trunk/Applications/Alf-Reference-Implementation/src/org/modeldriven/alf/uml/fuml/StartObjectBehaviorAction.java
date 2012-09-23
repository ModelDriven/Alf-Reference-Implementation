package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.CallAction;
import org.modeldriven.uml.fuml.InputPin;

public class StartObjectBehaviorAction extends CallAction implements
		org.modeldriven.alf.uml.StartObjectBehaviorAction {
	public StartObjectBehaviorAction() {
		this(
				new fUML.Syntax.Actions.CompleteActions.StartObjectBehaviorAction());
	}

	public StartObjectBehaviorAction(
			fUML.Syntax.Actions.CompleteActions.StartObjectBehaviorAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.CompleteActions.StartObjectBehaviorAction getBase() {
		return (fUML.Syntax.Actions.CompleteActions.StartObjectBehaviorAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return new InputPin(this.getBase().object);
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(((InputPin) object).getBase());
	}

}
