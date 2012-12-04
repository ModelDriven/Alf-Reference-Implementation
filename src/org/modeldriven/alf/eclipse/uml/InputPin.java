package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class InputPin extends Pin implements org.modeldriven.alf.uml.InputPin {
	public InputPin() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createInputPin());
	}

	public InputPin(org.eclipse.uml2.uml.InputPin base) {
		super(base);
	}

	public org.eclipse.uml2.uml.InputPin getBase() {
		return (org.eclipse.uml2.uml.InputPin) this.base;
	}

}
