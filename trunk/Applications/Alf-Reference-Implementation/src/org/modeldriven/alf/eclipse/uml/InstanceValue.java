package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class InstanceValue extends ValueSpecification implements
		org.modeldriven.alf.uml.InstanceValue {
	public InstanceValue() {
		this(UMLFactory.eINSTANCE.createInstanceValue());
	}

	public InstanceValue(fUML.Syntax.Classes.Kernel.InstanceValue base) {
		super(base);
	}

	public org.eclipse.uml2.uml.InstanceValue getBase() {
		return (org.eclipse.uml2.uml.InstanceValue) this.base;
	}

	public org.modeldriven.alf.uml.InstanceSpecification getInstance() {
		return new InstanceSpecification(this.getBase().getInstance());
	}

	public void setInstance(
			org.modeldriven.alf.uml.InstanceSpecification instance) {
		this.getBase().setInstance(
				instance == null ? null : ((InstanceSpecification) instance)
						.getBase());
	}

}
