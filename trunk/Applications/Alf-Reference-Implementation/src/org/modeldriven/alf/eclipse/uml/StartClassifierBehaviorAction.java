package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class StartClassifierBehaviorAction extends Action implements
		org.modeldriven.alf.uml.StartClassifierBehaviorAction {
	public StartClassifierBehaviorAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createStartClassifierBehaviorAction());
	}

	public StartClassifierBehaviorAction(
			org.eclipse.uml2.uml.StartClassifierBehaviorAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.StartClassifierBehaviorAction getBase() {
		return (org.eclipse.uml2.uml.StartClassifierBehaviorAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return new InputPin(this.getBase().getObject());
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(
				object == null ? null : ((InputPin) object).getBase());
	}

}
