package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Enumeration extends DataType implements
		org.modeldriven.alf.uml.Enumeration {
	public Enumeration() {
		this(UMLFactory.eINSTANCE.createEnumeration());
	}

	public Enumeration(fUML.Syntax.Classes.Kernel.Enumeration base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Enumeration getBase() {
		return (org.eclipse.uml2.uml.Enumeration) this.base;
	}

	public List<org.modeldriven.alf.uml.EnumerationLiteral> getOwnedLiteral() {
		List<org.modeldriven.alf.uml.EnumerationLiteral> list = new ArrayList<org.modeldriven.alf.uml.EnumerationLiteral>();
		for (org.eclipse.uml2.uml.EnumerationLiteral element : this.getBase()
				.getOwnedLiteral()) {
			list.add(new EnumerationLiteral(element));
		}
		return list;
	}

	public void addOwnedLiteral(
			org.modeldriven.alf.uml.EnumerationLiteral ownedLiteral) {
		this.getBase().addOwnedLiteral(
				ownedLiteral == null ? null
						: ((EnumerationLiteral) ownedLiteral).getBase());
	}

}
