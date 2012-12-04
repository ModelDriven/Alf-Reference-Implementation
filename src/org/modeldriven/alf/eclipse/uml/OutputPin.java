package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class OutputPin extends Pin implements org.modeldriven.alf.uml.OutputPin {
	public OutputPin() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createOutputPin());
	}

	public OutputPin(org.eclipse.uml2.uml.OutputPin base) {
		super(base);
	}

	public org.eclipse.uml2.uml.OutputPin getBase() {
		return (org.eclipse.uml2.uml.OutputPin) this.base;
	}

}
