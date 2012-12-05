package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Action extends ExecutableNode implements
		org.modeldriven.alf.uml.Action {

	public Action(org.eclipse.uml2.uml.Action base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Action getBase() {
		return (org.eclipse.uml2.uml.Action) this.base;
	}

	public List<org.modeldriven.alf.uml.OutputPin> getOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getOutputs()) {
			list.add(wrap(element));
		}
		return list;
	}

	public org.modeldriven.alf.uml.Classifier getContext() {
		return wrap(this.getBase().getContext());
	}

	public List<org.modeldriven.alf.uml.InputPin> getInput() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (org.eclipse.uml2.uml.InputPin element : this.getBase().getInputs()) {
			list.add(wrap(element));
		}
		return list;
	}

	public boolean getIsLocallyReentrant() {
		return this.getBase().isLocallyReentrant();
	}

	public void setIsLocallyReentrant(boolean isLocallyReentrant) {
		this.getBase().setIsLocallyReentrant(isLocallyReentrant);
	}

}
