package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Element;
import org.modeldriven.uml.fuml.InstanceSpecification;
import org.modeldriven.uml.fuml.StructuralFeature;
import org.modeldriven.uml.fuml.ValueSpecification;

public class Slot extends Element implements org.modeldriven.alf.uml.Slot {
	public Slot() {
		this(new fUML.Syntax.Classes.Kernel.Slot());
	}

	public Slot(fUML.Syntax.Classes.Kernel.Slot base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Slot getBase() {
		return (fUML.Syntax.Classes.Kernel.Slot) this.base;
	}

	public org.modeldriven.alf.uml.InstanceSpecification getOwningInstance() {
		return new InstanceSpecification(this.getBase().owningInstance);
	}

	public org.modeldriven.alf.uml.StructuralFeature getDefiningFeature() {
		return new StructuralFeature(this.getBase().definingFeature);
	}

	public void setDefiningFeature(
			org.modeldriven.alf.uml.StructuralFeature definingFeature) {
		this.getBase().setDefiningFeature(
				((StructuralFeature) definingFeature).getBase());
	}

	public List<org.modeldriven.alf.uml.ValueSpecification> getValue() {
		List<org.modeldriven.alf.uml.ValueSpecification> list = new ArrayList<org.modeldriven.alf.uml.ValueSpecification>();
		for (fUML.Syntax.Classes.Kernel.ValueSpecification element : this
				.getBase().value) {
			list.add(new ValueSpecification(element));
		}
		return list;
	}

	public void addValue(org.modeldriven.alf.uml.ValueSpecification value) {
		this.getBase().addValue(((ValueSpecification) value).getBase());
	}

}
