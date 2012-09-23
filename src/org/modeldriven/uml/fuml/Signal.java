package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Classifier;
import org.modeldriven.uml.fuml.Property;

public class Signal extends Classifier implements org.modeldriven.alf.uml.Signal {
	public Signal() {
		this(new fUML.Syntax.CommonBehaviors.Communications.Signal());
	}

	public Signal(fUML.Syntax.CommonBehaviors.Communications.Signal base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.Communications.Signal getBase() {
		return (fUML.Syntax.CommonBehaviors.Communications.Signal) this.base;
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
