package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class EnumerationLiteral extends InstanceSpecification implements
		org.modeldriven.alf.uml.EnumerationLiteral {
	public EnumerationLiteral() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createEnumerationLiteral());
	}

	public EnumerationLiteral(org.eclipse.uml2.uml.EnumerationLiteral base) {
		super(base);
	}

	public org.eclipse.uml2.uml.EnumerationLiteral getBase() {
		return (org.eclipse.uml2.uml.EnumerationLiteral) this.base;
	}

	public org.modeldriven.alf.uml.Enumeration getEnumeration() {
		return (org.modeldriven.alf.uml.Enumeration) wrap(this.getBase()
				.getEnumeration());
	}

	public org.modeldriven.alf.uml.Enumeration getClassifier() {
		return (org.modeldriven.alf.uml.Enumeration) wrap(this.getBase()
				.getClassifier());
	}

}
