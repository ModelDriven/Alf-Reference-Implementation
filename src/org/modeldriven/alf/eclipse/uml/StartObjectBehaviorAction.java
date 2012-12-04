package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class StartObjectBehaviorAction extends CallAction implements
		org.modeldriven.alf.uml.StartObjectBehaviorAction {
	public StartObjectBehaviorAction() {
		this(UMLFactory.eINSTANCE.createStartObjectBehaviorAction());
	}

	public StartObjectBehaviorAction(
			fUML.Syntax.Actions.CompleteActions.StartObjectBehaviorAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.StartObjectBehaviorAction getBase() {
		return (org.eclipse.uml2.uml.StartObjectBehaviorAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return new InputPin(this.getBase().getObject());
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(
				object == null ? null : ((InputPin) object).getBase());
	}

}
