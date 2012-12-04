package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ValueSpecification extends TypedElement implements
		org.modeldriven.alf.uml.ValueSpecification {

	public ValueSpecification(org.eclipse.uml2.uml.ValueSpecification base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ValueSpecification getBase() {
		return (org.eclipse.uml2.uml.ValueSpecification) this.base;
	}

}
