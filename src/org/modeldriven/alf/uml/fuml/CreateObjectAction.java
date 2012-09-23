package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Action;
import org.modeldriven.uml.fuml.Classifier;
import org.modeldriven.uml.fuml.OutputPin;

public class CreateObjectAction extends Action implements
		org.modeldriven.alf.uml.CreateObjectAction {
	public CreateObjectAction() {
		this(new fUML.Syntax.Actions.IntermediateActions.CreateObjectAction());
	}

	public CreateObjectAction(
			fUML.Syntax.Actions.IntermediateActions.CreateObjectAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.CreateObjectAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.CreateObjectAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.Classifier getClassifier() {
		return new Classifier(this.getBase().classifier);
	}

	public void setClassifier(org.modeldriven.alf.uml.Classifier classifier) {
		this.getBase().setClassifier(((Classifier) classifier).getBase());
	}

}
