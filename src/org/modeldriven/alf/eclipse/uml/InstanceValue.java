package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class InstanceValue extends ValueSpecification implements
		org.modeldriven.alf.uml.InstanceValue {
	public InstanceValue() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createInstanceValue());
	}

	public InstanceValue(org.eclipse.uml2.uml.InstanceValue base) {
		super(base);
	}

	public org.eclipse.uml2.uml.InstanceValue getBase() {
		return (org.eclipse.uml2.uml.InstanceValue) this.base;
	}

	public org.modeldriven.alf.uml.InstanceSpecification getInstance() {
		return wrap(this.getBase().getInstance());
	}

	public void setInstance(
			org.modeldriven.alf.uml.InstanceSpecification instance) {
		this.getBase().setInstance(
				instance == null ? null : ((InstanceSpecification) instance)
						.getBase());
	}

}
