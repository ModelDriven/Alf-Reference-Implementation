package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ReadIsClassifiedObjectAction extends Action implements
		org.modeldriven.alf.uml.ReadIsClassifiedObjectAction {
	public ReadIsClassifiedObjectAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createReadIsClassifiedObjectAction());
	}

	public ReadIsClassifiedObjectAction(
			org.eclipse.uml2.uml.ReadIsClassifiedObjectAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ReadIsClassifiedObjectAction getBase() {
		return (org.eclipse.uml2.uml.ReadIsClassifiedObjectAction) this.base;
	}

	public boolean getIsDirect() {
		return this.getBase().isDirect();
	}

	public void setIsDirect(boolean isDirect) {
		this.getBase().setIsDirect(isDirect);
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

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return new InputPin(this.getBase().getObject());
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(
				object == null ? null : ((InputPin) object).getBase());
	}

}
