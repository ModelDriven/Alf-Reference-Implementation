package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Action;
import org.modeldriven.uml.fuml.InputPin;

public class StartClassifierBehaviorAction extends Action implements
		org.modeldriven.alf.uml.StartClassifierBehaviorAction {
	public StartClassifierBehaviorAction() {
		this(
				new fUML.Syntax.Actions.CompleteActions.StartClassifierBehaviorAction());
	}

	public StartClassifierBehaviorAction(
			fUML.Syntax.Actions.CompleteActions.StartClassifierBehaviorAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.CompleteActions.StartClassifierBehaviorAction getBase() {
		return (fUML.Syntax.Actions.CompleteActions.StartClassifierBehaviorAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return new InputPin(this.getBase().object);
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(((InputPin) object).getBase());
	}

}
