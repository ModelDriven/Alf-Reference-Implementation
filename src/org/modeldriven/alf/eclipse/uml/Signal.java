package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Signal extends Classifier implements
		org.modeldriven.alf.uml.Signal {
	public Signal() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createSignal());
	}

	public Signal(org.eclipse.uml2.uml.Signal base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Signal getBase() {
		return (org.eclipse.uml2.uml.Signal) this.base;
	}

	public List<org.modeldriven.alf.uml.Property> getOwnedAttribute() {
		List<org.modeldriven.alf.uml.Property> list = new ArrayList<org.modeldriven.alf.uml.Property>();
		for (org.eclipse.uml2.uml.Property element : this.getBase()
				.getOwnedAttributes()) {
			list.add(wrap(element));
		}
		return list;
	}

	public void addOwnedAttribute(
			org.modeldriven.alf.uml.Property ownedAttribute) {
		this.getBase().getOwnedAttributes().add(
				ownedAttribute == null ? null : ((Property) ownedAttribute)
						.getBase());
	}

}
