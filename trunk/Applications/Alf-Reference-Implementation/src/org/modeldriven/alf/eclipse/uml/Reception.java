package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Reception extends BehavioralFeature implements
		org.modeldriven.alf.uml.Reception {
	public Reception() {
		this(UMLFactory.eINSTANCE.createReception());
	}

	public Reception(fUML.Syntax.CommonBehaviors.Communications.Reception base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Reception getBase() {
		return (org.eclipse.uml2.uml.Reception) this.base;
	}

	public org.modeldriven.alf.uml.Signal getSignal() {
		return new Signal(this.getBase().getSignal());
	}

	public void setSignal(org.modeldriven.alf.uml.Signal signal) {
		this.getBase().setSignal(
				signal == null ? null : ((Signal) signal).getBase());
	}

}
