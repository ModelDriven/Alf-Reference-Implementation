package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class DataType extends Classifier implements
		org.modeldriven.alf.uml.DataType {
	public DataType() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createDataType());
	}

	public DataType(org.eclipse.uml2.uml.DataType base) {
		super(base);
	}

	public org.eclipse.uml2.uml.DataType getBase() {
		return (org.eclipse.uml2.uml.DataType) this.base;
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
