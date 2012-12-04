package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Signal extends Classifier implements
		org.modeldriven.alf.uml.Signal {
	public Signal() {
		this(UMLFactory.eINSTANCE.createSignal());
	}

	public Signal(fUML.Syntax.CommonBehaviors.Communications.Signal base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Signal getBase() {
		return (org.eclipse.uml2.uml.Signal) this.base;
	}

	public List<org.modeldriven.alf.uml.Property> getOwnedAttribute() {
		List<org.modeldriven.alf.uml.Property> list = new ArrayList<org.modeldriven.alf.uml.Property>();
		for (org.eclipse.uml2.uml.Property element : this.getBase()
				.getOwnedAttribute()) {
			list.add(new Property(element));
		}
		return list;
	}

	public void addOwnedAttribute(
			org.modeldriven.alf.uml.Property ownedAttribute) {
		this.getBase().addOwnedAttribute(
				ownedAttribute == null ? null : ((Property) ownedAttribute)
						.getBase());
	}

}
