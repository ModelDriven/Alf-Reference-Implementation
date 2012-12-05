package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ValueSpecificationAction extends Action implements
		org.modeldriven.alf.uml.ValueSpecificationAction {
	public ValueSpecificationAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createValueSpecificationAction());
	}

	public ValueSpecificationAction(
			org.eclipse.uml2.uml.ValueSpecificationAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ValueSpecificationAction getBase() {
		return (org.eclipse.uml2.uml.ValueSpecificationAction) this.base;
	}

	public org.modeldriven.alf.uml.ValueSpecification getValue() {
		return wrap(this.getBase().getValue());
	}

	public void setValue(org.modeldriven.alf.uml.ValueSpecification value) {
		this.getBase().setValue(
				value == null ? null : ((ValueSpecification) value).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return wrap(this.getBase().getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

}
