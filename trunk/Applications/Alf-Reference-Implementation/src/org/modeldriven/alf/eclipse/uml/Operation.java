package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Operation extends BehavioralFeature implements
		org.modeldriven.alf.uml.Operation {
	public Operation() {
		this(UMLFactory.eINSTANCE.createOperation());
	}

	public Operation(fUML.Syntax.Classes.Kernel.Operation base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Operation getBase() {
		return (org.eclipse.uml2.uml.Operation) this.base;
	}

	public boolean getIsQuery() {
		return this.getBase().getIsQuery();
	}

	public void setIsQuery(boolean isQuery) {
		this.getBase().setIsQuery(isQuery);
	}

	public boolean getIsOrdered() {
		return this.getBase().getIsOrdered();
	}

	public boolean getIsUnique() {
		return this.getBase().getIsUnique();
	}

	public int getLower() {
		return this.getBase().getLower();
	}

	public int getUpper() {
		return this.getBase().getUpper();
	}

	public org.modeldriven.alf.uml.Class_ getClass() {
		return new Class_(this.getBase().getClass());
	}

	public List<org.modeldriven.alf.uml.Operation> getRedefinedOperation() {
		List<org.modeldriven.alf.uml.Operation> list = new ArrayList<org.modeldriven.alf.uml.Operation>();
		for (org.eclipse.uml2.uml.Operation element : this.getBase()
				.getRedefinedOperation()) {
			list.add(new Operation(element));
		}
		return list;
	}

	public void addRedefinedOperation(
			org.modeldriven.alf.uml.Operation redefinedOperation) {
		this.getBase().addRedefinedOperation(
				redefinedOperation == null ? null
						: ((Operation) redefinedOperation).getBase());
	}

	public org.modeldriven.alf.uml.Type getType() {
		return new Type(this.getBase().getType());
	}

	public List<org.modeldriven.alf.uml.Parameter> getOwnedParameter() {
		List<org.modeldriven.alf.uml.Parameter> list = new ArrayList<org.modeldriven.alf.uml.Parameter>();
		for (org.eclipse.uml2.uml.Parameter element : this.getBase()
				.getOwnedParameter()) {
			list.add(new Parameter(element));
		}
		return list;
	}

	public void addOwnedParameter(
			org.modeldriven.alf.uml.Parameter ownedParameter) {
		this.getBase().addOwnedParameter(
				ownedParameter == null ? null : ((Parameter) ownedParameter)
						.getBase());
	}

}
