package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Action extends ExecutableNode implements
		org.modeldriven.alf.uml.Action {

	public Action(fUML.Syntax.Actions.BasicActions.Action base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Action getBase() {
		return (org.eclipse.uml2.uml.Action) this.base;
	}

	public List<org.modeldriven.alf.uml.OutputPin> getOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getOutput()) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public org.modeldriven.alf.uml.Classifier getContext() {
		return new Classifier(this.getBase().getContext());
	}

	public List<org.modeldriven.alf.uml.InputPin> getInput() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (org.eclipse.uml2.uml.InputPin element : this.getBase().getInput()) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public boolean getIsLocallyReentrant() {
		return this.getBase().getIsLocallyReentrant();
	}

	public void setIsLocallyReentrant(boolean isLocallyReentrant) {
		this.getBase().setIsLocallyReentrant(isLocallyReentrant);
	}

}
