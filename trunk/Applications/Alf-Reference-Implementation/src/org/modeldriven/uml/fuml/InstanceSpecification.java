package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Classifier;
import org.modeldriven.uml.fuml.NamedElement;
import org.modeldriven.uml.fuml.Slot;

public class InstanceSpecification extends NamedElement implements
		org.modeldriven.alf.uml.InstanceSpecification {
	public InstanceSpecification() {
		this(new fUML.Syntax.Classes.Kernel.InstanceSpecification());
	}

	public InstanceSpecification(
			fUML.Syntax.Classes.Kernel.InstanceSpecification base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.InstanceSpecification getBase() {
		return (fUML.Syntax.Classes.Kernel.InstanceSpecification) this.base;
	}

	public List<org.modeldriven.alf.uml.Classifier> getClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (fUML.Syntax.Classes.Kernel.Classifier element : this.getBase().classifier) {
			list.add(new Classifier(element));
		}
		return list;
	}

	public void addClassifier(org.modeldriven.alf.uml.Classifier classifier) {
		this.getBase().addClassifier(((Classifier) classifier).getBase());
	}

	public List<org.modeldriven.alf.uml.Slot> getSlot() {
		List<org.modeldriven.alf.uml.Slot> list = new ArrayList<org.modeldriven.alf.uml.Slot>();
		for (fUML.Syntax.Classes.Kernel.Slot element : this.getBase().slot) {
			list.add(new Slot(element));
		}
		return list;
	}

	public void addSlot(org.modeldriven.alf.uml.Slot slot) {
		this.getBase().addSlot(((Slot) slot).getBase());
	}

}
