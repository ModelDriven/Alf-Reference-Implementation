package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class InstanceSpecification extends NamedElement implements
		org.modeldriven.alf.uml.InstanceSpecification {
	public InstanceSpecification() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createInstanceSpecification());
	}

	public InstanceSpecification(org.eclipse.uml2.uml.InstanceSpecification base) {
		super(base);
	}

	public org.eclipse.uml2.uml.InstanceSpecification getBase() {
		return (org.eclipse.uml2.uml.InstanceSpecification) this.base;
	}

	public List< org.modeldriven.alf.uml.Classifier> getClassifier
() {
		List< org.modeldriven.alf.uml.Classifier> list = new ArrayList< org.modeldriven.alf.uml.Classifier>();
		for (org.eclipse.uml2.uml.Classifier
 element: this.getBase().getClassifier
s()) {
			list.add( new Classifier(element)
);
		}
		return list;
	}

	public void addClassifier
( org.modeldriven.alf.uml.Classifier classifier) {
		this.getBase().getClassifier
s.add( classifier == null? null: ((Classifier)classifier).getBase()
);
	}

	public List< org.modeldriven.alf.uml.Slot> getSlot
() {
		List< org.modeldriven.alf.uml.Slot> list = new ArrayList< org.modeldriven.alf.uml.Slot>();
		for (org.eclipse.uml2.uml.Slot
 element: this.getBase().getSlot
s()) {
			list.add( new Slot(element)
);
		}
		return list;
	}

	public void addSlot
( org.modeldriven.alf.uml.Slot slot) {
		this.getBase().getSlot
s.add( slot == null? null: ((Slot)slot).getBase()
);
	}

}
