package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LiteralSpecification extends ValueSpecification implements
		org.modeldriven.alf.uml.LiteralSpecification {

	public LiteralSpecification(org.eclipse.uml2.uml.LiteralSpecification base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LiteralSpecification getBase() {
		return (org.eclipse.uml2.uml.LiteralSpecification) this.base;
	}

}
