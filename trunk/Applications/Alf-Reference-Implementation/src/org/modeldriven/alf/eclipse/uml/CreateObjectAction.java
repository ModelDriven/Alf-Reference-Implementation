package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class CreateObjectAction extends Action implements
		org.modeldriven.alf.uml.CreateObjectAction {
	public CreateObjectAction() {
		this(UMLFactory.eINSTANCE.createCreateObjectAction());
	}

	public CreateObjectAction(
			fUML.Syntax.Actions.IntermediateActions.CreateObjectAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.CreateObjectAction getBase() {
		return (org.eclipse.uml2.uml.CreateObjectAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.Classifier getClassifier() {
		return new Classifier(this.getBase().getClassifier());
	}

	public void setClassifier(org.modeldriven.alf.uml.Classifier classifier) {
		this.getBase()
				.setClassifier(
						classifier == null ? null : ((Classifier) classifier)
								.getBase());
	}

}
