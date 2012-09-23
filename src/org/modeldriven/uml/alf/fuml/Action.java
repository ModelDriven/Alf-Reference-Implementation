package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public class Action extends ExecutableNode implements
		org.modeldriven.alf.uml.Action {

	public Action(fUML.Syntax.Actions.BasicActions.Action base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.Action getBase() {
		return (fUML.Syntax.Actions.BasicActions.Action) this.base;
	}

	public List<org.modeldriven.alf.uml.OutputPin> getOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (fUML.Syntax.Actions.BasicActions.OutputPin element : this
				.getBase().output) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public org.modeldriven.alf.uml.Classifier getContext() {
		return new Classifier(this.getBase().context);
	}

	public List<org.modeldriven.alf.uml.InputPin> getInput() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (fUML.Syntax.Actions.BasicActions.InputPin element : this.getBase().input) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public boolean getIsLocallyReentrant() {
		return this.getBase().isLocallyReentrant;
	}

	public void setIsLocallyReentrant(boolean isLocallyReentrant) {
		this.getBase().setIsLocallyReentrant(isLocallyReentrant);
	}

}
