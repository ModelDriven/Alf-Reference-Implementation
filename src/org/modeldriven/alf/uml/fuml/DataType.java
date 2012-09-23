package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Classifier;
import org.modeldriven.uml.fuml.Property;

public class DataType extends Classifier implements
		org.modeldriven.alf.uml.DataType {
	public DataType() {
		this(new fUML.Syntax.Classes.Kernel.DataType());
	}

	public DataType(fUML.Syntax.Classes.Kernel.DataType base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.DataType getBase() {
		return (fUML.Syntax.Classes.Kernel.DataType) this.base;
	}

	public List<org.modeldriven.alf.uml.Property> getOwnedAttribute() {
		List<org.modeldriven.alf.uml.Property> list = new ArrayList<org.modeldriven.alf.uml.Property>();
		for (fUML.Syntax.Classes.Kernel.Property element : this.getBase().ownedAttribute) {
			list.add(new Property(element));
		}
		return list;
	}

	public void addOwnedAttribute(org.modeldriven.alf.uml.Property ownedAttribute) {
		this.getBase().addOwnedAttribute(((Property) ownedAttribute).getBase());
	}

}
