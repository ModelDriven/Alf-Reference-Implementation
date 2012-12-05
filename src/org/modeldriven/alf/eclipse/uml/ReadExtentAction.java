package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ReadExtentAction extends Action implements
		org.modeldriven.alf.uml.ReadExtentAction {
	public ReadExtentAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createReadExtentAction());
	}

	public ReadExtentAction(org.eclipse.uml2.uml.ReadExtentAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ReadExtentAction getBase() {
		return (org.eclipse.uml2.uml.ReadExtentAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return (org.modeldriven.alf.uml.OutputPin) wrap(this.getBase()
				.getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.Classifier getClassifier() {
		return (org.modeldriven.alf.uml.Classifier) wrap(this.getBase()
				.getClassifier());
	}

	public void setClassifier(org.modeldriven.alf.uml.Classifier classifier) {
		this.getBase()
				.setClassifier(
						classifier == null ? null : ((Classifier) classifier)
								.getBase());
	}

}
