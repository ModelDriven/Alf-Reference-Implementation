package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.BehavioralFeature;
import org.modeldriven.uml.fuml.Operation;
import org.modeldriven.uml.fuml.Parameter;
import org.modeldriven.uml.fuml.Type;

public class Operation extends BehavioralFeature implements
		org.modeldriven.alf.uml.Operation {
	public Operation() {
		this(new fUML.Syntax.Classes.Kernel.Operation());
	}

	public Operation(fUML.Syntax.Classes.Kernel.Operation base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Operation getBase() {
		return (fUML.Syntax.Classes.Kernel.Operation) this.base;
	}

	public boolean getIsQuery() {
		return this.getBase().isQuery;
	}

	public void setIsQuery(boolean isQuery) {
		this.getBase().setIsQuery(isQuery);
	}

	public boolean getIsOrdered() {
		return this.getBase().isOrdered;
	}

	public boolean getIsUnique() {
		return this.getBase().isUnique;
	}

	public int getLower() {
		return this.getBase().lower;
	}

	public int getUpper() {
		return this.getBase().upper.naturalValue;
	}

	public org.modeldriven.alf.uml.Class getClass_() {
		return new Class(this.getBase().class_);
	}

	public List<org.modeldriven.alf.uml.Operation> getRedefinedOperation() {
		List<org.modeldriven.alf.uml.Operation> list = new ArrayList<org.modeldriven.alf.uml.Operation>();
		for (fUML.Syntax.Classes.Kernel.Operation element : this.getBase().redefinedOperation) {
			list.add(new Operation(element));
		}
		return list;
	}

	public void addRedefinedOperation(
			org.modeldriven.alf.uml.Operation redefinedOperation) {
		this.getBase().addRedefinedOperation(
				((Operation) redefinedOperation).getBase());
	}

	public org.modeldriven.alf.uml.Type getType() {
		return new Type(this.getBase().type);
	}

	public List<org.modeldriven.alf.uml.Parameter> getOwnedParameter() {
		List<org.modeldriven.alf.uml.Parameter> list = new ArrayList<org.modeldriven.alf.uml.Parameter>();
		for (fUML.Syntax.Classes.Kernel.Parameter element : this.getBase().ownedParameter) {
			list.add(new Parameter(element));
		}
		return list;
	}

	public void addOwnedParameter(org.modeldriven.alf.uml.Parameter ownedParameter) {
		this.getBase()
				.addOwnedParameter(((Parameter) ownedParameter).getBase());
	}

    @Override
    public boolean isConstructor() {
        return false;
    }

    @Override
    public boolean isDestructor() {
        return false;
    }

}
