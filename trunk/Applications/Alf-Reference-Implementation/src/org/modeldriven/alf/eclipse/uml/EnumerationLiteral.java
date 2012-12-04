package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class EnumerationLiteral extends InstanceSpecification implements
		org.modeldriven.alf.uml.EnumerationLiteral {
	public EnumerationLiteral() {
		this(UMLFactory.eINSTANCE.createEnumerationLiteral());
	}

	public EnumerationLiteral(fUML.Syntax.Classes.Kernel.EnumerationLiteral base) {
		super(base);
	}

	public org.eclipse.uml2.uml.EnumerationLiteral getBase() {
		return (org.eclipse.uml2.uml.EnumerationLiteral) this.base;
	}

	public org.modeldriven.alf.uml.Enumeration getEnumeration() {
		return new Enumeration(this.getBase().getEnumeration());
	}

	public org.modeldriven.alf.uml.Enumeration getClassifier() {
		return new Enumeration(this.getBase().getClassifier());
	}

}
