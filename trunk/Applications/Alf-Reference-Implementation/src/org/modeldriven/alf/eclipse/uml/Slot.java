package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Slot extends Element implements org.modeldriven.alf.uml.Slot {
	public Slot() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createSlot());
	}

	public Slot(org.eclipse.uml2.uml.Slot base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Slot getBase() {
		return (org.eclipse.uml2.uml.Slot) this.base;
	}

	public org.modeldriven.alf.uml.InstanceSpecification getOwningInstance() {
		return new InstanceSpecification(this.getBase().getOwningInstance());
	}

	public org.modeldriven.alf.uml.StructuralFeature getDefiningFeature() {
		return new StructuralFeature(this.getBase().getDefiningFeature());
	}

	public void setDefiningFeature(
			org.modeldriven.alf.uml.StructuralFeature definingFeature) {
		this.getBase().setDefiningFeature(
				definingFeature == null ? null
						: ((StructuralFeature) definingFeature).getBase());
	}

	public List< org.modeldriven.alf.uml.ValueSpecification> getValue
() {
		List< org.modeldriven.alf.uml.ValueSpecification> list = new ArrayList< org.modeldriven.alf.uml.ValueSpecification>();
		for (org.eclipse.uml2.uml.ValueSpecification
 element: this.getBase().getValue
s()) {
			list.add( new ValueSpecification(element)
);
		}
		return list;
	}

	public void addValue
( org.modeldriven.alf.uml.ValueSpecification value) {
		this.getBase().getValue
s.add( value == null? null: ((ValueSpecification)value).getBase()
);
	}

}
