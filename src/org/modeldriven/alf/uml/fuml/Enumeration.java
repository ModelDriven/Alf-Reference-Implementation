package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.DataType;
import org.modeldriven.uml.fuml.EnumerationLiteral;

public class Enumeration extends DataType implements
		org.modeldriven.alf.uml.Enumeration {
	public Enumeration() {
		this(new fUML.Syntax.Classes.Kernel.Enumeration());
	}

	public Enumeration(fUML.Syntax.Classes.Kernel.Enumeration base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Enumeration getBase() {
		return (fUML.Syntax.Classes.Kernel.Enumeration) this.base;
	}

	public List<org.modeldriven.alf.uml.EnumerationLiteral> getOwnedLiteral() {
		List<org.modeldriven.alf.uml.EnumerationLiteral> list = new ArrayList<org.modeldriven.alf.uml.EnumerationLiteral>();
		for (fUML.Syntax.Classes.Kernel.EnumerationLiteral element : this
				.getBase().ownedLiteral) {
			list.add(new EnumerationLiteral(element));
		}
		return list;
	}

	public void addOwnedLiteral(
			org.modeldriven.alf.uml.EnumerationLiteral ownedLiteral) {
		this.getBase().addOwnedLiteral(
				((EnumerationLiteral) ownedLiteral).getBase());
	}

}
