package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Action;
import org.modeldriven.uml.fuml.OutputPin;
import org.modeldriven.uml.fuml.ValueSpecification;

public class ValueSpecificationAction extends Action implements
		org.modeldriven.alf.uml.ValueSpecificationAction {
	public ValueSpecificationAction() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction());
	}

	public ValueSpecificationAction(
			fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction) this.base;
	}

	public org.modeldriven.alf.uml.ValueSpecification getValue() {
		return new ValueSpecification(this.getBase().value);
	}

	public void setValue(org.modeldriven.alf.uml.ValueSpecification value) {
		this.getBase().setValue(((ValueSpecification) value).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(((OutputPin) result).getBase());
	}

}
