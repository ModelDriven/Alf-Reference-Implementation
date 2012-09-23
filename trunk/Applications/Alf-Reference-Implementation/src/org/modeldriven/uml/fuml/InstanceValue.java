package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.InstanceSpecification;
import org.modeldriven.uml.fuml.ValueSpecification;

public class InstanceValue extends ValueSpecification implements
		org.modeldriven.alf.uml.InstanceValue {
	public InstanceValue() {
		this(new fUML.Syntax.Classes.Kernel.InstanceValue());
	}

	public InstanceValue(fUML.Syntax.Classes.Kernel.InstanceValue base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.InstanceValue getBase() {
		return (fUML.Syntax.Classes.Kernel.InstanceValue) this.base;
	}

	public org.modeldriven.alf.uml.InstanceSpecification getInstance() {
		return new InstanceSpecification(this.getBase().instance);
	}

	public void setInstance(org.modeldriven.alf.uml.InstanceSpecification instance) {
		this.getBase()
				.setInstance(((InstanceSpecification) instance).getBase());
	}

}
