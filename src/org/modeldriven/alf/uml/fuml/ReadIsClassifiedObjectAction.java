package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Action;
import org.modeldriven.uml.fuml.Classifier;
import org.modeldriven.uml.fuml.InputPin;
import org.modeldriven.uml.fuml.OutputPin;

public class ReadIsClassifiedObjectAction extends Action implements
		org.modeldriven.alf.uml.ReadIsClassifiedObjectAction {
	public ReadIsClassifiedObjectAction() {
		this(
				new fUML.Syntax.Actions.CompleteActions.ReadIsClassifiedObjectAction());
	}

	public ReadIsClassifiedObjectAction(
			fUML.Syntax.Actions.CompleteActions.ReadIsClassifiedObjectAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.CompleteActions.ReadIsClassifiedObjectAction getBase() {
		return (fUML.Syntax.Actions.CompleteActions.ReadIsClassifiedObjectAction) this.base;
	}

	public boolean getIsDirect() {
		return this.getBase().isDirect;
	}

	public void setIsDirect(boolean isDirect) {
		this.getBase().setIsDirect(isDirect);
	}

	public org.modeldriven.alf.uml.Classifier getClassifier() {
		return new Classifier(this.getBase().classifier);
	}

	public void setClassifier(org.modeldriven.alf.uml.Classifier classifier) {
		this.getBase().setClassifier(((Classifier) classifier).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return new InputPin(this.getBase().object);
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(((InputPin) object).getBase());
	}

}
