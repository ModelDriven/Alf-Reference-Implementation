package org.modeldriven.uml.alf.fuml;


public class ReadExtentAction extends Action implements
		org.modeldriven.alf.uml.ReadExtentAction {
	public ReadExtentAction() {
		this(new fUML.Syntax.Actions.CompleteActions.ReadExtentAction());
	}

	public ReadExtentAction(
			fUML.Syntax.Actions.CompleteActions.ReadExtentAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.CompleteActions.ReadExtentAction getBase() {
		return (fUML.Syntax.Actions.CompleteActions.ReadExtentAction) this.base;
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
