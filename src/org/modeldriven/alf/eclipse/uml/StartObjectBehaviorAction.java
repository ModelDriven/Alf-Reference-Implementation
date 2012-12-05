package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class StartObjectBehaviorAction extends CallAction implements
		org.modeldriven.alf.uml.StartObjectBehaviorAction {
	public StartObjectBehaviorAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createStartObjectBehaviorAction());
	}

	public StartObjectBehaviorAction(
			org.eclipse.uml2.uml.StartObjectBehaviorAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.StartObjectBehaviorAction getBase() {
		return (org.eclipse.uml2.uml.StartObjectBehaviorAction) this.base;
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
